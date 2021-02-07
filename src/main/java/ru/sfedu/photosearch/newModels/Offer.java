package ru.sfedu.photosearch.newModels;

public class Offer<T> {
    private T id;
    private User user;
    private Event event;
    private Boolean isActive;

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

    public Offer(T id, User user, Event event, Boolean isActive) {
        this.id = id;
        this.user = user;
        this.event = event;
        this.isActive = isActive;
    }
}
