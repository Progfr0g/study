//package ru.sfedu.photosearch.providers;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ru.sfedu.photosearch.Constants;
//import ru.sfedu.photosearch.PhotoSearchClient;
//import ru.sfedu.photosearch.enums.Role;
//import ru.sfedu.photosearch.utils.CSV_util;
//import ru.sfedu.photosearch.utils.XML_util;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class DataProviderCSVTest {
//    public static final Logger log = LogManager.getLogger(DataProviderCSVTest.class);
//    DataProviderCSV provider;
//    String xml_profile_id_global = Constants.UTIL_EMPTY_STRING;
//    String xml_photo_id_global = Constants.UTIL_EMPTY_STRING;
//    String xml_event_id_global = Constants.UTIL_EMPTY_STRING;
//
//    @BeforeEach
//    void setUp() {
//        provider = new DataProviderCSV();
//        CSV_util.createFiles();
//    }
//
//    @Test
//    void createNewProfile() {
//        String date = LocalDate.now().toString();
//        String role = Role.CUSTOMER.name().toLowerCase();
//        String xml_profile_id = provider.createNewProfile("Nikolay", "Tolstoy", 50, date, role, "Moscow");
//
//        assertNotNull(xml_profile_id);
//    }
//
//    @Test
//    void createNewEvent() {
//        String xml_event_id = provider.createNewEvent("first event", "test description", "3", LocalDate.now().toString(), 129, 2.5f);
//        xml_event_id_global = xml_event_id;
//        assertNotNull(xml_event_id);
//    }
//
//    @Test
//    void getProfile() {
//        String date = LocalDate.now().toString();
//        String role = Role.CUSTOMER.name().toLowerCase();
//        String xml_profile_id = provider.createNewProfile("Nikolay", "Tolstoy", 50, date, role, "Moscow");
//        String result = provider.getProfile(xml_profile_id);
//        log.info(provider.getProfile(xml_profile_id));
//        assertNotNull(result);
//    }
//
//    @Test
//    void getEvent() {
//        String xml_event_id = provider.createNewEvent("first event", "test description", "3", LocalDate.now().toString(), 129, 2.5f);
//        String result = provider.getEvent(xml_event_id);
//        log.info(provider.getEvent(xml_event_id));
//        assertNotNull(result);
//    }
//
//    @Test
//    void editProfileById() {
//        String date = LocalDate.now().toString();
//        String role = Role.CUSTOMER.name().toLowerCase();
//        String xml_profile_id = provider.createNewProfile("Nikolay", "Tolstoy", 50, date, role, "Moscow");
//        provider.editProfileById(xml_profile_id, "town", "Saint-Petersburg");
//        String result = provider.getProfile(xml_profile_id);
//        log.info(provider.getProfile(xml_profile_id));
//        assertNotNull(result);
//    }
//
//    @Test
//    void editEventById() {
//        String xml_event_id = provider.createNewEvent("first event", "test description", "3", LocalDate.now().toString(), 129, 2.5f);
//        provider.editEventById(xml_event_id, "title", "updatedTitle");
//        String result = provider.getEvent(xml_event_id);
//        log.info(provider.getEvent(xml_event_id));
//        assertNotNull(result);
//    }
//
//    @Test
//    void deleteProfileById() {
//        String date = LocalDate.now().toString();
//        String role = Role.CUSTOMER.name().toLowerCase();
//        String xml_profile_id = provider.createNewProfile("Nikolay", "Tolstoy", 50, date, role, "Moscow");
//        provider.deleteProfileById(xml_profile_id);
//        String result = provider.getProfile(xml_profile_id);
//        log.info(provider.getProfile(xml_profile_id));
//        assertNull(result);
//    }
//
//    @Test
//    void deleteEventById() {
//        String xml_event_id = provider.createNewEvent("first event", "test description", "3", LocalDate.now().toString(), 129, 2.5f);
//        provider.deleteEventById(xml_event_id);
//        String result = provider.getEvent(xml_event_id_global);
//        log.info(provider.getEvent(xml_event_id_global));
//        assertNull(result);
//    }
//
//    @Test
//    void addPhoto() {
//        String date = LocalDate.now().toString();
//        String role = Role.CUSTOMER.name().toLowerCase();
//        String xml_profile_id = provider.createNewProfile("Nikolay", "Tolstoy", 50, date, role, "Moscow");
//        String xml_photo_id = provider.addPhoto(xml_profile_id, "./testPhotos/1.jpg");
//        assertNotNull(xml_photo_id);
//    }
//
//    @Test
//    void getPhoto() {
//        String date = LocalDate.now().toString();
//        String role = Role.CUSTOMER.name().toLowerCase();
//        String xml_profile_id = provider.createNewProfile("Nikolay", "Tolstoy", 50, date, role, "Moscow");
//        String xml_photo_id = provider.addPhoto(xml_profile_id, "./testPhotos/1.jpg");
//        String result = provider.getPhoto(xml_photo_id);
//        log.info(provider.getPhoto(xml_photo_id));
//        assertNotNull(result);
//    }
//
//    @Test
//    void editPhotoById() {
//        String date = LocalDate.now().toString();
//        String role = Role.CUSTOMER.name().toLowerCase();
//        String xml_profile_id = provider.createNewProfile("Nikolay", "Tolstoy", 50, date, role, "Moscow");
//        String xml_photo_id = provider.addPhoto(xml_profile_id, "./testPhotos/1.jpg");
//        provider.editPhotoById(xml_photo_id, "title", "test_photo");
//        String result = provider.getPhoto(xml_photo_id);
//        log.info(provider.getPhoto(xml_photo_id));
//        assertNotNull(result);
//    }
//
//    @Test
//    void deletePhotoById() {
//        String date = LocalDate.now().toString();
//        String role = Role.CUSTOMER.name().toLowerCase();
//        String xml_profile_id = provider.createNewProfile("Nikolay", "Tolstoy", 50, date, role, "Moscow");
//        String xml_photo_id = provider.addPhoto(xml_profile_id, "./testPhotos/1.jpg");
//        provider.deletePhotoById(xml_photo_id);
//        String result = provider.getPhoto(xml_photo_id);
//        log.info(provider.getPhoto(xml_photo_id));
//        assertNull(result);
//    }
//
//    @Test
//    void getPortfolio() {
//        String date = LocalDate.now().toString();
//        String role = Role.CUSTOMER.name().toLowerCase();
//        String csv_profile_id = provider.createNewProfile("Nikolay", "Tolstoy", 50, date, role, "Moscow");
//        String result = provider.getPortfolio(csv_profile_id);
//        log.info(provider.getPortfolio(csv_profile_id));
//        assertNotNull(result);
//    }
//
//    @Test
//    void getPhotoPathById() {
//        String date = LocalDate.now().toString();
//        String role = Role.CUSTOMER.name().toLowerCase();
//        String xml_profile_id = provider.createNewProfile("Nikolay", "Tolstoy", 50, date, role, "Moscow");
//        String xml_photo_id = provider.addPhoto(xml_profile_id, "./testPhotos/1.jpg");
//
//        String result = provider.getPhotoPathById(xml_photo_id);
//        log.info(provider.getPhotoPathById(xml_photo_id));
//        assertNotNull(result);
//    }
//}
//
////        log.info(provider.createNewProfile("evgeniy", "govor", 21, LocalDate.now().toString(), Role.CUSTOMER.name().toLowerCase(), "rostov"));
////        log.info(provider.getProfile("e4e1c071-9ce5-494a-b659-a39bb5a36c78"));
//
////        log.info(provider.createNewEvent("first event", "test description", "3", LocalDate.now().toString(), 129, 2.5f));
////        log.info(provider.getEvent("2787d219-2043-4de5-97c2-70645714fe61"));
//
////        provider.editProfileById("e4e1c071-9ce5-494a-b659-a39bb5a36c78", "town", "rostov");
////                log.info(provider.getProfile("e4e1c071-9ce5-494a-b659-a39bb5a36c78"));
////
////                provider.editEventById("2787d219-2043-4de5-97c2-70645714fe61", "title", "32");
////                log.info(provider.getEvent("2787d219-2043-4de5-97c2-70645714fe61"));