package ru.sfedu.photosearch.providers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.Main;
import ru.sfedu.photosearch.Models.Event;
import ru.sfedu.photosearch.Models.Photo;
import ru.sfedu.photosearch.Models.User;
import ru.sfedu.photosearch.utils.CSV_util;
import ru.sfedu.photosearch.utils.ConfigurationUtil;
import ru.sfedu.photosearch.utils.Database;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

class DataProviderDatabaseNegativeTest {
    public static final Logger log = LogManager.getLogger(DataProviderDatabaseNegativeTest.class);
    public static final DataProviderDatabase provider = new DataProviderDatabase();

    @BeforeEach
    void setUp() throws IOException {
        Database.deleteFiles();
        provider.DB.connect();
        provider.DB.createTables();
    }

    @Test
    void createNewProfileNegative() {
        String args = "DB CREATE_NEW_PROFI Sergey Esenin 12-10-1999 customer Moscow";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertFalse(result);

        args = "DB CREATE_NEW_PROFILE Sergey Esenin 12-10-1999 cola Moscow";
        result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertFalse(result);
    }

    @Test
    void getProfileNegative() {
        String args = "DB GET_PROFILE 5000";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertFalse(result);
    }

    @Test
    void editProfileByIdNegative() {
        String args = "DB EDIT_PROFILE 5000 name Alexey";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertFalse(result);
    }

    @Test
    void deleteProfileByIdNegative() {
        String args = "DB DELETE_PROFILE 5000";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertFalse(result);
    }

    @Test
    void createNewEventNegative() {
//      title, description, customer, eventDate, creationDate, price, quantity
        String args = "DB CREATE_NEW_EVENT auto_race competition 5000 14-02-2021 150 2.5";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertFalse(result);
    }

    @Test
    void getEventNegative() {
        String args = "DB GET_EVENT 5000";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertFalse(result);
    }

    @Test
    void editEventByIdNegative() {
        String args = "DB EDIT_EVENT 5000 title racing";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertFalse(result);
    }

    @Test
    void deleteEventByIdNegative() {
        String args = "DB DELETE_EVENT 5000";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertFalse(result);
    }

    @Test
    void addPhotoNegative() {
        String args = "DB ADD_PHOTO 5000 ./testPhotos/test2.jpg";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertFalse(result);
    }

    @Test
    void getPhotoNegative() {
        String args = "DB GET_PHOTO 5000";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertFalse(result);
    }

    @Test
    void editPhotoByIdNegative() {
        String args = "DB EDIT_PHOTO 5000 title test_photo";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertFalse(result);
    }

    @Test
    void deletePhotoByIdNegative() {
        String args = "DB DELETE_PHOTO 5000";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertFalse(result);
    }

    @Test
    void getPortfolioNegative() {
        String args = "DB GET_PORTFOLIO 5000";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertFalse(result);
    }

//    @Test
//    void getPhotoPathById() {
//        addPhoto();
//        String args = "CSV SHOW_PHOTO " + provider.getLastPhotoId();
//        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
//        assertTrue(result);
//    }

    @Test
    void getLastUserIdNegative() {
        String result = provider.getLastUserId();
        log.info(result);
        assertNull(result);
    }

    @Test
    void getLastEventIdNegative() {
        String result = provider.getLastEventId();
        log.info(result);
        assertNull(result);
    }

    @Test
    void getLastPhotoIdNegative() {
        String result = provider.getLastPhotoId();
        log.info(result);
        assertNull(result);
    }

    @Test
    void getLastPhotographerIdNegative() {
        String result = provider.getLastPhotographerId();
        log.info(result);
        assertNull(result);
    }

    @Test
    void getAllUsersNegative() {
        Optional<ArrayList<User>> result = provider.getAllUsers();
        result.ifPresent(users -> users.forEach(x -> log.info(x.getUserOutput())));
        assertNull(result.orElse(null));
    }

    @Test
    void getAllEventsNegative() {
        Optional<ArrayList<Event>> result = provider.getAllEvents();
        result.ifPresent(events -> events.forEach(x -> log.info(x.getEventOutput())));
        assertNull(result.orElse(null));
    }

    @Test
    void getAllPhotosNegative() {
        Optional<ArrayList<Photo>> result = provider.getAllPhotos();
        result.ifPresent(photos -> photos.forEach(x -> log.info(x.getPhotoOutput())));
        assertNull(result.orElse(null));
    }

    @Test
    void searchUsersNegative() {
        String args = "DB SEARCH_USERS town Moscow44";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertFalse(result);
    }

    @Test
    void searchPhotographersNegative() {
        String args = "DB SEARCH_PHOTOGRAPHER town Moscow";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertFalse(result);
    }

    @Test
    void searchEventsNegative() {
        String args = "DB SEARCH_EVENTS description competiti";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertFalse(result);
    }

    @Test
    void addCommentNegative() {
        String args = "DB ADD_COMMENT 5000 " + provider.getLastPhotoId() + " beautiful_photo";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertFalse(result);
    }

    @Test
    void getAllCommentsByIdNegative() {
        String args = "DB GET_ALL_COMMENTS 5000";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertFalse(result);
    }

    @Test
    void addRateNegative() {
        String args = "DB ADD_RATE 5000 " + provider.getLastPhotoId() + " 5";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertFalse(result);
    }
    @Test
    void getAllRatesNegative() {
        String args = "DB GET_ALL_RATES 5000 " + provider.getLastPhotoId();
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertFalse(result);
    }

    @Test
    void addFeedbackNegative() {
        String userId = "5000";
        String photographerId = "3000";

        String args = "DB ADD_FEEDBACK " + userId + " " + photographerId + " 5 thank_you";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertFalse(result);
    }
    @Test
    void getAllFeedbacksNegative() {
        String args = "DB GET_ALL_FEEDBACKS 5000";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertFalse(result);
    }

    @Test
    void addOfferNegative() {
        String userId = "5000";
        String eventId = provider.getLastEventId();
        String photographerId = provider.getLastPhotographerId();

        String args = "DB CREATE_OFFER " + userId + " " + eventId + " " + photographerId;
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertFalse(result);
    }
    @Test
    void getAllOffersNegative() {
        String args = "DB GET_ALL_OFFERS 5000 " + provider.getLastEventId();
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertFalse(result);
    }
}
