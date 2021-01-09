package ru.sfedu.photosearch.providers;

public interface DataProvider{
     boolean createNewProfile(
             String name,
             String last_name,
             Integer age,
             String date_of_registration,
             String role,
             String town);

    boolean createNewEvent(
            String title,
            String description,
            String customer,
            String event_date,
            Integer price,
            Float quantity);

    boolean editProfileById(String id, String field, String value);

    boolean editEventById(String id, String field, String value);

    void addPhotoByProfileId(String id, String field, String path);



}
