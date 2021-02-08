package ru.sfedu.photosearch;

public class Constants {
    public static final String DATAPROVIDER_DB = "db";
    public static final String DATAPROVIDER_CSV = "csv";
    public static final String DATAPROVIDER_XML = "xml";

    public static final String CONFIG_PATH = "config.path";

    //  Queries for create tables
    public static final String DB_FILE_PATH = "jdbc:h2:./dbFile/photosearch";

    public static final String XML_DIR_PATH = "./xmlFiles";
    public static final String XML_USERS_FILE_PATH = "./xmlFiles/users.xml";
    public static final String XML_EVENTS_FILE_PATH = "./xmlFiles/events.xml";
    public static final String XML_PHOTOS_FILE_PATH = "./xmlFiles/photos.xml";
    public static final String XML_COMMENTS_FILE_PATH = "./xmlFiles/comments.xml";
    public static final String XML_RATES_FILE_PATH = "./xmlFiles/rates.xml";

    public static final String CSV_DIR_PATH = "./csvFiles";
    public static final String CSV_USERS_FILE_PATH = "./csvFiles/users.csv";
    public static final String CSV_EVENTS_FILE_PATH = "./csvFiles/events.csv";
    public static final String CSV_PHOTOS_FILE_PATH = "./csvFiles/photos.csv";
    public static final String CSV_COMMENTS_FILE_PATH = "./csvFiles/comments.csv";
    public static final String CSV_RATES_FILE_PATH = "./csvFiles/rates.csv";

    public static final String CSV_USERS_COLUMNS = "id, name, lastName, age, dateOfRegistration, role, town, rating";
    public static final String CSV_EVENTS_COLUMNS = "id, title, description, customer, executor, event_date, price, quantity, payment_method, paid, payment_date, status";
    public static final String CSV_PHOTOS_COLUMNS = "id, user_id, event_id, title, description, tag, photo_path, rating";
    public static final String CSV_COMMENTS_COLUMNS = "id, user_id, photo_id, text";
    public static final String CSV_RATES_COLUMNS = "id, user_id, photo_id, rate, date";

    public static final String TABLE_USERS =
            "CREATE TABLE IF NOT EXISTS USERS (" +
                    "id IDENTITY, " +
                    "name VARCHAR(30)," +
                    "last_name VARCHAR(30)," +
                    "birthday DATE," +
                    "date_of_registration DATE," +
                    "role ENUM('customer', 'photographer', 'none')," +
                    "town VARCHAR(100)," +
                    "wallet FLOAT(1) default 0.0," +
                    "rating FLOAT(1) default 0.0," +
                    "experience INT default 0," +
                    "costLevel ENUM('cheap', 'average', 'expensive', 'none'))";
    public static final String TABLE_EVENTS =
            "CREATE TABLE IF NOT EXISTS EVENTS (" +
                    "id IDENTITY, " +
                    "title VARCHAR(100)," +
                    "description VARCHAR," +
                    "event_date DATE," +
                    "creation_date DATE," +
                    "price INT," +
                    "quantity FLOAT(2)," +
                    "customer INT," +
                    "executor INT," +
                    "status ENUM('uncompleted', 'completed', 'reservation', 'approved', 'none') default 'none'," +
                    "type ENUM('offer', 'order', 'none') default 'none')";
    public static final String TABLE_PHOTOS =
            "CREATE TABLE IF NOT EXISTS PHOTOS (" +
                    "id IDENTITY, " +
                    "userId INT," +
                    "eventId INT," +
                    "title VARCHAR(100)," +
                    "description VARCHAR," +
                    "tag VARCHAR(50)," +
                    "photoPath VARCHAR)";
    public static final String TABLE_COMMENTS =
            "CREATE TABLE IF NOT EXISTS СOMMENTS (" +
                    "id IDENTITY, " +
                    "userId INT," +
                    "photoId INT," +
                    "comment VARCHAR," +
                    "date DATE)";
    public static final String TABLE_RATES =
            "CREATE TABLE IF NOT EXISTS RATES (" +
                    "id IDENTITY, " +
                    "userId INT," +
                    "photoId INT," +
                    "rate FLOAT(2)," +
                    "date DATE)";

    public static final String M_CREATE_NEW_PROFILE = "CREATE_NEW_PROFILE";
    public static final String M_CREATE_NEW_EVENT = "CREATE_NEW_EVENT";
    public static final String M_EDIT_PROFILE = "EDIT_PROFILE";
    public static final String M_EDIT_EVENT = "EDIT_EVENT";
    public static final String M_GET_PROFILE = "GET_PROFILE";
    public static final String M_GET_EVENT = "GET_EVENT";
    public static final String M_DELETE_PROFILE = "DELETE_PROFILE";
    public static final String M_DELETE_EVENT = "DELETE_EVENT";
    public static final String M_ADD_PHOTO = "ADD_PHOTO";
    public static final String M_GET_PHOTO = "GET_PHOTO";
    public static final String M_EDIT_PHOTO = "EDIT_PHOTO";
    public static final String M_DELETE_PHOTO = "DELETE_PHOTO";
    public static final String M_GET_PORTFOLIO = "GET_PORTFOLIO";
    public static final String M_SHOW_PHOTO = "SHOW_PHOTO";
    public static final String M_ADD_COMMENT = "ADD_COMMENT";
    public static final String M_ADD_RATE = "ADD_RATE";
    public static final String M_MARK_PHOTOGRAPHER = "MARK_PHOTOGRAPHER";
    public static final String M_SEARCH_PHOTOGRAPHER = "SEARCH_PHOTOGRAPHER";

