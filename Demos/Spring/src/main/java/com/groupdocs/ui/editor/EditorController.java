package com.groupdocs.ui.editor;

import com.groupdocs.ui.config.GlobalConfiguration;
import com.groupdocs.ui.editor.model.EditDocumentRequest;
import com.groupdocs.ui.editor.model.EditorConfiguration;
import com.groupdocs.ui.editor.service.EditorService;
import com.groupdocs.ui.exception.TotalGroupDocsException;
import com.groupdocs.ui.model.request.FileTreeRequest;
import com.groupdocs.ui.model.request.LoadDocumentRequest;
import com.groupdocs.ui.model.response.FileDescriptionEntity;
import com.groupdocs.ui.model.response.LoadDocumentEntity;
import com.groupdocs.ui.model.response.UploadedDocumentEntity;
import com.groupdocs.ui.util.Utils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.Set;

import static com.groupdocs.ui.util.Utils.setLocalPort;
import static com.groupdocs.ui.util.Utils.uploadFile;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Controller
@RequestMapping(value = "/editor")
public class EditorController {

    private static final Logger logger = LoggerFactory.getLogger(EditorController.class);

    @Autowired
    private GlobalConfiguration globalConfiguration;

    @Autowired
    private EditorService editorService;

    /**
     * Get editor page
     *
     * @param request http request
     * @return template name
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getView(HttpServletRequest request) {
        setLocalPort(request, globalConfiguration.getServer());
        return "editor";
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
        return editorService.getFileList(fileTreeRequest.getPath());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/loadConfig", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public EditorConfiguration loadConfig() {
        return editorService.getEditorConfiguration();
    }

    /**
     * Get document description
     *
     * @return document description
     */
    @RequestMapping(method = RequestMethod.POST, value = "/loadDocumentDescription", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public LoadDocumentEntity loadDocumentDescription(@RequestBody LoadDocumentRequest loadDocumentRequest) {
        return editorService.loadDocument(loadDocumentRequest);
    }

    /**
     * Get supported formats
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/loadFormats", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Set<String> loadFormats() {
        return editorService.getSupportedFormats();
    }

    @RequestMapping(method = RequestMethod.POST, value = "saveFile", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public LoadDocumentEntity saveFile(@RequestBody EditDocumentRequest loadDocumentEntity) {
        return editorService.saveDoc(loadDocumentEntity);
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
        String documentStoragePath = editorService.getEditorConfiguration().getFilesDirectory();
        // upload the file
        String pathToFile = uploadFile(documentStoragePath, content, url, rewrite);
        // create response data
        UploadedDocumentEntity uploadedDocument = new UploadedDocumentEntity();
        uploadedDocument.setGuid(pathToFile);
        return uploadedDocument;
    }

    /**
     * Download document
     */
    @RequestMapping(method = RequestMethod.GET, value = "/downloadDocument")
    public void downloadDocument(@RequestParam(name = "path") String documentGuid, HttpServletResponse response) {
        File file = new File(documentGuid);
        // set response content info
        Utils.addFileDownloadHeaders(response, file.getName(), file.length());
        // download the document
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(documentGuid));
             ServletOutputStream outputStream = response.getOutputStream()) {

            IOUtils.copyLarge(inputStream, outputStream);
        } catch (Exception ex) {
            logger.error("Exception in downloading document", ex);
            throw new TotalGroupDocsException(ex.getMessage(), ex);
        }
    }
}
