package ru.sfedu.photosearch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class Database {
    private static Logger log = LogManager.getLogger(Database.class);
    private static Connection connection;

    public boolean connect(){
        try {
            connection = DriverManager.getConnection(Constants.DB_FILE_PATH);
            return true;
        } catch (SQLException ex) {
            log.error(Constants.ERROR_FAILED_DB_CONNECTION + ex.getMessage());
        }
        return false;
    }

    public ResultSet select(String query){
        try {
            ResultSet result = connection.prepareStatement(query).executeQuery();
            return result;
        } catch (SQLException ex) {
            log.error(Constants.ERROR_SELECT_QUERY + ex.getMessage());
        }
        return null;
    }

    public int insert(String query){
        try {
            int result = connection.prepareStatement(query).executeUpdate();
            log.debug(String.format(Constants.DEBUG_SUCCESS_INSERT, result));
            return result;
        } catch (SQLException ex) {
            log.error(Constants.ERROR_INSERT_QUERY + ex.getMessage());
        }
        return 0;
    }

    public int update(String query){
        try {
            int result = connection.prepareStatement(query).executeUpdate();
            log.debug(String.format(Constants.DEBUG_SUCCESS_UPDATE, result));
            return result;
        } catch (SQLException ex) {
            log.error(Constants.ERROR_UPDATE_QUERY + ex.getMessage());
        }
        return 0;
    }

    public int delete(String query){
        try {
            int result = connection.prepareStatement(query).executeUpdate();
            log.debug(String.format(Constants.DEBUG_SUCCESS_DELETE, result));
            return result;
        } catch (SQLException ex) {
            log.error(Constants.ERROR_DELETE_QUERY + ex.getMessage());
        }
        return 0;
    }

    public boolean run(String query){
        try {
            connection.prepareStatement(query).execute();
            return true;
        } catch (SQLException ex) {
            log.error(Constants.ERROR_EXECUTE_QUERY + ex.getMessage());
        }
        return false;
    }

    public boolean closeConnection(){
        try {
            connection.close();
            return true;
        } catch (SQLException ex) {
            log.error(Constants.ERROR_FAILED_DB_CONNECTION_CLOSE + ex.getMessage());
        }
        return false;
    }

    public void createTables() {
        run(Constants.TABLE_USERS);
        run(Constants.TABLE_EVENTS);
        run(Constants.TABLE_EVENTS);
        run(Constants.TABLE_COMMENTS);
        run(Constants.TABLE_RATES);
        log.debug(Constants.SUCCESS_TABLES_CREATING);
    }

}