    public static final String INSERT_USERS_QUERY = "INSERT INTO USERS " +
            "(name, last_name, birthday, date_of_registration, role, town) VALUES ('%s','%s',TO_DATE('%s','dd-MM-yyyy'),TO_DATE('%s','dd-MM-yyyy'),'%s','%s')";
    public static final String INSERT_EVENTS_QUERY = "INSERT INTO EVENTS " +
            "(title, description, customer, event_date, creation_date, price, quantity, type) " +
            "VALUES ('%s','%s','%s',TO_DATE('%s','dd-MM-yyyy'),TO_DATE('%s','dd-MM-yyyy'),%s, %s,'%s')";
    public static final String SELECT_PROFILE_QUERY = "SELECT * FROM USERS WHERE id = ";
    public static final String SELECT_EVENT_QUERY = "SELECT * FROM EVENTS WHERE id = ";
    public static final String UPDATE_PROFILE_QUERY = "UPDATE %s SET %s = '%s' WHERE id = ";
    public static final String UPDATE_EVENT_QUERY = "UPDATE %s SET %s = '%s' WHERE id = ";
    public static final String DELETE_PROFILE_QUERY = "DELETE FROM %s WHERE id = ";
    public static final String DELETE_EVENT_QUERY = "DELETE FROM %s WHERE id = ";

    public static final String SELECT_PHOTO_QUERY = "SELECT * FROM PHOTOS WHERE id = ";
    public static final String INSERT_PHOTO_QUERY = "INSERT INTO %s " +
            "(userId, photoPath) " +
            "VALUES ('%s','%s')";
    public static final String UPDATE_PHOTO_QUERY = "UPDATE %s SET %s = '%s' WHERE id = ";
    public static final String DELETE_PHOTO_QUERY = "DELETE FROM %s WHERE id = ";

    public static final String SELECT_COMMENTS_QUERY = "SELECT * FROM СOMMENTS WHERE id = ";
    public static final String INSERT_COMMENTS_QUERY = "DELETE FROM %s WHERE id = ";
    public static final String UPDATE_COMMENTS_QUERY = "UPDATE %s SET %s = '%s' WHERE id = ";
    public static final String DELETE_COMMENTS_QUERY = "DELETE FROM %s WHERE id = ";

    public static final String SELECT_PORTFOLIO_QUERY = "SELECT * FROM PHOTOS WHERE userId = ";
    public static final String SELECT_PHOTO_PATH_QUERY = "SELECT photoPath FROM PHOTOS WHERE id = ";
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
    public static final String SUCCESS_ADD_PHOTO = "****Successfully added photo for profile with id %s !****";
    public static final String SUCCESS_GET_PHOTO = "****Successfully get photo from profile with id %s !****";
    public static final String SUCCESS_UPDATE_PHOTO = "****Successfully updated photo from profile with id %s !****";
    public static final String SUCCESS_DELETE_PHOTO = "****Successfully deleted photo from profile with id %s !****";
    public static final String SUCCESS_GET_PORTFOLIO = "****Successfully get portfolio from profile with id %s !****";
    public static final String SUCCESS_GET_PHOTO_PATH = "****Successfully get photo path from photo with id %s !****";
    public static final String SUCCESS_NEW_PROFILE_XML = "New profile id: %s";
    public static final String SUCCESS_NEW_EVENT_XML = "New event id: %s";
    public static final String SUCCESS_UPDATE_PROFILE_XML = "Updated profile with id: %s";
    public static final String SUCCESS_UPDATE_EVENT_XML = "Updated event with id: %s";
    public static final String SUCCESS_UPDATE_PHOTO_XML = "Updated photo with id: %s";
    public static final String SUCCESS_DELETE_PROFILE_XML = "Deleted profile with id: %s";
    public static final String SUCCESS_DELETE_EVENT_XML = "Deleted event with id: %s";
    public static final String SUCCESS_DELETE_PHOTO_XML = "Deleted photo with id: %s";
    public static final String SUCCESS_NEW_PHOTO_XML = "New photo id: %s";;

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
    public static final String ERROR_GET_PHOTO = "Failed to get photo with id %s. ";
    public static final String ERROR_GET_PORTFOLIO = "Failed to get portfolio with profile id %s. ";
    public static final String ERROR_SHOW_PHOTO = "Failed to show photo with id %s. ";
    public static final String ERROR_GET_PHOTO_PATH = "Failed to get photo path with id %s. ";
    public static final String ERROR_XML_SAME_ID = "Some records in xml_table %s has similar id's";
    public static final String ERROR_CSV_SAME_ID = "Some records in csv_table %s has similar id's";
    public static final String ERROR_XML_EMPTY_FILE = "File %s is empty. Nothing to read.";
    public static final String ERROR_WRONG_FIELD = "Field %s can not be changed. It's not exists.";
    public static final String ERROR_USER_CONVERT = "User convertion from csv to User() has been failed";
    public static final String ERROR_EVENT_CONVERT = "Event convertion from csv to Event() has been failed";
    public static final String ERROR_PHOTO_CONVERT = "Photo convertion from csv to Photo() has been failed";
    public static final String ERROR_COMMENT_CONVERT = "Comment convertion from csv to Comment() has been failed";
    public static final String ERROR_RATE_CONVERT = "Rate convertion from csv to Rate() has been failed";
    public static final String ERROR_FILE_DOESNT_EXISTS = "[The file: %s doesn't exist.]";

