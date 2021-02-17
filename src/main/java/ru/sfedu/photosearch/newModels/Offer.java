package ru.sfedu.photosearch.newModels;

import org.simpleframework.xml.Element;

import java.util.Date;

public class Offer<T> {
    @Element
    private T id;
    @Element
    private User user;
    @Element
    private Event event;
    @Element
    private Boolean isActive;
    @Element
    private Date creationDate;

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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Offer() {
    }

    public Offer(T id, User user, Event event, Boolean isActive, Date creationDate) {
        this.id = id;
        this.user = user;
        this.event = event;
        this.isActive = isActive;
        this.creationDate = creationDate;
    }
}
