package ru.sfedu.photosearch.providers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.Main;
import ru.sfedu.photosearch.PhotoSearchClient;
import ru.sfedu.photosearch.enums.Role;
import ru.sfedu.photosearch.newModels.Event;
import ru.sfedu.photosearch.newModels.Photo;
import ru.sfedu.photosearch.newModels.User;
import ru.sfedu.photosearch.utils.CSV_util;
import ru.sfedu.photosearch.utils.XML_util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DataProviderCSVTest {
    public static final Logger log = LogManager.getLogger(DataProviderCSVTest.class);
    DataProviderCSV provider;
    String xml_profile_id_global = Constants.UTIL_EMPTY_STRING;
    String xml_photo_id_global = Constants.UTIL_EMPTY_STRING;
    String xml_event_id_global = Constants.UTIL_EMPTY_STRING;

    @BeforeEach
    void setUp() {
        provider = new DataProviderCSV();
        CSV_util.createFiles();
    }

    @Test
    void createNewProfile() {
        String args = "CSV CREATE_NEW_PROFILE Maksim Tolstoy 12-10-1999 customer Moscow";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }
    //243ab70a-fb5c-4f6a-911b-f2bd00ba1fee
    @Test
    void getProfile() {
        String args = "CSV GET_PROFILE cfcecf3c-5507-41e5-b173-a1a01dd3058d";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void editProfileById() {
        String args = "CSV EDIT_PROFILE e7a6ce08-eb38-4101-b457-b3e2d6c85ee5 name Alexey";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void deleteProfileById() {
        String args = "CSV DELETE_PROFILE 65d9f0c8-54a4-4ac3-97d7-9b143471e357";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void createNewEvent() {
        String args = "CSV CREATE_NEW_EVENT auto_race competition 65d9f0c8-54a4-4ac3-97d7-9b143471e357 14-02-2021 150 2.5";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getEvent() {
        String args = "CSV GET_EVENT 8e0f8a56-05f7-4dcd-ae1b-60811778f413";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void editEventById() {
        String args = "CSV EDIT_EVENT 8e0f8a56-05f7-4dcd-ae1b-60811778f413 title running";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void deleteEventById() {
        String args = "CSV DELETE_EVENT 8e0f8a56-05f7-4dcd-ae1b-60811778f413";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void addPhoto() {
        String args = "CSV ADD_PHOTO 65d9f0c8-54a4-4ac3-97d7-9b143471e357 ./testPhotos/test.jpg";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getPhoto() {
        String args = "CSV GET_PHOTO 1e6a0624-7a81-41c9-9dc4-e465bbc37188";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void editPhotoById() {
        String args = "CSV EDIT_PHOTO 1e6a0624-7a81-41c9-9dc4-e465bbc37188 title test_photo";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }
    @Test
    void deletePhotoById() {
        String args = "CSV DELETE_PHOTO 2edbee71-9c58-433d-a675-dab4377a528b";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getPortfolio() {
        String args = "CSV GET_PORTFOLIO 65d9f0c8-54a4-4ac3-97d7-9b143471e357";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getPhotoPathById() {
        String args = "CSV SHOW_PHOTO 26b84608-e526-4be5-8054-d66979bf6181";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getLastUserId() {
        String result = provider.getLastUserId();
        log.info(result);
        assertNotNull(result);
    }

    @Test
    void getLastEventId() {
        String result = provider.getLastEventId();
        log.info(result);
        assertNotNull(result);
    }

    @Test
    void getLastPhotoId() {
        String result = provider.getLastPhotoId();
        log.info(result);
        assertNotNull(result);
    }

    @Test
    void getAllUsers() {
        ArrayList<User> result = provider.getAllUsers();
        result.forEach(x->log.info(x.getUserOutput()));
        assertNotNull(result);
    }

    @Test
    void getAllEvents() {
        ArrayList<Event> result = provider.getAllEvents();
        result.forEach(x->log.info(x.getEventOutput()));
        assertNotNull(result);
    }

    @Test
    void getAllPhotos() {
        ArrayList<Photo> result = provider.getAllPhotos();
        result.forEach(x->log.info(x.getPhotoOutput()));
        assertNotNull(result);
    }
}
