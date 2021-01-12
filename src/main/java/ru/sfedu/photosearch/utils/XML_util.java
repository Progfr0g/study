package ru.sfedu.photosearch.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.photosearch.Constants;


import java.io.File;

public class XML_util {
    private static Logger log = LogManager.getLogger(XML_util.class);
    public static void createFiles(){
        try {
            File theDir = new File(Constants.XML_DIR_PATH);
            if (!theDir.exists()){
                theDir.mkdirs();
            }
            File file = new File(Constants.XML_USERS_FILE_PATH);
            if (file.createNewFile()) {
                log.info("File created: " + file.getName());
            } else {
                log.info("File already exists.");
            }
        } catch (Exception e) {
            log.info("An error occurred." + e.getMessage());
        }
    }
}
