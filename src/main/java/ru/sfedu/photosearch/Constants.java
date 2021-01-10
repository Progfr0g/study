package ru.sfedu.photosearch;

public class Constants {
    public static final String DATAPROVIDER_DB = "db";
    public static final String DATAPROVIDER_CSV = "csv";
    public static final String DATAPROVIDER_XML = "xml";

    public static final String CONFIG_PATH = "config.path";


    //  Queries for create tables
    public static final String DB_FILE_PATH = "jdbc:h2:~/dbFile/photosearch";
    public static final String TABLE_USERS =
            "CREATE TABLE IF NOT EXISTS USERS (" +
                    "id IDENTITY, " +
                    "name VARCHAR(30)," +
                    "last_name VARCHAR(30)," +
                    "age INT," +
                    "date_of_registration DATE," +
                    "role ENUM('customer', 'executor')," +
                    "town VARCHAR(100)," +
                    "rating FLOAT(1) default 0.0)";
    public static final String TABLE_EVENTS =
            "CREATE TABLE IF NOT EXISTS EVENTS (" +
                    "id IDENTITY, " +
                    "title VARCHAR(100)," +
                    "description VARCHAR," +
                    "customer INT," +
                    "executor INT," +
                    "event_date DATE," +
                    "price INT," +
                    "quantity FLOAT(2)," +
                    "payment_method ENUM('cash', 'online_payment')," +
                    "paid BOOLEAN DEFAULT FALSE," +
                    "payment_date DATE," +
                    "status ENUM('uncompleted', 'completed') default 'uncompleted'," +
                    "foreign key (customer) references USERS(id)," +
                    "foreign key (executor) references USERS(id)" +
                    ")";
    public static final String TABLE_PHOTOS =
            "CREATE TABLE IF NOT EXISTS PHOTOS (" +
                    "id IDENTITY, " +
                    "user_id INT," +
                    "event_id INT," +
                    "title VARCHAR(100)," +
                    "description VARCHAR," +
                    "tag VARCHAR(50)," +
                    "photo_path VARCHAR," +
                    "rating INT default 0.0," +
                    "foreign key (user_id) references USERS(id)," +
                    "foreign key (event_id) references EVENTS(id)" +
                    ")";
    public static final String TABLE_COMMENTS =
            "CREATE TABLE IF NOT EXISTS PHOTOS (" +
                    "id IDENTITY, " +
                    "user_id INT," +
                    "photo_id INT," +
                    "text VARCHAR," +
                    "date DATE," +
                    "foreign key (user_id) references USERS(id)," +
                    "foreign key (photo_id) references PHOTOS(id)" +
                    ")";
    public static final String TABLE_RATES =
            "CREATE TABLE IF NOT EXISTS RATES (" +
                    "id IDENTITY, " +
                    "user_id INT," +
                    "photo_id INT," +
                    "rate FLOAT(2)," +
                    "date DATE," +
                    "foreign key (user_id) references USERS(id)," +
                    "foreign key (photo_id) references PHOTOS(id)" +
                    ")";

    public static final String M_CREATE_NEW_PROFILE = "CREATE_NEW_PROFILE";
    public static final String M_CREATE_NEW_EVENT = "CREATE_NEW_EVENT";
    public static final String M_EDIT_PROFILE = "EDIT_PROFILE";
    public static final String M_EDIT_EVENT = "EDIT_EVENT";
    public static final String M_GET_PROFILE = "GET_PROFILE";
    public static final String M_GET_EVENT = "GET_EVENT";
    public static final String M_DELETE_PROFILE = "DELETE_PROFILE";
    public static final String M_DELETE_EVENT = "DELETE_EVENT";
    public static final String M_ADD_PHOTO = "ADD_PHOTO";
    public static final String M_ADD_COMMENT = "ADD_COMMENT";
    public static final String M_ADD_RATE = "ADD_RATE";
    public static final String M_MARK_PHOTOGRAPHER = "MARK_PHOTOGRAPHER";
    public static final String M_SEARCH_PHOTOGRAPHER = "SEARCH_PHOTOGRAPHER";

