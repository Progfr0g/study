package ru.sfedu.photosearch.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

public class Rate extends Comment{
    @Attribute
    private String id;
    @Element
    private String user_id;
    @Element(required = false)
    private String photo_id;
    @Element(required = false)
    private String title;
    @Element(required = false)
    private String description;

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    @Element(required = false)
    private Float rate;

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

    public String getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(String photo_id) {
        this.photo_id = photo_id;
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

}