    public static final String FAILURE = "Failed to run method.";

    public static final String EMPTY_GET_PROFILE = "[Profile with id %s not exists.] Empty response received.";
    public static final String EMPTY_GET_EVENT = "[Event with id %s not exists.] Empty response received.";
    public static final String EMPTY_GET_PHOTO = "[Photo with id %s not exists.] Empty response received.";
    public static final String EMPTY_GET_PORTFOLIO = "[Portfolio with profile id %s not exists.] Empty response received.";
    public static final String EMPTY_GET_PHOTO_PATH = "[Photo path with photo id %s not exists.] Empty response received.";

    public static final String XML_USERS_OUTPUT = "\nid: %s\nname: %s\nlastName: %s\nbirthDay: %s\ndateOfRegistration: %s\nrole: %s\ntown: %s\nwallet: %s\n";
    public static final String XML_EVENTS_OUTPUT = "\nid: %s\ntitle: %s\ndescription: %s\neventDate: %s\ncreationDate: %s\nprice: %s\nquantity: %s\ncustomer: %s\nexecutor: %s\nstatus: %s\ntype: %s\n";
    public static final String XML_PHOTOS_OUTPUT = "\nid: %s\nuser_id: %s\nevent_id: %s\ntitle: %s\ndescription: %s\ntag: %s\nphoto_path: %s";


    public static final String CSV_USERS_OUTPUT  = "%s,%s,%s,%s,%s,%s,%s,%s";
    public static final String CSV_EVENTS_OUTPUT = "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s";
    public static final String CSV_PHOTOS_OUTPUT = "%s,%s,%s,%s,%s,%s,%s";
//    public static final String CSV_USERS_OUTPUT= "\nid: %s\nname: %s\nlastName: %s\nbirthDay: %s\ndateOfRegistration: %s\nrole: %s\ntown: %s\nwallet: %s\n";
//    public static final String CSV_EVENTS_OUTPUT = "\nid: %s\ntitle: %s\ndescription: %s\neventDate: %s\ncreationDate: %s\nprice: %s\nquantity: %s\ncustomer: %s\nexecutor: %s\nstatus: %s\ntype: %s\n";
//    public static final String CSV_PHOTOS_OUTPUT = "\nid: %s\nuser_id: %s\nevent_id: %s\ntitle: %s\ndescription: %s\ntag: %s\nphoto_path: %s";

    public static final String UTIL_SPACE = " ";
    public static final String UTIL_NEW_LINE = "\n";
    public static final String UTIL_DOUBLE_DOTS = ":";
    public static final String UTIL_EMPTY_STRING = "";
    public static final String UTIL_SEPARATOR = "-------------------------";
    public static final String UTIL_R_BOX = "]";
    public static final String UTIL_L_BOX = "[";
    public static final String UTIL_R_FIG = "}";
    public static final String UTIL_L_FIG = "{";

    public static final String USERS_NAME = "name";
    public static final String USERS_LAST_NAME = "last_name";
    public static final String USERS_BIRTHDAY = "birthday";
    public static final String USERS_EXPERIENCE = "experience";
    public static final String USERS_TOWN = "town";

    public static final String EVENTS_TITLE = "title";
    public static final String EVENTS_DESCRIPTION = "description";
    public static final String EVENTS_PRICE = "price";
    public static final String EVENTS_EXECUTOR = "executor";
    public static final String EVENTS_CUSTOMER = "customer";
    public static final String EVENTS_QUANTITY = "quantity";

    public static final String PHOTOS_TITLE = "title";
    public static final String PHOTOS_USER = "user";
    public static final String PHOTOS_EVENT = "eventId";
    public static final String PHOTOS_DESCRIPTION = "description";
    public static final String PHOTOS_TAG = "tag";
    public static final String PHOTOS_PATH = "path";


    public static final String UTIL_GET_WORD = "get";
    public static final Float DEFAULT_RATING = 0.0f;

    public static final String FILE_CREATED = "File created: ";
    public static final String FILE_EXISTS = "File already exists: ";

}
