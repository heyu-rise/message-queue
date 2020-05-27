package com.heyu.messagequeue.kafka.serializer;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.heyu.messagequeue.utils.JsonUtil;

/**
 * @author heyu
 * @date 2020/5/27
 */
public class JacksonDeserializer implements Deserializer<Object> {

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
	public Object deserialize(String topic, byte[] data) {
		try {
			if (data == null) {
				return null;
			} else {
				String str = new String(data, encoding);
				return JsonUtil.json2obj(str, Object.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
//			throw new SerializationException(
//					"Error when deserializing byte[] to string due to unsupported encoding " + encoding);
		}
	}

//	public Class<T> getType() {
//		Type genType = getClass().getGenericSuperclass();
//		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
//		return (Class) params[0];
//	}

}
