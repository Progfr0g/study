package ru.sfedu.photosearch.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.photosearch.Constants;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class CSV_util {
    private static Logger log = LogManager.getLogger(CSV_util.class);

    public static void createFiles(){
        List<String> paths = new ArrayList<>();
        paths.add(Constants.CSV_USERS_FILE_PATH);
        paths.add(Constants.CSV_PHOTOGRAPHERS_FILE_PATH);
        paths.add(Constants.CSV_EVENTS_FILE_PATH);
        paths.add(Constants.CSV_PHOTOS_FILE_PATH);
        paths.add(Constants.CSV_COMMENTS_FILE_PATH);
        paths.add(Constants.CSV_RATES_FILE_PATH);
        try {
            File theDir = new File(Constants.CSV_DIR_PATH);
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
}
