package com.j3dream.core.util;

import com.j3dream.core.constant.Constants;
import com.j3dream.core.constant.TextConstants;
import com.j3dream.core.exception.CloneFailedException;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * <p>文件名称: ObjectUtils </p>
 * <p>所属包名: com.lumotime.core.util</p>
 * <p>描述: Object处理工具类 </p>
 * <p>创建时间: 2020/3/13 15:12 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class ObjectUtils implements TextConstants {

    public ObjectUtils() {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORT_CREATE_INSTANTIATE);
    }

    /**
     * 判断内容是否为空
     *
     * @param object 支持的类型:
     *               <ul>
     *               <li>{@link CharSequence}: Considered empty if its length is zero.</li>
     *               <li>{@code Array}: Considered empty if its length is zero.</li>
     *               <li>{@link Collection}: Considered empty if it has zero elements.</li>
     *               <li>{@link Map}: Considered empty if it has zero key-value mappings.</li>
     *               </ul>
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isEmpty(final Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof CharSequence) {
            return ((CharSequence) object).length() == 0;
        }
        if (object.getClass().isArray()) {
            return Array.getLength(object) == 0;
        }
        if (object instanceof Collection<?>) {
            return ((Collection<?>) object).isEmpty();
        }
        if (object instanceof Map<?, ?>) {
            return ((Map<?, ?>) object).isEmpty();
        }
        return false;
    }

    /**
     * 判断内容是否不为空
     *
     * @param object 支持的类型:
     *               <ul>
     *               <li>{@link CharSequence}: Considered empty if its length is zero.</li>
     *               <li>{@code Array}: Considered empty if its length is zero.</li>
     *               <li>{@link Collection}: Considered empty if it has zero elements.</li>
     *               <li>{@link Map}: Considered empty if it has zero key-value mappings.</li>
     *               </ul>
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isNotEmpty(final Object object) {
        return !isEmpty(object);
    }

    /**
     * 判断内容是否为空, 如果为空则返回默认值
     *
     * @param object       支持的类型:
     *                     <ul>
     *                     <li>{@link CharSequence}: Considered empty if its length is zero.</li>
     *                     <li>{@code Array}: Considered empty if its length is zero.</li>
     *                     <li>{@link Collection}: Considered empty if it has zero elements.</li>
     *                     <li>{@link Map}: Considered empty if it has zero key-value mappings.</li>
     *                     </ul>
     * @param defaultValue 默认值
     * @param <T>          类型
     * @return {@code true}: defaultValue<br>{@code false}: object
     */
    public static <T> T defaultIfNull(final T object, final T defaultValue) {
        return object != null ? object : defaultValue;
    }

    /**
     * 返回数组的第一个，如果为空则返回null
     *
     * @param values 支持的类型:
     *               <ul>
     *               <li>{@link CharSequence}: Considered empty if its length is zero.</li>
     *               <li>{@code Array}: Considered empty if its length is zero.</li>
     *               <li>{@link Collection}: Considered empty if it has zero elements.</li>
     *               <li>{@link Map}: Considered empty if it has zero key-value mappings.</li>
     *               </ul>
     * @param <T>    类型
     * @return {@code values[0]} 数组不为空,返回数组0下标的值 {@code null} 数组为空
     */
    @SafeVarargs
    public static <T> T firstNonNull(final T... values) {
        if (values != null) {
            for (final T val : values) {
                if (val != null) {
                    return val;
                }
            }
        }
        return null;
    }

    /**
     * 判断两个对象是否一致
     *
     * @param object1 第一个对象可以是{@code null}
     * @param object2 第二个对象可以是{@code null}
     * @return {@code true} 一致 {@code false} 其他情况
     */
    public static boolean equals(final Object object1, final Object object2) {
        return Objects.equals(object1, object2);
    }

    /**
     * 判断两个对象是否不一致
     *
     * @param object1 第一个对象可以是{@code null}
     * @param object2 第二个对象可以是{@code null}
     * @return {@code true} 不一致 {@code false} 其他情况
     */
    public static boolean notEqual(final Object object1, final Object object2) {
        return !equals(object1, object2);
    }

    /**
     * 获取对象的哈希码，当对象为{@code null}时则为0
     *
     * @param obj 第一个对象可以是{@code null}
     * @return {@code 0} 空对象 {@code hashCode} 对象的Hash码
     */
    public static int hashCode(final Object obj) {
        return Objects.hashCode(obj);
    }

    /**
     * 获取返回的{@code Object}的{@code toString} 如果输入{@code null}，则为空字符串（“”）
     *
     * @param obj 输入对象可以是{@code null}
     * @return 传入的对象的toString，如果输入{@code null}，则返回{@code ""}
     */
    public static String toString(final Object obj) {
        return obj == null ? EMPTY : obj.toString();
    }

    /**
     * 对象克隆
     *
     * @param obj 输入对象
     * @param <T> 输入对象的类型
     * @return 如果对象实现{@link Cloneable}，则为克隆，否则为{@code null}
     */
    public static <T> T clone(final T obj) {
        if (obj instanceof Cloneable) {
            final Object result;
            if (obj.getClass().isArray()) {
                final Class<?> componentType = obj.getClass().getComponentType();
                if (componentType.isPrimitive()) {
                    int length = Array.getLength(obj);
                    result = Array.newInstance(componentType, length);
                    while (length-- > 0) {
                        Array.set(result, length, Array.get(obj, length));
                    }
                } else {
                    result = ((Object[]) obj).clone();
                }
            } else {
                try {
                    final Method clone = obj.getClass().getMethod("clone");
                    result = clone.invoke(obj);
                } catch (final NoSuchMethodException e) {
                    throw new CloneFailedException("Cloneable type "
                            + obj.getClass().getName()
                            + " has no clone method", e);
                } catch (final IllegalAccessException e) {
                    throw new CloneFailedException("Cannot clone Cloneable type "
                            + obj.getClass().getName(), e);
                } catch (final InvocationTargetException e) {
                    throw new CloneFailedException("Exception cloning Cloneable type "
                            + obj.getClass().getName(), e.getCause());
                }
            }
            @SuppressWarnings("unchecked") final T checked = (T) result;
            return checked;
        }
        return null;
    }

    /**
     * 空安全的对象克隆
     *
     * @param obj 输入对象
     * @param <T> 输入对象的类型
     * @return 如果对象实现{@link Cloneable}，则为克隆，否则为{@code null}
     */
    public static <T> T cloneIfPossible(final T obj) {
        final T clone = clone(obj);
        return clone == null ? obj : clone;
    }
}
