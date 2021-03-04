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
        String args = "XML CREATE_NEW_PROFILE Sergey Tolstoy 12-10-1999 customer Moscow";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getProfile() {
        String args = "XML GET_PROFILE 977ede93-6a7d-4e6e-97f4-a751d66c2035";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void editProfileById() {
        String args = "XML EDIT_PROFILE 977ede93-6a7d-4e6e-97f4-a751d66c2035 name Kostya";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void deleteProfileById() {
        String args = "XML DELETE_PROFILE 977ede93-6a7d-4e6e-97f4-a751d66c2035";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void createNewEvent() {

        String args = "XML CREATE_NEW_EVENT wedding one_couple " + provider.getLastUserId() + " 14-02-2021 150 2.5";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getEvent() {
        String args = "XML GET_EVENT cdb12bd3-e8c1-4e81-beae-87bb4492b0aa";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void editEventById() {
        String args = "XML EDIT_EVENT cdb12bd3-e8c1-4e81-beae-87bb4492b0aa title running";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void deleteEventById() {
        String args = "XML DELETE_EVENT a680f25f-08dd-44bb-b59a-7bac2bf420c5";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }
//  bb2bee28-c928-4d4b-aac3-9c17841d6743
    @Test
    void addPhoto() {
        String args = "XML ADD_PHOTO 92078c3c-a492-406d-98b2-b64267c121c5 ./testPhotos/test2.jpg";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getPhoto() {
        String args = "XML GET_PHOTO 1f492bb5-fc83-421b-a8df-d9cd3f12f272";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void editPhotoById() {
        String args = "XML EDIT_PHOTO 1f492bb5-fc83-421b-a8df-d9cd3f12f272 title test_photo";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }
    @Test
    void deletePhotoById() {
        String args = "XML DELETE_PHOTO 1f492bb5-fc83-421b-a8df-d9cd3f12f272";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getPortfolio() {
        String args = "XML GET_PORTFOLIO 9b97eecc-3f17-4fea-8605-a6e06002f67b";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getPhotoPathById() {
        String args = "XML SHOW_PHOTO 93a5a1ee-337f-40eb-a078-583679148dbb";
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
        String args = "XML ADD_COMMENT 92078c3c-a492-406d-98b2-b64267c121c5 5aee6807-0f41-4425-b496-8df5fa62d267 beautiful_photo";
        Boolean result = Main.chooseMethod(provider, Arrays.asList(args.split(Constants.UTIL_SPACE)));
        assertTrue(result);
    }

    @Test
    void getAllComments() {
        ArrayList<Comment> result = provider.getAllComments();
        result.forEach(x->log.info(x.getCommentOutput()));
        assertNotNull(result);
    }

    @Test
    void searchUsers() {
        ArrayList<User> result = provider.searchUsers("name", "Sergey");
        result.forEach(x->log.info(x.getUserOutput()));
        assertNotNull(result);
    }

    @Test
    void searchEvents() {
        ArrayList<Event> result = provider.searchEvents("title", "wedding");
        result.forEach(x->log.info(x.getEventOutput()));
        assertNotNull(result);
    }
}