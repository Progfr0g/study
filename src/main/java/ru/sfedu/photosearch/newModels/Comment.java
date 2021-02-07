package ru.sfedu.photosearch.newModels;

import java.util.Date;

public class Comment<T> {
    private T id;
    private User user;
    private Photo photo;
    private Float rate;
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

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Comment() {
    }

    public Comment(T id, User user, Photo photo, Float rate, Date date) {
        this.id = id;
        this.user = user;
        this.photo = photo;
        this.rate = rate;
        this.date = date;
    }
}
