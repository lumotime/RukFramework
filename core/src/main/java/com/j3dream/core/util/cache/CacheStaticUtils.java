package com.j3dream.core.util.cache;

import java.io.Serializable;

/**
 * <p>文件名称: CacheStaticUtils </p>
 * <p>所属包名: com.lumotime.core.util.cache</p>
 * <p>描述: 缓存工具类 该类直接使用为二级缓存 第一级内存、第二级文件 </p>
 * <p>创建时间: 2020/3/13 09:32 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 * from: https://github.com/Blankj/AndroidUtilCode
 */
public final class CacheStaticUtils {

    private static CacheUtils sDefaultCacheUtils;

    /**
     * Put bytes in cache.
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(final String key, final byte[] value) {
        put(key, value, getDefaultCacheDoubleUtils());
    }

    /**
     * Put bytes in cache.
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(final String key, byte[] value, final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDoubleUtils());
    }

    /**
     * Return the bytes in cache.
     *
     * @param key The key of cache.
     * @return the bytes if cache exists or null otherwise
     */
    public static byte[] getBytes(final String key) {
        return getBytes(key, getDefaultCacheDoubleUtils());
    }

    /**
     * Return the bytes in cache.
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the bytes if cache exists or defaultValue otherwise
     */
    public static byte[] getBytes(final String key, final byte[] defaultValue) {
        return getBytes(key, defaultValue, getDefaultCacheDoubleUtils());
    }

    /**
     * Put string value in cache.
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(final String key, final String value) {
        put(key, value, getDefaultCacheDoubleUtils());
    }

    ///////////////////////////////////////////////////////////////////////////
    // about String
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put string value in cache.
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(final String key, final String value, final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDoubleUtils());
    }

    /**
     * Return the string value in cache.
     *
     * @param key The key of cache.
     * @return the string value if cache exists or null otherwise
     */
    public static String getString(final String key) {
        return getString(key, getDefaultCacheDoubleUtils());
    }

    /**
     * Return the string value in cache.
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the string value if cache exists or defaultValue otherwise
     */
    public static String getString(final String key, final String defaultValue) {
        return getString(key, defaultValue, getDefaultCacheDoubleUtils());
    }

    /**
     * Put serializable in cache.
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(final String key, final Serializable value) {
        put(key, value, getDefaultCacheDoubleUtils());
    }

    ///////////////////////////////////////////////////////////////////////////
    // about Serializable
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put serializable in cache.
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(final String key, final Serializable value, final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDoubleUtils());
    }

    /**
     * Return the serializable in cache.
     *
     * @param key The key of cache.
     * @return the bitmap if cache exists or null otherwise
     */
    public static Object getSerializable(final String key) {
        return getSerializable(key, getDefaultCacheDoubleUtils());
    }

    /**
     * Return the serializable in cache.
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the bitmap if cache exists or defaultValue otherwise
     */
    public static Object getSerializable(final String key, final Object defaultValue) {
        return getSerializable(key, defaultValue, getDefaultCacheDoubleUtils());
    }

    /**
     * Return the size of cache in disk.
     *
     * @return the size of cache in disk
     */
    public static long getCacheDiskSize() {
        return getCacheDiskSize(getDefaultCacheDoubleUtils());
    }

    /**
     * Return the count of cache in disk.
     *
     * @return the count of cache in disk
     */
    public static int getCacheDiskCount() {
        return getCacheDiskCount(getDefaultCacheDoubleUtils());
    }

    /**
     * Return the count of cache in memory.
     *
     * @return the count of cache in memory.
     */
    public static int getCacheMemoryCount() {
        return getCacheMemoryCount(getDefaultCacheDoubleUtils());
    }

    /**
     * Remove the cache by key.
     *
     * @param key The key of cache.
     */
    public static void remove(String key) {
        remove(key, getDefaultCacheDoubleUtils());
    }

    /**
     * Clear all of the cache.
     */
    public static void clear() {
        clear(getDefaultCacheDoubleUtils());
    }

    /**
     * Put bytes in cache.
     *
     * @param key        The key of cache.
     * @param value      The value of cache.
     * @param cacheUtils The instance of {@link CacheUtils}.
     */
    public static void put(final String key,
                           final byte[] value,
                           final CacheUtils cacheUtils) {
        cacheUtils.put(key, value);
    }

    ///////////////////////////////////////////////////////////////////////////
    // dividing line
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put bytes in cache.
     *
     * @param key        The key of cache.
     * @param value      The value of cache.
     * @param saveTime   The save time of cache, in seconds.
     * @param cacheUtils The instance of {@link CacheUtils}.
     */
    public static void put(final String key,
                           final byte[] value,
                           final int saveTime,
                           final CacheUtils cacheUtils) {
        cacheUtils.put(key, value, saveTime);
    }

    /**
     * Return the bytes in cache.
     *
     * @param key        The key of cache.
     * @param cacheUtils The instance of {@link CacheUtils}.
     * @return the bytes if cache exists or null otherwise
     */
    public static byte[] getBytes(final String key, final CacheUtils cacheUtils) {
        return cacheUtils.getBytes(key);
    }

