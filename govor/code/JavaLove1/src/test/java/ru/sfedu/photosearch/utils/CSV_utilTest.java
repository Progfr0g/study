package ru.sfedu.photosearch.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.sfedu.photosearch.PhotoSearchClient;
import ru.sfedu.photosearch.providers.DataProviderCSV;
import ru.sfedu.photosearch.providers.DataProviderXML;

import static org.junit.jupiter.api.Assertions.*;

class CSV_utilTest {
    public static final Logger log = LogManager.getLogger(CSV_utilTest.class);

    @Test
    void write_files() throws Exception {
        DataProviderCSV provider = new DataProviderCSV();
        CSV_util.createFiles();
;
    }
}