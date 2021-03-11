package ru.sfedu.photosearch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.sfedu.photosearch.enums.EventType;
import ru.sfedu.photosearch.enums.Role;
import ru.sfedu.photosearch.Models.*;
import ru.sfedu.photosearch.providers.DataProvider;
import ru.sfedu.photosearch.providers.DataProviderCSV;
import ru.sfedu.photosearch.providers.DataProviderDatabase;
import ru.sfedu.photosearch.providers.DataProviderXML;
import ru.sfedu.photosearch.utils.CSV_util;
import ru.sfedu.photosearch.utils.Formatter;
import ru.sfedu.photosearch.utils.PhotoViewer;
import ru.sfedu.photosearch.utils.XML_util;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 *  Main class where program starts working
 */
public class Main {
    private static Logger log = LogManager.getLogger(Main.class);

    public Main() {
    }

    /**
     * Возвращает провайдер в соответствии с входными параметрами из коммандной строки
     * @param argProvider
     * @return provider одного из трех типов (DataProviderXML, DataProviderCSV, DataProviderDB)
     *
     * @see DataProviderDatabase
     * @see DataProviderCSV
     * @see DataProviderXML
     * @see DataProvider - интерфейс дата-провайдера
     */
    private static DataProvider chooseDataProvider(String argProvider) throws IOException {
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

    /**
     * Данный метод вызывает нужный метод взаимодействия с API, в соответствии
     * с входящими параметрами из командной строки
     * @param provider
     * @param args
     * @return Boolean значение. True - если метод отработал без ошибок
     *                           False - если при выполнении возникли ошибки
     */
    public static Boolean chooseMethod(DataProvider provider, List<String> args) {
        switch (args.get(1)) {
            case Constants.M_CREATE_NEW_PROFILE: {
                try {
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
                    if (result) {
                        log.info(Constants.SUCCESS_NEW_PROFILE);
                        return true;
                    } else {
                        log.error(Constants.FAILURE + args.get(1));
                        return false;
                    }
                } catch (Exception ex){
                    log.error(ex);
                    return false;
                }

            }
            case Constants.M_GET_PROFILE: {
                String id = args.get(2);
                Optional<User> result = provider.getProfile(id);
                if (result.isPresent()) {
                    log.debug(String.format(Constants.SUCCESS_GET_PROFILE, id));
                    log.info(result.get().getUserOutput());
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
                Optional<User> customer = provider.getProfile(id.toLowerCase());
                if (customer.isPresent()){
                    Boolean result = provider.editProfileById(id, field, value);
                    if (result) {
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
                Optional<User> customer = provider.getProfile(id.toLowerCase());;
                if (customer.isPresent()){
                    Boolean result = provider.deleteProfileById(id);
                    if (result) {
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
                Optional<User> customer = provider.getProfile(args.get(4).toLowerCase());
                Date eventDate = Formatter.normalFormatDay(args.get(5));
                Date creationDate = Formatter.localAsDate(LocalDate.now());
                Integer price = Integer.parseInt(args.get(6));
                Float quantity = Float.parseFloat(args.get(7));
                EventType type = null;
                if (customer.isPresent()){
                    if (customer.get().getRole() == Role.CUSTOMER){
                        type = EventType.ORDER;
                    }else if (customer.get().getRole() == Role.PHOTOGRAPHER) {
                        type = EventType.OFFER;
                    }
                } else {
                    return false;
                }
                Boolean result = provider.createNewEvent(
                        title,
                        description,
                        customer.get().getId().toString(),
                        eventDate,
                        creationDate,
                        price,
                        quantity,
                        type);
                if (result) {
                    log.info(Constants.SUCCESS_NEW_EVENT);
                    return true;
                } else {
                    log.error(Constants.FAILURE + args.get(1));
                    return false;
                }
            }
            case Constants.M_GET_EVENT: {
                String id = args.get(2);
                Optional<Event> result = provider.getEvent(id);
                if (result.isPresent()) {
                    log.debug(String.format(Constants.SUCCESS_GET_EVENT, id));
                    log.info(result.get().getEventOutput());
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
                Optional<Event> event = provider.getEvent(id.toLowerCase());
                if (event.isPresent()){
                    if (field.equals(Constants.EVENTS_CUSTOMER)){
                        Optional<User> user = provider.getProfile(value.toLowerCase());
                        if (user.isPresent()){
                            Boolean result = provider.editEventById(id, field, value);
                            if (result) {
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
                        Optional<User> user = provider.getProfile(value.toLowerCase());
                        if (user.isPresent()){
                            Boolean result = provider.editEventById(id, field, value);
                            if (result) {
                                log.info(String.format(Constants.SUCCESS_UPDATE_EVENT, id));
                                return true;
                            } else {
                                log.error(Constants.FAILURE + args.get(1));
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        Boolean result = provider.editEventById(id, field, value);
                        if (result) {
                            log.info(String.format(Constants.SUCCESS_UPDATE_EVENT, id));
                            return true;
                        } else {
                            log.error(Constants.FAILURE + args.get(1));
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            }
            case Constants.M_DELETE_EVENT: {
                String id = args.get(2);
                Optional<Event> event = provider.getEvent(id.toLowerCase());;
                if (event.isPresent()){
                    Boolean result = provider.deleteEventById(id);
                    if (result) {
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
                Optional<User> user = provider.getProfile(userId.toLowerCase());
                String path = args.get(3);
                if (user.isPresent()){
                    Boolean result = provider.addPhoto(userId, path);
                    if (result) {
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
                Optional<Photo> photo = provider.getPhoto(id.toLowerCase());
                if (photo.isPresent()){
                    if (field.equals(Constants.PHOTOS_USER)){
                        Optional<User> user = provider.getProfile(value.toLowerCase());
                        if (user.isPresent()){
                            Boolean result = provider.editPhotoById(id, field, value);
                            if (result) {
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
                        Optional<Event> event = provider.getEvent(value.toLowerCase());
                        if (event.isPresent()){
                            Boolean result = provider.editPhotoById(id, field, value);
                            if (result) {
                                log.info(String.format(Constants.SUCCESS_UPDATE_PHOTO, id));
                                return true;
                            } else {
                                log.error(Constants.FAILURE + args.get(1));
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        Boolean result = provider.editPhotoById(id, field, value);
                        if (result) {
                            log.info(String.format(Constants.SUCCESS_UPDATE_PHOTO, id));
                            return true;
                        } else {
                            log.error(Constants.FAILURE + args.get(1));
                            return false;
                        }
                    }
                } else {
                    log.error(Constants.FAILURE + args.get(1));
                    return false;
                }
            }
            case Constants.M_GET_PHOTO: {
                String id = args.get(2);
                Optional<Photo> result = provider.getPhoto(id);
                if (result.isPresent()) {
                    log.debug(String.format(Constants.SUCCESS_GET_PHOTO, id));
                    log.info(result.get().getPhotoOutput());
                    return true;
                } else {
                    log.error(Constants.FAILURE + args.get(1));
                    return false;
                }
            }
            case Constants.M_DELETE_PHOTO: {
                String id = args.get(2);
                Optional<Photo> photo = provider.getPhoto(id.toLowerCase());;
                if (photo.isPresent()){
                    Boolean result = provider.deletePhotoById(id);
                    if (result) {
                        log.info(String.format(Constants.SUCCESS_DELETE_PHOTO, id));
                        return true;
                    } else {
                        log.error(Constants.FAILURE + args.get(1));
                        return false;
                    }
                } else {
                    return false;
                }
            }

            case Constants.M_GET_PORTFOLIO: {
                String userId = args.get(2);
                Optional<ArrayList<Photo>> result = provider.getPortfolio(userId);
                if (result.isPresent()) {
                    log.debug(String.format(Constants.SUCCESS_GET_PORTFOLIO, userId));
                    for (Photo photo: result.get()){
                        log.info(photo.getPhotoOutput());
                    }
                    return true;
                } else {
                    log.error(Constants.FAILURE + args.get(1));
                    return false;
                }
            }

            case Constants.M_SHOW_PHOTO: {
                String id = args.get(2);
                String result = provider.getPhotoPathById(id);
                if (result != null) {
                    Boolean showResult = PhotoViewer.showPhoto(result);
                    if (showResult) {
                        return true;
                    } else {
                        log.error(String.format(Constants.ERROR_SHOW_PHOTO, id));
                        log.error(Constants.FAILURE + args.get(1));
                        return false;
                    }
                } else {
                    log.error(Constants.FAILURE + args.get(1));
                    return false;
                }
            }

            case Constants.M_GET_ALL_USERS: {
                Optional<ArrayList<User>> result = provider.getAllUsers();
                if (result.isPresent()) {
                    result.get().forEach(x->log.info(x.getUserOutput()));
                    log.info(Constants.SUCCESS_GET_ALL_USERS);
                    return true;
                } else {
                    log.error(Constants.FAILURE + args.get(1));
                    return false;
                }
            }
            case Constants.M_GET_ALL_EVENTS: {
                Optional<ArrayList<Event>> result = provider.getAllEvents();
                if (result.isPresent()) {
                    result.get().forEach(x->log.info(x.getEventOutput()));
                    log.info(Constants.SUCCESS_GET_ALL_EVENTS);
                    return true;
                } else {
                    log.error(Constants.FAILURE + args.get(1));
                    return false;
                }
            }
            case Constants.M_GET_ALL_PHOTOS: {
                Optional<ArrayList<Photo>> result = provider.getAllPhotos();
                if (result.isPresent()) {
                    result.get().forEach(x->log.info(x.getPhotoOutput()));
                    log.info(Constants.SUCCESS_GET_ALL_PHOTOS);
                    return true;
                } else {
                    log.error(Constants.FAILURE + args.get(1));
                    return false;
                }
            }
            case Constants.M_ADD_COMMENT: {
                String userId = args.get(2);
                String photoId = args.get(3);
                Optional<User> user = provider.getProfile(userId.toLowerCase());
                Optional<Photo> photo = provider.getPhoto(photoId.toLowerCase());
                String text = args.get(4);
                Date creationDate = Formatter.localAsDate(LocalDate.now());
                if (user.isPresent() && photo.isPresent()){
                    Boolean result = provider.addComment(userId,
                            photo.get().getId().toString(),
                            text,
                            creationDate);
                    if (result) {
                        log.info(String.format(Constants.SUCCESS_ADD_COMMENT, photoId));
                        return true;
                    } else {
                        log.error(Constants.FAILURE + args.get(1));
                        return false;
                    }
                } else {
                    log.error(Constants.FAILURE + args.get(1));
                    return false;
                }
            }
            case Constants.M_GET_ALL_COMMENTS: {
                String photoId = args.get(2);
                Optional<ArrayList<Comment>> result = provider.getAllCommentsById(photoId);
                if (result.isPresent()) {
                    result.get().forEach(x->log.info(x.getCommentOutput()));
                    log.info(Constants.SUCCESS_GET_ALL_COMMENTS);
                    return true;
                } else {
                    log.error(Constants.FAILURE + args.get(1));
                    return false;
                }
            }
            case Constants.M_SEARCH_USERS: {
                String field = args.get(2);
                String value = args.get(3);
                Optional<ArrayList<User>> result = provider.searchUsers(field, value);
                if (result.isPresent()) {
                    result.get().forEach(x->log.info(x.getUserOutput()));
                    log.info(Constants.SUCCESS_SEARCH_USERS);
                    return true;
                } else {
                    log.error(Constants.FAILURE + args.get(1));
                    return false;
                }
            }
            case Constants.M_SEARCH_PHOTOGRAPHERS: {
                String field = args.get(2);
                String value = args.get(3);
                Optional<ArrayList<User>> result = provider.searchPhotographers(field, value);
                if (result.isPresent()) {
                    result.get().forEach(x->log.info(x.getUserOutput()));
                    log.info(Constants.SUCCESS_SEARCH_USERS);
                    return true;
                } else {
                    log.error(Constants.FAILURE + args.get(1));
                    return false;
                }
            }
            case Constants.M_SEARCH_EVENTS: {
                String field = args.get(2);
                String value = args.get(3);
                Optional<ArrayList<Event>> result = provider.searchEvents(field, value);
                if (result.isPresent()) {
                    result.get().forEach(x->log.info(x.getEventOutput()));
                    log.info(Constants.SUCCESS_SEARCH_EVENTS);
                    return true;
                } else {
                    log.error(Constants.FAILURE + args.get(1));
                    return false;
                }
            }
            case Constants.M_ADD_RATE: {
                String userId = args.get(2);
                String photoId = args.get(3);
                Optional<User> user = provider.getProfile(userId.toLowerCase());
                Optional<Photo> photo = provider.getPhoto(photoId.toLowerCase());
                Integer rate = Integer.parseInt(args.get(4));
                Date creationDate = Formatter.localAsDate(LocalDate.now());
                if (user.isPresent() && photo.isPresent()){
                    Boolean result = provider.addRate(userId,
                            photo.get().getId().toString(),
                            rate,
                            creationDate);
                    if (!result.equals(false)) {
                        log.info(String.format(Constants.SUCCESS_ADD_RATE, userId));
                        return true;
                    } else {
                        log.error(Constants.FAILURE + args.get(1));
                        return false;
                    }
                } else {
                    log.error(Constants.FAILURE + args.get(1));
                    return false;
                }
            }
            case Constants.M_GET_ALL_RATES: {
                String photoId = args.get(2);
                Optional<ArrayList<Rate>> result = provider.getAllRatesById(photoId);
                if (result.isPresent()) {
                    result.get().forEach(x->log.info(x.getRateOutput()));
                    log.info(Constants.SUCCESS_GET_ALL_RATES);
                    return true;
                } else {
                    log.error(Constants.FAILURE + args.get(1));
                    return false;
                }
            }
            case Constants.M_ADD_FEEDBACK: {
                String userId = args.get(2);
                String photographerId = args.get(3);
                Integer rate = Integer.parseInt(args.get(4));
                String text = args.get(5);
                Optional<User> user = provider.getProfile(userId.toLowerCase());
                Optional<User> photographer = provider.getProfile(photographerId.toLowerCase());
                Date creationDate = Formatter.localAsDate(LocalDate.now());
                if (user.isPresent() && photographer.isPresent()){
                    Boolean result = provider.addFeedback(userId,
                            photographer.get().getId().toString(),
                            rate,text,
                            creationDate);
                    if (result) {
                        log.info(String.format(Constants.SUCCESS_ADD_FEEDBACK, photographerId));
                        return true;
                    } else {
                        log.error(Constants.FAILURE + args.get(1));
                        return false;
                    }
                } else {
                    log.error(Constants.FAILURE + args.get(1));
                    return false;
                }
            }
            case Constants.M_GET_ALL_FEEDBACKS: {
                String photographerId = args.get(2);
                Optional<ArrayList<Feedback>> result = provider.getAllFeedbacksById(photographerId);
                if (result.isPresent()) {
                    result.get().forEach(x->log.info(x.getFeedbackOutput()));
                    log.info(Constants.SUCCESS_GET_ALL_FEEDBACKS);
                    return true;
                } else {
                    log.error(Constants.FAILURE + args.get(1));
                    return false;
                }
            }
            case Constants.M_CREATE_OFFER: {
                String userId = args.get(2);
                String eventId = args.get(3);
                String photographerId = args.get(4);
                Boolean isActive = true;
                Optional<User> user = provider.getProfile(userId.toLowerCase());
                Optional<Event> event = provider.getEvent(eventId.toLowerCase());
                Optional<User> photographer = provider.getProfile(photographerId.toLowerCase());
                Date creationDate = Formatter.localAsDate(LocalDate.now());
                if (user.isPresent() && photographer.isPresent() && event.isPresent()){
                    Boolean result = provider.createOffer(userId,
                            event.get().getId().toString(),
                            photographer.get().getId().toString(),
                            isActive,
                            creationDate);
                    if (result) {
                        log.info(String.format(Constants.SUCCESS_ADD_OFFER, eventId));
                        return true;
                    } else {
                        log.error(Constants.FAILURE + args.get(1));
                        return false;
                    }
                } else {
                    log.error(Constants.FAILURE + args.get(1));
                    return false;
                }
            }
            case Constants.M_GET_ALL_OFFERS: {
                String eventId = args.get(2);
                Optional<ArrayList<Offer>> result = provider.getAllOffersById(eventId);
                if (result.isPresent()) {
                    result.get().forEach(x->log.info(x.getOfferOutput()));
                    log.info(Constants.SUCCESS_GET_ALL_OFFERS);
                    return true;
                } else {
                    log.error(Constants.FAILURE + args.get(1));
                    return false;
                }
            }
            default:
                log.error("Unexpected method: " + args.get(1));
                return false;
        }
    }

    /**
     * Главная точка входа в приложение. Здесь параметры, полученные из командной строки используются
     * для вызова нужного дата-провайдера и соответствующего метода API
     * @param args
     */
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
        } catch (ArrayIndexOutOfBoundsException | IOException e) {
            log.error(Constants.ERROR_INCORRECT_INPUT_ARGS);
            System.exit(1);
        }
    }
}
