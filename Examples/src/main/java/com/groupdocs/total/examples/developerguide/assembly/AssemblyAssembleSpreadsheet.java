package com.groupdocs.total.examples.developerguide.assembly;

import com.groupdocs.assembly.DataSourceInfo;
import com.groupdocs.assembly.DocumentAssembler;
import com.groupdocs.assembly.DocumentTable;
import com.groupdocs.assembly.DocumentTableOptions;
import com.groupdocs.total.examples.ExampleFiles;

public class AssemblyAssembleSpreadsheet {
    public static void run() throws Exception {
        DocumentTableOptions options = new DocumentTableOptions();
        options.setFirstRowContainsColumnNames(true);

        DocumentTable contracts = new DocumentTable(ExampleFiles.path("Contracts Data.xlsx"), 0, options);

        DocumentAssembler assembler = new DocumentAssembler();
        assembler.assembleDocument(
                ExampleFiles.path("Using Spreadsheet as Table of Data.docx"),
                ExampleFiles.output("assembly-spreadsheet.docx"),
                new DataSourceInfo(contracts, "contracts"));

        System.out.println("Saved assembly-spreadsheet.docx");
    }
}
