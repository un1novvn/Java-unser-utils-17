package cn.org.unk;


import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeTools {


    private static Unsafe getUnsafe() throws Exception{
        Class unsafeClass = Class.forName("sun.misc.Unsafe");
        Field field = unsafeClass.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        return unsafe;
    }

    // 哪个类里面有 setAccessible 跨模块操作会报错的, 调用这个就行
    public static void bypassModule(Class clazz) throws Exception{
        setFieldValue(clazz,"module",Object.class.getModule());
    }

    public static void setFieldValue(Object obj,Field field,Object value) throws Exception{
        Unsafe unsafe = getUnsafe();
        long offset = unsafe.objectFieldOffset(field);
        unsafe.putObject(obj, offset, value);
    }

    public static void setFieldValue(Object obj,String fieldName,Object value) throws Exception{
        Field declaredField = obj.getClass().getDeclaredField(fieldName);
        setFieldValue(obj, declaredField,value);
    }
}