package ru.sfedu.photosearch.providers;

import ru.sfedu.photosearch.enums.EventType;
import ru.sfedu.photosearch.enums.Role;
import ru.sfedu.photosearch.newModels.Comment;
import ru.sfedu.photosearch.newModels.Event;
import ru.sfedu.photosearch.newModels.Photo;
import ru.sfedu.photosearch.newModels.User;

import java.util.ArrayList;
import java.util.Date;

public interface DataProvider{
     Boolean createNewProfile(
             String name,
             String lastName,
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

    Boolean deletePhotoById(String id);

    ArrayList<Photo> getPortfolio(String userId);

    String getPhotoPathById(String id);

    String getLastUserId();

    String getLastEventId();

    String getLastPhotoId();

    ArrayList<User> getAllUsers();

    ArrayList<Event> getAllEvents();

    ArrayList<Photo> getAllPhotos();

    Boolean addComment(String userId, String photoId, String comment, Date date);

    ArrayList<Comment> getAllComments();

    Boolean addRate(String userId, String photoId, Float rate, Date date);

    Boolean addFeedback(String userId, String photographerId, Float rate, Date creationDate);

    Boolean createOffer(String userId, String eventId, Date creationDate);

    ArrayList<User> searchUsers(String field, String value);

}
