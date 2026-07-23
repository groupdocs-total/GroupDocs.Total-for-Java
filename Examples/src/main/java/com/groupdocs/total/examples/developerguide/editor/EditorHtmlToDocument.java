package com.groupdocs.total.examples.developerguide.editor;

import com.groupdocs.editor.EditableDocument;
import com.groupdocs.editor.Editor;
import com.groupdocs.editor.formats.WordProcessingFormats;
import com.groupdocs.editor.options.WordProcessingEditOptions;
import com.groupdocs.editor.options.WordProcessingSaveOptions;

public class EditorHtmlToDocument {
    public static void run() throws Exception {
        Editor editor = new Editor("contract.docx");

        EditableDocument original = editor.edit(new WordProcessingEditOptions());

        // Stand in for the user's edit in a web editor
        String editedHtml = original.getBodyContent()
            .replace("Consulting Services Agreement", "Consulting Services Agreement (revised)");

        EditableDocument edited = EditableDocument.fromMarkup(editedHtml, null);

        editor.save(edited, "editor-html-to-document.docx",
            new WordProcessingSaveOptions(WordProcessingFormats.Docx));

        System.out.println("Saved editor-html-to-document.docx");
    }
}
