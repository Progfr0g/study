package ru.sfedu.photosearch.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.enums.PaymentMethod;
import ru.sfedu.photosearch.enums.Role;
import ru.sfedu.photosearch.enums.Status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Root(name="photo")
public class Photo{
    public static final Logger log = LogManager.getLogger(User.class);
    @Attribute
    private String id;
    @Element
    private String user_id;
    @Element(required = false)
    private String event_id;
    @Element(required = false)
    private String title;
    @Element(required = false)
    private String description;
    @Element(required = false)
    private String tag;
    @Element
    private String photo_path;
    @Element(required = false)
    private double rating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Photo() {
        super();
    }

    public Photo(String id, String user_id, String event_id, String title, String description, String tag, String photo_path, double rating) {
        this.id = id;
        this.user_id = user_id;
        this.event_id = event_id;
        this.title = title;
        this.description = description;
        this.tag = tag;
        this.photo_path = photo_path;
        this.rating = rating;
    }

    public String checkNull(String value){
        if (value == null){
            return Constants.UTIL_EMPTY_STRING;
        } else {
            return value;
        }

    }

    public String getPhotoOutput(){
        String result = String.format(Constants.XML_PHOTOS_OUTPUT,
                checkNull(String.valueOf(getId())),
                checkNull(getUser_id()),
                checkNull(getEvent_id()),
                checkNull(getTitle()),
                checkNull(getDescription()),
                checkNull(getTag()),
                checkNull(getPhoto_path()),
                checkNull(String.valueOf(getRating())));
        return result;
    }

    public String getCSVPhotoOutput(){
        String result = String.format(Constants.CSV_PHOTOS_OUTPUT,
                checkNull(String.valueOf(getId())),
                checkNull(getUser_id()),
                checkNull(getEvent_id()),
                checkNull(getTitle()),
                checkNull(getDescription()),
                checkNull(getPhoto_path()),
                checkNull(String.valueOf(getRating())));
        return result;
    }

    public static List<Photo> convertFromCSV(List<String[]> data){
        try {
            List<Photo> photos = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                photos.add(new Photo(data.get(i)));
            }
            return photos;
        }catch (Exception ex){
            log.error(Constants.ERROR_USER_CONVERT + ex);
        }
        return null;
    }

    public Photo(String[] data){
        this.id = data[0];
        this.user_id = data[1];
        this.event_id = data[2];
        this.title = data[3];
        this.description = data[4];
        this.tag = data[5];
        this.photo_path = data[6];
        this.rating = Double.parseDouble(data[7]);
    }


}
