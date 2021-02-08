package ru.sfedu.photosearch.newModels;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.enums.Role;
import ru.sfedu.photosearch.utils.Formatter;

import java.util.ArrayList;
import java.util.List;

public class Photo<T> {
    public static final Logger log = LogManager.getLogger(ru.sfedu.photosearch.newModels.Photo.class);
    private T id;
    private User user;
    private String title;
    private String description;
    private Event event;
    private String tag;
    private String path;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Photo() {
    }

    public Photo(T id, User user, String title, String description, Event event, String tag, String path) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.description = description;
        this.event = event;
        this.tag = tag;
        this.path = path;
    }

    public String checkNull(String value){
        if (value == null){
            return Constants.UTIL_EMPTY_STRING;
        } else {
            return value;
        }
    }
    public String checkNullUser(User value){
        if (value == null){
            return Constants.UTIL_EMPTY_STRING;
        } else {
            return value.getId().toString();
        }
    }
    public String checkNullEvent(Event value){
        if (value == null){
            return Constants.UTIL_EMPTY_STRING;
        } else {
            return value.getId().toString();
        }
    }

    public String getPhotoOutput(){
        String result = String.format(Constants.XML_PHOTOS_OUTPUT,
                checkNull(getId().toString()),
                checkNullUser(getUser()),
                checkNullEvent(getEvent()),
                checkNull(getTitle()),
                checkNull(getDescription()),
                checkNull(getTag()),
                checkNull(getPath()));
        return result;
    }
    public String getCSVPhotoOutput(){
        String result = String.format(Constants.CSV_USERS_OUTPUT,
                checkNull(getId().toString()),
                checkNullUser(getUser()),
                checkNullEvent(getEvent()),
                checkNull(getTitle()),
                checkNull(getDescription()),
                checkNull(getTag()),
                checkNull(getPath()));
        return result;
    }

    public static List<Photo> convertFromCSV(List<String[]> data, User user, Event event){
        try {
            List<ru.sfedu.photosearch.newModels.Photo> photos = new ArrayList<>();
            for (String[] line : data) {
                photos.add(new ru.sfedu.photosearch.newModels.Photo(line, user, event));
            }
            return photos;
        }catch (Exception ex){
            log.error(Constants.ERROR_PHOTO_CONVERT + ex);
        }
        return null;
    }

    public Photo(String[] data, User user, Event event){
        this.id = (T) data[0];
        this.user = user;
        this.event = event;
        this.title = data[3];
        this.description = data[4];
        this.tag = data[5];
        this.path = data[6];
    }
}