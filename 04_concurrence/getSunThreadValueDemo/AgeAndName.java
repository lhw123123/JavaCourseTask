package com.lhw.week04;

/**
 * @author lhw
 * @title
 * @description
 * @created 7/16/21 4:32 PM
 * @changeRecord
 */
public class AgeAndName {

    private Integer age;

    private String name;

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "[age=" + age + " name=" + name + "]";
    }

}

