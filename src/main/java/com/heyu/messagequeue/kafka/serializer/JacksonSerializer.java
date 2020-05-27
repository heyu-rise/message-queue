package com.heyu.messagequeue.kafka.serializer;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Objects;

import org.apache.kafka.common.serialization.Serializer;

import com.heyu.messagequeue.utils.JsonUtil;

/**
 * @author heyu
 * @date 2020/5/27
 */
public class JacksonSerializer<T> implements Serializer<T> {

    private String encoding = "UTF8";

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        String propertyName = isKey ? "key.deserializer.encoding" : "value.deserializer.encoding";
        Object encodingValue = configs.get(propertyName);
        if (encodingValue == null) {
            encodingValue = configs.get("deserializer.encoding");
        }
        if (encodingValue instanceof String) {
            encoding = (String) encodingValue;
        }
    }

    @Override
    public byte[] serialize(String topic, T data) {
        if (data == null){
            return null;
        }
        try {
            return Objects.requireNonNull(JsonUtil.obj2json(data)).getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
