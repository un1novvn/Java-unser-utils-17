package cn.org.unk.test;

import java.io.Serializable;

public class TestToString implements Serializable {

    private String username;

    public TestToString(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        System.out.println("TestToString#toString");
        return "TestToString{" +
                "username='" + username + '\'' +
                '}';
    }
}
