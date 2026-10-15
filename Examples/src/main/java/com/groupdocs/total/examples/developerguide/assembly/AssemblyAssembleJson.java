package com.groupdocs.total.examples.developerguide.assembly;

import com.groupdocs.assembly.DataSourceInfo;
import com.groupdocs.assembly.DocumentAssembler;
import com.groupdocs.assembly.JsonDataSource;
import com.groupdocs.total.examples.ExampleFiles;

public class AssemblyAssembleJson {
    public static void run() throws Exception {
        JsonDataSource managers = new JsonDataSource(ExampleFiles.path("ManagerData.json"));

        DocumentAssembler assembler = new DocumentAssembler();
        assembler.assembleDocument(
                ExampleFiles.path("SimpleDatasetDemo.docx"),
                ExampleFiles.output("assembly-json.docx"),
                new DataSourceInfo(managers, "managers"));

        System.out.println("Saved assembly-json.docx");
    }
}
