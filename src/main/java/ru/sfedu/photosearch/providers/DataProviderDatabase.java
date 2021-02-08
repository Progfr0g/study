package ru.sfedu.photosearch.providers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.Database;
import ru.sfedu.photosearch.enums.EventType;
import ru.sfedu.photosearch.enums.Role;
import ru.sfedu.photosearch.enums.Tables;
import ru.sfedu.photosearch.newModels.Event;
import ru.sfedu.photosearch.newModels.Photo;
import ru.sfedu.photosearch.newModels.User;
import ru.sfedu.photosearch.utils.Formatter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;



public class DataProviderDatabase implements DataProvider {
    private static Logger log = LogManager.getLogger(DataProviderDatabase.class);
    public Database DB = new Database();

    @Override
    public Boolean createNewProfile(String name,
                                   String lastName,
                                   Date birthDay,
                                   Date dateOfRegistration,
                                   Role role,
                                   String town) {

        String query = String.format(
                Constants.INSERT_USERS_QUERY,
                name,
                lastName,
                Formatter.normalFormatDay(birthDay),
                Formatter.dateOfRegistration(dateOfRegistration),
                role,
                town);
        if (DB.connect() && (DB.insert(query) > 0) && DB.closeConnection()) {
            return true;
        }
        return false;
    }

