# AGENTS.md

Guidance for AI agents (and people) working in the GroupDocs.Total for Java examples.

## What this is

Runnable Java examples for GroupDocs.Total for Java.

- Docs: https://docs.groupdocs.com/total/java/
- Package: `com.groupdocs:groupdocs-total`
- Repository: https://releases.groupdocs.com/java/repo/
- `Examples/pom.xml` pins `groupdocs-total` at 26.10

`Examples/` is generated from the documentation. Edit the docs, not the generated classes. A change here is overwritten on the next docs-to-code run.

## Commands

From `Examples/`:

```bash
mvn -q compile exec:java
mvn -q compile exec:java -Dexec.args="--list"
mvn -q compile exec:java -Dexec.args="--example ViewerRenderToHtml"
```

## Requirements

- JDK 8 or later
- Apache Maven 3
- A license is optional. Without one the APIs add trial watermarks. Temporary license: https://purchase.groupdocs.com/temporary-license

## Do not

- Invent a format count.
- Pin the dependency to `LATEST`.
- Edit Cloud product repositories or the MCP server from this repo.
