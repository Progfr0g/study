package ru.sfedu.photosearch.providers;

import ru.sfedu.photosearch.enums.EventType;
import ru.sfedu.photosearch.enums.Role;
import ru.sfedu.photosearch.newModels.Event;
import ru.sfedu.photosearch.newModels.Photo;
import ru.sfedu.photosearch.newModels.User;

import java.util.Date;

public interface DataProvider{
     Boolean createNewProfile(
             String name,
             String last_name,
             Date birthDay,
             Date dateOfRegistration,
             Role role,
             String town);

    User getProfile(
            String id);

    Boolean editProfileById(String id, String field, String value);

    Boolean deleteProfileById(String id);

    Boolean createNewEvent(
            String title,
            String description,
            String customer,
            Date eventDate,
            Date creationDate,
            Integer price,
            Float quantity,
            EventType type);

    Event getEvent(
            String id);

    Boolean editEventById(String id, String field, String value);

    Boolean deleteEventById(String id);

    Boolean addPhoto(String id, String path);

    Photo getPhoto(String id);

    Boolean editPhotoById(String id, String field, String value);

    void deletePhotoById(String id);

    String getPortfolio(String user_id);

    String getPhotoPathById(String id);

}
