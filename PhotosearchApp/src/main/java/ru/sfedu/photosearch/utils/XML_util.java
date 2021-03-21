package ru.sfedu.photosearch.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.photosearch.Constants;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XML_util {
    private static Logger log = LogManager.getLogger(XML_util.class);

    /**
     * Создание файлов и папок XML
     * @throws IOException
     */
    public static void createFiles() throws IOException {
        List<String> paths = new ArrayList<>();
        paths.add(ConfigurationUtil.getConfigurationEntry(Constants.XML_CONFIG_USERS_FILE_PATH));
        paths.add(ConfigurationUtil.getConfigurationEntry(Constants.XML_CONFIG_PHOTOGRAPHERS_FILE_PATH));
        paths.add(ConfigurationUtil.getConfigurationEntry(Constants.XML_CONFIG_EVENTS_FILE_PATH));
        paths.add(ConfigurationUtil.getConfigurationEntry(Constants.XML_CONFIG_PHOTOS_FILE_PATH));
        paths.add(ConfigurationUtil.getConfigurationEntry(Constants.XML_CONFIG_COMMENTS_FILE_PATH));
        paths.add(ConfigurationUtil.getConfigurationEntry(Constants.XML_CONFIG_FEEDBACKS_FILE_PATH));
        paths.add(ConfigurationUtil.getConfigurationEntry(Constants.XML_CONFIG_OFFERS_FILE_PATH));
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
//                    log.debug("File already exists: "+ file.getName());
//                }
            }

        } catch (Exception e) {
            log.info("An error occurred." + e.getMessage());
        }
    }

    /**
     * используется для удаления файлов после тестов
     */
    public static void deleteFiles() throws IOException {
        try {
            File theDir = new File(Constants.XML_DIR_PATH);
            if (theDir.exists()) {
                theDir.delete();
                String[] entries = theDir.list();
                for(String s: entries){
                    File currentFile = new File(theDir.getPath(),s);
                    currentFile.delete();
                }
            }
        } catch (Exception e) {
            log.info("An error occurred." + e.getMessage());
        }
    }
}
