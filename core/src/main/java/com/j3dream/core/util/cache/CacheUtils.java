package com.j3dream.core.util.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>文件名称: CacheUtils </p>
 * <p>所属包名: com.lumotime.core.util.cache</p>
 * <p>描述: 缓存工具类 该类直接使用为二级缓存 第一级内存、第二级文件 </p>
 * <p>创建时间: 2020/3/13 09:32 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 * from: https://github.com/Blankj/AndroidUtilCode
 */
public final class CacheUtils {

    private static final Map<String, CacheUtils> CACHE_MAP = new HashMap<>();

    private final CacheMemoryUtils mCacheMemoryUtils;
    private final CacheDiskUtils mCacheDiskUtils;

    private CacheUtils(CacheMemoryUtils cacheMemoryUtils, CacheDiskUtils cacheUtils) {
        mCacheMemoryUtils = cacheMemoryUtils;
        mCacheDiskUtils = cacheUtils;
    }

    /**
     * Return the single {@link CacheUtils} instance.
     *
     * @return the single {@link CacheUtils} instance
     */
    public static CacheUtils getInstance() {
        return getInstance(CacheMemoryUtils.getInstance(), CacheDiskUtils.getInstance());
    }

    /**
     * Return the single {@link CacheUtils} instance.
     *
     * @param cacheMemoryUtils The instance of {@link CacheMemoryUtils}.
     * @param cacheDiskUtils   The instance of {@link CacheDiskUtils}.
     * @return the single {@link CacheUtils} instance
     */
    public static CacheUtils getInstance(final CacheMemoryUtils cacheMemoryUtils,
                                         final CacheDiskUtils cacheDiskUtils) {
        final String cacheKey = cacheDiskUtils.toString() + "_" + cacheMemoryUtils.toString();
        CacheUtils cache = CACHE_MAP.get(cacheKey);
        if (cache == null) {
            synchronized (CacheUtils.class) {
                cache = CACHE_MAP.get(cacheKey);
                if (cache == null) {
                    cache = new CacheUtils(cacheMemoryUtils, cacheDiskUtils);
                    CACHE_MAP.put(cacheKey, cache);
                }
            }
        }
        return cache;
    }


    ///////////////////////////////////////////////////////////////////////////
    // about bytes
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put bytes in cache.
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public void put(final String key, final byte[] value) {
        put(key, value, -1);
    }

    /**
     * Put bytes in cache.
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public void put(final String key, byte[] value, final int saveTime) {
        mCacheMemoryUtils.put(key, value, saveTime);
        mCacheDiskUtils.put(key, value, saveTime);
    }

    /**
     * Return the bytes in cache.
     *
     * @param key The key of cache.
     * @return the bytes if cache exists or null otherwise
     */
    public byte[] getBytes(final String key) {
        return getBytes(key, null);
    }

    /**
     * Return the bytes in cache.
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the bytes if cache exists or defaultValue otherwise
     */
    public byte[] getBytes(final String key, final byte[] defaultValue) {
        byte[] obj = mCacheMemoryUtils.get(key);
        if (obj != null) return obj;
        return mCacheDiskUtils.getBytes(key, defaultValue);
    }

    ///////////////////////////////////////////////////////////////////////////
    // about String
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put string value in cache.
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public void put(final String key, final String value) {
        put(key, value, -1);
    }

    /**
     * Put string value in cache.
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public void put(final String key, final String value, final int saveTime) {
        mCacheMemoryUtils.put(key, value, saveTime);
        mCacheDiskUtils.put(key, value, saveTime);
    }

    /**
     * Return the string value in cache.
     *
     * @param key The key of cache.
     * @return the string value if cache exists or null otherwise
     */
    public String getString(final String key) {
        return getString(key, null);
    }

    /**
     * Return the string value in cache.
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the string value if cache exists or defaultValue otherwise
     */
    public String getString(final String key, final String defaultValue) {
        String obj = mCacheMemoryUtils.get(key);
        if (obj != null) return obj;
        return mCacheDiskUtils.getString(key, defaultValue);
    }

    ///////////////////////////////////////////////////////////////////////////
    // about Serializable
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put serializable in cache.
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public void put(final String key, final Serializable value) {
        put(key, value, -1);
    }

    /**
     * Put serializable in cache.
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public void put(final String key, final Serializable value, final int saveTime) {
        mCacheMemoryUtils.put(key, value, saveTime);
        mCacheDiskUtils.put(key, value, saveTime);
    }

    /**
     * Return the serializable in cache.
     *
     * @param key The key of cache.
     * @return the bitmap if cache exists or null otherwise
     */
    public Object getSerializable(final String key) {
        return getSerializable(key, null);
    }

    /**
     * Return the serializable in cache.
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the bitmap if cache exists or defaultValue otherwise
     */
    public Object getSerializable(final String key, final Object defaultValue) {
        Object obj = mCacheMemoryUtils.get(key);
        if (obj != null) return obj;
        return mCacheDiskUtils.getSerializable(key, defaultValue);
    }

    /**
     * Return the size of cache in disk.
     *
     * @return the size of cache in disk
     */
    public long getCacheDiskSize() {
        return mCacheDiskUtils.getCacheSize();
    }

    /**
     * Return the count of cache in disk.
     *
     * @return the count of cache in disk
     */
    public int getCacheDiskCount() {
        return mCacheDiskUtils.getCacheCount();
    }

    /**
     * Return the count of cache in memory.
     *
     * @return the count of cache in memory.
     */
    public int getCacheMemoryCount() {
        return mCacheMemoryUtils.getCacheCount();
    }

    /**
     * Remove the cache by key.
     *
     * @param key The key of cache.
     */
    public void remove(String key) {
        mCacheMemoryUtils.remove(key);
        mCacheDiskUtils.remove(key);
    }

    /**
     * Clear all of the cache.
     */
    public void clear() {
        mCacheMemoryUtils.clear();
        mCacheDiskUtils.clear();
    }
}
