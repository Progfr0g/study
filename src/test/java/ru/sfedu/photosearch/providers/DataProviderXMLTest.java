package ru.sfedu.photosearch.providers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.photosearch.CSV;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.Main;
import ru.sfedu.photosearch.enums.Role;
import ru.sfedu.photosearch.newModels.Comment;
import ru.sfedu.photosearch.newModels.Event;
import ru.sfedu.photosearch.newModels.Photo;
import ru.sfedu.photosearch.newModels.User;
import ru.sfedu.photosearch.utils.CSV_util;
import ru.sfedu.photosearch.utils.XML_util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DataProviderXMLTest {
    public static final Logger log = LogManager.getLogger(DataProviderXMLTest.class);
    DataProviderXML provider;
    String xml_profile_id_global = Constants.UTIL_EMPTY_STRING;
    String xml_photo_id_global  = Constants.UTIL_EMPTY_STRING;
    String xml_event_id_global  = Constants.UTIL_EMPTY_STRING;


    @BeforeEach
    void setUp() {
        provider = new DataProviderXML();
        XML_util.createFiles();
    }


    @Test
    void createNewProfile() {
        String args = "XML CREATE_NEW_PROFILE Sergey Esenin 12-10-1999 customer Moscow";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getProfile() {
        createNewProfile();
        String args = "XML GET_PROFILE " + provider.getLastUserId();
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void editProfileById() {
        createNewProfile();
        String args = "XML EDIT_PROFILE " + provider.getLastUserId() + " name Alexey";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void deleteProfileById() {
        createNewProfile();
        String args = "XML DELETE_PROFILE " + provider.getLastUserId();
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void createNewEvent() {
//      title, description, customer, eventDate, creationDate, price, quantity
        createNewProfile();
        String args = "XML CREATE_NEW_EVENT auto_race competition " + provider.getLastUserId() +  " 14-02-2021 150 2.5";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getEvent() {
        createNewEvent();
        String args = "XML GET_EVENT " + provider.getLastEventId();
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void editEventById() {
        createNewProfile();
        String args = "XML EDIT_EVENT " + provider.getLastEventId() + " title racing";;
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void deleteEventById() {
        createNewEvent();
        String args = "XML DELETE_EVENT " + provider.getLastEventId();
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void addPhoto() {
        createNewProfile();
        String args = "XML ADD_PHOTO " + provider.getLastUserId() + " ./testPhotos/test2.jpg";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getPhoto() {
        addPhoto();
        String args = "XML GET_PHOTO " + provider.getLastPhotoId();
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void editPhotoById() {
        addPhoto();
        String args = "XML EDIT_PHOTO " + provider.getLastPhotoId() + " title test_photo";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void deletePhotoById() {
        addPhoto();
        String args = "XML DELETE_PHOTO " + provider.getLastPhotoId();
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getPortfolio() {
        addPhoto();
        String args = "XML GET_PORTFOLIO " + provider.getLastUserId();
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

//    @Test
//    void getPhotoPathById() {
//        addPhoto();
//        String args = "DB SHOW_PHOTO " + provider.getLastPhotoId();
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
    void addComment() {
        addPhoto();
        String args = "XML ADD_COMMENT " + provider.getLastUserId() + " " + provider.getLastPhotoId() + " beautiful_photo";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getAllComments() {
        addPhoto();
        String args = "XML GET_ALL_COMMENTS";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void searchUsers() {
        createNewProfile();
        String args = "XML SEARCH_USERS town Moscow";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void searchPhotographers() {
        String args = "DB CREATE_NEW_PROFILE Sergey Esenin 12-10-1999 photographer Moscow";
        Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        args = "DB SEARCH_PHOTOGRAPHERS town Moscow";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void searchEvents() {
        createNewEvent();
        String args = "XML SEARCH_EVENTS description competition";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }
}