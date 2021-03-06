package ru.sfedu.photosearch.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.providers.DataProviderDatabase;

import java.awt.Desktop;
import java.io.File;

/**
 * Класс для просмотра фотографий с помощью ОС
 */
public class PhotoViewer {
    private static Logger log = LogManager.getLogger(PhotoViewer.class);

    /**
     * Выводит фото в окне по полученному пути
     * @param path - путь к файлу фото
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    public static Boolean showPhoto(String path) {
        try {
            File f = new File(path);
            Desktop dt = Desktop.getDesktop();
            dt.open(f);
            return true;
        } catch (Exception er) {
            log.info(String.format(Constants.ERROR_FILE_DOESNT_EXISTS, path));
            return false;
        }
    }
}
