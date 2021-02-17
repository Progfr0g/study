package ru.sfedu.photosearch.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.photosearch.Constants;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XML_util {
    private static Logger log = LogManager.getLogger(XML_util.class);
    public static void createFiles(){
        List<String> paths = new ArrayList<>();
        paths.add(Constants.XML_USERS_FILE_PATH);
        paths.add(Constants.XML_EVENTS_FILE_PATH);
        paths.add(Constants.XML_PHOTOS_FILE_PATH);
        paths.add(Constants.XML_COMMENTS_FILE_PATH);
        paths.add(Constants.XML_RATES_FILE_PATH);
        try {
            File theDir = new File(Constants.XML_DIR_PATH);
            if (!theDir.exists()){
                theDir.mkdirs();
            }
            for (String path: paths){
                File file = new File(path);
                if (file.createNewFile()) {
                    log.info("File created: " + file.getName());
                }
//                else {
//                    log.info("File already exists: "+ file.getName());
//                }
            }

        } catch (Exception e) {
            log.info("An error occurred." + e.getMessage());
        }
    }
}
