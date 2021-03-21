package ru.sfedu.photosearch.xmlTables;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import ru.sfedu.photosearch.Models.Rate;
import ru.sfedu.photosearch.Models.User;

import java.util.List;

/**
 * таблица для конвертации профилей из XML
 * @see User
 */
@Root (name="users_table")
public class XML_UsersTable {
    @ElementList
    private List<User> users;

    public List<User> getxmlUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        for (User user: users){
            user.setId(user.getId().toString());
        }
        this.users = users;
    }
}
