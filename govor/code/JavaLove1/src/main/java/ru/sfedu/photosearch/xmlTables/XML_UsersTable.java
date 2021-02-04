package ru.sfedu.photosearch.xmlTables;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import ru.sfedu.photosearch.models.User;

import java.util.List;


@Root (name="users_table")
public class XML_UsersTable {
    @ElementList
    private List<User> users;

    public List<User> getxmlUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
