package com.groupdocs.ui.conversion.service;

import com.groupdocs.conversion.Converter;
import com.groupdocs.conversion.contracts.documentinfo.IDocumentInfo;
import com.groupdocs.conversion.licensing.License;
import com.groupdocs.conversion.options.convert.ConvertOptions;
import com.groupdocs.conversion.options.convert.ImageConvertOptions;
import com.groupdocs.ui.common.config.DefaultDirectories;
import com.groupdocs.ui.common.config.GlobalConfiguration;
import com.groupdocs.ui.common.entity.web.request.FileTreeRequest;
import com.groupdocs.ui.common.exception.TotalGroupDocsException;
import com.groupdocs.ui.conversion.config.ConversionConfiguration;
import com.groupdocs.ui.conversion.filter.DestinationTypesFilter;
import com.groupdocs.ui.conversion.model.request.ConversionPostedData;
import com.groupdocs.ui.conversion.model.response.ConversionTypesEntity;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.groupdocs.ui.common.util.Utils.orderByTypeAndName;

public class ConversionServiceImpl implements ConversionService {

    private static final Logger logger = LoggerFactory.getLogger(ConversionServiceImpl.class);
    private final List<String> supportedImageFormats = Arrays.asList("jp2", "ico", "psd", "svg", "bmp", "jpeg", "jpg", "tiff", "tif", "png", "gif", "emf", "wmf", "dwg", "dicom", "dxf", "jpe", "jfif");
    private final GlobalConfiguration globalConfiguration;
    private final ConversionConfiguration conversionConfiguration;

    public ConversionServiceImpl(GlobalConfiguration globalConfiguration) {
        this.globalConfiguration = globalConfiguration;
        // check files directories
        conversionConfiguration = globalConfiguration.getConversion();
        String resultDirectory = conversionConfiguration.getResultDirectory();
        DefaultDirectories.makeDirs(new File(resultDirectory).toPath());
        // set GroupDocs license
        try {
            License license = new License();
            license.setLicense(globalConfiguration.getApplication().getLicensePath());
        } catch (Throwable exc) {
            logger.error("Can not verify Conversion license!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ConversionTypesEntity> loadFiles(FileTreeRequest fileTreeRequest) {
        String currentPath = fileTreeRequest.getPath();
        if (StringUtils.isEmpty(currentPath)) {
            currentPath = globalConfiguration.getConversion().getFilesDirectory();
        } else {
            currentPath = String.format("%s%s%s", globalConfiguration.getConversion().getFilesDirectory(), File.separator, currentPath);
        }
        File directory = new File(currentPath);
        List<ConversionTypesEntity> fileList = new ArrayList<>();
        File[] files = directory.listFiles();
        List<File> filesList = files != null ? Arrays.asList(files) : Collections.emptyList();
        try {
            // sort list of files and folders
            filesList = orderByTypeAndName(filesList);
            for (File file : filesList) {
                // check if current file/folder is hidden
                if (!file.isHidden()) {
                    ConversionTypesEntity fileDescription = getFileDescriptionEntity(file);
                    // add object to array list
                    fileList.add(fileDescription);
                }
            }
            return fileList;
        } catch (Exception ex) {
            logger.error("Exception occurred while load file tree");
            throw new TotalGroupDocsException(ex.getMessage(), ex);
        }
    }

    @Override
    public ConversionConfiguration getConversionConfiguration() {
        return conversionConfiguration;
    }

    @Override
    public void convert(ConversionPostedData postedData) {
        String destinationType = postedData.getDestinationType();
        String destinationFile = FilenameUtils.removeExtension(FilenameUtils.getName(postedData.getGuid())) + "." + destinationType;
        String resultFileName = FilenameUtils.concat(conversionConfiguration.getResultDirectory(), destinationFile);

        Converter converter = new Converter(postedData.getGuid());
        ConvertOptions<?> convertOptions = converter.getPossibleConversions().getTargetConversion(destinationType).getConvertOptions();
        IDocumentInfo documentInfo = converter.getDocumentInfo();
        if (convertOptions instanceof ImageConvertOptions) {
            ImageConvertOptions imageConvertOptions = (ImageConvertOptions) convertOptions;
            for (int i = 0; i < documentInfo.getPagesCount(); i++) {
                String fileName = FilenameUtils.removeExtension(FilenameUtils.getName(postedData.getGuid())) + "-" + i + "." + destinationType;
                fileName = FilenameUtils.concat(conversionConfiguration.getResultDirectory(), fileName);
                imageConvertOptions.setPageNumber(i + 1);
                imageConvertOptions.setPagesCount(1);
                converter.convert(fileName, convertOptions);
            }
        } else {
            converter.convert(resultFileName, convertOptions);
        }
        converter.dispose();
    }

    @Override
    public String download(String path) throws IOException {
        if (path != null && !path.isEmpty()) {

            String destinationPath = FilenameUtils.concat(conversionConfiguration.getResultDirectory(), path);
            String ext = FilenameUtils.getExtension(destinationPath);
            String fileNameWithoutExt = FilenameUtils.removeExtension(path);
            if (supportedImageFormats.contains(ext)) {
                String zipName = fileNameWithoutExt + ".zip";
                File zipPath = new File(FilenameUtils.concat(conversionConfiguration.getResultDirectory(), zipName));
                File[] files = new File(conversionConfiguration.getResultDirectory()).listFiles((d, name) ->
                        name.endsWith("." + ext) && name.startsWith(fileNameWithoutExt)
                );
                if (files == null) files = new File[0];
                if (zipPath.exists()) {
                    if (!zipPath.delete()) {
                        throw new RuntimeException("Can not delete file " + zipPath);
                    }
                }
                ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipPath));
                for (File filePath : files) {
                    zipOut.putNextEntry(new ZipEntry(filePath.getName()));
                    Files.copy(filePath.toPath(), zipOut);
                }
                zipOut.close();
                destinationPath = zipPath.getAbsolutePath();
            }
            if (new File(destinationPath).exists()) {
                return destinationPath;
            }
        }
        throw new FileNotFoundException();
    }

    /**
     * Create file description
     *
     * @param file file
     * @return file description
     */
    private ConversionTypesEntity getFileDescriptionEntity(File file) {
        ConversionTypesEntity fileDescription = new ConversionTypesEntity();
        // set path to file
        fileDescription.setGuid(file.getAbsolutePath());
        // set file name
        fileDescription.setName(file.getName());
        // set is directory true/false
        fileDescription.setDirectory(file.isDirectory());
        // set file size
        fileDescription.setSize(file.length());

        String ext = FilenameUtils.getExtension(fileDescription.getGuid());
        if (ext != null && !ext.isEmpty()) {
            fileDescription.conversionTypes = new ArrayList<>();
            String[] availableTypes = new DestinationTypesFilter().getPosibleConversions(ext);
            fileDescription.conversionTypes.addAll(Arrays.asList(availableTypes));
        }
        return fileDescription;
    }
}
