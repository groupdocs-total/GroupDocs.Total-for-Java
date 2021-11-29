package com.groupdocs.ui.viewer.controller;

import com.groupdocs.ui.config.GlobalConfiguration;
import com.groupdocs.ui.exception.TotalGroupDocsException;
import com.groupdocs.ui.model.response.PageDescriptionEntity;
import com.groupdocs.ui.util.Utils;
import com.groupdocs.ui.viewer.config.ViewerConfiguration;
import com.groupdocs.ui.viewer.model.request.FileTreeRequest;
import com.groupdocs.ui.viewer.model.request.LoadDocumentPageRequest;
import com.groupdocs.ui.viewer.model.request.LoadDocumentRequest;
import com.groupdocs.ui.viewer.model.request.RotateDocumentPagesRequest;
import com.groupdocs.ui.viewer.model.response.FileDescriptionEntity;
import com.groupdocs.ui.viewer.model.response.LoadDocumentEntity;
import com.groupdocs.ui.viewer.model.response.UploadedDocumentEntity;
import com.groupdocs.ui.viewer.service.ViewerService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import static com.groupdocs.ui.util.Utils.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Controller
@RequestMapping(value = "/viewer")
public class ViewerController {

    private static final Logger logger = LoggerFactory.getLogger(ViewerController.class);

    @Autowired
    private GlobalConfiguration globalConfiguration;

    @Autowired
    private ViewerService viewerService;

    /**
     * Get viewer page
     *
     * @param request http request
     * @param model   model data for template
     * @return template name
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getView(HttpServletRequest request, Map<String, Object> model) {
        setLocalPort(request, globalConfiguration.getServer());

        model.put("globalConfiguration", globalConfiguration);

        logger.debug("viewer config: {}", viewerService.getViewerConfiguration());
        model.put("viewerConfiguration", viewerService.getViewerConfiguration());
        return "viewer";
    }

    /**
     * Get files and directories
     *
     * @return files and directories list
     */
    @RequestMapping(method = RequestMethod.POST, value = "/loadFileTree",
            consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<FileDescriptionEntity> loadFileTree(@RequestBody FileTreeRequest fileTreeRequest) {
        return viewerService.getFileList(fileTreeRequest.getPath());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/loadConfig", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ViewerConfiguration loadConfig() {
        return viewerService.getViewerConfiguration();
    }

    /**
     * Get document description
     *
     * @return document description
     */
    @RequestMapping(method = RequestMethod.POST, value = "/loadDocumentDescription", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public LoadDocumentEntity loadDocumentDescription(@RequestBody LoadDocumentRequest loadDocumentRequest) {
        return viewerService.loadDocument(loadDocumentRequest, viewerService.getViewerConfiguration().getPreloadPageCount() == 0, false);
    }

    /**
     * Get all pages for thumbnails
     */
    @RequestMapping(method = RequestMethod.POST, value = "/loadThumbnails", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public LoadDocumentEntity loadThumbnails(@RequestBody LoadDocumentRequest loadDocumentRequest) {
        return viewerService.loadDocument(loadDocumentRequest, true, false);
    }

    /**
     * Get document for printing
     */
    @RequestMapping(method = RequestMethod.POST, value = "/loadPrint", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public LoadDocumentEntity loadPrint(@RequestBody LoadDocumentRequest loadDocumentRequest) {
        return viewerService.loadDocument(loadDocumentRequest, true, true);
    }

    /**
     * Get pdf document for printing
     */
    @RequestMapping(method = RequestMethod.POST, value = "/printPdf", consumes = APPLICATION_JSON_VALUE)
    public void printPdf(@RequestBody LoadDocumentRequest loadDocumentRequest, HttpServletResponse response) {
        String fileName = FilenameUtils.getName(loadDocumentRequest.getGuid());
        String fileExtension = FilenameUtils.getExtension(fileName);
        String pdfFileName = fileName.replace(fileExtension, "pdf");
        InputStream inputStream = viewerService.getPdf(loadDocumentRequest);

        try (OutputStream outputStream = response.getOutputStream()){
            int size = inputStream.available();
            
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader("Content-Length", String.valueOf(size));
    
            IOUtils.copyLarge(inputStream, outputStream);
        } catch (Exception ex){
            logger.error("Exception in opening document", ex);
            throw new TotalGroupDocsException(ex.getMessage(), ex);
        }
    }

    /**
     * Get document page
     *
     * @return document page info
     */
    @RequestMapping(method = RequestMethod.POST, value = "/loadDocumentPage", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public PageDescriptionEntity loadDocumentPage(@RequestBody LoadDocumentPageRequest loadDocumentPageRequest) {
        return viewerService.loadDocumentPage(loadDocumentPageRequest);
    }

    /**
     * Rotate page(s)
     *
     * @return rotated pages list (each object contains page number and rotated angle information)
     */
    @RequestMapping(method = RequestMethod.POST, value = "/rotateDocumentPages", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public PageDescriptionEntity rotateDocumentPages(@RequestBody RotateDocumentPagesRequest rotateDocumentPagesRequest) {
        return viewerService.rotateDocumentPages(rotateDocumentPagesRequest);
    }

    /**
     * Download document
     */
    @RequestMapping(method = RequestMethod.GET, value = "/downloadDocument")
    public void downloadDocument(@RequestParam(name = "path") String documentGuid, HttpServletResponse response) {
        downloadFile(documentGuid, response);
    }

    /**
     * Upload document
     *
     * @return uploaded document object (the object contains uploaded document guid)
     */
    @RequestMapping(method = RequestMethod.POST, value = "/uploadDocument",
            consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public UploadedDocumentEntity uploadDocument(@Nullable @RequestParam("file") MultipartFile content,
                                                 @RequestParam(value = "url", required = false) String url,
                                                 @RequestParam("rewrite") Boolean rewrite) {
        // get documents storage path
        String documentStoragePath = viewerService.getViewerConfiguration().getFilesDirectory();
        // upload the file
        String pathToFile = uploadFile(documentStoragePath, content, url, rewrite);
        // create response data
        UploadedDocumentEntity uploadedDocument = new UploadedDocumentEntity();
        uploadedDocument.setGuid(pathToFile);
        return uploadedDocument;
    }

    /**
     * Rotate page(s)
     *
     * @return rotated pages list (each object contains page number and rotated angle information)
     */
    @RequestMapping(method = RequestMethod.GET, value = "resources/{guid}/{resourceName}")
    @ResponseBody
    public ResponseEntity<byte[]> getResource(@PathVariable String guid, @PathVariable String resourceName) {
        return viewerService.getResource(guid, resourceName);
    }

}