package ru.sfedu.photosearch.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.photosearch.Constants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *  класс для работы с CSV файлами (создание файлов)
 */
public class CSV_util {
    private static Logger log = LogManager.getLogger(CSV_util.class);

    /**
     * Создание файлов и папок CSV
     * @throws IOException
     */
    public static void createFiles() throws IOException {
        List<String> paths = new ArrayList<>();
        paths.add(ConfigurationUtil.getConfigurationEntry(Constants.CSV_CONFIG_USERS_FILE_PATH));
        paths.add(ConfigurationUtil.getConfigurationEntry(Constants.CSV_CONFIG_PHOTOGRAPHERS_FILE_PATH));
        paths.add(ConfigurationUtil.getConfigurationEntry(Constants.CSV_CONFIG_EVENTS_FILE_PATH));
        paths.add(ConfigurationUtil.getConfigurationEntry(Constants.CSV_CONFIG_PHOTOS_FILE_PATH));
        paths.add(ConfigurationUtil.getConfigurationEntry(Constants.CSV_CONFIG_COMMENTS_FILE_PATH));
        paths.add(ConfigurationUtil.getConfigurationEntry(Constants.CSV_CONFIG_RATES_FILE_PATH));
        paths.add(ConfigurationUtil.getConfigurationEntry(Constants.CSV_CONFIG_FEEDBACKS_FILE_PATH));
        paths.add(ConfigurationUtil.getConfigurationEntry(Constants.CSV_CONFIG_OFFERS_FILE_PATH));
        try {
            File theDir = new File(ConfigurationUtil.getConfigurationEntry(Constants.CSV_CONFIG_DIR_PATH));
            if (!theDir.exists()){
                theDir.mkdirs();
            }
            for (String path: paths){

                File check_file = new File(path);
                if (!check_file.exists() || check_file.length() == 0) {
                    FileWriter file = new FileWriter(path);
                    CSVWriter writer = new CSVWriter(file);
                    switch (path){
                        case Constants.CSV_USERS_FILE_PATH:{
                            String[] record = Constants.CSV_USERS_COLUMNS.split(", ");
                            writer.writeNext(record);
                            break;
                        }
                        case Constants.CSV_PHOTOGRAPHERS_FILE_PATH:{
                            String[] record = Constants.CSV_PHOTOGRAPHERS_COLUMNS.split(", ");
                            writer.writeNext(record);
                            break;
                        }
                        case Constants.CSV_EVENTS_FILE_PATH:{
                            String[] record = Constants.CSV_EVENTS_COLUMNS.split(", ");
                            writer.writeNext(record);
                            break;
                        }
                        case Constants.CSV_PHOTOS_FILE_PATH:{
                            String[] record = Constants.CSV_PHOTOS_COLUMNS.split(", ");
                            writer.writeNext(record);
                            break;
                        }
                        case Constants.CSV_COMMENTS_FILE_PATH:{
                            String[] record = Constants.CSV_COMMENTS_COLUMNS.split(", ");
                            writer.writeNext(record);
                            break;
                        }
                        case Constants.CSV_RATES_FILE_PATH:{
                            String[] record = Constants.CSV_RATES_COLUMNS.split(", ");
                            writer.writeNext(record);
                            break;
                        }
                        case Constants.CSV_FEEDBACKS_FILE_PATH:{
                            String[] record = Constants.CSV_FEEDBACKS_COLUMNS.split(", ");
                            writer.writeNext(record);
                            break;
                        }
                        case Constants.CSV_OFFERS_FILE_PATH:{
                            String[] record = Constants.CSV_OFFERS_COLUMNS.split(", ");
                            writer.writeNext(record);
                            break;
                        }
                    }
                    writer.close();
                    log.debug(Constants.FILE_CREATED + path);
                }
//                else {
//                    log.debug(Constants.FILE_EXISTS + check_file.getName());
//                }
            }

        } catch (Exception ex) {
            log.info("An error occurred." + ex.getMessage());
        }
    }

    /**
     * используется для удаления файлов после тестов
     */
    public static void deleteFiles() {
        try {
            File theDir = new File(ConfigurationUtil.getConfigurationEntry(Constants.CSV_CONFIG_DIR_PATH));
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
