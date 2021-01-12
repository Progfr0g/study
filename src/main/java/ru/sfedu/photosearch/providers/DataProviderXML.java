package ru.sfedu.photosearch.providers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.enums.Tables;
import ru.sfedu.photosearch.models.User;
import ru.sfedu.photosearch.xmlTables.XML_UsersTable;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataProviderXML implements DataProvider {
    private static Logger log = LogManager.getLogger(DataProviderXML.class);
    @Override
    public boolean createNewProfile(String name, String last_name, Integer age, String date_of_registration, String role, String town) {
        try {
            Serializer serializer = new Persister();
            File source = new File(Constants.XML_USERS_FILE_PATH);
            XML_UsersTable table;
            if (source.length() == 0) {
                User[] users_array = new User[1];
                users_array[0] = new User(UUID.randomUUID().toString(), name, last_name, age.toString(), LocalDate.now().toString(), role, town);
                table = new XML_UsersTable();
                table.setUsers(users_array);
                File result = new File(Constants.XML_USERS_FILE_PATH);

                serializer.write(table, result);
                log.info(Constants.SUCCESS_NEW_PROFILE);
                return true;
            } else {
                table = serializer.read(XML_UsersTable.class, source);
                List<User> writedUsers = Arrays.asList(table.getxmlUsers().clone());

                User[] users_array = new User[writedUsers.size()+1];
                for (int i = 0; i < writedUsers.size(); i++) {
                    users_array[i] = writedUsers.get(i);
                }
                users_array[writedUsers.size()] = new User(UUID.randomUUID().toString(), name, last_name, age.toString(), LocalDate.now().toString(), role, town);

                table.setUsers(users_array);
                File result = new File(Constants.XML_USERS_FILE_PATH);

                serializer.write(table, result);
                log.info(Constants.SUCCESS_NEW_PROFILE);
                return true;
            }

        } catch (Exception ex) {
            log.error(Constants.ERROR_INSERT_QUERY + ex.getMessage());
        }
        return false;

    }

    @Override
    public boolean createNewEvent(String title, String description, String customer, String event_date, Integer price, Float quantity) {
        return false;
    }

    @Override
    public String getProfile(String id) {
        try{
            Serializer serializer = new Persister();
            File source = new File(Constants.XML_USERS_FILE_PATH);
            XML_UsersTable table;
            table = serializer.read(XML_UsersTable.class, source);
            Set<String> targetSet = new HashSet<String>();
            for (User user: table.getxmlUsers()){
                if (user.getId().equals(id)){
                    if (!targetSet.add(user.getId())) {
                        return String.format(Constants.ERROR_XML_SAME_ID, Tables.USERS.name().toLowerCase());
                    }
                    log.debug(String.format(Constants.SUCCESS_GET_PROFILE, user.getId()));
                    return user.getUserOutput();
                } else {
                    log.info(String.format(Constants.EMPTY_GET_PROFILE, id));
                }
            }
        } catch (Exception ex) {
            log.info(Constants.ERROR_GET_PROFILE + ex);
        }
        return null;
    }

    @Override
    public String getEvent(String id) {
        return null;
    }

    @Override
    public void editProfileById(String id, String field, String value) {

    }

    @Override
    public void editEventById(String id, String field, String value) {

    }

    @Override
    public void deleteProfileById(String id) {

    }

    @Override
    public void deleteEventById(String id) {

    }

    @Override
    public void addPhoto(String id, String path) {

    }

    @Override
    public String getPhoto(String id) {
        return null;
    }

    @Override
    public void editPhotoById(String id, String field, String value) {

    }

    @Override
    public void deletePhotoById(String id) {

    }

    @Override
    public String getPortfolio(String user_id) {
        return null;
    }

    @Override
    public String getPhotoPathById(String id) {
        return null;
    }
}
