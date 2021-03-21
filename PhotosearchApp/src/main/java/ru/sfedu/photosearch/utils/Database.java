package ru.sfedu.photosearch.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.photosearch.Constants;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class Database {
    private static Logger log = LogManager.getLogger(Database.class);
    private static Connection connection;

    /**
     * Подключение к БД
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    public boolean connect(){
        try {
            String db_file;
            if (System.getProperty(Constants.DB_FILE_PATH) != null) {
                db_file = System.getProperty(Constants.DB_FILE_PATH);
            } else {
                db_file = Constants.DB_FORMAT + ConfigurationUtil.getConfigurationEntry(Constants.DB_FILE_PATH);
            }
            connection = DriverManager.getConnection(db_file);
            return true;
        } catch (SQLException | IOException ex) {
            log.error(Constants.ERROR_FAILED_DB_CONNECTION + ex.getMessage());
        }
        return false;
    }

    /**
     * Выполнение запроса на выборку
     * @return ResultSet - полученные значения
     */
    public ResultSet select(String query){
        try {
            ResultSet result = connection.prepareStatement(query).executeQuery();
            return result;
        } catch (SQLException ex) {
            log.error(Constants.ERROR_SELECT_QUERY + ex.getMessage());
        }
        return null;
    }

    /**
     * Выполнение запроса на поиск
     * @return ResultSet - выбранные значения
     * @deprecated (не используется)
     */
    public ResultSet select_search(String query){
        try {
            ResultSet result = connection.prepareStatement(query).executeQuery();
            return result;
        } catch (SQLException ex) {
            log.error(Constants.ERROR_SELECT_QUERY + ex.getMessage());
        }
        return null;
    }

    /**
     * Выполнение запроса на вставку
     * @return int кол-во изменненных строк
     */
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

    /**
     * Выполнение запроса на обновление
     * @return int кол-во изменненных строк
     */
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

    /**
     * Выполнение запроса на удаление
     * @return int кол-во изменненных строк
     */
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

    /**
     * Выполнение различных запросов
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    public boolean run(String query){
        try {
            connection.prepareStatement(query).execute();
            return true;
        } catch (SQLException ex) {
            log.error(Constants.ERROR_EXECUTE_QUERY + ex.getMessage());
        }
        return false;
    }

    /**
     * Закрытие соединения с БД
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    public boolean closeConnection(){
        try {
            connection.close();
            return true;
        } catch (SQLException ex) {
            log.error(Constants.ERROR_FAILED_DB_CONNECTION_CLOSE + ex.getMessage());
            return false;
        }
    }

    /**
     * Создание таблиц
     */
    public void createTables() {
        if (run(Constants.TABLE_USERS) &&
                run(Constants.TABLE_EVENTS) &&
                run(Constants.TABLE_PHOTOS) &&
                run(Constants.TABLE_COMMENTS) &&
                run(Constants.TABLE_FEEDBACKS) &&
                run(Constants.TABLE_OFFERS)){
            log.debug(Constants.SUCCESS_TABLES_CREATING);
        } else {
            log.error(Constants.ERROR_TABLES_CREATING);
        }
    }

    public static void deleteFiles() throws IOException {
        try {
            File theDir = new File(Constants.DB_DIR_PATH);
            if (theDir.exists()) {
                theDir.delete();
                String[] entries = theDir.list();
                for(String s: entries){
                    File currentFile = new File(theDir.getPath(),s);
                    currentFile.delete();
                }
            }
        } catch (Exception e) {
            log.info("An error occurred." + e.getMessage());
        }
    }
}
