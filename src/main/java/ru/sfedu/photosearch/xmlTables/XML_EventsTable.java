package ru.sfedu.photosearch.xmlTables;

import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;
import ru.sfedu.photosearch.models.User;


@Root (name="events_table")
public class XML_EventsTable {
    @ElementArray
    private User[] users;

    public User[] getxmlUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }
}
