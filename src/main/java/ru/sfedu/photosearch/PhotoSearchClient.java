package ru.sfedu.photosearch;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


import java.sql.DriverManager;

public class PhotoSearchClient {

    public static final Logger log = LogManager.getLogger(PhotoSearchClient.class);
//    public PhotoSearchClient() {
//        log.debug("Client[0]: starting application...");
//    }
//
    public void log_info(String string){
        log.info(string);
    }

    public void log_debug(String string){
        log.debug(string);
    }
//
//    public void logBasicSystemInfo() {
//
//        log.info("Launching the application...");
//        log.info("Operating System: " + System.getProperty("os.name") + " " +
//                System.getProperty("os.version"));
//        log.info("JRE: " + System.getProperty("java.version"));
//        log.info("Java Launched From: " + System.getProperty("java.home"));
//        log.info("Class Path: " + System.getProperty("java.class.path"));
//        log.info("Library Path: " + System.getProperty("java.library.path"));
//        log.info("User Home Directory: " + System.getProperty("user.home"));
//        log.info("User Working Directory: " + System.getProperty("user.dir"));
//        log.info("Test INFO logging.");
//    }

//    public static void main(String[] args) {
//        PhotoSearchClient cl = new PhotoSearchClient();
//        cl.logBasicSystemInfo();
//    }
}
