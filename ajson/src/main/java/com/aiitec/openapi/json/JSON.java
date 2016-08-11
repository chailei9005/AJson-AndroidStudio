
package com.aiitec.openapi.json;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.aiitec.openapi.ann.JSONField;
import com.aiitec.openapi.enums.AIIAction;
import com.aiitec.openapi.enums.CombinationType;
import com.aiitec.openapi.utils.LogRecordUtils;
import com.aiitec.openapi.utils.PacketUtil;
/**
 * 这个类是处理json和JavaBean的总类， 主要有：<br>JSON.toJsonString(t)<br>JSON.parseObject(jsonString, Entity.class)<br>以及不同参数的重载方法
 * @author Anthony <br>
 * createTime 2016-1-29
 */
public class JSON {

    /**
     * 组包模式，有两种，一种是正常模式NORMAL， 一种是AII_STYLE模式，这种模式的主要是数组的组包多了一层key
     */
    public static CombinationType combinationType = CombinationType.AII_STYLE;
    /**是否对密码加盐*/
	public static boolean saltingPassword = true;

    private static String getStringFromArray(List<?> list, CombinationType combinationType)
            throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, JSONException {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < list.size(); i++) {
            // PacketUtil.isCommonField(clazz) //我写的是否是常用数据类型包括String
            // list.get(i).getClass().isPrimitive();
            // java自带的是否是常用数据类型，但是不包括String
            if (PacketUtil.isCommonField(list.get(i).getClass())) {
                sb.append("\"").append(list.get(i)).append("\"");
            } else {
                String value = toJsonStringFromParent(list.get(i).getClass(),
                        list.get(i), combinationType);
                sb.append(value);
            }
            if (i != list.size() - 1) {
                sb.append(',');
            }
        }
        sb.append(']');
        return sb.toString();
    }
    public static <T> String toJsonString(T t)  throws IllegalAccessException,
    IllegalArgumentException, InvocationTargetException,
    NoSuchMethodException, JSONException{
    	return toJsonString(t, JSON.combinationType);
    }
    /**
     * 把对象组包成json
     * @param t 
     * @return json格式的字符串
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws JSONException
     */
    public static <T> String toJsonString(T t, CombinationType combinationType) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, JSONException {
        String requestData = "";
        if (t.getClass().equals(List.class)) {

            List<?> list = (List<?>) t;
            requestData = getStringFromArray(list, combinationType);

        } else if (t.getClass().equals(ArrayList.class)) {

            ArrayList<?> list = (ArrayList<?>) t;
            requestData = getStringFromArray(list, combinationType);

        } else {
            requestData = toJsonStringFromParent(t.getClass(), t, combinationType);
        }
        return requestData;
    }


    /**
     * 遍历所有当前类和父类、祖宗类的所有变量，进行组包，组成json格式的String
     * 
     * @param clazz
     * @param t
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws JSONException
     */
    private static <T> String toJsonStringFromParent(Class<?> clazz, T t, CombinationType combinationType)
            throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, JSONException {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
      //必须不是枚举，被枚举坑过，很惨
        if (clazz != null && !PacketUtil.isCommonField(clazz) && !Enum.class.isAssignableFrom(clazz)) {

            List<Field> fields = CombinationUtil.getAllFields(clazz);
            for (Field field : fields) {
            	
                Combination.appendJsonData(t, clazz, field, sb, combinationType);
            }
            if (sb.toString().endsWith(",")) {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        sb.append('}');
        return sb.toString();
    }

    /**
     * 把json的值传到对象里
     * 
     * @param clazz
     *            对象的类型
     * @param field
     *            属性字段
     * @param t
     *            对象
     * @param json
     *            json数据
     * @throws JSONException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws InstantiationException 
     * @throws Exception
     *             抛出异常
     */
    private static <T> void setValuesFromDictination(Class<?> clazz,
            Field field, T t, JSONObject json, CombinationType combinationType) throws  IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException, JSONException {
    	

        String fieldName = null;
        String entityName = null;
        boolean isEnum = false;

        // 获取字段的注解
        JSONField annotation = field.getAnnotation(JSONField.class);
        if (annotation != null) {
            fieldName = annotation.name();
            entityName = annotation.entityName();
            isEnum = annotation.isEnum();
        }
        if (fieldName == null || fieldName.equals("")) {
            fieldName = field.getName();
        }

        if (json.has(fieldName)) {
            if (isEnum) {
                if (field.getType() == AIIAction.class) {
                	int value = json.optInt(fieldName, 0);
                	field.setAccessible(true);
                	field.set(t, AIIAction.valueOf(value));
                }
            } else {
                // 如果是常用数据类型
                if (PacketUtil.isCommonField(field.getType())) {
                    DesCombination.setValueToAttribute(field, clazz, json, t,
                            fieldName);
                }
                // 如果是数组
                else if (List.class.isAssignableFrom(field.getType())) {
                    try {
                    	ParameterizedType type = (ParameterizedType) field.getGenericType();                    		
                    	Class<?> childClass = null;
                    	if (type.getActualTypeArguments() != null
                    			&& type.getActualTypeArguments().length > 0) {
                    		childClass = (Class<?>) type.getActualTypeArguments()[0];
                    		DesCombination.desCombinationArray(json, t, clazz,
                    				childClass, field, fieldName, entityName, combinationType);
                    	}
                    } catch (ClassCastException e) {
                    	e.printStackTrace();
                    	LogRecordUtils.record("ClassCastException"+field.getGenericType()+"------  字段"+field.getName()+" ---->   json:"+json);
                    	
                    }
                }
                // 如果是对象
                else {
                	if(!TextUtils.isEmpty(json.optString(fieldName))){
                		try {							
                			Object entity = JSON.parseObject(json.optString(fieldName), field.getType());
                			if(entity != null){
                				field.setAccessible(true);
                				field.set(t, entity);                				
                			}
						} catch (JSONException e) {
							e.printStackTrace();
							LogRecordUtils.record("json解析异常 ：字段"+fieldName +"-->"+json+"----"+field.getType());
						}
                	}
                }

            }
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> setArrayValuesFromDictination(
            Class<T> entityClazz, JSONArray array, CombinationType combinationType) throws JSONException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        List<T> list = new ArrayList<T>();

        for (int i = 0; i < array.length(); i++) {
            if (entityClazz != null && !PacketUtil.isCommonField(entityClazz) && !Enum.class.isAssignableFrom(entityClazz)) {
                List<Field> fields = CombinationUtil.getAllFields(entityClazz);
                T entity = null;
                try {
                    entity = entityClazz.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (Field field : fields) {

                    JSONObject obj = array.getJSONObject(i);
                    setValuesFromDictination(entityClazz, field, entity, obj, combinationType);
                }
                list.add(entity);

            } else {
                list.add((T) array.get(i));

            }
        }
        return list;
    }

  
    private static <T> T valueFromDictionaryFromParent(Class<?> clazz,
            JSONObject json, T t, CombinationType combinationType) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, JSONException, InstantiationException  {
        List<Field> fields = CombinationUtil.getAllFields(clazz);
        for (Field field : fields) {
            setValuesFromDictination(clazz, field, t, json, combinationType);
        }
        return t;
    }

    public static <T> T parseObject(String json, Class<T> clazz, CombinationType combinationType) throws JSONException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        JSONObject js = new JSONObject(json);
        return parseObject(js, clazz, combinationType);
    }

    public static <T> T parseObject(JSONObject json, Class<T> clazz, CombinationType combinationType) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, JSONException
           {
        T t = clazz.newInstance();
        valueFromDictionaryFromParent(clazz, json, t, combinationType);
        return t;
    }
    public static <T> T parseObject(String json, Class<T> clazz) throws JSONException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
    	JSONObject js = new JSONObject(json);
    	return parseObject(js, clazz, JSON.combinationType);
    }
    
    public static <T> T parseObject(JSONObject json, Class<T> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, JSONException
    {
    	T t = clazz.newInstance();
    	valueFromDictionaryFromParent(clazz, json, t, JSON.combinationType);
    	return t;
    }

//    public static <T> T parseObject(JSONObject json, T t) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException, JSONException {
//        valueFromDictionaryFromParent(t.getClass(), json, t);
//        return t;
//    }
//
//    public static <T> T parseObject(String json, T t) throws JSONException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException{
//        JSONObject js = new JSONObject(json);
//        parseObject(js, t);
//        return t;
//    }
    /**
     * 把JSON组成List集合
     * @param json json字符串
     * @param entityClazz 集合的子选项类 比如要转换成List<User> 那么就得传User.class， 而不是List.class 
     * @return 转换后的List
     * @throws Exception
     */
    public static <T> List<T> parseArray(String json, Class<T> entityClazz)
            throws Exception {
        JSONArray array = new JSONArray(json);
        List<T> list = null;
        list = setArrayValuesFromDictination(entityClazz, array, JSON.combinationType);
        return list;
    }

}
