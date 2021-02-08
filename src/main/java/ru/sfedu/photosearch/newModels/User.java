package ru.sfedu.photosearch.newModels;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.enums.Role;
import ru.sfedu.photosearch.utils.Formatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User<T> {
    public static final Logger log = LogManager.getLogger(ru.sfedu.photosearch.newModels.User.class);
    @Attribute
    private T id;
    @Element
    private String name;
    @Element
    private String lastName;
    @Element
    private Date birthDay;
    @Element
    private Date dateOfRegistration;
    @Element
    private Role role;
    @Element
    private String town;
    @Element
    private Float wallet;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Date dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public Float getWallet() {
        return wallet;
    }

    public void setWallet(Float wallet) {
        this.wallet = wallet;
    }

    public User() {
        super();
    }

    public User(T id, String name, String lastName, Date birthDay, Date dateOfRegistration, Role role, String town, Float wallet) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.dateOfRegistration = dateOfRegistration;
        this.role = role;
        this.town = town;
        this.wallet = wallet;
    }

    public String checkNull(String value){
        if (value == null){
            return Constants.UTIL_EMPTY_STRING;
        } else {
            return value;
        }
    }

    public String getUserOutput(){
        String result = String.format(Constants.XML_USERS_OUTPUT,
                checkNull(getId().toString()),
                checkNull(getName()),
                checkNull(getLastName()),
                checkNull(getBirthDay().toString()),
                checkNull(getDateOfRegistration().toString()),
                checkNull(getRole().toString()),
                checkNull(getTown()),
                checkNull(String.valueOf(getWallet())));
        return result;
    }
    public String getCSVUserOutput(){
        String result = String.format(Constants.CSV_USERS_OUTPUT,
                checkNull(getId().toString()),
                checkNull(getName()),
                checkNull(getLastName()),
                checkNull(getBirthDay().toString()),
                checkNull(getDateOfRegistration().toString()),
                checkNull(getRole().toString()),
                checkNull(getTown()),
                checkNull(String.valueOf(getWallet())));
        return result;
    }

    public static List<ru.sfedu.photosearch.newModels.User> convertFromCSV(List<String[]> data){
        try {
            List<ru.sfedu.photosearch.newModels.User> users = new ArrayList<>();
            for (String[] line : data) {
                if (line[3] != null){
                    line[3] = Formatter.normalFormatDay(Formatter.csvDateFromString(line[3]));
                }
                if (line[4] != null){
                    line[4] = Formatter.normalFormatDay(Formatter.csvDateFromString(line[4]));
                }
                users.add(new ru.sfedu.photosearch.newModels.User(line));
            }
            return users;
        }catch (Exception ex){
            log.error(Constants.ERROR_USER_CONVERT + ex);
        }
        return null;
    }

    public User(String[] data){
        this.id = (T) data[0];
        this.name = data[1];
        this.lastName = data[2];
        this.birthDay = Formatter.birthDayFromDB(data[3]);
        this.dateOfRegistration = Formatter.dateOfRegistrationFromDB(data[4]);
        this.role = Role.valueOf(data[5].toUpperCase());
        this.town = data[6];
        this.wallet = Float.valueOf(data[7]);
    }
}
