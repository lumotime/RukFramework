package com.j3dream.core.util;

import com.j3dream.core.constant.Constants;
import com.j3dream.core.constant.RandomConstants;
import com.j3dream.core.constant.TextConstants;

import java.util.Random;
import java.util.UUID;

/**
 * <p>文件名称: RandomUtils </p>
 * <p>所属包名: com.lumotime.core.util</p>
 * <p>描述: 随机值操作工具，用于对随机值的获取 </p>
 * <p>创建时间: 2020/3/13 14:28 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class RandomUtils implements RandomConstants, TextConstants {
    /**
     * 随机方法使用的随机对象(必须不是本地的);随机方法，以便在同一毫秒内不返回相同的值。
     */
    private static final Random RANDOM = new Random();

    private RandomUtils() {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORT_CREATE_INSTANTIATE);
    }

    /**
     * 返回一个随机布尔值
     *
     * @return 随机布尔值
     */
    public static boolean nextBoolean() {
        return RANDOM.nextBoolean();
    }

    /**
     * 创建一个随机字节数组
     *
     * @param count 数组长度
     * @return 随机字节数组
     */
    public static byte[] nextBytes(final int count) {
        int nextCount = 0;
        if (count >= 0) {
            nextCount = count;
        }
        final byte[] result = new byte[nextCount];
        RANDOM.nextBytes(result);
        return result;
    }

    /**
     * 返回0到Integer.MAX_VALUE之间的随机整数
     *
     * @return 0到Integer.MAX_VALUE之间的随机整数
     */
    public static int nextInt() {
        return nextInt(0, Integer.MAX_VALUE);
    }

    /**
     * 返回0到endExclusive之间的随机整数
     *
     * @param endExclusive 可返回的最最大值
     * @return 0到Integer.endExclusive之间的随机整数
     */
    public static int nextInt(final int endExclusive) {
        return nextInt(0, endExclusive);
    }

    /**
     * 返回指定范围内的随机整数
     *
     * @param startInclusive 可返回的最小值
     * @param endExclusive   可返回的最最大值
     * @return 随机整数 如果最小值或最大值为负值将返回 最小值的绝对值
     */
    public static int nextInt(final int startInclusive, final int endExclusive) {
        if (startInclusive < 0 || endExclusive < 0) {
            return Math.abs(startInclusive);
        }

        if (startInclusive == endExclusive) {
            return startInclusive;
        }
        return (Math.min(startInclusive, endExclusive))
                + RANDOM.nextInt(
                Math.max(startInclusive, endExclusive) - Math.min(startInclusive, endExclusive)
        );
    }

    /**
     * 返回0-Long.MAX_VALUE之间的随机长整数
     *
     * @return 0-Long.MAX_VALUE之间的随机长整数
     */
    public static long nextLong() {
        return nextLong(0, Long.MAX_VALUE);
    }

    /**
     * 返回0-endExclusive之间的随机长整数
     *
     * @param endExclusive 可返回的最最大值
     * @return 0-endExclusive之间的随机长整数
     */
    public static long nextLong(final long endExclusive) {
        return nextLong(0, endExclusive);
    }

    /**
     * 返回指定范围内的随机长整数
     *
     * @param startInclusive 可返回的最小值
     * @param endExclusive   可返回的最最大值
     * @return 随机长整数 如果最小值或最大值为负值将返回 最小值的绝对值
     */
    public static long nextLong(final long startInclusive, final long endExclusive) {
        if (startInclusive < 0 || endExclusive < 0) {
            return Math.abs(startInclusive);
        }

        if (startInclusive == endExclusive) {
            return startInclusive;
        }
        return (long) nextDouble(startInclusive, endExclusive);
    }

    /**
     * 返回0-Long.MAX_VALUE之间的随机双浮点数
     *
     * @return 0-Double.MAX_VALUE之间的随机双浮点数
     */
    public static double nextDouble() {
        return nextDouble(0, Double.MAX_VALUE);
    }

    /**
     * 返回0-endInclusive之间的随机双浮点数
     *
     * @param endInclusive 可返回的最最大值
     * @return 0-endInclusive之间的随机双浮点数
     */
    public static double nextDouble(final double endInclusive) {
        return nextDouble(0, endInclusive);
    }

    /**
     * 返回指定范围内的随机双浮点数
     *
     * @param startInclusive 可返回的最小值
     * @param endInclusive   可返回的最最大值
     * @return 随机双浮点数 如果最小值或最大值为负值将返回 最小值的绝对值
     */
    public static double nextDouble(final double startInclusive, final double endInclusive) {
        if (startInclusive < 0 || endInclusive < 0) {
            return Math.abs(startInclusive);
        }

        if (startInclusive == endInclusive) {
            return startInclusive;
        }
        return startInclusive + ((endInclusive - startInclusive) * RANDOM.nextDouble());
    }

    /**
     * 返回0-Long.MAX_VALUE之间的随机单浮点数
     *
     * @return 0-Float.MAX_VALUE之间的随机单浮点数
     */
    public static float nextFloat() {
        return nextFloat(0, Float.MAX_VALUE);
    }

    /**
     * 返回0-endInclusive之间的随机单浮点数
     *
     * @param endInclusive 可返回的最最大值
     * @return 0-endInclusive之间的随机单浮点数
     */
    public static float nextFloat(final float endInclusive) {
        return nextFloat(0, endInclusive);
    }

    /**
     * 返回指定范围内的随机单浮点数
     *
     * @param startInclusive 可返回的最小值
     * @param endInclusive   可返回的最最大值
     * @return 随机单浮点数 如果最小值或最大值为负值将返回 最小值的绝对值
     */
    public static float nextFloat(final float startInclusive, final float endInclusive) {
        if (startInclusive < 0 || endInclusive < 0) {
            return Math.abs(startInclusive);
        }
        if (startInclusive == endInclusive) {
            return startInclusive;
        }
        return startInclusive + ((endInclusive - startInclusive) * RANDOM.nextFloat());
    }

    /**
     * 返回8位规定长度的随机字符串
     *
     * @return 8位规定长度的随机字符串
     */
    public static String nextString() {
        return nextString(DEFAULT_RANDOM_TEXT_LENGTH);
    }

    /**
     * 返回指定长度的随机字符串
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String nextString(int length) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < length; i++) {
            ret.append(DEFAULT_RANDOM_TEXT_SEED_KEY.charAt(nextInt(0, DEFAULT_RANDOM_TEXT_SEED_KEY.length())));
        }
        return ret.toString();
    }

    /**
     * 返回一个随机的UUID字符串（去掉连接符）
     *
     * @return 随机的UUID字符串
     */
    public static String randomUUIDString() {
        return randomUUIDString(true);
    }

    /**
     * 返回一个随机的UUID字符串 ()
     *
     * @param replaced 是否需要去掉连接符
     * @return 随机的UUID字符串
     */
    public static String randomUUIDString(final boolean replaced) {
        UUID uuid = randomUUID();
        String uuidString = uuid == null ? EMPTY : uuid.toString();
        return replaced ? uuidString.replace(UUID_CONNECT_SYMBOL, EMPTY) : uuidString;
    }

    /**
     * 返回一个随机的UUID
     *
     * @return 随机的UUID字符串
     */
    private static UUID randomUUID() {
        return UUID.randomUUID();
    }
}
