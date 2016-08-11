package com.aiitec.openapi.json;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.aiitec.openapi.ann.JSONField;
import com.aiitec.openapi.enums.AIIAction;
import com.aiitec.openapi.enums.CombinationType;
import com.aiitec.openapi.utils.AiiUtil;
import com.aiitec.openapi.utils.PacketUtil;

/***
 * 组包类
 * 
 * @author Anthony
 * 
 */
public class Combination {

    /**
     * 
     * @param field
     * @param result
     * @param sb
     * @param replceName
     * @param replaceSpecialChar
     * @throws JSONException
     */
    private static void appendValueToJson(Field field, Object result,
            StringBuilder sb, String replceName) throws JSONException {

        if (field.getType() == int.class || field.getType() == float.class
                || field.getType() == double.class
                || field.getType() == long.class) {
            if (!result.toString().equalsIgnoreCase("-1")
                    && !result.toString().equalsIgnoreCase("-1.0")) {// 如果值不是-1
                String result2 = AiiUtil.formatString(result);
                if (replceName == null || replceName.equals("")) {
                    sb.append('\"').append(field.getName());
                } else {
                    sb.append('\"').append(replceName);
                }
                sb.append('\"').append(':').append(result2).append(',');
            }
        } else if (field.getType() == boolean.class) {
            if (result.toString().equalsIgnoreCase("true")) {// 如果值是true
                if (replceName == null || replceName.equals("")) {
                    sb.append('\"').append(field.getName());
                } else {
                    sb.append('\"').append(replceName);
                }
                sb.append('\"').append(':').append("1").append(',');
            } else if (result.toString().equalsIgnoreCase("false")) {// 如果值是true
                if (replceName == null || replceName.equals("")) {
                    sb.append('\"').append(field.getName());
                } else {
                    sb.append('\"').append(replceName);
                }
                sb.append('\"').append(':').append("0").append(',');
            }
        } else if (List.class.isAssignableFrom(field.getType() )) {
            @SuppressWarnings("unchecked")
            ArrayList<Object> array = (ArrayList<Object>) result;
            sb.append('\"');
            if (replceName == null || replceName.equals("")) {
                sb.append(field.getName());
            } else {
                sb.append(replceName);
            }
            sb.append('\"').append(':');
            sb.append('[');
            for (int i = 0; i < array.size(); i++) {
            	if(array.get(i) != null){
            		String result2 = stringToJson(array.get(i).toString());
            		sb.append(result2);
            		sb.append(',');
            		
            	}
            }
            if (sb.charAt(sb.toString().length() - 1) == ',') {
                sb.deleteCharAt(sb.toString().length() - 1);
            }
            sb.append(']');

            sb.append(',');
        } else {
            sb.append('\"');
            if (replceName == null || replceName.equals("")) {
                sb.append(field.getName());
            } else {
                sb.append(replceName);
            }
            sb.append('\"').append(':');

            if (!result.toString().startsWith("[")) {
                String result2 = stringToJson(result.toString());
                sb.append(result2);
            } else {// 如果是数组，就不加引号
                JSONArray array = new JSONArray(result.toString());
                sb.append('[');
                for (int i = 0; i < array.length(); i++) {
                    String result2 = stringToJson(array.get(i).toString());
                    sb.append(result2);
                    sb.append(',');
                }
                if (sb.charAt(sb.toString().length() - 1) == ',') {
                    sb.deleteCharAt(sb.toString().length() - 1);
                }
                sb.append(']');
            }

            sb.append(',');
        }

    }

