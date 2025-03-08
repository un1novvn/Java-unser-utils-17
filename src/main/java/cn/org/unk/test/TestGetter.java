package cn.org.unk.test;

import java.io.Serializable;

public class TestGetter implements Serializable {

    private String outputProperties;
    public String getOutputProperties(){
        System.out.println("TestGetter#getOutputProperties");
        return "";
    }

}