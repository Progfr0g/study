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
            log.error("Database connection failure: " + ex.getMessage());
        }
        return false;
    }

    public ResultSet select(String query){
        try {
            ResultSet result = connection.prepareStatement(query).executeQuery();
            return result;
        } catch (SQLException ex) {
            log.error("SELECT query failed to execute: " + ex.getMessage());
        }
        return null;
    }

    public int insert(String query){
        try {
            int result = connection.prepareStatement(query).executeUpdate();

            log.info(String.format("[INSERTED %d rows]", result));
            return result;
        } catch (SQLException ex) {
            log.error("SELECT query failed to execute: " + ex.getMessage());
        }
        return 0;
    }

    public int update(String query){
        try {
            int result = connection.prepareStatement(query).executeUpdate();
            log.debug(String.format("[UPDATED %d rows]", result));
            return result;
        } catch (SQLException ex) {
            log.error("UPDATE query failed to execute: " + ex.getMessage());
        }
        return 0;
    }

    public int delete(String query){
        try {
            int result = connection.prepareStatement(query).executeUpdate();
            log.debug(String.format("[DELETED %d rows]", result));
            return result;
        } catch (SQLException ex) {
            log.error("UPDATE query failed to execute: " + ex.getMessage());
        }
        return 0;
    }

    public boolean run(String query){
        try {
            connection.prepareStatement(query).execute();
            return true;
        } catch (SQLException ex) {
            log.error("Query failed to execute: " + ex.getMessage());
        }
        return false;
    }

    public boolean closeConnection(){
        try {
            connection.close();
            return true;
        } catch (SQLException ex) {
            log.error("Failed to close connection: " + ex.getMessage());
        }
        return false;
    }

//    try (
//    Connection db = DriverManager.getConnection("jdbc:h2:~/test")) {
//        try (Statement dataQuery = db.createStatement()) {
//            dataQuery.execute(CREATE_USERS);
//            dataQuery.execute(CREATE_EVENTS);
//            dataQuery.execute(CREATE_PHOTOS);
//            dataQuery.execute(CREATE_COMMENTS);
////                dataQuery.execute(DATA_QUERY);
//        }
//
//        try (PreparedStatement query =
//                     db.prepareStatement("SELECT * FROM USERS")) {
//            ResultSet rs = query.executeQuery();
//            while (rs.next()) {
//                System.out.println(String.format("%s, %s, %s!",
//                        rs.getString(1),
//                        rs.getString("age"),
//                        rs.getString("role")));
//            }
//            rs.close();
//        }
//    } catch (SQLException ex) {
//        System.out.println();
//    }
}
