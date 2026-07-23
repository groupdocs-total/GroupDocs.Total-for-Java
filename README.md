# GroupDocs.Total for Java - Code Examples

Runnable code for [GroupDocs.Total for Java](https://products.groupdocs.com/total/java/) — one Maven
artifact containing every GroupDocs document API: viewing, conversion, comparison, signing,
annotation, editing, metadata, search, watermarking, merging, parsing and redaction.

## Examples

[`Examples/`](Examples) holds a small, self-contained class per feature across all 12 bundled
products — each opens a sample file, does one thing, and writes an output. It is the same code
published on the
[GroupDocs.Total for Java developer guide](https://docs.groupdocs.com/total/java/developer-guide/),
and the sample files sit next to the code that uses them, so an example runs with no setup.

```bash
cd Examples

# Run every example
mvn -q compile exec:java

# List every example, then run one on its own
mvn -q compile exec:java -Dexec.args="--list"
mvn -q compile exec:java -Dexec.args="--example ViewerRenderToHtml"
```

The `groupdocs-total` package is served from the
[GroupDocs Java repository](https://releases.groupdocs.com/java/repo/) — declared in
[`Examples/pom.xml`](Examples/pom.xml), it is **not** on Maven Central. See
[`Examples/README.md`](Examples/README.md) for the full walkthrough.

> **`Examples/` is generated, not hand-written.** Every example is extracted from the documentation
> by the `docs-to-code-examples` pipeline, compiled, executed, and its real output published back
> into the [developer guide](https://docs.groupdocs.com/total/java/developer-guide/) page it came
> from. That is what keeps the two in step. **Edit the documentation, not the generated code** — a
> change made here is overwritten on the next run.

## Requirements

- **JDK 8** (or later) and **Apache Maven 3**
- A GroupDocs license is optional: without one the APIs run in evaluation mode and add trial
  watermarks to output. Set the `GROUPDOCS_LIC_PATH` environment variable to a `*.lic` file, or drop
  one next to where you run the examples. You can
  [request a temporary license](https://purchase.groupdocs.com/temporary-license) to remove the
  watermarks.

## All-in-one document automation API

**GroupDocs.Total for Java** is a comprehensive suite of high-performance libraries that integrate
document manipulation into desktop and web applications — no other commercial software required.
Across **PDF, DOCX, PPTX, XLSX** and 90+ other formats it can:

- **View** documents as HTML, PDF or images
- **Convert** between 170+ formats
- **Compare** two versions and accept or reject changes
- **Sign** documents and search or verify signatures
- **Annotate** documents and read the markup back
- **Edit** documents through an HTML round trip
- **Read and edit metadata**, **search** document collections, **watermark**, **merge/split**,
  **parse** text and data, and **redact** sensitive content

> GroupDocs.Assembly, GroupDocs.Markdown and GroupDocs.Classification are separate products and are
> not part of the GroupDocs.Total for Java artifact.

## Individual product examples

Each bundled product also ships its own, deeper example repository:

| Product | Examples | API Reference | Documentation |
|---|---|---|---|
| [Annotation](https://docs.groupdocs.com/annotation/java/) | [Examples](https://github.com/groupdocs-annotation/GroupDocs.Annotation-for-Java) | [API](https://reference.groupdocs.com/annotation/java/) | [Docs](https://docs.groupdocs.com/annotation/java/) |
| [Comparison](https://docs.groupdocs.com/comparison/java/) | [Examples](https://github.com/groupdocs-comparison/GroupDocs.Comparison-for-Java) | [API](https://reference.groupdocs.com/comparison/java/) | [Docs](https://docs.groupdocs.com/comparison/java/) |
| [Conversion](https://docs.groupdocs.com/conversion/java/) | [Examples](https://github.com/groupdocs-conversion/GroupDocs.Conversion-for-Java) | [API](https://reference.groupdocs.com/conversion/java/) | [Docs](https://docs.groupdocs.com/conversion/java/) |
| [Editor](https://docs.groupdocs.com/editor/java/) | [Examples](https://github.com/groupdocs-editor/GroupDocs.Editor-for-Java) | [API](https://reference.groupdocs.com/editor/java/) | [Docs](https://docs.groupdocs.com/editor/java/) |
| [Merger](https://docs.groupdocs.com/merger/java/) | [Examples](https://github.com/groupdocs-merger/GroupDocs.Merger-for-Java) | [API](https://reference.groupdocs.com/merger/java/) | [Docs](https://docs.groupdocs.com/merger/java/) |
| [Metadata](https://docs.groupdocs.com/metadata/java/) | [Examples](https://github.com/groupdocs-metadata/GroupDocs.Metadata-for-Java) | [API](https://reference.groupdocs.com/metadata/java/) | [Docs](https://docs.groupdocs.com/metadata/java/) |
| [Parser](https://docs.groupdocs.com/parser/java/) | [Examples](https://github.com/groupdocs-parser/GroupDocs.Parser-for-Java) | [API](https://reference.groupdocs.com/parser/java/) | [Docs](https://docs.groupdocs.com/parser/java/) |
| [Redaction](https://docs.groupdocs.com/redaction/java/) | [Examples](https://github.com/groupdocs-redaction/GroupDocs.Redaction-for-Java) | [API](https://reference.groupdocs.com/redaction/java/) | [Docs](https://docs.groupdocs.com/redaction/java/) |
| [Search](https://docs.groupdocs.com/search/java/) | [Examples](https://github.com/groupdocs-search/GroupDocs.Search-for-Java) | [API](https://reference.groupdocs.com/search/java/) | [Docs](https://docs.groupdocs.com/search/java/) |
| [Signature](https://docs.groupdocs.com/signature/java/) | [Examples](https://github.com/groupdocs-signature/GroupDocs.Signature-for-Java) | [API](https://reference.groupdocs.com/signature/java/) | [Docs](https://docs.groupdocs.com/signature/java/) |
| [Viewer](https://docs.groupdocs.com/viewer/java/) | [Examples](https://github.com/groupdocs-viewer/GroupDocs.Viewer-for-Java) | [API](https://reference.groupdocs.com/viewer/java/) | [Docs](https://docs.groupdocs.com/viewer/java/) |
| [Watermark](https://docs.groupdocs.com/watermark/java/) | [Examples](https://github.com/groupdocs-watermark/GroupDocs.Watermark-for-Java) | [API](https://reference.groupdocs.com/watermark/java/) | [Docs](https://docs.groupdocs.com/watermark/java/) |

## Resources

- **Product page:** [GroupDocs.Total for Java](https://products.groupdocs.com/total/java/)
- **Documentation:** [docs.groupdocs.com/total/java](https://docs.groupdocs.com/total/java/)
- **Developer guide:** [every product, with runnable examples](https://docs.groupdocs.com/total/java/developer-guide/)
- **API reference:** [reference.groupdocs.com](https://reference.groupdocs.com/)
- **Releases:** [GroupDocs Java repository](https://releases.groupdocs.com/total/java/)
- **Free support:** [Forum](https://forum.groupdocs.com/c/total)
- **Paid support:** [Helpdesk](https://helpdesk.groupdocs.com)
- **Blog:** [GroupDocs.Total](https://blog.groupdocs.com/category/groupdocs-total-product-family)

## License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.
