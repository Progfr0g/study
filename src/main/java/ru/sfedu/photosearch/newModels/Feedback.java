package ru.sfedu.photosearch.newModels;

public class Feedback<T> {
    private T id;
    private User user;
    private User photographer;
    private Float rate;
    private String text;

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

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Feedback() {
    }

    public Feedback(T id, User user, User photographer, Float rate, String text) {
        this.id = id;
        this.user = user;
        this.photographer = photographer;
        this.rate = rate;
        this.text = text;
    }
}