    /**
     * 组包json数据
     * @param t
     * @param clazz
     * @param field
     * @param sb
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws JSONException
     */
    public static <T> void appendJsonData(T t, Class<?> clazz, Field field,
            StringBuilder sb, CombinationType combinationType) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, JSONException {
        String fieldName = null;
        String entityName = null;
        boolean isEnum = false;
        boolean notCombination = false;
        boolean isPassword = false;
        // 获取变量的注解
        JSONField annotation = field.getAnnotation(JSONField.class);
        if (annotation != null) {
            fieldName = annotation.name();
            isEnum = annotation.isEnum();
            entityName = annotation.entityName();
            notCombination = annotation.notCombination();
            isPassword = annotation.isPassword();
        }
        if (notCombination) {
            return;
        }
        // 如果注解名为空，则去原本的字段名
        if (fieldName == null || fieldName.equals("")) {
            fieldName = field.getName();
        }
        if (isEnum) {// 当是枚举的时候
            if (field.getType() == AIIAction.class) {
                field.setAccessible(true);
                AIIAction result = (AIIAction) field.get(t);
                if (result != AIIAction.NULL) {
                    int value = AIIAction.getValues(result);
                    sb.append('\"').append(fieldName).append('\"').append(':');
                    sb.append("" + value).append(',');
                }
            }
        } else {

            if (PacketUtil.isCommonField(field.getType())) {
                field.setAccessible(true);
                Object result = field.get(t);
                if (result != null) {
                	if(isPassword){
                		if(JSON.saltingPassword){
                			result = Encrypt.saltingPassword(result.toString());                			
                		} else {
                			result = AiiUtil.md5(result.toString());
                		}
                	}
                    appendValueToJson(field, result, sb, fieldName);
                }
            }
            // 当是集合的时候
            else if (field.getType() == List.class
                    || field.getType() == ArrayList.class) {
                ParameterizedType type = (ParameterizedType) field
                        .getGenericType();
                if (type.getActualTypeArguments() != null
                        && type.getActualTypeArguments().length > 0) {

                    Class<?> childClass = (Class<?>) type
                            .getActualTypeArguments()[0];
                    field.setAccessible(true);
                    ArrayList<?> result = (ArrayList<?>) field.get(t);
//                    ArrayList<?> result = (ArrayList<?>) clazz.getMethod(
//                            PacketUtil.getMethodName(field.getName()))
//                            .invoke(t);
                    if (result != null) {
                        if (PacketUtil.isCommonField(childClass)) {
                            appendValueToJson(field, result, sb, fieldName);
                        } else {
                            appendArrayData(fieldName, entityName, result, sb, combinationType);
                        }
                    }

                }
            } else {
//                Object en = clazz.getMethod(
//                        PacketUtil.getMethodName(field.getName())).invoke(t);
                field.setAccessible(true);
                Object en = field.get(t);
                if (en != null) {
                    String query = JSON.toJsonString(en);
                    sb.append('\"').append(fieldName).append('\"').append(':');
                    sb.append(query).append(',');

                }
            }
        }

    }

    /**
     * 对数组的组包
     * 
     * @param fieldName
     *            变量名 （更改后的 如action 的 应该是 a）
     * @param result
     *            变量的值
     * @param sb
     *            组合字符串
     * @throws Exception
     *             抛出异常
     */
    private static void appendArrayData(String arrayName, String entityName,
            ArrayList<?> result, StringBuilder sb, CombinationType combinationType) {
        sb.append('\"').append(arrayName).append('\"').append(':');
        sb.append('[');
        for (int i = 0; i < result.size(); i++) {
            Object entity = result.get(i);
            if (entity != null) {
                if (entityName == null || entityName.equals("")) {// 正常情况，数组是
                                                                  // names,
                                                                  // 那么实体就是name，去掉s或者es
                    if (arrayName.endsWith("ses")) {
                        entityName = arrayName.substring(0,
                                arrayName.length() - 2);
                    } else if (arrayName.endsWith("s")) {
                        entityName = arrayName.substring(0,
                                arrayName.length() - 1);
                    } else {// 如果不是s或者es结尾就数组和实体的名称一样
                        entityName = arrayName;
                    }
                }
                String query = "";
                try {
                    query = com.aiitec.openapi.json.JSON.toJsonString(entity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (combinationType == CombinationType.AII_STYLE && query != null) {
                    sb.append('{').append('\"').append(entityName).append('\"')
                            .append(':').append(query).append('}').append(',');

                } else {
                    sb.append(query).append(',');
                }
            }
        }
        if (sb.toString().endsWith(",")) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(']');
        sb.append(',');
    }

    /**
     * 将 String 对象编码为 JSON格式，只需处理好特殊字符
     * 
     * @param s
     *            String 对象
     * @return String:JSON格式
     * @version 1.0
     * @date 2015-10-11
     * @Author zhou.wenkai
     */
    private static String stringToJson(final String str) {
        if (str == null || str.length() == 0) {
            return "\"\"";
        }
        final StringBuilder sb = new StringBuilder(str.length() + 2 << 4);
        sb.append('\"');
        for (int i = 0; i < str.length(); i++) {
            final char c = str.charAt(i);

            sb.append(c == '\"' ? "\\\"" : c == '\\' ? "\\\\"
                    : c == '/' ? "\\/" : c == '\b' ? "\\b" : c == '\f' ? "\\f"
                            : c == '\n' ? "\\n" : c == '\r' ? "\\r"
                                    : c == '\t' ? "\\t" : c);
        }
        sb.append('\"');
        return sb.toString();
    }
}
