package ru.sfedu.photosearch;

public class Constants2 {
    public static final String DATAPROVIDER_DB = "dataprovider.db";
    public static final String DATAPROVIDER_CSV = "dataprovider.db";
    public static final String DATAPROVIDER_XML = "dataprovider.db";

    public static final String CONFIG_PATH = "config.path";


//  Queries for create tables
    public static final String DB_FILE_PATH = "jdbc:h2:/dbFile/photosearch";
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
    public static final String M_DELETE_PROFILE = "GET_PROFILE";
    public static final String M_DELETE_EVENT = "GET_EVENT";
    public static final String M_ADD_PHOTO = "ADD_PHOTO";
    public static final String M_ADD_COMMENT = "ADD_COMMENT";
    public static final String M_ADD_RATE = "ADD_RATE";
    public static final String M_MARK_PHOTOGRAPHER = "MARK_PHOTOGRAPHER";
    public static final String M_SEARCH_PHOTOGRAPHER = "SEARCH_PHOTOGRAPHER";

    public static final String INSERT_USERS_QUERY = "INSERT INTO USERS (name, last_name, age, date_of_registration, role, town) VALUES (%s,%s,%s,%s,%s,%s)";
    public static final String INSERT_EVENTS_QUERY = "INSERT INTO EVENTS " +
            "(title, description, customer, event_date, price, quantity) " +
            "VALUES (%s,%s,%s,%s,%s,%s)";
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
//    public static final String;
//    public static final String;
//    public static final String;
//    public static final String;
//    public static final String;
//    public static final String;
//    public static final String
//    public static final String;
//    public static final String;
//    public static final String;
//    public static final String;

    public static final String ERROR_INCORRECTLY_CHOSEN_PROVIDER = "Dataprovider was chosen incorrectly: ";
    public static final String ERROR_EMPTY_INPUT_ARGS = "Error. Input args is empty.";
    public static final String ERROR_INCORRECT_INPUT_ARGS = "Error. Input args is incorrect.";

}
