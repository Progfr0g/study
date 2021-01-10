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

    String getProfile(
            String id);

    String getEvent(
            String id);

    void editProfileById(String id, String field, String value);

    void editEventById(String id, String field, String value);

    void addPhotoByProfileId(String id, String field, String path);

    void deleteProfileById(String id);

    void deleteEventById(String id);


}
