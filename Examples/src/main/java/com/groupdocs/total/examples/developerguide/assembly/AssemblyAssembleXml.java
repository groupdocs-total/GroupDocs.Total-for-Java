package com.groupdocs.total.examples.developerguide.assembly;

import com.groupdocs.assembly.DataSourceInfo;
import com.groupdocs.assembly.DocumentAssembler;
import com.groupdocs.assembly.XmlDataSource;
import com.groupdocs.total.examples.ExampleFiles;

public class AssemblyAssembleXml {
    public static void run() throws Exception {
        XmlDataSource managers = new XmlDataSource(ExampleFiles.path("Managers.xml"));

        DocumentAssembler assembler = new DocumentAssembler();
        assembler.assembleDocument(
                ExampleFiles.path("SimpleDatasetDemo.docx"),
                ExampleFiles.output("assembly-xml.docx"),
                new DataSourceInfo(managers, "managers"));

        System.out.println("Saved assembly-xml.docx");
    }
}
