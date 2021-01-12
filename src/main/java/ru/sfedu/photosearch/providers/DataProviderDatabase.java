package ru.sfedu.photosearch.providers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.Database;
import ru.sfedu.photosearch.enums.Tables;

import java.sql.ResultSet;
import java.sql.SQLException;


public class DataProviderDatabase implements DataProvider {
    private static Logger log = LogManager.getLogger(DataProviderDatabase.class);
    public Database DB = new Database();

    @Override
    public boolean createNewProfile(String name, String last_name, Integer age, String date_of_registration, String role, String town) {
        String query = String.format(Constants.INSERT_USERS_QUERY, name, last_name, age.toString(), date_of_registration, role, town);
        return DB.connect() && (DB.insert(query) > 0) && DB.closeConnection();
    }

    @Override
    public boolean createNewEvent(String title, String description, String customer, String event_date, Integer price, Float quantity) {
        String query = String.format(Constants.INSERT_EVENTS_QUERY, title, description, customer, event_date, price.toString(), quantity.toString());
        return DB.connect() && (DB.insert(query) > 0) && DB.closeConnection();
    }

    @Override
    public String getProfile(String id) {
        String query = Constants.SELECT_PROFILE_QUERY + id;
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            String result = Constants.UTIL_NEW_LINE;
            while (rs.next()){
                for (int i = 0; i < rsMetaSize; i++) {
                    String value = Constants.UTIL_EMPTY_STRING;
                    if (rs.getString(i+1) != null) value = rs.getString(i+1);
                    result += rs.getMetaData().getColumnName(i+1)
                            + Constants.UTIL_DOUBLE_DOTS
                            + Constants.UTIL_SPACE
                            + value
                            + Constants.UTIL_NEW_LINE;
                }
            }
            DB.closeConnection();

            if (result == Constants.UTIL_NEW_LINE){
                return String.format(Constants.EMPTY_GET_PROFILE, id);
            } else
                log.debug(String.format(Constants.SUCCESS_GET_PROFILE, id));
                return result;

        } catch (SQLException ex) {
            log.error(String.format(Constants.ERROR_GET_PROFILE, id) + ex.getMessage());
        }
        return null;
    }

    @Override
    public String getEvent(String id) {
        String query = Constants.SELECT_EVENT_QUERY + id;
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            String result = Constants.UTIL_NEW_LINE;
            while (rs.next()){
                for (int i = 0; i < rsMetaSize; i++) {
                    String value = Constants.UTIL_EMPTY_STRING;
                    if (rs.getString(i+1) != null) value = rs.getString(i+1);
                    result += rs.getMetaData().getColumnName(i+1)
                            + Constants.UTIL_DOUBLE_DOTS
                            + Constants.UTIL_SPACE
                            + value
                            + Constants.UTIL_NEW_LINE;
                }
            }
            DB.closeConnection();

            if (result == Constants.UTIL_NEW_LINE){
                return String.format(Constants.EMPTY_GET_EVENT, id);
            } else
                log.debug(String.format(Constants.SUCCESS_GET_EVENT, id));
                return result;
        } catch (SQLException ex) {
            log.error(String.format(Constants.ERROR_GET_EVENT, id) + ex.getMessage());
        }
        return null;
    }

    @Override
    public void editProfileById(String id, String field, String value) {
        DB.connect();
        String query = String.format(Constants.UPDATE_PROFILE_QUERY, Tables.USERS.toString(), field, value) + id;
        DB.update(query);
        DB.closeConnection();
    }

    @Override
    public void deleteProfileById(String id) {
        DB.connect();
        String query = String.format(Constants.DELETE_PROFILE_QUERY, Tables.USERS.toString()) + id;
        DB.delete(query);
        DB.closeConnection();
    }

    @Override
    public void deleteEventById(String id) {
        DB.connect();
        String query = String.format(Constants.DELETE_EVENT_QUERY, Tables.EVENTS.toString()) + id;
        DB.delete(query);
        DB.closeConnection();
    }

    @Override
    public void editEventById(String id, String field, String value) {
        DB.connect();
        String query = String.format(Constants.UPDATE_EVENT_QUERY, Tables.EVENTS.toString(), field, value) + id;
        DB.update(query);
        DB.closeConnection();
    }

    @Override
    public void addPhoto(String id, String path) {
        DB.connect();
        String query = String.format(Constants.INSERT_PHOTO_QUERY, Tables.PHOTOS.toString(), id, path);
        DB.insert(query);
        DB.closeConnection();
    }

    @Override
    public String getPhoto(String id) {
        String query = Constants.SELECT_PHOTO_QUERY + id;
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            String result = Constants.UTIL_NEW_LINE;
            while (rs.next()){
                for (int i = 0; i < rsMetaSize; i++) {
                    String value = Constants.UTIL_EMPTY_STRING;
                    if (rs.getString(i+1) != null) value = rs.getString(i+1);
                    result += rs.getMetaData().getColumnName(i+1)
                            + Constants.UTIL_DOUBLE_DOTS
                            + Constants.UTIL_SPACE
                            + value
                            + Constants.UTIL_NEW_LINE;
                }
            }
            DB.closeConnection();
            if (result == Constants.UTIL_NEW_LINE){
                return String.format(Constants.EMPTY_GET_PHOTO, id);
            } else
                log.debug(String.format(Constants.SUCCESS_GET_PHOTO, id));
            return result;
        } catch (SQLException ex) {
            log.error(String.format(Constants.ERROR_GET_PHOTO, id) + ex.getMessage());
        }
        return null;
    }

    @Override
    public void editPhotoById(String id, String field, String value) {
        DB.connect();
        String query = String.format(Constants.UPDATE_PHOTO_QUERY, Tables.PHOTOS.toString(), field, value);
        DB.update(query);
        DB.closeConnection();
    }

    @Override
    public void deletePhotoById(String id) {
        DB.connect();
        String query = String.format(Constants.DELETE_PHOTO_QUERY, Tables.PHOTOS.toString(), id);
        DB.delete(query);
        DB.closeConnection();
    }

    @Override
    public String getPortfolio(String user_id) {
        String query = Constants.SELECT_PORTFOLIO_QUERY + user_id;
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            String result = Constants.UTIL_NEW_LINE;
            while (rs.next()){
                for (int i = 0; i < rsMetaSize; i++) {
                    String value = Constants.UTIL_EMPTY_STRING;
                    if (rs.getString(i+1) != null) value = rs.getString(i+1);
                    result += rs.getMetaData().getColumnName(i+1)
                            + Constants.UTIL_DOUBLE_DOTS
                            + Constants.UTIL_SPACE
                            + value
                            + Constants.UTIL_NEW_LINE;
                }
                result += Constants.UTIL_SEPARATOR
                        + Constants.UTIL_NEW_LINE;
            }
            DB.closeConnection();
            if (result == Constants.UTIL_NEW_LINE){
                return String.format(Constants.EMPTY_GET_PORTFOLIO, user_id);
            } else
                log.debug(String.format(Constants.SUCCESS_GET_PORTFOLIO, user_id));
            return result;
        } catch (SQLException ex) {
            log.error(String.format(Constants.ERROR_GET_PORTFOLIO, user_id) + ex.getMessage());
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
