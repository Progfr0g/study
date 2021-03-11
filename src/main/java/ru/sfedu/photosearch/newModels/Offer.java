package ru.sfedu.photosearch.newModels;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Element;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.utils.Formatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Offer<T> {
    public static final Logger log = LogManager.getLogger(Offer.class);
    @Element
    private T id;
    @Element
    private User user;
    @Element
    private Event event;
    @Element
    private User photographer;
    @Element
    private Boolean isActive;
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

    public Event getEvent() { return event; }

    public void setEvent(Event event) { this.event = event; }

    public User getPhotographer() {
        return photographer;
    }

    public void setPhotographer(User photographer) {
        this.photographer = photographer;
    }

    public Boolean getActive() { return isActive; }

    public void setActive(Boolean active) { isActive = active; }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Offer() {
    }

    public Offer(T id, User user,Event event, User photographer, Boolean isActive, Date date) {
        this.id = id;
        this.user = user;
        this.event = event;
        this.photographer = photographer;
        this.isActive = isActive;
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

    public String checkNullEvent(Event value){
        if (value == null){
            return Constants.UTIL_EMPTY_STRING;
        } else {
            String result = value.getId().toString();
            return result;
        }
    }

    public String getOfferOutput(){
        String result = String.format(Constants.XML_OFFERS_OUTPUT,
                checkNull(getId().toString()),
                checkNullUser(getUser()),
                checkNullEvent(getEvent()),
                checkNullUser(getPhotographer()),
                checkNull(getActive().toString()),
                checkNull(getDate().toString()));
        return result;
    }

    public String getCSVOfferOutput(){
        String result = String.format(Constants.CSV_OFFERS_OUTPUT,
                checkNull(getId().toString()),
                checkNullUser(getUser()),
                checkNullEvent(getEvent()),
                checkNullUser(getPhotographer()),
                checkNull(getActive().toString()),
                checkNull(getDate().toString()));
        return result;
    }

    public static List<Offer> convertFromCSV(List<String[]> data, List<User> costumers, List<User> photographers,
                                             List<Event> events){
        try {
            List<Offer> offers = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i)[5] != null){
                    data.get(i)[5] = Formatter.birthDayToDB(Formatter.csvDateFromString(data.get(i)[5]));
                }
                offers.add(new Offer(data.get(i), costumers.get(i), events.get(i), photographers.get(i)));
            }
            return offers;
        }catch (Exception ex){
            log.error(Constants.ERROR_OFFER_CONVERT + ex);
            return null;
        }
    }

    public Offer(String[] data, User user, Event event, User photographer){
        this.id = (T) data[0];
        this.user = user;
        this.event = event;
        this.photographer = photographer;
        this.isActive = true;
        this.date = Formatter.birthDayFromDB(data[5]);
    }
}
