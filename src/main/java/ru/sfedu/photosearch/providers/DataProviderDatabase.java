package ru.sfedu.photosearch.providers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.Database;
import ru.sfedu.photosearch.enums.EventType;
import ru.sfedu.photosearch.enums.Role;
import ru.sfedu.photosearch.enums.Tables;
import ru.sfedu.photosearch.newModels.Comment;
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
                }
                Event resultEvent = new Event<String>(strings, costumer, executor);
                return resultEvent;
            }
        } catch (SQLException ex) {
            log.error(String.format(Constants.ERROR_GET_EVENT, id) + ex.getMessage());
            return null;
        }
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

    @Override
    public String getLastUserId() {
        String query = Constants.SELECT_LAST_USER_QUERY;
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
                log.info(Constants.EMPTY_GET_LAST_USER);
                return null;
            } else{
                User resultUser = new User<String>(strings);
                return resultUser.getId().toString();
            }
        } catch (SQLException ex) {
            log.error(Constants.ERROR_GET_LAST_PROFILE + ex.getMessage());
        }
        return null;
    }

    @Override
    public String getLastEventId() {
        String query = Constants.SELECT_LAST_EVENT_QUERY;
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
                log.info(Constants.EMPTY_GET_LAST_EVENT);
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
                }
                Event resultEvent = new Event<String>(strings, costumer, executor);
                return resultEvent.getId().toString();
            }
        } catch (SQLException ex) {
            log.error(Constants.ERROR_GET_EVENT + ex.getMessage());
            return null;
        }
    }

    @Override
    public String getLastPhotoId() {
        String query = Constants.SELECT_LAST_PHOTO_QUERY;
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
                log.info(Constants.EMPTY_GET_LAST_PHOTO);
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
                return resultPhoto.getId().toString();
            }
        } catch (SQLException ex) {
            log.error(Constants.ERROR_GET_LAST_PHOTO + ex.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<User> getAllUsers() {
        String query = Constants.SELECT_ALL_USERS;
        ArrayList<User> resultUsers = new ArrayList<User>();
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            while (rs.next()){
                String[] strings = new String[rsMetaSize];
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
                User resultUser = new User<String>(strings);
                resultUsers.add(resultUser);
            }
            DB.closeConnection();
            if (resultUsers.size() == 0){
                log.info(Constants.EMPTY_GET_ALL_USERS);
                return null;
            } else{
                return resultUsers;
            }
        } catch (SQLException ex) {
            log.error(Constants.ERROR_GET_ALL_PROFILES + ex.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Event> getAllEvents() {
        String query = Constants.SELECT_ALL_EVENTS;
        ArrayList<Event> resultEvents = new ArrayList<Event>();
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            while (rs.next()){
                String[] strings = new String[rsMetaSize];
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
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
                }
                Event resultEvent = new Event<String>(strings, costumer, executor);
                resultEvents.add(resultEvent);
            }
            DB.closeConnection();
            if (resultEvents.size() == 0){
                log.info(Constants.EMPTY_GET_ALL_EVENTS);
                return null;
            } else{
                return resultEvents;
            }
        } catch (SQLException ex) {
            log.error(Constants.ERROR_GET_ALL_EVENTS + ex.getMessage());
            return null;
        }
    }

    @Override
    public ArrayList<Photo> getAllPhotos() {
        String query = Constants.SELECT_ALL_PHOTOS;
        ArrayList<Photo> resultPhotos = new ArrayList<Photo>();
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            while (rs.next()){
                String[] strings = new String[rsMetaSize];
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
                User user;
                if (strings[1]!=null) {
                    user = getProfile(strings[1]);
                } else user = null;
                Event event;
                if (strings[2]!=null) {
                    event = getEvent(strings[2]);
                } else event = null;
                Photo resultPhoto = new Photo<String>(strings, user, event);
                resultPhotos.add(resultPhoto);
            }
            DB.closeConnection();
            if (resultPhotos.size() == 0){
                log.info(Constants.EMPTY_GET_ALL_PHOTOS);
                return null;
            } else{
                return resultPhotos;
            }
        } catch (SQLException ex) {
            log.error(Constants.ERROR_GET_ALL_PHOTOS + ex.getMessage());
        }
        return null;
    }

    @Override
    public Boolean addComment(String userId, String photoId, String comment, Date date) {
        String query = String.format(Constants.INSERT_COMMENTS_QUERY, Tables.COMMENTS.toString(),
                userId, photoId, comment, Formatter.dateOfRegistration(date));
        if (DB.connect() && (DB.insert(query) > 0) && DB.closeConnection()) {
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Comment> getAllComments() {
        String query = Constants.SELECT_ALL_COMMENTS;
        ArrayList<Comment> resultComments = new ArrayList<Comment>();
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            while (rs.next()){
                String[] strings = new String[rsMetaSize];
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
                User user;
                if (strings[1]!=null) {
                    user = getProfile(strings[1]);
                } else user = null;
                Photo photo;
                if (strings[2]!=null) {
                    photo = getPhoto(strings[2]);
                } else photo = null;
                Comment resultComment = new Comment(strings, user, photo);
                resultComments.add(resultComment);
            }
            DB.closeConnection();
            if (resultComments.size() == 0){
                log.info(Constants.EMPTY_GET_ALL_COMMENTS);
                return null;
            } else{
                return resultComments;
            }
        } catch (SQLException ex) {
            log.error(Constants.ERROR_GET_ALL_COMMENTS + ex.getMessage());
        }
        return null;
    }


    @Override
    public ArrayList<User> searchUsers(String field, String value) {
        String query = String.format(Constants.SELECT_USER_SEARCH, field, value);
        ArrayList<User> resultUsers = new ArrayList<User>();
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            while (rs.next()){
                String[] strings = new String[rsMetaSize];
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
                User resultUser = new User<String>(strings);
                resultUsers.add(resultUser);
            }
            DB.closeConnection();
            if (resultUsers.size() == 0){
                log.info(Constants.EMPTY_GET_USERS_SEARCH);
                return null;
            } else{
                return resultUsers;
            }
        } catch (SQLException ex) {
            log.error(Constants.ERROR_GET_PROFILES_SEARCH + ex.getMessage());
        }
        return null;
    }

    @Override
    public Boolean addRate(String userId, String photoId, Float rate, Date date) {
        return null;
    }

    @Override
    public Boolean addFeedback( String userId, String photographerId, Float rate, Date creationDate) {
        return null;
    }

    @Override
    public Boolean createOffer(String userId, String eventId, Date creationDate) {
        return null;
    }


}
