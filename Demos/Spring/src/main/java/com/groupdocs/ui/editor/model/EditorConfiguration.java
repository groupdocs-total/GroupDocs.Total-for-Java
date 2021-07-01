package com.groupdocs.ui.editor.model;

import com.groupdocs.ui.config.CommonConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

import static com.groupdocs.ui.config.DefaultDirectories.defaultEditorDirectory;
import static com.groupdocs.ui.config.DefaultDirectories.relativePathToAbsolute;

@Component
public class EditorConfiguration extends CommonConfiguration {

    @Value("${editor.filesDirectory}")
    private String filesDirectory;

    @Value("${editor.fontsDirectory}")
    private String fontsDirectory;

    @Value("${editor.defaultDocument}")
    private String defaultDocument;

    @Value("#{new Boolean('${editor.createNewFile}')}")
    private Boolean createNewFile;

    @PostConstruct
    public void init() {
        this.filesDirectory = StringUtils.isEmpty(this.filesDirectory) ? defaultEditorDirectory() : relativePathToAbsolute(this.filesDirectory);
    }

    public String getFilesDirectory() {
        return filesDirectory;
    }

    public void setFilesDirectory(String filesDirectory) {
        this.filesDirectory = filesDirectory;
    }

    public String getFontsDirectory() {
        return fontsDirectory;
    }

    public void setFontsDirectory(String fontsDirectory) {
        this.fontsDirectory = fontsDirectory;
    }

    public String getDefaultDocument() {
        return defaultDocument;
    }

    public void setDefaultDocument(String defaultDocument) {
        this.defaultDocument = defaultDocument;
    }

    public Boolean getCreateNewFile() {
        return createNewFile;
    }

    public void setCreateNewFile(Boolean createNewFile) {
        this.createNewFile = createNewFile;
    }

    @Override
    public String toString() {
        return "EditorConfiguration{" +
                "filesDirectory='" + filesDirectory + '\'' +
                ", fontsDirectory='" + fontsDirectory + '\'' +
                ", defaultDocument='" + defaultDocument + '\'' +
                ", createNewFile=" + createNewFile +
                '}';
    }
}
