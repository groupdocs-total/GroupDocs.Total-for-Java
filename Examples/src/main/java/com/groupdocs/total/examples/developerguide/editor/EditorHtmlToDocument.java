package com.groupdocs.total.examples.developerguide.editor;

import com.groupdocs.editor.EditableDocument;
import com.groupdocs.editor.Editor;
import com.groupdocs.editor.formats.WordProcessingFormats;
import com.groupdocs.editor.options.WordProcessingEditOptions;
import com.groupdocs.editor.options.WordProcessingSaveOptions;
import com.groupdocs.total.examples.ExampleFiles;

public class EditorHtmlToDocument {
    public static void run() throws Exception {
        Editor editor = new Editor(ExampleFiles.path("contract.docx"));

        EditableDocument original = editor.edit(new WordProcessingEditOptions());

        // Stand in for the user's edit in a web editor
        String editedHtml = original.getBodyContent()
            .replace("Consulting Services Agreement", "Consulting Services Agreement (revised)");

        EditableDocument edited = EditableDocument.fromMarkup(editedHtml, null);

        editor.save(edited, ExampleFiles.output("editor-html-to-document.docx"),
            new WordProcessingSaveOptions(WordProcessingFormats.Docx));

        System.out.println("Saved editor-html-to-document.docx");
    }
}
