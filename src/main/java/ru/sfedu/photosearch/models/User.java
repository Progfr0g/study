package ru.sfedu.photosearch.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.sfedu.photosearch.Constants;

@Root(name="user")
public class User {
    @Attribute
    private String id;
    @Element
    private String name;
    @Element
    private String last_name;
    @Element
    private String age;
    @Element
    private String dateOfRegistration;
    @Element
    private String town;
    @Element
    private String rating;

    public void setName(String name) {
        this.name = name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setDateOfRegistration(String dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getAge() {
        return age;
    }

    public String getDateOfRegistration() {
        return dateOfRegistration;
    }

    public String getTown() {
        return town;
    }

    public String getRating() {
        return rating;
    }
    public User() {
        super();
    }
    public User(String id, String name, String last_name, String age, String dateOfRegistration, String town, String rating) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.age = age;
        this.dateOfRegistration = dateOfRegistration;
        this.town = town;
        this.rating = rating;
    }
    public String checkNull(String value){
        if (value == null){
            return Constants.UTIL_EMPTY_STRING;
        } else {
            return value;
        }

    }

    public String getUserOutput(){
        String result = String.format(Constants.XML_USERS_OUTPUT, checkNull(getId()),
                                                                  checkNull(getName()),
                                                                  checkNull(getLast_name()),
                                                                  checkNull(getAge()),
                                                                  checkNull(getDateOfRegistration()),
                                                                  checkNull(getTown()),
                                                                  checkNull(getRating()));
        return result;
    }
}
