package ru.sfedu.photosearch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.sfedu.photosearch.enums.EventType;
import ru.sfedu.photosearch.enums.Role;
import ru.sfedu.photosearch.newModels.Event;
import ru.sfedu.photosearch.newModels.Photo;
import ru.sfedu.photosearch.newModels.User;
import ru.sfedu.photosearch.providers.DataProvider;
//import ru.sfedu.photosearch.providers.DataProviderCSV;
import ru.sfedu.photosearch.providers.DataProviderDatabase;
//import ru.sfedu.photosearch.providers.DataProviderXML;
import ru.sfedu.photosearch.utils.Formatter;
//import ru.sfedu.photosearch.providers.DataProviderCSV;
//import ru.sfedu.photosearch.providers.DataProviderJDBC;
//import ru.sfedu.photosearch.providers.DataProviderXML;
//import ru.sfedu.photosearch.utils.BaseUtil;
//import ru.sfedu.photosearch.utils.Response;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
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
//            case Constants.DATAPROVIDER_XML: {
//                DataProviderXML provider = new DataProviderXML();
//                XML_util.createFiles();
//                if (provider != null) return provider;
//            }
//            case Constants.DATAPROVIDER_CSV: {
//                DataProviderCSV provider = new DataProviderCSV();
//                CSV_util.createFiles();
//                if (provider != null) return provider;
//            }
        }
        log.error(Constants.ERROR_INCORRECTLY_CHOSEN_PROVIDER + argProvider);
        return null;
    }


    public static Boolean chooseMethod(DataProvider provider, List<String> args) {

        switch (args.get(1)) {
            case Constants.M_CREATE_NEW_PROFILE: {
                String name = args.get(2);
                String lastName = args.get(3);
                Date birthDay = Formatter.normalFormatDay(args.get(4));
                Date dateOfRegistration = Formatter.localAsDate(LocalDate.now());
                Role role = Role.valueOf(args.get(5).toUpperCase());
                String town = args.get(6);
                Boolean result = provider.createNewProfile(
                        name,
                        lastName,
                        birthDay,
                        dateOfRegistration,
                        role,
                        town);
                if (result != false) {
                    log.info(Constants.SUCCESS_NEW_PROFILE);
                    return true;
                } else {
                    log.error(Constants.FAILURE + args.get(1));
                    return false;
                }
            }
            case Constants.M_GET_PROFILE: {
                String id = args.get(2);
                User result = provider.getProfile(id);
                if (result != null) {
                    log.debug(String.format(Constants.SUCCESS_GET_PROFILE, id));
                    log.info(result.getUserOutput());
                    return true;
                } else {
                    log.error(Constants.FAILURE + args.get(1));
                    return false;
                }
            }
            case Constants.M_EDIT_PROFILE: {
                String id = args.get(2);
                String field = args.get(3);
                String value = args.get(4);
                User customer = provider.getProfile(id.toLowerCase());;
                if (customer != null){
                    Boolean result = provider.editProfileById(id, field, value);
                    if (result != false) {
                        log.info(String.format(Constants.SUCCESS_UPDATE_PROFILE, id));
                        return true;
                    } else {
                        log.error(Constants.FAILURE + args.get(1));
                        return false;
                    }
                } else {
                    return false;
                }
            }
            case Constants.M_DELETE_PROFILE: {
                String id = args.get(2);
                User customer = provider.getProfile(id.toLowerCase());;
                if (customer != null){
                    Boolean result = provider.deleteProfileById(id);
                    if (result != false) {
                        log.info(String.format(Constants.SUCCESS_DELETE_PROFILE, id));
                        return true;
                    } else {
                        log.error(Constants.FAILURE + args.get(1));
                        return false;
                    }
                } else {
                    return false;
                }
            }
            case Constants.M_CREATE_NEW_EVENT:{
                String title = args.get(2);
                String description = args.get(3);
                User customer = provider.getProfile(args.get(4).toLowerCase());
                Date eventDate = Formatter.normalFormatDay(args.get(5));
                Date creationDate = Formatter.localAsDate(LocalDate.now());
                Integer price = Integer.parseInt(args.get(6));
                Float quantity = Float.parseFloat(args.get(7));
                EventType type = null;
                if (customer != null){
                    if (customer.getRole() == Role.CUSTOMER){
                        type = EventType.ORDER;
                    }else if (customer.getRole() == Role.EXECUTOR) {
                        type = EventType.OFFER;
                    }
                } else {
                    return false;
                }
                Boolean result = provider.createNewEvent(
                        title,
                        description,
                        customer.getId().toString(),
                        eventDate,
                        creationDate,
                        price,
                        quantity,
                        type);
                if (result != false) {
                    log.info(Constants.SUCCESS_NEW_EVENT);
                    return true;
                } else {
                    log.error(Constants.FAILURE + args.get(1));
                    return false;
                }
            }
            case Constants.M_GET_EVENT: {
                String id = args.get(2);
                Event result = provider.getEvent(id);
                if (result != null) {
                    log.debug(String.format(Constants.SUCCESS_GET_EVENT, id));
                    log.info(result.getEventOutput());
                    return true;
                } else {
                    log.error(Constants.FAILURE + args.get(1));
                    return false;
                }
            }
            case Constants.M_EDIT_EVENT: {
                String id = args.get(2);
                String field = args.get(3);
                String value = args.get(4);
                Event event = provider.getEvent(id.toLowerCase());
                if (event != null){
                    if (field.equals(Constants.EVENTS_CUSTOMER)){
                        User user = provider.getProfile(value.toLowerCase());
                        if (user != null){
                            Boolean result = provider.editEventById(id, field, value);
                            if (result != false) {
                                log.info(String.format(Constants.SUCCESS_UPDATE_EVENT, id));
                                return true;
                            } else {
                                log.error(Constants.FAILURE + args.get(1));
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }
                    if (field.equals(Constants.EVENTS_EXECUTOR)){
                        User user = provider.getProfile(value.toLowerCase());
                        if (user != null){
                            Boolean result = provider.editEventById(id, field, value);
                            if (result != false) {
                                log.info(String.format(Constants.SUCCESS_UPDATE_EVENT, id));
                                return true;
                            } else {
                                log.error(Constants.FAILURE + args.get(1));
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
                return false;
            }
            case Constants.M_DELETE_EVENT: {
                String id = args.get(2);
                Event event = provider.getEvent(id.toLowerCase());;
                if (event != null){
                    Boolean result = provider.deleteEventById(id);
                    if (result != false) {
                        log.info(String.format(Constants.SUCCESS_DELETE_EVENT, id));
                        return true;
                    } else {
                        log.error(Constants.FAILURE + args.get(1));
                        return false;
                    }
                } else {
                    return false;
                }
            }
            case Constants.M_ADD_PHOTO: {
                String userId = args.get(2);
                User user = provider.getProfile(userId.toLowerCase());
                String path = args.get(3);
                if (user != null){
                    Boolean result = provider.addPhoto(userId, path);
                    if (result != false) {
                        log.info(String.format(Constants.SUCCESS_ADD_PHOTO, userId));
                        return true;
                    } else {
                        log.error(Constants.FAILURE + args.get(1));
                        return false;
                    }
                } else {
                    return false;
                }
            }
            case Constants.M_EDIT_PHOTO: {
                String id = args.get(2);
                String field = args.get(3);
                String value = args.get(4);
                Photo photo = provider.getPhoto(id.toLowerCase());
                if (photo != null){
                    if (field.equals(Constants.PHOTOS_USER)){
                        User user = provider.getProfile(value.toLowerCase());
                        if (user != null){
                            Boolean result = provider.editPhotoById(id, field, value);
                            if (result != false) {
                                log.info(String.format(Constants.SUCCESS_UPDATE_PHOTO, id));
                                return true;
                            } else {
                                log.error(Constants.FAILURE + args.get(1));
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }
                    if (field.equals(Constants.PHOTOS_EVENT)){
                        Event event = provider.getEvent(value.toLowerCase());
                        if (event != null){
                            Boolean result = provider.editPhotoById(id, field, value);
                            if (result != false) {
                                log.info(String.format(Constants.SUCCESS_UPDATE_PHOTO, id));
                                return true;
                            } else {
                                log.error(Constants.FAILURE + args.get(1));
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
                log.error(Constants.FAILURE + args.get(1));
                return false;
            }
            case Constants.M_GET_PHOTO: {
                String id = args.get(2);
                Photo result = provider.getPhoto(id);
                if (result != null) {
                    log.debug(String.format(Constants.SUCCESS_GET_PHOTO, id));
                    log.info(result.getPhotoOutput());
                    return true;
                } else {
                    log.error(Constants.FAILURE + args.get(1));
                    return false;
                }
            }
//            case Constants.M_DELETE_PHOTO: {
//                String id = args.get(2);
//                provider.deletePhotoById(id);
//                return String.format(Constants.SUCCESS_DELETE_PHOTO, id);
//            }
//
//            case Constants.M_GET_PORTFOLIO: {
//                String user_id = args.get(2);
//                return provider.getPortfolio(user_id);
//            }
//
//            case Constants.M_SHOW_PHOTO: {
//                String id = args.get(2);
//                String path = provider.getPhotoPathById(id);
//                PhotoViewer.showPhoto(path);
//                return String.format(Constants.SUCCESS_GET_PHOTO, id);
//            }

            default:
                throw new IllegalStateException("Unexpected method: " + args.get(1));
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
                    Boolean answerMsg = chooseMethod(provider, listArgs);
                    if (answerMsg == false) log.error(Constants.FAILURE);
                }
            }
            System.exit(1);
        } catch (ArrayIndexOutOfBoundsException e) {
            log.error(Constants.ERROR_INCORRECT_INPUT_ARGS);
            System.exit(1);
        }
    }
}
