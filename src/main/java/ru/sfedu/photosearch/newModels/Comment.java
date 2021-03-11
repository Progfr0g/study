package ru.sfedu.photosearch.newModels;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Element;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.enums.EventStatus;
import ru.sfedu.photosearch.enums.EventType;
import ru.sfedu.photosearch.utils.Formatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Comment<T> {
    public static final Logger log = LogManager.getLogger(Comment.class);
    @Element
    private T id;
    @Element
    private User user;
    @Element
    private Photo photo;
    @Element
    private String text;
    @Element
    private Date date;

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

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Comment() {
    }

    public Comment(T id, User user, Photo photo, String text, Date date) {
        this.id = id;
        this.user = user;
        this.photo = photo;
        this.text = text;
        this.date = date;
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
            String result = value.getId().toString();
            return result;
        }
    }
    public String checkNullPhoto(Photo value){
        if (value == null){
            return Constants.UTIL_EMPTY_STRING;
        } else {
            String result = value.getId().toString();
            return result;
        }
    }

    public String getCommentOutput(){
        String result = String.format(Constants.XML_COMMENTS_OUTPUT,
                checkNull(getId().toString()),
                checkNullUser(getUser()),
                checkNullPhoto(getPhoto()),
                checkNull(getText()),
                checkNull(getDate().toString()));
        return result;
    }

    public String getCSVCommentOutput(){
        String result = String.format(Constants.CSV_COMMENTS_OUTPUT,
                checkNull(getId().toString()),
                checkNullUser(getUser()),
                checkNullPhoto(getPhoto()),
                checkNull(getText()),
                checkNull(getDate().toString()));
        return result;
    }

    public static List<Comment> convertFromCSV(List<String[]> data, List<User> costumers, List<Photo> photos){
        try {
            List<Comment> events = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                if (!data.get(i)[4].equals(null)){
                    data.get(i)[4] = Formatter.birthDayToDB(Formatter.csvDateFromString(data.get(i)[4]));
                }
                events.add(new Comment(data.get(i), costumers.get(i), photos.get(i)));
            }
            return events;
        }catch (Exception ex){
            log.error(Constants.ERROR_COMMENT_CONVERT + ex);
            return null;
        }
    }

    public Comment(String[] data, User user, Photo photo){
        this.id = (T) data[0];
        this.user = user;
        this.photo = photo;
        this.text = data[3];
        this.date = Formatter.birthDayFromDB(data[4]);
    }
}
