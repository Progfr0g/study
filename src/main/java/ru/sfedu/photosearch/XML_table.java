package ru.sfedu.photosearch;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;

import java.util.UUID;


@Root (name="test_table")
public class XML_table {

    @ElementArray
    private XML_user[] users;

    public XML_user[] getXML_users() {
        return users;
    }

    public void setUsers(XML_user[] users) {
        this.users = users;
    }
}
