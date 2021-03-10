package ru.sfedu.photosearch.newModels;

import org.simpleframework.xml.Element;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.enums.CostLevel;
import ru.sfedu.photosearch.enums.Role;
import ru.sfedu.photosearch.utils.Formatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Photographer<T> extends User<T> {
    @Element
    Float rating;
    @Element
    Integer experience;
    @Element
    CostLevel costLevel;

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public CostLevel getCostLevel() {
        return costLevel;
    }

    public void setCostLevel(CostLevel costLevel) {
        this.costLevel = costLevel;
    }

    public Photographer() { super();
    }

    public Photographer (T id, String name, String lastName, Date birthDay, Date dateOfRegistration, Role role, String town, Float wallet, Float rating, Integer experience, CostLevel costLevel) {
        super(id, name, lastName, birthDay, dateOfRegistration, role, town, wallet);
        this.rating = rating;
        this.experience = experience;
        this.costLevel = costLevel;
    }

    public static List<User> convertFromCSV(List<String[]> data){
        try {
            List<User> photographers = new ArrayList<>();
            for (String[] line : data) {
                if (line[3] != null){
                    line[3] = Formatter.birthDayToDB(Formatter.csvDateFromString(line[3]));
                }
                if (line[4] != null){
                    line[4] = Formatter.birthDayToDB(Formatter.csvDateFromString(line[4]));
                }
                photographers.add(new Photographer(line));
            }
            return photographers;
        }catch (Exception ex){
            log.error(Constants.ERROR_USER_CONVERT + ex);
            return null;
        }
    }

    public Photographer(String[] data){
        setId((T) data[0]);
        setName(data[1]);
        setLastName(data[2]);
        setBirthDay(Formatter.birthDayFromDB(data[3]));
        setDateOfRegistration(Formatter.dateOfRegistrationFromDB(data[4]));
        setRole(Role.valueOf(data[5].toUpperCase()));
        setTown(data[6]);
        setWallet(Float.valueOf(data[7]));
        setRating(Float.parseFloat(data[8]));
        setExperience(Integer.parseInt(data[9]));
        setCostLevel(CostLevel.valueOf(data[10].toUpperCase()));
    }

    @Override
    public String getUserOutput(){
        String result = String.format(Constants.XML_PHOTOGRAPHERS_OUTPUT,
                checkNull(getId().toString()),
                checkNull(getName()),
                checkNull(getLastName()),
                checkNull(getBirthDay().toString()),
                checkNull(getDateOfRegistration().toString()),
                checkNull(getRole().toString()),
                checkNull(getTown()),
                checkNull(getRating().toString()),
                checkNull(getExperience().toString()),
                checkNull(getCostLevel().toString()));
        return result;
    }

    @Override
    public String getCSVUserOutput(){
        String result = String.format(Constants.CSV_PHOTOGRAPHERS_OUTPUT,
                checkNull(getId().toString()),
                checkNull(getName()),
                checkNull(getLastName()),
                checkNull(getBirthDay().toString()),
                checkNull(getDateOfRegistration().toString()),
                checkNull(getRole().toString()),
                checkNull(getTown()),
                checkNull(getWallet().toString()),
                checkNull(getRating().toString()),
                checkNull(getExperience().toString()),
                checkNull(getCostLevel().toString()));
        return result;
    }
}
