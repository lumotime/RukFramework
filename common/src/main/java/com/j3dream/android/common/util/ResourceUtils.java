package com.j3dream.android.common.util;

import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.annotation.RawRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.j3dream.android.common.constant.Constants;
import com.j3dream.core.util.IOUtils;
import com.j3dream.core.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * <p>文件名称: ResourceUtils </p>
 * <p>所属包名: com.lumotime.base.util</p>
 * <p>描述: 系统资源工具类 </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/7/14 15:37 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class ResourceUtils {

    private static final int BUFFER_SIZE = 8192;

    private ResourceUtils() {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORT_CREATE_INSTANTIATE);
    }

    public static Drawable getDrawable(@DrawableRes int id) {
        return ContextCompat.getDrawable(Utils.getApp(), id);
    }

    public static int getIdByName(String name) {
        return Utils.getApp().getResources().getIdentifier(name, "id", Utils.getApp().getPackageName());
    }

    public static String getString(@StringRes int id) {
        return Utils.getApp().getResources().getString(id);
    }

    public static String getString(@StringRes int id, Object... formatArgs) {
        return Utils.getApp().getResources().getString(id, formatArgs);
    }

    /**
     * 按名称返回字符串标识符。
     *
     * @param name 字符串名称。
     * @return 按名称的字符串标识符
     */
    public static int getStringIdByName(String name) {
        return Utils.getApp().getResources().getIdentifier(name, "string", Utils.getApp().getPackageName());
    }

    /**
     * 按名称返回颜色标识符
     *
     * @param name 颜色的名称
     * @return 名称的颜色标识符
     */
    public static int getColorIdByName(String name) {
        return Utils.getApp().getResources().getIdentifier(name, "color", Utils.getApp().getPackageName());
    }

    /**
     * 按名称返回尺寸标识符。
     *
     * @param name 尺寸大小的名称
     * @return 尺寸标识符
     */
    public static int getDimenIdByName(String name) {
        return Utils.getApp().getResources().getIdentifier(name, "dimen", Utils.getApp().getPackageName());
    }

    /**
     * 按名称返回可绘制标识符。
     *
     * @param name 可绘制的名称
     * @return 可绘制标识符的名称
     */
    public static int getDrawableIdByName(String name) {
        return Utils.getApp().getResources().getIdentifier(name, "drawable", Utils.getApp().getPackageName());
    }

    /**
     * 按名称返回mipmap标识符。
     *
     * @param name mipmap的名称。
     * @return 按名称的Mipmap标识符
     */
    public static int getMipmapIdByName(String name) {
        return Utils.getApp().getResources().getIdentifier(name, "mipmap", Utils.getApp().getPackageName());
    }

    /**
     * 按名称返回布局标识符。
     *
     * @param name 布局的名称。
     * @return 布局标识符（按名称
     */
    public static int getLayoutIdByName(String name) {
        return Utils.getApp().getResources().getIdentifier(name, "layout", Utils.getApp().getPackageName());
    }

    /**
     * 按名称返回样式标识符
     *
     * @param name 样式的名称。
     * @return 样式标识符（按名称）
     */
    public static int getStyleIdByName(String name) {
        return Utils.getApp().getResources().getIdentifier(name, "style", Utils.getApp().getPackageName());
    }

    /**
     * 按名称返回动画标识符
     *
     * @param name 动画的名称
     * @return 动画标识符（按名称)
     */
    public static int getAnimIdByName(String name) {
        return Utils.getApp().getResources().getIdentifier(name, "anim", Utils.getApp().getPackageName());
    }

    /**
     * 按名称返回菜单标识符.
     *
     * @param name 菜单名称
     * @return 菜单标识符的名称
     */
    public static int getMenuIdByName(String name) {
        return Utils.getApp().getResources().getIdentifier(name, "menu", Utils.getApp().getPackageName());
    }

    public static boolean copyFileFromAssets(final String assetsFilePath, final String destFilePath) {
        boolean res = true;
        try {
            String[] assets = Utils.getApp().getAssets().list(assetsFilePath);
            if (assets != null && assets.length > 0) {
                for (String asset : assets) {
                    res &= copyFileFromAssets(assetsFilePath + "/" + asset, destFilePath + "/" + asset);
                }
            } else {
                res = IOUtils.writeFileFromIS(
                        destFilePath,
                        Utils.getApp().getAssets().open(assetsFilePath)
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }

    public static String readAssets2String(final String assetsFilePath) {
        return readAssets2String(assetsFilePath, null);
    }

    public static String readAssets2String(final String assetsFilePath, final String charsetName) {
        try {
            InputStream is = Utils.getApp().getAssets().open(assetsFilePath);
            byte[] bytes = IOUtils.toByteArray(is);
            if (bytes == null) return "";
            if (StringUtils.isSpace(charsetName)) {
                return new String(bytes);
            } else {
                try {
                    return new String(bytes, charsetName);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return "";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean copyFileFromRaw(@RawRes final int resId, final String destFilePath) {
        return IOUtils.writeFileFromIS(
                destFilePath,
                Utils.getApp().getResources().openRawResource(resId)
        );
    }

    public static String readRaw2String(@RawRes final int resId) {
        return readRaw2String(resId, null);
    }

    public static String readRaw2String(@RawRes final int resId, final String charsetName) {
        InputStream is = Utils.getApp().getResources().openRawResource(resId);
        byte[] bytes = new byte[0];
        try {
            bytes = IOUtils.toByteArray(is);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        if (bytes == null) return null;
        if (StringUtils.isSpace(charsetName)) {
            return new String(bytes);
        } else {
            try {
                return new String(bytes, charsetName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
            }
        }
    }
}
