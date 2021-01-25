package com.j3dream.android.common.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * <p>文件名称: StringFormatUtils </p>
 * <p>所属包名: com.j3dream.android.common.util</p>
 * <p>描述:  </p>
 * <p></p>
 * <p>创建时间: 2020/11/10 10:07 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class StringFormatUtils {

    private static final String LINE_SEP = System.getProperty("line.separator");

    /**
     * json 字符串格式化文本
     *
     * @param json 原始 json 字符串
     * @return 格式化后的 json 字符串文本
     */
    public static String formatJson(String json) {
        try {
            for (int i = 0, len = json.length(); i < len; i++) {
                char c = json.charAt(i);
                if (c == '{') {
                    return new JSONObject(json).toString(2);
                } else if (c == '[') {
                    return new JSONArray(json).toString(2);
                } else if (!Character.isWhitespace(c)) {
                    return json;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * xml 字符串格式化文本
     *
     * @param xml 原始xml字符串
     * @return 格式化后的 xml 字符串文本
     */
    public static String formatXml(String xml) {
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(xmlInput, xmlOutput);
            xml = xmlOutput.getWriter().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xml;
    }
}
