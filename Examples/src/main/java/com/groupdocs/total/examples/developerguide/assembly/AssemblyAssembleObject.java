package com.groupdocs.total.examples.developerguide.assembly;

import com.groupdocs.assembly.DataSourceInfo;
import com.groupdocs.assembly.DocumentAssembler;
import com.groupdocs.total.examples.ExampleFiles;

public class AssemblyAssembleObject {
    public static void run() throws Exception {
        Manager manager = new Manager("John Smith", 37);

        DocumentAssembler assembler = new DocumentAssembler();
        assembler.assembleDocument(
                ExampleFiles.path("Single Row.txt"),
                ExampleFiles.output("assembly-object.txt"),
                new DataSourceInfo(manager, "manager"));

        System.out.println("Saved assembly-object.txt");
    }

    public static final class Manager {
        private final String name;
        private final int age;

        public Manager(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}
