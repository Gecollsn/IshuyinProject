package com.ishuyin.gecollsn.utils.spUtil;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by gecollsn on 2016/3/24 11:13.
 * <p/>
 * Company: zjp-company
 * Email: cs@zhijiepay.com
 * TEL: 0592-5101911
 * <p/>
 * Declare: SharePreference的相关操作, 增删改查
 * 需要Application中初始化
 */
public class SPUtil {

    /** 设置一个统一的上下文环境 */
    private static Context context;

    public static void initContext(Context context) {
        SPUtil.context = context;
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public static void put(String key, Object object) {
        put(SPFather.FILE, key, object);
    }

    public static void put(String fileName, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultValue
     */
    public static <T> T get(String key, T defaultValue) {
        return get(SPFather.FILE, key, defaultValue);
    }

    public static <T> T get(String fileName, String key, T defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        if (defaultValue instanceof String) {
            return (T) sp.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return (T) ((Integer) sp.getInt(key, (Integer) defaultValue));
        } else if (defaultValue instanceof Boolean) {
            return (T) ((Boolean) sp.getBoolean(key, (Boolean) defaultValue));
        } else if (defaultValue instanceof Float) {
            return (T) ((Float) sp.getFloat(key, (Float) defaultValue));
        } else if (defaultValue instanceof Long) {
            return (T) ((Long) sp.getLong(key, (Long) defaultValue));
        }

        return null;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public static void remove(String key) {
        remove(SPFather.FILE, key);
    }

    public static void remove(String fileName, String key) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /** 清除所有数据 */
    public static void clear() {
        clear(SPFather.FILE);
    }

    public static void clear(String fileName) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 返回SP的Editor对象
     *
     * @return
     */
    public static SharedPreferences.Editor retrieveEditor() {
        return retrieveEditor(SPFather.FILE);
    }

    public static SharedPreferences.Editor retrieveEditor(String fileName) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.edit();
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     */
    public static boolean contains(String key) {
        return contains(SPFather.FILE, key);
    }

    public static boolean contains(String fileName, String key) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public static Map<String, ?> getAll() {
        return getAll(SPFather.FILE);

    }

    public static Map<String, ?> getAll(String fileName) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            editor.commit();
        }
    }
}
