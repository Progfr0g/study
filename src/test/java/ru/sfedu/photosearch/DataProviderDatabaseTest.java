package ru.sfedu.photosearch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import ru.sfedu.photosearch.providers.DataProvider;

class DataProviderDatabaseTest {
    private static Logger log = LogManager.getLogger(DataProviderDatabaseTest.class);
    @Test
    void hh(){
        DataProviderDatabase data = new DataProviderDatabase();

        log.info(data.editProfileById("5", "age", "2"));
    }

}