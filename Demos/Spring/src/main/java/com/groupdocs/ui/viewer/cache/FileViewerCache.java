package com.groupdocs.ui.viewer.cache;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.groupdocs.ui.exception.TotalGroupDocsException;
import com.groupdocs.ui.viewer.cache.jackson.JacksonCacheableFactory;
import com.groupdocs.ui.viewer.cache.jackson.model.*;
import com.groupdocs.ui.viewer.exception.DiskAccessException;
import com.groupdocs.ui.viewer.util.ViewerUtils;
import com.groupdocs.viewer.caching.extra.CacheableFactory;
import com.groupdocs.viewer.results.Character;
import com.groupdocs.viewer.results.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileViewerCache implements ViewerCache {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final long WAIT_TIMEOUT = 100L;
    private static final Class<?>[] SERIALIZATION_MODELS = new Class[]{
            ArchiveViewInfoModel.class,
            AttachmentModel.class,
            CadViewInfoModel.class,
            CharacterModel.class,
            FileInfoModel.class,
            LayerModel.class,
            LayoutModel.class,
            LineModel.class,
            LotusNotesViewInfoModel.class,
            OutlookViewInfoModel.class,
            PageModel.class,
            PdfViewInfoModel.class,
            ProjectManagementViewInfoModel.class,
            TextElementModel.class,
            ViewInfoModel.class,
            WordModel.class
    };

    static {
        // Configure mapper
        MAPPER.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        MAPPER.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        SimpleModule module = new SimpleModule(Version.unknownVersion());

        SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
        resolver.addMapping(ArchiveViewInfo.class, ArchiveViewInfoModel.class);
        resolver.addMapping(Attachment.class, AttachmentModel.class);
        resolver.addMapping(CadViewInfo.class, CadViewInfoModel.class);
        resolver.addMapping(Character.class, CharacterModel.class);
        resolver.addMapping(FileInfo.class, FileInfoModel.class);
        resolver.addMapping(Layer.class, LayerModel.class);
        resolver.addMapping(Layout.class, LayoutModel.class);
        resolver.addMapping(Line.class, LineModel.class);
        resolver.addMapping(LotusNotesViewInfo.class, LotusNotesViewInfoModel.class);
        resolver.addMapping(OutlookViewInfo.class, OutlookViewInfoModel.class);
        resolver.addMapping(Page.class, PageModel.class);
        resolver.addMapping(PdfViewInfo.class, PdfViewInfoModel.class);
        resolver.addMapping(ProjectManagementViewInfo.class, ProjectManagementViewInfoModel.class);
        resolver.addMapping(TextElement.class, TextElementModel.class);
        resolver.addMapping(ViewInfo.class, ViewInfoModel.class);
        resolver.addMapping(Word.class, WordModel.class);

        module.setAbstractTypes(resolver);

        MAPPER.registerModule(module);
    }

    /**
     * Gets the Relative or absolute path to the cache folder.
     */
    private Path mCachePath;

    private String mDocumentGuid;

    /**
     * Initializes a new instance of the FileViewerCache class.
     *
     * @param cachePath or absolute path where document cache will be stored.
     */
    public FileViewerCache(Path cachePath, String documentGuid) {
        if (cachePath == null) {
            throw new IllegalArgumentException("cachePath");
        }
        if (documentGuid == null) {
            throw new IllegalArgumentException("cachePath");
        }
        // Configure Viewer to use custom cache models for caching
        CacheableFactory.setInstance(new JacksonCacheableFactory());

        this.mCachePath = cachePath;
        this.mDocumentGuid = documentGuid;
    }

    /**
     * Serializes data to the local disk.
     *
     * @param key   An unique identifier for the cache entry.
     * @param value The object to serialize.
     */
    @Override
    public void set(String key, Object value) {
        if (value == null) {
            return;
        }

        Path filePath = this.getCacheFilePath(key);
        try {
            OutputStream dst = null;
            try {
                if (value instanceof InputStream) {
                    dst = this.getStream(filePath);
                    IOUtils.copy((InputStream) value, dst);

                } else {
                    dst = this.getStream(filePath);
                    MAPPER.writerWithDefaultPrettyPrinter().writeValue(dst, value);
                }
            } finally {
                if (dst != null) {
                    dst.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deserializes data associated with this key if present.
     *
     * @param key A key identifying the requested entry.
     * @return true if the key was found.
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        Path cacheFilePath = this.getCacheFilePath(key);
        if (Files.notExists(cacheFilePath)) {
            return null;
        }
        try (final FileInputStream inputStream = new FileInputStream(cacheFilePath.toFile())) {
            final byte[] bytes = IOUtils.toByteArray(inputStream);
            for (Class<?> clazz : SERIALIZATION_MODELS) {
                try {
                    return (T) MAPPER.readValue(bytes, clazz);
                } catch (JsonParseException | JsonMappingException e) {
                    // continue, is not this type or is stream
                }
            }
            return (T) new ByteArrayInputStream(bytes);
        } catch (IOException e) {
            throw new TotalGroupDocsException("Cache loading error", e);
        }
    }

    @Override
    public List<String> getKeys(String s) {
        Path folderPath = this.getCachePath();
        if (Files.exists(folderPath)) {
            try {
                return Files.list(folderPath)
                        .filter(item -> Files.isDirectory(item))
                        .map(path -> path.getFileName().toString())
                        .collect(Collectors.toList());
            } catch (Exception e) {
                throw new DiskAccessException("list files", folderPath);
            }
        }
        return new ArrayList<>();
    }


    @Override
    public Path getCacheFilePath(String key) {
        Path cachePath = this.getCachePath();

        final String fileSubDir = ViewerUtils.replaceChars(Paths.get(mDocumentGuid).getFileName().toString());
        final Path folderPath = cachePath.resolve(fileSubDir);
        try {
            if (Files.notExists(folderPath)) {
                Files.createDirectories(folderPath);
            }
        } catch (IOException e) {
            throw new DiskAccessException("create cache directory", folderPath);
        }

        return folderPath.resolve(key);
    }

    @Override
    public void clearCache(int pageNumber) {
        // Get folder with cache for current file
        Path filePath = this.getCacheFilePath("");
        try {
            FileUtils.deleteDirectory(filePath.toFile());
        } catch (IOException e) {
            System.err.println("Can't clear cache folder");
            throw new RuntimeException(e);
        }
    }

    private OutputStream getStream(Path path) throws FileNotFoundException, InterruptedException {
        OutputStream stream = null;
        long totalTime = 0;
        long interval = 50;
        while (stream == null) {
            try {
                stream = new FileOutputStream(path.toFile());
            } catch (IOException e) {
                Thread.sleep(50);
                totalTime += interval;

                if (totalTime > WAIT_TIMEOUT) {
                    throw e;
                }
            }
        }

        return stream;
    }

    @Override
    public Path getCachePath() {
        return mCachePath;
    }
}
