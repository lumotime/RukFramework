package com.j3dream.core.util;

import com.j3dream.core.constant.Constants;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * <p>文件名称: SerializationUtils </p>
 * <p>所属包名: com.lumotime.core.util</p>
 * <p>描述: 对象序列化工具 </p>
 * <p>创建时间: 2020/4/22 16:29 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class SerializationUtils {

    private SerializationUtils() {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORT_CREATE_INSTANTIATE);
    }

    /**
     * 将对象序列化为字节数组
     *
     * @param obj 待序列化对象
     * @return 序列化的字节数组
     * @throws IOException IO 异常
     */
    public static byte[] serialize(Object obj)
            throws IOException {
        ObjectOutputStream oos = null;
        byte[] bytes;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        bytes = bos.toByteArray();
        oos.close();
        return bytes;
    }

    /**
     * 将字节数组反序列化为对象
     *
     * @param objBytes 序列化的字节数组
     * @return 反序列化对象
     * @throws IOException            IO 异常
     * @throws ClassNotFoundException 找不到类型
     */
    public static Object deserialize(byte[] objBytes)
            throws IOException, ClassNotFoundException {
        ObjectInputStream oip = null;
        Object obj;
        oip = new ObjectInputStream(new ByteArrayInputStream(objBytes));
        Object result = oip.readObject();
        obj = result;
        oip.close();
        return obj;
    }

    /**
     * 序列化对象到文件中
     *
     * @param filePath     目标文件路径
     * @param serializable 待写入的对象
     * @return 是否写入到文件中
     */
    public static boolean serialize2File(String filePath, Serializable serializable) {
        return serialize2File(new File(filePath), serializable);
    }

    /**
     * 序列化对象到文件中
     *
     * @param file         目标文件
     * @param serializable 待写入的对象
     * @return 是否写入到文件中
     */
    public static boolean serialize2File(File file, Serializable serializable) {
        if (file == null || !file.exists() || !file.isFile()) {
            FileUtils.createOrExistsFile(file);
        }

        if (serializable == null) {
            return false;
        }

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(serializable);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 从文件中读取序列化对象
     *
     * @param filePath 目标文件路径
     * @param <T>      序列化对象类型
     * @return 存储的对象
     */
    public static <T> T deserializeInFile(String filePath) {
        return deserializeInFile(new File(filePath));
    }

    /**
     * 从文件中读取序列化对象
     *
     * @param file 目标文件
     * @param <T>  序列化对象类型
     * @return 存储的对象
     */
    public static <T> T deserializeInFile(File file) {

        if (file == null || !file.exists() || !file.isFile()) {
            return null;
        }

        Object obj = null;
        try {
            ObjectInput input = new ObjectInputStream(new FileInputStream(file));
            obj = input.readObject();
            input.close();
            return (T) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从文件中读取序列化对象
     *
     * @param is  input stream
     * @param <T> 序列化对象类型
     * @return 存储的对象
     */
    public static <T> T deserializeInInputStream(InputStream is) {

        Object obj = null;
        try {
            ObjectInput input = new ObjectInputStream(is);
            obj = input.readObject();
            input.close();
            return (T) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
