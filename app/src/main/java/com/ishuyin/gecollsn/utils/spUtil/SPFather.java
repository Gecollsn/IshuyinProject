package com.ishuyin.gecollsn.utils.spUtil;

import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

/**
 * Created by gecollsn on 2016/5/19 10:31.
 * <p/>
 * Company: zjp-company
 * Email: cs@zhijiepay.com
 * TEL: 0592-5101911
 * <p/>
 * Declare: SPFather
 */
public abstract class SPFather {

    public static final String FILE = "UniteSharePreferMap";

    /**
     * 获取SP文件中的所有元素
     *
     * @param fileName
     * @return
     */
    public static Map getElementsMap(String fileName) {
        return SPUtil.getAll(fileName);
    }

    /**
     * 批量写入SP元素
     *
     * @param elementsMap
     * @param fileName
     */
    public static void setElementsMap(Map<String, ?> elementsMap, String fileName) {
        SharedPreferences.Editor editor = SPUtil.retrieveEditor(fileName);
        Set<String> keys = elementsMap.keySet();
        for (String key : keys) {
            Object value = elementsMap.get(key);
            Class<?> valueClazz = value.getClass();
            if (valueClazz == String.class) editor.putString(key, (String) value);
            else if (valueClazz == int.class || valueClazz == Integer.class) editor.putInt(key, (int) value);
            else if (valueClazz == long.class || valueClazz == Long.class) editor.putLong(key, (long) value);
            else if (valueClazz == boolean.class || valueClazz == Boolean.class)
                editor.putBoolean(key, (boolean) value);
            else if (valueClazz == float.class || valueClazz == Float.class) editor.putFloat(key, (float) value);
        }

        editor.commit();
    }
}
