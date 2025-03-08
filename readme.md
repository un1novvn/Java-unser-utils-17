# Java-unser-utils-17
Java-unser-utils在jdk17+的利用版本

## Gadget17
- 排除了无法在jdk17+下使用的gadget
- getLDAPAttributeProxy

  使用JdkDynamicProxy封装LDAPAttribute,使之可以绕过包隔离机制触发getter->jndi

- getTextAndMnemonicHashMap

  readObject->toString

## Util17
内部自动使用unsafe绕过包隔离机制，用户直接调用setFieldValue等方法即可。


