package com.groupdocs.ui.viewer.cache;

import java.nio.file.Path;

/**
 * Defines methods required for storing rendered document and document resources сache.
 */
public interface ViewerCache {
    /**
     * The Relative or absolute path to the cache folder.
     */
    Path getCachePath();

    /**
     * The sub-folder to append to the CachePath.
     */
    String getCacheSubFolder();

    /**
     * Inserts a cache entry into the cache.
     *
     * @param key   A unique identifier for the cache entry.
     * @param value The object to insert.
     */
    <T> void set(String key, T value);

    /**
     * Gets the entry associated with this key if present.
     *
     * @param key A key identifying the requested entry.
     * @return true if the key was found.
     */
    <T> T get(String key, T defaultEntry, Class<?>[] clazzs);

    /**
     * Gets cache file path;.
     *
     * @param key The cache file key.
     * @return Cache file path.
     */
    Path getCacheFilePath(String key);

    boolean doesNotContains(String key);

    void clearCache(int pageNumber);
}
