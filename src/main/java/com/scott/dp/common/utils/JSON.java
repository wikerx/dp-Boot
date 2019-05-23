package com.scott.dp.common.utils;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 	JSON 可以将对象序列化成为Json 对象<br>
 *  也可以讲 JSON 对象反序列化成JAVA对象，该类是线程安全的<br>
 *  由于加了synchronized 锁控制，存在阻塞可能。不能用于大并发环境
 * 
 * @author SJF
 *
 */
public class JSON{
	private static ObjectMapper objectMapper = new ObjectMapper(); 

	/**
	 * 将对象转换成  JSON 字符串
	 * @param object
	 * @return
	 */
	public static synchronized String toJson(Object object){
		String value = "";
		try {
			 value = objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return value; 
	}
	
	/**
	 * 将 JSON 对象反序列化为 JAVA对象
	 * @param json
	 * @param valueType
	 * @return
	 */
	public static synchronized Object toObject(String json,Class<?>valueType){
		try {
			return objectMapper.readValue(json, valueType);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * 获取集合形式的对象
	 * @param json
	 * @param t @see <br>com.fasterxml.jackson.core.type.TypeReference<br>
	 * 如果要获取User 类型的 数组, 则参数为： new TypeRefernce&lt;List&lt;User&gt;&gt;(){}
	 * @return
	 */
	public static synchronized List<?> toList(String json,TypeReference<?> t){
		try {
			return objectMapper.readValue(json, t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; 
	}
}
