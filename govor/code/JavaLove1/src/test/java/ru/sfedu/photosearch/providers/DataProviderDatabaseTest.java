package ru.sfedu.photosearch.providers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.PhotoSearchClient;
import ru.sfedu.photosearch.enums.Role;
import ru.sfedu.photosearch.utils.CSV_util;
import ru.sfedu.photosearch.utils.XML_util;

import java.time.LocalDate;

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
        String date = LocalDate.now().toString();
        String role = Role.CUSTOMER.name().toLowerCase();
        String xml_profile_id = provider.createNewProfile("Nikolay", "Tolstoy", 50, date, role, "Moscow");

        assertNotNull(xml_profile_id);
    }

    @Test
    void createNewEvent() {
        String xml_event_id = provider.createNewEvent("first event", "test description", "3", LocalDate.now().toString(), 129, 2.5f);
        xml_event_id_global = xml_event_id;
        assertNotNull(xml_event_id);
    }

    @Test
    void getProfile() {
        String date = LocalDate.now().toString();
        String role = Role.CUSTOMER.name().toLowerCase();
        String xml_profile_id = provider.createNewProfile("Nikolay", "Tolstoy", 50, date, role, "Moscow");
        String result = provider.getProfile("1");
        log.info(provider.getProfile("1"));
        assertNotNull(result);
    }

    @Test
    void getEvent() {
        String xml_event_id = provider.createNewEvent("first event", "test description", "3", LocalDate.now().toString(), 129, 2.5f);
        String result = provider.getEvent("1");
        log.info(provider.getEvent("1"));
        assertNotNull(result);
    }

    @Test
    void editProfileById() {
        String date = LocalDate.now().toString();
        String role = Role.CUSTOMER.name().toLowerCase();
        String xml_profile_id = provider.createNewProfile("Nikolay", "Tolstoy", 50, date, role, "Moscow");
        provider.editProfileById("1", "town", "Saint-Petersburg");
        String result = provider.getProfile("1");
        log.info(provider.getProfile("1"));
        assertNotNull(result);
    }

    @Test
    void editEventById() {
        String xml_event_id = provider.createNewEvent("first event", "test description", "3", LocalDate.now().toString(), 129, 2.5f);
        provider.editEventById("1", "title", "updatedTitle");
        String result = provider.getEvent("1");
        log.info(provider.getEvent("1"));
        assertNotNull(result);
    }

    @Test
    void deleteProfileById() {
        String date = LocalDate.now().toString();
        String role = Role.CUSTOMER.name().toLowerCase();
        String xml_profile_id = provider.createNewProfile("Nikolay", "Tolstoy", 50, date, role, "Moscow");
        provider.deleteProfileById("1");
        String result = provider.getProfile("1");
        log.info(provider.getProfile("1"));
        assertNotNull(result);
    }

    @Test
    void deleteEventById() {
        String xml_event_id = provider.createNewEvent("first event", "test description", "3", LocalDate.now().toString(), 129, 2.5f);
        provider.deleteEventById("1");
        String result = provider.getEvent("1");
        log.info(provider.getEvent("1"));
        assertNotNull(result);
    }

    @Test
    void addPhoto() {
        String date = LocalDate.now().toString();
        String role = Role.CUSTOMER.name().toLowerCase();
        provider.createNewProfile("Nikolay", "Tolstoy", 50, date, role, "Moscow");

        assertNotNull(provider.addPhoto("1", "./testPhotos/1.jpg"));
    }

    @Test
    void getPhoto() {
        String date = LocalDate.now().toString();
        String role = Role.CUSTOMER.name().toLowerCase();
        String xml_profile_id = provider.createNewProfile("Nikolay", "Tolstoy", 50, date, role, "Moscow");
        String xml_photo_id = provider.addPhoto(xml_profile_id, "./testPhotos/1.jpg");
        String result = provider.getPhoto("1");
        log.info(provider.getPhoto("1"));
        assertNotNull(result);
    }

    @Test
    void editPhotoById() {
        String date = LocalDate.now().toString();
        String role = Role.CUSTOMER.name().toLowerCase();
        String xml_profile_id = provider.createNewProfile("Nikolay", "Tolstoy", 50, date, role, "Moscow");
        String xml_photo_id = provider.addPhoto("3", "./testPhotos/1.jpg");
        provider.editPhotoById("3", "title", "test_photo");
        String result = provider.getPhoto("3");
        log.info(provider.getPhoto("3"));
        assertNotNull(result);
    }

    @Test
    void deletePhotoById() {
        String date = LocalDate.now().toString();
        String role = Role.CUSTOMER.name().toLowerCase();
        String xml_profile_id = provider.createNewProfile("Nikolay", "Tolstoy", 50, date, role, "Moscow");
        String xml_photo_id = provider.addPhoto("5", "./testPhotos/1.jpg");
        provider.deletePhotoById("1");
        String result = provider.getPhoto("1");
        log.info(provider.getPhoto("1"));
        assertNotNull(result);
    }

    @Test
    void getPortfolio() {
        String date = LocalDate.now().toString();
        String role = Role.CUSTOMER.name().toLowerCase();
        String csv_profile_id = provider.createNewProfile("Nikolay", "Tolstoy", 50, date, role, "Moscow");
        String result = provider.getPortfolio("3");
        log.info(provider.getPortfolio("3"));
        assertNotNull(result);
    }

    @Test
    void getPhotoPathById() {
        String date = LocalDate.now().toString();
        String role = Role.CUSTOMER.name().toLowerCase();
        String xml_profile_id = provider.createNewProfile("Nikolay", "Tolstoy", 50, date, role, "Moscow");
        String xml_photo_id = provider.addPhoto("2", "./testPhotos/1.jpg");

        String result = provider.getPhotoPathById("2");
        log.info(provider.getPhotoPathById("2"));
        assertNotNull(result);
    }
}
