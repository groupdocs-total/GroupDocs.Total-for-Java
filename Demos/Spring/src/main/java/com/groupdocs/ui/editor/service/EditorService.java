package com.groupdocs.ui.editor.service;

import com.groupdocs.ui.editor.model.EditDocumentRequest;
import com.groupdocs.ui.editor.model.EditorConfiguration;
import com.groupdocs.ui.model.request.LoadDocumentRequest;
import com.groupdocs.ui.model.response.FileDescriptionEntity;
import com.groupdocs.ui.model.response.LoadDocumentEntity;

import java.util.List;
import java.util.Set;

public interface EditorService {
    List<FileDescriptionEntity> getFileList(String path);

    EditorConfiguration getEditorConfiguration();

    LoadDocumentEntity loadDocument(LoadDocumentRequest loadDocumentRequest);

    /**
     * Get supported formats
     *
     * @return
     */
    Set<String> getSupportedFormats();

    LoadDocumentEntity saveDoc(EditDocumentRequest loadDocumentEntity);
}
