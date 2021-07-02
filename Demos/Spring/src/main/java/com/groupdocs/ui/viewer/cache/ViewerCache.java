package com.groupdocs.ui.viewer.cache;

import com.groupdocs.viewer.caching.Cache;

import java.nio.file.Path;

/**
 * Defines methods required for storing rendered document and document resources сache.
 */
public interface ViewerCache extends Cache {
    /**
     * The Relative or absolute path to the cache folder.
     */
    Path getCachePath();

    /**
     * Gets cache file path;.
     *
     * @param key The cache file key.
     * @return Cache file path.
     */
    Path getCacheFilePath(String key);

    void clearCache(int pageNumber);
}
