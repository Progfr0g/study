package ru.sfedu.photosearch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.sfedu.photosearch.providers.DataProvider;
import ru.sfedu.photosearch.providers.DataProviderCSV;
import ru.sfedu.photosearch.providers.DataProviderDatabase;
import ru.sfedu.photosearch.providers.DataProviderXML;
import ru.sfedu.photosearch.utils.CSV_util;
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
            case Constants.DATAPROVIDER_CSV: {
                DataProviderCSV provider = new DataProviderCSV();
                CSV_util.createFiles();
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
                String id = provider.createNewProfile(
                        name,
                        last_name,
                        Integer.valueOf(age),
                        date_of_registration,
                        role,
                        town);
                if (id != null) {
                    return Constants.SUCCESS_NEW_PROFILE;
                } else return Constants.FAILURE;
            }
            case Constants.M_CREATE_NEW_EVENT:{
                String title = args.get(2);
                String description = args.get(3);
                String customer = args.get(4).toLowerCase();
                String event_date = LocalDate.now().toString();
                String price = args.get(5);
                String quantity = args.get(6);

                String id = provider.createNewEvent(
                        title,
                        description,
                        customer,
                        event_date,
                        Integer.valueOf(price),
                        Float.valueOf(quantity));
                if (id != null) {
                    return Constants.SUCCESS_NEW_EVENT;
                } else return Constants.FAILURE;
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
                if (provider.addPhoto(id, path) != null){
                    return String.format(Constants.SUCCESS_ADD_PHOTO, id);
                }else return Constants.FAILURE;
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
}
