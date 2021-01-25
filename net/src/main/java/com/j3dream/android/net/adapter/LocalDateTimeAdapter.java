package com.j3dream.android.net.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.j3dream.core.util.TimeDateUtils;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>文件名称: LocalDateTimeAdapter </p>
 * <p>所属包名: com.lumotime.net.adapter</p>
 * <p>描述: Gson String <==> LocalDataTime </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/8/21 14:11 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class LocalDateTimeAdapter implements JsonSerializer<Calendar>, JsonDeserializer<Calendar> {

    @Override
    public Calendar deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String asString = jsonElement.getAsJsonPrimitive().getAsString();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = fmt.parse(asString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JsonElement serialize(Calendar src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(TimeDateUtils.timestamp2FormatTimeString(src.getTimeInMillis(),
                "yyyy-MM-dd HH:mm:ss"));
    }
}