    /**
     * Return the bytes in cache.
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @param cacheUtils   The instance of {@link CacheUtils}.
     * @return the bytes if cache exists or defaultValue otherwise
     */
    public static byte[] getBytes(final String key,
                                  final byte[] defaultValue,
                                  final CacheUtils cacheUtils) {
        return cacheUtils.getBytes(key, defaultValue);
    }

    /**
     * Put string value in cache.
     *
     * @param key        The key of cache.
     * @param value      The value of cache.
     * @param cacheUtils The instance of {@link CacheUtils}.
     */
    public static void put(final String key,
                           final String value,
                           final CacheUtils cacheUtils) {
        cacheUtils.put(key, value);
    }

    ///////////////////////////////////////////////////////////////////////////
    // about String
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put string value in cache.
     *
     * @param key        The key of cache.
     * @param value      The value of cache.
     * @param saveTime   The save time of cache, in seconds.
     * @param cacheUtils The instance of {@link CacheUtils}.
     */
    public static void put(final String key,
                           final String value,
                           final int saveTime,
                           final CacheUtils cacheUtils) {
        cacheUtils.put(key, value, saveTime);
    }

    /**
     * Return the string value in cache.
     *
     * @param key        The key of cache.
     * @param cacheUtils The instance of {@link CacheUtils}.
     * @return the string value if cache exists or null otherwise
     */
    public static String getString(final String key, final CacheUtils cacheUtils) {
        return cacheUtils.getString(key);
    }

    /**
     * Return the string value in cache.
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @param cacheUtils   The instance of {@link CacheUtils}.
     * @return the string value if cache exists or defaultValue otherwise
     */
    public static String getString(final String key,
                                   final String defaultValue,
                                   final CacheUtils cacheUtils) {
        return cacheUtils.getString(key, defaultValue);
    }

    /**
     * Put serializable in cache.
     *
     * @param key        The key of cache.
     * @param value      The value of cache.
     * @param cacheUtils The instance of {@link CacheUtils}.
     */
    public static void put(final String key,
                           final Serializable value,
                           final CacheUtils cacheUtils) {
        cacheUtils.put(key, value);
    }

    ///////////////////////////////////////////////////////////////////////////
    // about Serializable
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put serializable in cache.
     *
     * @param key        The key of cache.
     * @param value      The value of cache.
     * @param saveTime   The save time of cache, in seconds.
     * @param cacheUtils The instance of {@link CacheUtils}.
     */
    public static void put(final String key,
                           final Serializable value,
                           final int saveTime,
                           final CacheUtils cacheUtils) {
        cacheUtils.put(key, value, saveTime);
    }

    /**
     * Return the serializable in cache.
     *
     * @param key        The key of cache.
     * @param cacheUtils The instance of {@link CacheUtils}.
     * @return the bitmap if cache exists or null otherwise
     */
    public static Object getSerializable(final String key, final CacheUtils cacheUtils) {
        return cacheUtils.getSerializable(key);
    }

    /**
     * Return the serializable in cache.
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @param cacheUtils   The instance of {@link CacheUtils}.
     * @return the bitmap if cache exists or defaultValue otherwise
     */
    public static Object getSerializable(final String key,
                                         final Object defaultValue,
                                         final CacheUtils cacheUtils) {
        return cacheUtils.getSerializable(key, defaultValue);
    }

    /**
     * Return the size of cache in disk.
     *
     * @param cacheUtils The instance of {@link CacheUtils}.
     * @return the size of cache in disk
     */
    public static long getCacheDiskSize(final CacheUtils cacheUtils) {
        return cacheUtils.getCacheDiskSize();
    }

    /**
     * Return the count of cache in disk.
     *
     * @param cacheUtils The instance of {@link CacheUtils}.
     * @return the count of cache in disk
     */
    public static int getCacheDiskCount(final CacheUtils cacheUtils) {
        return cacheUtils.getCacheDiskCount();
    }

    /**
     * Return the count of cache in memory.
     *
     * @param cacheUtils The instance of {@link CacheUtils}.
     * @return the count of cache in memory.
     */
    public static int getCacheMemoryCount(final CacheUtils cacheUtils) {
        return cacheUtils.getCacheMemoryCount();
    }

    /**
     * Remove the cache by key.
     *
     * @param key        The key of cache.
     * @param cacheUtils The instance of {@link CacheUtils}.
     */
    public static void remove(String key, final CacheUtils cacheUtils) {
        cacheUtils.remove(key);
    }

    /**
     * Clear all of the cache.
     *
     * @param cacheUtils The instance of {@link CacheUtils}.
     */
    public static void clear(final CacheUtils cacheUtils) {
        cacheUtils.clear();
    }

    private static CacheUtils getDefaultCacheDoubleUtils() {
        return sDefaultCacheUtils != null ? sDefaultCacheUtils : CacheUtils.getInstance();
    }

    /**
     * Set the default instance of {@link CacheUtils}.
     *
     * @param cacheUtils The default instance of {@link CacheUtils}.
     */
    public static void setDefaultCacheDoubleUtils(final CacheUtils cacheUtils) {
        sDefaultCacheUtils = cacheUtils;
    }
}
