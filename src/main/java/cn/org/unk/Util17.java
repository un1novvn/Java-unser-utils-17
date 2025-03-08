package cn.org.unk;

import sun.reflect.ReflectionFactory;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Util17 {


    // 绕过隔离机制
    static{
        try {
            UnsafeTools.bypassModule(Util17.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //From Boogipop
    //https://boogipop.com/2023/03/21/TCTF2022%20_%20Hessian-onlyJdk/
    public static <T> T createWithoutConstructor(Class<T> classToInstantiate) throws Exception{
        return createWithConstructor(classToInstantiate, Object.class, new Class[0], new Object[0]);
    }


    public static Method getMethod(final Class<?> clazz, final String methodName, Class[] paramClass) {
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(methodName,paramClass);
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            if (clazz.getSuperclass() != null){
                method = getMethod(clazz.getSuperclass(), methodName,paramClass);
            }else{
                throw new RuntimeException(e);
            }
        }
        return method;
    }
    public static Object invokeStaticMethod(Class clazz, final String methodName,Class[] paramClass,Object[] params) {
        Method method = getMethod(clazz,methodName,paramClass);
        try {
            return method.invoke(null, params);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    public static Object invokeMethod(Object obj, final String methodName,Class[] paramClass,Object[] params) {
        Method method = getMethod(obj.getClass(),methodName,paramClass);
        try {
            return method.invoke(obj, params);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }



    public static <T> T createWithConstructor(Class<T> classToInstantiate, Class<? super T> constructorClass, Class<?>[] consArgTypes, Object[] consArgs) throws Exception {
        Constructor<? super T> objCons = constructorClass.getDeclaredConstructor(consArgTypes);
        objCons.setAccessible(true);
        Constructor<?> sc = ReflectionFactory.getReflectionFactory().newConstructorForSerialization(classToInstantiate, objCons);
        sc.setAccessible(true);
        return (T) sc.newInstance(consArgs);
    }
    public static void setFieldValue(Object obj,String fieldname,Object value) throws Exception, IllegalAccessException {
        Field field= getField(obj.getClass(),fieldname);

        if(field == null) throw new Exception("field "+fieldname + "not found!");
        field.setAccessible(true);
        field.set(obj,value);
    }
    public static Object getFieldValue(Object obj,String fieldname) throws Exception{
        Field field = getField(obj.getClass(), fieldname);
        if(field == null){
            throw new RuntimeException("No such field: " + fieldname);
        }
        return field.get(obj);
    }
    public static Field getField(final Class<?> clazz, final String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
        } catch (Exception ex) {
            if (clazz.getSuperclass() != null)
                field = getField(clazz.getSuperclass(), fieldName);
        }
        return field;
    }

    public static byte[] serialize(Object obj) throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bytes);
        out.writeObject(obj);
        return bytes.toByteArray();
    }


    public static void serialize(Object obj,String filename) throws Exception {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
        out.writeObject(obj);
    }
    public static Object unserialize(String filename) throws Exception {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
        Object o = in.readObject();
        return o;
    }

    public static Object unserialize(byte[] bytes) throws Exception{
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes));
        return in.readObject();
    }
}
