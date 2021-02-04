package ru.sfedu.photosearch.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.enums.Role;

import java.util.ArrayList;
import java.util.List;

@Root(name="user")
public class User {
    public static final Logger log = LogManager.getLogger(User.class);
    @Attribute
    private String id;
    @Element
    private String name;
    @Element
    private String last_name;
    @Element
    private Integer age;
    @Element
    private String dateOfRegistration;
    @Element
    private Role role;
    @Element
    private String town;
    @Element
    private double rating;

    public void setName(String name) {
        this.name = name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDateOfRegistration(String dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setRating(Float rating) {
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

    public int getAge() {
        return age;
    }

    public String getDateOfRegistration() {
        return dateOfRegistration;
    }

    public String getTown() {
        return town;
    }

    public double getRating() {
        return rating;
    }

    public Role getRole() { return role; }

    public void setRole(Role role) { this.role = role; }


    public User() {
        super();
    }
    public User(String id, String name, String last_name, Integer age, String dateOfRegistration, Role role, String town, double rating) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.age = age;
        this.dateOfRegistration = dateOfRegistration;
        this.role = role;
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
                                                                  checkNull(String.valueOf(getAge())),
                                                                  checkNull(getDateOfRegistration()),
                                                                  checkNull(getRole().toString()),
                                                                  checkNull(getTown()),
                                                                  checkNull(String.valueOf(getRating())));
        return result;
    }
    public String getCSVUserOutput(){
        String result = String.format(Constants.CSV_USERS_OUTPUT, checkNull(getId()),
                checkNull(getName()),
                checkNull(getLast_name()),
                checkNull(String.valueOf(getAge())),
                checkNull(getDateOfRegistration()),
                checkNull(getRole().toString()),
                checkNull(getTown()),
                checkNull(String.valueOf(getRating())));
        return result;
    }

    public static List<User> convertFromCSV(List<String[]> data){
        try {
            List<User> users = new ArrayList<>();
            for (String[] line : data) {
                users.add(new User(line));
            }
            return users;
        }catch (Exception ex){
            log.error(Constants.ERROR_USER_CONVERT + ex);
        }
        return null;
    }

    public User(String[] data){
        this.id = data[0];
        this.name = data[1];
        this.last_name = data[2];
        this.age = Integer.parseInt(data[3]);
        this.dateOfRegistration = data[4];
        this.role = Role.valueOf(data[5]);
        this.town = data[6];
        this.rating = Double.parseDouble(data[7]);
    }
}
