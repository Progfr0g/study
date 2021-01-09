package ru.sfedu.photosearch;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;

import java.util.UUID;

@Root(name="test_table")
public class XML_user {
    @Attribute
    private String id;

    @Element
    private String name;

    @Element
    private String last_name;

    @Element
    private String age;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getAge() {
        return age;
    }
    public XML_user() {
        super();
    }

    public XML_user(String name, String last_name, String age){
        setId(UUID.randomUUID().toString());
        setName(name);
        setLast_name(last_name);
        setAge(age);
    }

}

