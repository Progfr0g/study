package ru.sfedu.photosearch.newModels;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Element;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.utils.Formatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Feedback<T> {
    public static final Logger log = LogManager.getLogger(Feedback.class);
    @Element
    private T id;
    @Element
    private User user;
    @Element
    private User photographer;
    @Element
    private Integer rate;
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

    public User getPhotographer() {
        return photographer;
    }

    public void setPhotographer(User photographer) {
        this.photographer = photographer;
    }

    public Integer getRate() { return rate; }

    public void setRate(Integer rate) { this.rate = rate; }

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



    public Feedback() {
    }

    public Feedback(T id, User user, User photographer, Integer rate, String text, Date date) {
        this.id = id;
        this.user = user;
        this.photographer = photographer;
        this.text = text;
        this.rate = rate;
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

    public String getFeedbackOutput(){
        String result = String.format(Constants.XML_FEEDBACKS_OUTPUT,
                checkNull(getId().toString()),
                checkNullUser(getUser()),
                checkNullUser(getPhotographer()),
                checkNull(getRate().toString()),
                checkNull(getText()),
                checkNull(getDate().toString()));
        return result;
    }

    public String getCSVFeedbackOutput(){
        String result = String.format(Constants.CSV_FEEDBACKS_OUTPUT,
                checkNull(getId().toString()),
                checkNullUser(getUser()),
                checkNullUser(getPhotographer()),
                checkNull(getRate().toString()),
                checkNull(getText()),
                checkNull(getDate().toString()));
        return result;
    }

    public static List<Feedback> convertFromCSV(List<String[]> data, List<User> costumers, List<User> photographers){
        try {
            List<Feedback> feedbacks = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i)[5] != null){
                    data.get(i)[5] = Formatter.birthDayToDB(Formatter.csvDateFromString(data.get(i)[5]));
                }
                feedbacks.add(new Feedback(data.get(i), costumers.get(i), photographers.get(i)));
            }
            return feedbacks;
        }catch (Exception ex){
            log.error(Constants.ERROR_FEEDBACK_CONVERT + ex);
            return null;
        }
    }

    public Feedback(String[] data, User user, User photographer){
        this.id = (T) data[0];
        this.user = user;
        this.photographer = photographer;
        this.rate = Integer.parseInt(data[3]);
        this.text = data[4];
        this.date = Formatter.birthDayFromDB(data[5]);
    }
}
