package ru.sfedu.photosearch.newModels;

import ru.sfedu.photosearch.enums.CostLevel;
import ru.sfedu.photosearch.enums.Role;

import java.util.Date;

public class Photographer extends User {
    Float rating;
    Integer experience;
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

    public Photographer() {
    }

    public Photographer(Object id, String name, String lastName, Date birthDay, Date dateOfRegistration, Role role, String town, Float wallet, Float rating, Integer experience, CostLevel costLevel) {
        super(id, name, lastName, birthDay, dateOfRegistration, role, town, wallet);
        this.rating = rating;
        this.experience = experience;
        this.costLevel = costLevel;
    }
}
