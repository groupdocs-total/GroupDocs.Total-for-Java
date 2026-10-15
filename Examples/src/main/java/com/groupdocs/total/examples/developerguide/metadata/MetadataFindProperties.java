package com.groupdocs.total.examples.developerguide.metadata;

import com.groupdocs.metadata.Metadata;
import com.groupdocs.metadata.core.MetadataProperty;
import com.groupdocs.metadata.search.ContainsTagSpecification;
import com.groupdocs.metadata.tagging.Tags;
import com.groupdocs.total.examples.ExampleFiles;

public class MetadataFindProperties {
    public static void run() throws Exception {
        try (Metadata metadata = new Metadata(ExampleFiles.path("contract.docx"))) {
            // Properties tagged as creator
            Iterable<MetadataProperty> properties = metadata.findProperties(
                new ContainsTagSpecification(Tags.getPerson().getCreator()));

            for (MetadataProperty property : properties) {
                System.out.println(property.getName() + " = " + property.getValue());
            }
        }
    }
}
