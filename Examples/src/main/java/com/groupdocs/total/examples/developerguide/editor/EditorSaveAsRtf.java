package com.groupdocs.total.examples.developerguide.editor;

import com.groupdocs.editor.EditableDocument;
import com.groupdocs.editor.Editor;
import com.groupdocs.editor.formats.WordProcessingFormats;
import com.groupdocs.editor.options.WordProcessingEditOptions;
import com.groupdocs.editor.options.WordProcessingSaveOptions;
import com.groupdocs.total.examples.ExampleFiles;

public class EditorSaveAsRtf {
    public static void run() throws Exception {
        Editor editor = new Editor(ExampleFiles.path("contract.docx"));

        EditableDocument document = editor.edit(new WordProcessingEditOptions());

        editor.save(document, ExampleFiles.output("editor-save-as-rtf.rtf"),
            new WordProcessingSaveOptions(WordProcessingFormats.Rtf));

        System.out.println("Saved editor-save-as-rtf.rtf");
    }
}
