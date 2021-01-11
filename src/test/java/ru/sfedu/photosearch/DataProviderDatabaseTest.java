package ru.sfedu.photosearch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sfedu.photosearch.enums.Role;
import ru.sfedu.photosearch.utils.PhotoViewer;

import java.time.LocalDate;

class DataProviderDatabaseTest {
    private static Logger log = LogManager.getLogger(DataProviderDatabaseTest.class);
    public static DataProviderDatabase provider;


    void init(){
        provider = new DataProviderDatabase();
        provider.DB.connect();
        provider.DB.createTables();
        provider.DB.closeConnection();
    }

    @Test
    void getProfile() {
        init();
        provider.DB.connect();
//        provider.createNewProfile("Maksim","Gorky", 30, LocalDate.now().toString(), Role.CUSTOMER.name(), "rostov");
//        provider.addPhoto("1", "C:\\Users\\cucumber\\Downloads\\2742096282.jpg");
        log.info(provider.getProfile("2"));
        log.info(provider.getPhoto("2"));
        log.info(provider.getPortfolio("2"));

        PhotoViewer.showPhoto(provider.getPhotoPathById("2"));
        provider.DB.closeConnection();
    }
}