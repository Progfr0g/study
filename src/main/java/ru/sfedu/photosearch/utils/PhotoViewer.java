package ru.sfedu.photosearch.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.DataProviderDatabase;
import ru.sfedu.photosearch.Main;

import java.awt.Desktop;
import java.io.File;

public class PhotoViewer {
    private static Logger log = LogManager.getLogger(DataProviderDatabase.class);
    public static void showPhoto(String path) {
        try {
            File f = new File(path);
            Desktop dt = Desktop.getDesktop();
            dt.open(f);
        } catch (Exception er) {
            log.error(Constants.ERROR_SHOW_PHOTO + er);
        }

    }
}
