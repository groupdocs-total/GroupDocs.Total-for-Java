package com.groupdocs.total.examples.developerguide.editor;

import com.groupdocs.editor.EditableDocument;
import com.groupdocs.editor.Editor;
import com.groupdocs.editor.options.WordProcessingEditOptions;
import com.groupdocs.total.examples.ExampleFiles;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EditorDocumentToHtml {
    public static void run() throws Exception {
        Editor editor = new Editor(ExampleFiles.path("contract.docx"));

        EditableDocument document = editor.edit(new WordProcessingEditOptions());

        String bodyHtml = document.getBodyContent();

        // HTML with inlined images and styles
        Files.write(Paths.get(ExampleFiles.output("editor-document-to-html.html")),
            document.getEmbeddedHtml().getBytes(StandardCharsets.UTF_8));

        System.out.println("Body HTML length: " + bodyHtml.length() + " characters");
    }
}