    @Override
    public User getProfile(String id) {
        String query = Constants.SELECT_PROFILE_QUERY + id;
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            Integer rows = 0;
            String[] strings = new String[rsMetaSize];
            while (rs.next()){
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
                rows++;
            }
            DB.closeConnection();
            if (rows == 0){
                log.info(String.format(Constants.EMPTY_GET_PROFILE, id));
                return null;
            } else{
                User resultUser = new User<String>(strings);
                return resultUser;
            }
        } catch (SQLException ex) {
            log.error(String.format(Constants.ERROR_GET_PROFILE, id) + ex.getMessage());
        }
        return null;
    }

    @Override
    public Boolean editProfileById(String id, String field, String value) {
        String query = String.format(Constants.UPDATE_PROFILE_QUERY, Tables.USERS.toString(), field, value) + id;
        if (DB.connect() && (DB.update(query) > 0) && DB.closeConnection()) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteProfileById(String id) {
        String query = String.format(Constants.DELETE_PROFILE_QUERY, Tables.USERS.toString()) + id;
        if (DB.connect() && (DB.delete(query) > 0) && DB.closeConnection()) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean createNewEvent(String title,
                                  String description,
                                  String customer,
                                  Date eventDate,
                                  Date creationDate,
                                  Integer price,
                                  Float quantity,
                                  EventType type) {
        String query = String.format(
                Constants.INSERT_EVENTS_QUERY,
                title,
                description,
                customer,
                Formatter.normalFormatDay(eventDate),
                Formatter.dateOfRegistration(creationDate),
                price.toString(),
                quantity.toString(),
                type);
        if (DB.connect() && (DB.insert(query) > 0) && DB.closeConnection()) {
            return true;
        }
        return false;
    }

    @Override
    public Event getEvent(String id) {
        String query = Constants.SELECT_EVENT_QUERY + id;
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            Integer rows = 0;
            String[] strings = new String[rsMetaSize];
            while (rs.next()){
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
                rows++;
            }
            DB.closeConnection();
            if (rows == 0){
                log.info(String.format(Constants.EMPTY_GET_EVENT, id));
                return null;
            } else{
                User costumer;
                if (strings[7]!=null) {
                    costumer = getProfile(strings[7]);
                } else
                    costumer = null;
                User executor;
                if (strings[8]!=null) {
                    executor = getProfile(strings[8]);
                } else {
                    executor = null;
                };
                Event resultEvent = new Event<String>(strings, costumer, executor);
                return resultEvent;
            }
        } catch (SQLException ex) {
            log.error(String.format(Constants.ERROR_GET_EVENT, id) + ex.getMessage());
        }
        return null;
    }

    @Override
    public Boolean editEventById(String id, String field, String value) {
        String query = String.format(Constants.UPDATE_EVENT_QUERY, Tables.EVENTS.toString(), field, value) + id;
        if (DB.connect() && (DB.update(query) > 0) && DB.closeConnection()) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteEventById(String id) {
        String query = String.format(Constants.DELETE_EVENT_QUERY, Tables.EVENTS.toString()) + id;
        if (DB.connect() && (DB.delete(query) > 0) && DB.closeConnection()) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean addPhoto(String userId, String path) {
        String query = String.format(Constants.INSERT_PHOTO_QUERY, Tables.PHOTOS.toString(), userId, path);
        if (DB.connect() && (DB.insert(query) > 0) && DB.closeConnection()) {
            return true;
        }
        return false;
    }

    @Override
    public Photo getPhoto(String id) {
        String query = Constants.SELECT_PHOTO_QUERY + id;
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            Integer rows = 0;
            String[] strings = new String[rsMetaSize];
            while (rs.next()){
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
                rows++;
            }
            DB.closeConnection();
            if (rows == 0){
                log.info(String.format(Constants.EMPTY_GET_PHOTO, id));
                return null;
            } else{
                User user;
                if (strings[1]!=null) {
                    user = getProfile(strings[1]);
                } else user = null;
                Event event;
                if (strings[2]!=null) {
                    event = getEvent(strings[2]);
                } else event = null;
                Photo resultPhoto = new Photo<String>(strings, user, event);
                return resultPhoto;
            }
        } catch (SQLException ex) {
            log.error(String.format(Constants.ERROR_GET_PHOTO, id) + ex.getMessage());
        }
        return null;
    }

    @Override
    public Boolean editPhotoById(String id, String field, String value) {
        String query = String.format(Constants.UPDATE_PHOTO_QUERY, Tables.PHOTOS.toString(), field, value) + id;
        if (DB.connect() && (DB.update(query) > 0) && DB.closeConnection()) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean deletePhotoById(String id) {
        String query = String.format(Constants.DELETE_PHOTO_QUERY, Tables.PHOTOS.toString())+ id;
        if (DB.connect() && (DB.delete(query) > 0) && DB.closeConnection()) {
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Photo> getPortfolio(String userId) {
        String query = Constants.SELECT_PORTFOLIO_QUERY + userId;
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            Integer rows = 0;
            String[] strings = new String[rsMetaSize];
            ArrayList<Photo> photos = new ArrayList<Photo>();
            while (rs.next()){
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
                rows++;
                User user;
                if (strings[1]!=null) {
                    user = getProfile(strings[1]);
                } else user = null;
                Event event;
                if (strings[2]!=null) {
                    event = getEvent(strings[2]);
                } else event = null;
                Photo resultPhoto = new Photo<String>(strings, user, event);
                photos.add(resultPhoto);
            }
            DB.closeConnection();
            if (rows == 0){
                log.info(String.format(Constants.EMPTY_GET_PORTFOLIO, userId));
                return null;
            } else{
                return photos;
            }
        } catch (SQLException ex) {
            log.error(String.format(Constants.ERROR_GET_PORTFOLIO, userId) + ex.getMessage());
        }
        return null;
    }

    @Override
    public String getPhotoPathById(String id) {
        String query = Constants.SELECT_PHOTO_PATH_QUERY + id;
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            String result = Constants.UTIL_NEW_LINE;
            rs.next();
            if (rs.getString(1) != null) result = rs.getString(1);
            DB.closeConnection();
            if (result == Constants.UTIL_NEW_LINE){
                return String.format(Constants.EMPTY_GET_PHOTO_PATH, id);
            } else
                log.debug(String.format(Constants.SUCCESS_GET_PHOTO_PATH, id));
            return result;
        } catch (SQLException ex) {
            log.error(String.format(Constants.ERROR_GET_PHOTO_PATH, id) + ex.getMessage());
        }
        return null;
    }
}
