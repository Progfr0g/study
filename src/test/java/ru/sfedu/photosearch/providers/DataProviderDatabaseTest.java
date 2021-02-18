package ru.sfedu.photosearch.providers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.Main;
import ru.sfedu.photosearch.PhotoSearchClient;
import ru.sfedu.photosearch.enums.Role;
import ru.sfedu.photosearch.newModels.Comment;
import ru.sfedu.photosearch.newModels.Event;
import ru.sfedu.photosearch.newModels.Photo;
import ru.sfedu.photosearch.newModels.User;
import ru.sfedu.photosearch.utils.CSV_util;
import ru.sfedu.photosearch.utils.XML_util;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DataProviderDatabaseTest {
    public static final Logger log = LogManager.getLogger(DataProviderDatabaseTest.class);
    public static final DataProviderDatabase provider = new DataProviderDatabase();
    public static final String id = "1";
    String xml_profile_id_global = Constants.UTIL_EMPTY_STRING;
    String xml_photo_id_global = Constants.UTIL_EMPTY_STRING;
    String xml_event_id_global = Constants.UTIL_EMPTY_STRING;

    @BeforeEach
    void setUp() {
        provider.DB.connect();
        provider.DB.createTables();
    }

    @Test
    void createNewProfile() {
        String args = "DB CREATE_NEW_PROFILE Sergey Tolstoy 12-10-1999 customer Moscow";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getProfile() {
        String args = "DB GET_PROFILE 4";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void editProfileById() {
        String args = "DB EDIT_PROFILE 3 name Alexey";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void deleteProfileById() {
        String args = "DB DELETE_PROFILE 3";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void createNewEvent() {
//      title, description, customer, eventDate, creationDate, price, quantity
        String args = "DB CREATE_NEW_EVENT auto_race competition 1 14-02-2021 150 2.5";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getEvent() {
        String args = "DB GET_EVENT 4";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void editEventById() {
        String args = "DB EDIT_EVENT 4 customer 2";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void deleteEventById() {
        String args = "DB DELETE_EVENT 1";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void addPhoto() {
        String args = "DB ADD_PHOTO 1 ./testPhotos/test2.jpg";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getPhoto() {
        String args = "DB GET_PHOTO 1";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void editPhotoById() {
        String args = "DB EDIT_PHOTO 7 title test_photo";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void deletePhotoById() {
        String args = "DB DELETE_PHOTO 8";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getPortfolio() {
        String args = "DB GET_PORTFOLIO 3";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getPhotoPathById() {
        String args = "DB SHOW_PHOTO 1";
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

    @Test
    void addComment() {
        String args = "DB ADD_COMMENT 1 1 beautiful_photo";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getAllComments() {
        String args = "DB GET_ALL_COMMENTS";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void searchUsers() {
        String args = "DB SEARCH_USERS town Moscow";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }
}
