package ru.sfedu.photosearch;

public class User {
    private int id;
    private String name;
    private String last_name;
    private int age;
    private String date_of_registration;
    private String town;
    private float rating;

    public void setName(String name) {
        this.name = name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDate_of_registration(String date_of_registration) {
        this.date_of_registration = date_of_registration;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getId() {
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

    public String getDate_of_registration() {
        return date_of_registration;
    }

    public String getTown() {
        return town;
    }

    public float getRating() {
        return rating;
    }

    public User(int id, String name, String last_name, int age, String date_of_registration, String town, float rating) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.age = age;
        this.date_of_registration = date_of_registration;
        this.town = town;
        this.rating = rating;
    }
}
