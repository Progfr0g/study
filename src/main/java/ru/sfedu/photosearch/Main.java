package ru.sfedu.photosearch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.sfedu.photosearch.providers.DataProvider;
import ru.sfedu.photosearch.providers.DataProviderDatabase;
import ru.sfedu.photosearch.providers.DataProviderXML;
import ru.sfedu.photosearch.utils.PhotoViewer;
import ru.sfedu.photosearch.utils.XML_util;
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
                provider.DB.createTables();
                if (provider != null) return provider;
            }
            case Constants.DATAPROVIDER_XML: {
                DataProviderXML provider = new DataProviderXML();
                XML_util.createFiles();
                if (provider != null) return provider;
            }
        }
        log.error(Constants.ERROR_INCORRECTLY_CHOSEN_PROVIDER + argProvider);
        return null;
    }


    private static String chooseMethod(DataProvider provider, List<String> args) {

        switch (args.get(1)){
            case Constants.M_CREATE_NEW_PROFILE:{
                String name = args.get(2);
                String last_name = args.get(3);
                String age = args.get(4);
                String date_of_registration = LocalDate.now().toString();
                String role = args.get(5).toLowerCase();
                String town = args.get(6);
                if (provider.createNewProfile(
                        name,
                        last_name,
                        Integer.valueOf(age),
                        date_of_registration,
                        role,
                        town)) {
                    return Constants.SUCCESS_NEW_PROFILE;
                } else return null;
            }
            case Constants.M_CREATE_NEW_EVENT:{
                String title = args.get(2);
                String description = args.get(3);
                String customer = args.get(4).toLowerCase();
                String event_date = LocalDate.now().toString();
                String price = args.get(5);
                String quantity = args.get(6);

                if (provider.createNewEvent(
                        title,
                        description,
                        customer,
                        event_date,
                        Integer.valueOf(price),
                        Float.valueOf(quantity))){
                    return Constants.SUCCESS_NEW_EVENT;
                }else return null;
            }
            case Constants.M_GET_PROFILE: {
                String id = args.get(2);
                return provider.getProfile(id);
            }
            case Constants.M_GET_EVENT: {
                String id = args.get(2);
                return provider.getEvent(id);
            }
            case Constants.M_EDIT_PROFILE: {
                String id = args.get(2);
                String field = args.get(3);
                String value = args.get(4);
                provider.editProfileById(id, field, value);
                return String.format(Constants.SUCCESS_UPDATE_PROFILE, id);
            }
            case Constants.M_EDIT_EVENT: {
                String id = args.get(2);
                String field = args.get(3);
                String value = args.get(4);
                provider.editEventById(id, field, value);
                return String.format(Constants.SUCCESS_UPDATE_EVENT, id);
            }

            case Constants.M_DELETE_PROFILE: {
                String id = args.get(2);
                provider.deleteProfileById(id);
                return String.format(Constants.SUCCESS_DELETE_PROFILE, id);

            }
            case Constants.M_DELETE_EVENT: {
                String id = args.get(2);
                provider.deleteEventById(id);
                return String.format(Constants.SUCCESS_DELETE_EVENT, id);
            }
            case Constants.M_ADD_PHOTO: {
                String id = args.get(2);
                String path = args.get(3);
                provider.addPhoto(id, path);
                return String.format(Constants.SUCCESS_ADD_PHOTO, id);
            }
            case Constants.M_EDIT_PHOTO: {
                String id = args.get(2);
                String field = args.get(3);
                String value = args.get(3);
                provider.editPhotoById(id, field, value);
                return String.format(Constants.SUCCESS_UPDATE_PHOTO, id);
            }
            case Constants.M_GET_PHOTO: {
                String id = args.get(2);
                return provider.getPhoto(id);
            }
            case Constants.M_DELETE_PHOTO: {
                String id = args.get(2);
                provider.deletePhotoById(id);
                return String.format(Constants.SUCCESS_DELETE_PHOTO, id);
            }

            case Constants.M_GET_PORTFOLIO: {
                String user_id = args.get(2);
                return provider.getPortfolio(user_id);
            }

            case Constants.M_SHOW_PHOTO: {
                String id = args.get(2);
                String path = provider.getPhotoPathById(id);
                PhotoViewer.showPhoto(path);
                return String.format(Constants.SUCCESS_GET_PHOTO, id);
            }
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
                log.info(Constants.APP_STARTING);
                log.info(Constants.DP_STARTING);
                DataProvider provider = chooseDataProvider(argProvider);
                if (provider != null){
                    log.info(Constants.SUCCESS_APP_STARTING);
                    String answerMsg = chooseMethod(provider, listArgs);
                    if (answerMsg != null) log.info(answerMsg);
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