    public static final String INSERT_USERS_QUERY = "INSERT INTO USERS " +
            "(name, last_name, age, date_of_registration, role, town) VALUES ('%s','%s',%s,'%s','%s','%s')";
    public static final String INSERT_EVENTS_QUERY = "INSERT INTO EVENTS " +
            "(title, description, customer, event_date, price, quantity) " +
            "VALUES ('%s','%s',%s,'%s',%s,%s)";
    public static final String SELECT_PROFILE_QUERY = "SELECT * FROM USERS WHERE id = ";
    public static final String SELECT_EVENT_QUERY = "SELECT * FROM EVENTS WHERE id = ";
    public static final String UPDATE_PROFILE_QUERY = "UPDATE %s SET %s = '%s' WHERE id = ";
    public static final String UPDATE_EVENT_QUERY = "UPDATE %s SET %s = '%s' WHERE id = ";
    public static final String DELETE_PROFILE_QUERY = "DELETE FROM %s WHERE id = ";
    public static final String DELETE_EVENT_QUERY = "DELETE FROM %s WHERE id = ";
//    public static final String;
//    public static final String;
//    public static final String;
//    public static final String;
//    public static final String;
//    public static final String;
//    public static final String;
//    public static final String;
//    public static final String;
//    public static final String;
//    public static final String;
//    public static final String;
//    public static final String;
//    public static final String;
//    public static final String;
//    public static final String;'
//    public static final String;
//    public static final String;
//    public static final String;
//    public static final String;
//    public static final String;
    public static final String DEBUG_SUCCESS_INSERT = "[INSERTED %d rows]";
    public static final String DEBUG_SUCCESS_UPDATE = "[UPDATED %d rows]";
    public static final String DEBUG_SUCCESS_DELETE = "[DELETED %d rows]";


    public static final String APP_STARTING = "App starting...";
    public static final String DP_STARTING = "DataProvider initialising...";
    public static final String SUCCESS_APP_STARTING = "---------APP IS WORKING---------";
    public static final String SUCCESS_TABLES_CREATING = "TABLES IS CREATED";
    public static final String SUCCESS_NEW_PROFILE = "****Successfully created new profile!****";
    public static final String SUCCESS_NEW_EVENT = "****Successfully created new event!****";
    public static final String SUCCESS_GET_PROFILE = "****Successfully get profile with id %s !****";
    public static final String SUCCESS_GET_EVENT = "****Successfully get event with id %s !****";
    public static final String SUCCESS_UPDATE_PROFILE = "****Successfully updated profile with id %s !****";
    public static final String SUCCESS_UPDATE_EVENT = "****Successfully updated event with id %s !****";
    public static final String SUCCESS_DELETE_PROFILE = "****Successfully deleted profile with id %s !****";
    public static final String SUCCESS_DELETE_EVENT = "****Successfully deleted event with id %s !****";

    public static final String ERROR_INCORRECTLY_CHOSEN_PROVIDER = "Dataprovider was chosen incorrectly: ";
    public static final String ERROR_EMPTY_INPUT_ARGS = "Input args is empty.";
    public static final String ERROR_INCORRECT_INPUT_ARGS = "Input args is incorrect.";
    public static final String ERROR_GET_PROFILE = "Failed to get profile with id %s. ";
    public static final String ERROR_GET_EVENT = "Failed to get event with id %s. ";
    public static final String ERROR_FAILED_DB_CONNECTION = "Database connection failure: ";
    public static final String ERROR_FAILED_DB_CONNECTION_CLOSE = "Database close connection failure: ";
    public static final String ERROR_SELECT_QUERY = "SELECT query failed to execute: ";
    public static final String ERROR_INSERT_QUERY = "INSERT query failed to execute: ";
    public static final String ERROR_UPDATE_QUERY = "UPDATE query failed to execute: ";
    public static final String ERROR_DELETE_QUERY = "DELETE query failed to execute: ";
    public static final String ERROR_EXECUTE_QUERY = "Query failed to execute: ";

    public static final String EMPTY_GET_PROFILE = "Profile with id %s not exists. Empty response received.";
    public static final String EMPTY_GET_EVENT = "Event with id %s not exists. Empty response received.";

    public static final String UTIL_SPACE = " ";
    public static final String UTIL_NEW_LINE = "\n";
    public static final String UTIL_DOUBLE_DOTS = ":";
    public static final String UTIL_EMPTY_STRING = "";
    public static final String UTIL_R_BOX = "]";
    public static final String UTIL_L_BOX = "[";
    public static final String UTIL_R_FIG = "}";
    public static final String UTIL_L_FIG = "{";


}
