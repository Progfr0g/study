package ru.sfedu.photosearch.providers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.Main;
import ru.sfedu.photosearch.PhotoSearchClient;
import ru.sfedu.photosearch.enums.Role;
import ru.sfedu.photosearch.utils.CSV_util;
import ru.sfedu.photosearch.utils.XML_util;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

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
        String args = "DB GET_PROFILE 2";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void editProfileById() {
        String args = "DB EDIT_PROFILE 1 name Alexey";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void deleteProfileById() {
        String args = "DB DELETE_PROFILE 10";
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
        String args = "DB GET_EVENT 3";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void editEventById() {
        String args = "DB EDIT_EVENT 3 customer 2";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void deleteEventById() {
        String args = "DB DELETE_EVENT 4";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void addPhoto() {
        String args = "DB ADD_PHOTO 2 ./testPhotos/1.jpg";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getPhoto() {
        String args = "DB GET_PHOTO 3";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void editPhotoById() {
        String args = "DB EDIT_PHOTO 3 eventId 3";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }
//
//    @Test
//    void deletePhotoById() {
//        String date = LocalDate.now().toString();
//        String role = Role.CUSTOMER.name().toLowerCase();
//        String xml_profile_id = provider.createNewProfile("Nikolay", "Tolstoy", 50, date, role, "Moscow");
//        String xml_photo_id = provider.addPhoto("5", "./testPhotos/1.jpg");
//        provider.deletePhotoById("1");
//        String result = provider.getPhoto("1");
//        log.info(provider.getPhoto("1"));
//        assertNotNull(result);
//    }
//
//    @Test
//    void getPortfolio() {
//        String date = LocalDate.now().toString();
//        String role = Role.CUSTOMER.name().toLowerCase();
//        String csv_profile_id = provider.createNewProfile("Nikolay", "Tolstoy", 50, date, role, "Moscow");
//        String result = provider.getPortfolio("3");
//        log.info(provider.getPortfolio("3"));
//        assertNotNull(result);
//    }
//
//    @Test
//    void getPhotoPathById() {
//        String date = LocalDate.now().toString();
//        String role = Role.CUSTOMER.name().toLowerCase();
//        String xml_profile_id = provider.createNewProfile("Nikolay", "Tolstoy", 50, date, role, "Moscow");
//        String xml_photo_id = provider.addPhoto("2", "./testPhotos/1.jpg");
//
//        String result = provider.getPhotoPathById("2");
//        log.info(provider.getPhotoPathById("2"));
//        assertNotNull(result);
//    }
}
