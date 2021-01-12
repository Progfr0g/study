package ru.sfedu.photosearch.xmlTables;

import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;
import ru.sfedu.photosearch.models.User;


@Root (name="users_table")
public class XML_UsersTable {

    @ElementArray
    private User[] users;

    public User[] getxmlUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }
}
