package com.groupdocs.total.examples.developerguide.assembly;

import com.groupdocs.assembly.CsvDataLoadOptions;
import com.groupdocs.assembly.CsvDataSource;
import com.groupdocs.assembly.DataSourceInfo;
import com.groupdocs.assembly.DocumentAssembler;
import com.groupdocs.total.examples.ExampleFiles;

public class AssemblyAssembleCsv {
    public static void run() throws Exception {
        CsvDataLoadOptions options = new CsvDataLoadOptions(true);
        CsvDataSource persons = new CsvDataSource(ExampleFiles.path("Person.csv"), options);

        DocumentAssembler assembler = new DocumentAssembler();
        assembler.assembleDocument(
                ExampleFiles.path("CsvDatasetDemo.txt"),
                ExampleFiles.output("assembly-csv.docx"),
                new DataSourceInfo(persons, "persons"));

        System.out.println("Saved assembly-csv.docx");
    }
}
