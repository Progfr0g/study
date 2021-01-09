package ru.sfedu.photosearch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.sfedu.photosearch.providers.DataProvider;
//import ru.sfedu.photosearch.providers.DataProviderCSV;
//import ru.sfedu.photosearch.providers.DataProviderJDBC;
//import ru.sfedu.photosearch.providers.DataProviderXML;
//import ru.sfedu.photosearch.utils.BaseUtil;
//import ru.sfedu.photosearch.utils.Response;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static Logger log = LogManager.getLogger(Main.class);

    public Main() {
    }

    private static DataProvider chooseDataProvider(String argProvider) {
        switch (argProvider) {
            case Constants.DATAPROVIDER_DB: {
                DataProviderDatabase provider = new DataProviderDatabase();
                provider.DB.connect();
                if (provider != null) return provider;
            }
        }
        log.error(Constants.ERROR_INCORRECTLY_CHOSEN_PROVIDER + argProvider);
        return null;
    }


    private static String chooseMethod(DataProvider provider, List<String> args) {
        switch (args.get(1).toLowerCase()){
            case Constants.M_CREATE_NEW_PROFILE:{
                String name = args.get(2);
                String last_name = args.get(3);
                String age = args.get(4);
                String date_of_registration = LocalDate.now().toString();
                String role = args.get(5).toLowerCase();
                String town = args.get(6);

                return provider.createNewProfile(
                        name,
                        last_name,
                        Integer.valueOf(age),
                        date_of_registration,
                        role,
                        town) + " newProfile";
            }
            case Constants.M_CREATE_NEW_EVENT:{
                String title = args.get(2);
                String description = args.get(3);
                String customer = args.get(4).toLowerCase();
                String event_date = LocalDate.now().toString();
                String price = args.get(5);
                String quantity = args.get(6);

                return provider.createNewEvent(
                        title,
                        description,
                        customer,
                        event_date,
                        Integer.valueOf(price),
                        Float.valueOf(quantity)) + " newEvent";
            }

//            case Constants.M_EDIT_PROFILE: {
//
//            }
//            case Constants.M_EDIT_EVENT: {
//
//            }
//            case Constants.M_GET_PROFILE: {
//
//            }
//            case Constants.M_GET_EVENT: {
//
//            }
//            case Constants.M_DELETE_PROFILE: {
//
//            }
//            case Constants.M_DELETE_EVENT: {
//
//            }
//            case Constants.M_ADD_PHOTO: {
//
//            }
//            case Constants.M_ADD_COMMENT: {
//
//            }
//            case Constants.M_ADD_RATE: {
//
//            }
//            case Constants.M_MARK_PHOTOGRAPHER: {
//
//            }
//            case Constants.M_SEARCH_PHOTOGRAPHER: {
//
//            }

            default:
                throw new IllegalStateException("Unexpected value: " + args.get(1));
        }
    }



    public static void main(String[] args) {
        try {
            List<String> listArgs = Arrays.asList(args);
            if (listArgs.size() == 0) {
                log.error(Constants.ERROR_EMPTY_INPUT_ARGS);
            } else {
                String argProvider = listArgs.get(0);
                DataProvider provider = chooseDataProvider(argProvider);
                if (provider != null){
                    String answerMsg = chooseMethod(provider, listArgs);
                    if (answerMsg != null) log.info(answerMsg.toString());
                }
            }
            System.exit(1);
        } catch (ArrayIndexOutOfBoundsException e) {
            log.error(Constants.ERROR_INCORRECT_INPUT_ARGS);
            System.exit(1);
        }
    }

//    Database db = new Database();
//    public static final Logger log = LogManager.getLogger(App.class);
//
//    /**
//     * Query that create table.
//     */
//    private static final String CREATE_USERS =
//                    "CREATE TABLE IF NOT EXISTS USERS (" +
//                    "id IDENTITY, " +
//                    "name VARCHAR(30)," +
//                    "last_name VARCHAR(30)," +
//                    "age INT," +
//                    "date_of_registration DATE," +
//                    "role ENUM('customer', 'executor')," +
//                    "town VARCHAR(100)," +
//                    "rating FLOAT(1))";
//    private static final String CREATE_EVENTS =
//            "CREATE TABLE IF NOT EXISTS EVENTS (" +
//                    "id IDENTITY, " +
//                    "title VARCHAR(100)," +
//                    "description VARCHAR," +
//                    "customer INT," +
//                    "executor INT," +
//                    "event_date DATE," +
//                    "price INT," +
//                    "quantity FLOAT(2)," +
//                    "payment_method ENUM('cash', 'online_payment')," +
//                    "paid BOOLEAN DEFAULT FALSE," +
//                    "payment_date DATE," +
//                    "status ENUM('uncompleted', 'completed')," +
//                    "foreign key (customer) references USERS(id)," +
//                    "foreign key (executor) references USERS(id)" +
//                    ")";
//    private static final String CREATE_PHOTOS =
//            "CREATE TABLE IF NOT EXISTS PHOTOS (" +
//                    "id IDENTITY, " +
//                    "user_id INT," +
//                    "event_id INT," +
//                    "title VARCHAR(100)," +
//                    "description VARCHAR," +
//                    "tag VARCHAR(50)," +
//                    "photo_path VARCHAR," +
//                    "rating INT," +
//                    "foreign key (user_id) references USERS(id)," +
//                    "foreign key (event_id) references EVENTS(id)" +
//                    ")";
//    private static final String CREATE_COMMENTS =
//            "CREATE TABLE IF NOT EXISTS PHOTOS (" +
//                    "id IDENTITY, " +
//                    "user_id INT," +
//                    "photo_id INT," +
//                    "text VARCHAR," +
//                    "date DATE," +
//                    "foreign key (user_id) references USERS(id)," +
//                    "foreign key (photo_id) references PHOTOS(id)" +
//                    ")";
//    private static final String CREATE_RATES =
//            "CREATE TABLE IF NOT EXISTS RATES (" +
//                    "id IDENTITY, " +
//                    "user_id INT," +
//                    "photo_id INT," +
//                    "rate FLOAT(2)," +
//                    "date DATE," +
//                    "foreign key (user_id) references USERS(id)," +
//                    "foreign key (photo_id) references PHOTOS(id)" +
//                    ")";
//    /**
//     * Query that populates table with data.
//     */
//    private static final String DATA_QUERY =
//            "INSERT INTO USERS (name, last_name, age, role) VALUES ('Evgeniy','Govor', 21, 'customer')";
//
//    /**
//     * Do not construct me.
//     */
//    private App() {
//    }
//
//    /**
//     * Entry point.
//     *
//     * @param args Commans line args. Not used.
//     */
}
