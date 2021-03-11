package ru.sfedu.photosearch.providers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.Main;
import ru.sfedu.photosearch.Models.Event;
import ru.sfedu.photosearch.Models.Photo;
import ru.sfedu.photosearch.Models.User;
import ru.sfedu.photosearch.utils.CSV_util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DataProviderCSVTest {
    public static final Logger log = LogManager.getLogger(DataProviderCSVTest.class);
    DataProviderCSV provider;

    @BeforeEach
    void setUp() throws IOException {
        CSV_util.deleteFiles();
        provider = new DataProviderCSV();
        CSV_util.createFiles();
    }

//    @AfterAll
//    static void clean(){
//        CSV_util.deleteFiles();
//    }

    @Test
    void createNewProfile() {
        String args = "CSV CREATE_NEW_PROFILE Sergey Esenin 12-10-1999 customer Moscow";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getProfile() {
        createNewProfile();
        String args = "CSV GET_PROFILE " + provider.getLastUserId();
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void editProfileById() {
        createNewProfile();
        String args = "CSV EDIT_PROFILE " + provider.getLastUserId() + " name Alexey";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void deleteProfileById() {
        createNewProfile();
        String args = "CSV DELETE_PROFILE " + provider.getLastUserId();
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void createNewEvent() {
//      title, description, customer, eventDate, creationDate, price, quantity
        createNewProfile();
        String args = "CSV CREATE_NEW_EVENT auto_race competition " + provider.getLastUserId() +  " 14-02-2021 150 2.5";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getEvent() {
        createNewEvent();
        String args = "CSV GET_EVENT " + provider.getLastEventId();
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void editEventById() {
        createNewEvent();
        String args = "CSV EDIT_EVENT " + provider.getLastEventId() + " title racing";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void deleteEventById() {
        createNewEvent();
        String args = "CSV DELETE_EVENT " + provider.getLastEventId();
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void addPhoto() {
        createNewProfile();
        String args = "CSV ADD_PHOTO " + provider.getLastUserId() + " ./testPhotos/test2.jpg";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getPhoto() {
        addPhoto();
        String args = "CSV GET_PHOTO " + provider.getLastPhotoId();
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void editPhotoById() {
        addPhoto();
        String args = "CSV EDIT_PHOTO " + provider.getLastPhotoId() + " title test_photo";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void deletePhotoById() {
        addPhoto();
        String args = "CSV DELETE_PHOTO " + provider.getLastPhotoId();
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getPortfolio() {
        addPhoto();
        String args = "CSV GET_PORTFOLIO " + provider.getLastUserId();
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

//    @Test
//    void getPhotoPathById() {
//        addPhoto();
//        String args = "CSV SHOW_PHOTO " + provider.getLastPhotoId();
//        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
//        assertTrue(result);
//    }

    @Test
    void getLastUserId() {
        createNewProfile();
        String result = provider.getLastUserId();
        log.info(result);
        assertNotNull(result);
    }

    @Test
    void getLastEventId() {
        createNewEvent();
        String result = provider.getLastEventId();
        log.info(result);
        assertNotNull(result);
    }

    @Test
    void getLastPhotoId() {
        addPhoto();
        String result = provider.getLastPhotoId();
        log.info(result);
        assertNotNull(result);
    }

    @Test
    void getLastPhotographerId() {
        String args = "CSV CREATE_NEW_PROFILE Sergey Esenin 12-10-1999 photographer Moscow";
        Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        String result = provider.getLastPhotographerId();
        log.info(result);
        assertNotNull(result);
    }

    @Test
    void getAllUsers() {
        createNewProfile();
        Optional<ArrayList<User>> result = provider.getAllUsers();
        result.ifPresent(users -> users.forEach(x -> log.info(x.getUserOutput())));
        assertNotNull(result.orElse(null));
    }

    @Test
    void getAllEvents() {
        createNewEvent();
        Optional<ArrayList<Event>> result = provider.getAllEvents();
        result.ifPresent(events -> events.forEach(x -> log.info(x.getEventOutput())));
        assertNotNull(result.orElse(null));
    }

    @Test
    void getAllPhotos() {
        addPhoto();
        Optional<ArrayList<Photo>> result = provider.getAllPhotos();
        result.ifPresent(photos -> photos.forEach(x -> log.info(x.getPhotoOutput())));
        assertNotNull(result.orElse(null));
    }

    @Test
    void searchUsers() {
        createNewProfile();
        String args = "CSV SEARCH_USERS town Moscow";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void searchPhotographers() {
        String args = "CSV CREATE_NEW_PROFILE Sergey Esenin 12-10-1999 photographer Moscow";
        Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        args = "CSV SEARCH_PHOTOGRAPHERS town Moscow";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void searchEvents() {
        createNewEvent();
        String args = "CSV SEARCH_EVENTS description competition";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void addComment() {
        addPhoto();
        String args = "CSV ADD_COMMENT " + provider.getLastUserId() + " " + provider.getLastPhotoId() + " beautiful_photo";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getAllCommentsById() {
        addComment();
        String args = "CSV GET_ALL_COMMENTS " + provider.getLastPhotoId();
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void addRate() {
        addPhoto();
        String args = "CSV ADD_RATE " + provider.getLastUserId() + " " + provider.getLastPhotoId() + " 5";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }
    @Test
    void getAllRates() {
        addRate();
        String args = "CSV GET_ALL_RATES " + provider.getLastPhotoId();
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void addFeedback() {
        createNewProfile();
        String userId = provider.getLastUserId();

        String args = "CSV CREATE_NEW_PROFILE Sergey Esenin 12-10-1999 photographer Moscow";
        Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));

        String photographerId = provider.getLastPhotographerId();

        args = "CSV ADD_FEEDBACK " + userId + " " + photographerId + " 5 thank_you";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }
    @Test
    void getAllFeedbacks() {
        addFeedback();
        String args = "CSV GET_ALL_FEEDBACKS " + provider.getLastPhotographerId();
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void addOffer() {
        createNewProfile();
        createNewEvent();
        String args = "CSV CREATE_NEW_PROFILE Sergey Esenin 12-10-1999 photographer Moscow";
        Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));

        String userId = provider.getLastUserId();
        String eventId = provider.getLastEventId();
        String photographerId = provider.getLastPhotographerId();

        args = "CSV CREATE_OFFER " + userId + " " + eventId + " " + photographerId;
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }
    @Test
    void getAllOffers() {
        addOffer();
        String args = "CSV GET_ALL_OFFERS " + provider.getLastEventId();
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

}
