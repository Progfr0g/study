package ru.sfedu.photosearch.providers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.enums.*;
import ru.sfedu.photosearch.newModels.*;
import ru.sfedu.photosearch.utils.Formatter;
import ru.sfedu.photosearch.xmlTables.*;

import java.io.File;
import java.util.*;

public class DataProviderXML implements DataProvider {
    private static Logger log = LogManager.getLogger(DataProviderXML.class);

    @Override
    public Boolean createNewProfile(String name,
                                    String lastName,
                                    Date birthDay,
                                    Date dateOfRegistration,
                                    Role role,
                                    String town) {
        try {
            Serializer serializer = new Persister();
            XML_UsersTable table;
            XML_PhotographersTable phTable;
            if (Role.PHOTOGRAPHER.equals(role)){
                File source = new File(Constants.XML_PHOTOGRAPHERS_FILE_PATH);
                if (source.length() == 0) {
                    List<Photographer> photographers_array = new ArrayList<>();
                    UUID id = UUID.randomUUID();
                    Photographer<UUID> newUser = new Photographer<UUID>(id, name, lastName, birthDay, dateOfRegistration, role,
                            town,
                            Constants.DEFAULT_WALLET,
                            Constants.DEFAULT_RATING,
                            0,
                            CostLevel.NONE);
                    phTable = new XML_PhotographersTable();
                    photographers_array.add(newUser);
                    phTable.setUsers(photographers_array);
                    File result = new File(Constants.XML_PHOTOGRAPHERS_FILE_PATH);
                    log.info(String.format(Constants.SUCCESS_NEW_PROFILE_XML, id));
                    serializer.write(phTable, result);
                    return true;
                } else {
                    phTable = serializer.read(XML_PhotographersTable.class, source);
                    List<Photographer> writedUsers = phTable.getxmlPhotographers();
                    UUID id = UUID.randomUUID();
                    Photographer<UUID> newUser = new Photographer<UUID>(id, name, lastName, birthDay, dateOfRegistration, role,
                            town,
                            Constants.DEFAULT_WALLET,
                            Constants.DEFAULT_RATING,
                            0,
                            CostLevel.NONE);
                    writedUsers.add(newUser);
                    File result = new File(Constants.XML_PHOTOGRAPHERS_FILE_PATH);
                    phTable.setUsers(writedUsers);
                    serializer.write(phTable, result);
                    log.info(String.format(Constants.SUCCESS_NEW_PROFILE_XML, id));
                    return true;
                }
            } else if (Role.CUSTOMER.equals(role)){
                File source = new File(Constants.XML_USERS_FILE_PATH);
                if (source.length() == 0) {
                    List<User> users_array = new ArrayList<>();
                    UUID id = UUID.randomUUID();
                    User<UUID> newUser = new User<UUID>(id, name, lastName, birthDay, dateOfRegistration, role, town, Constants.DEFAULT_RATING);
                    table = new XML_UsersTable();
                    users_array.add(newUser);
                    table.setUsers(users_array);
                    File result = new File(Constants.XML_USERS_FILE_PATH);
                    log.info(String.format(Constants.SUCCESS_NEW_PROFILE_XML, id));
                    serializer.write(table, result);
                    return true;
                } else {
                    table = serializer.read(XML_UsersTable.class, source);
                    List<User> writedUsers = table.getxmlUsers();
                    UUID id = UUID.randomUUID();
                    User<UUID> newUser = new User<UUID>(id, name, lastName, birthDay, dateOfRegistration, role, town, Constants.DEFAULT_RATING);
                    writedUsers.add(newUser);
                    File result = new File(Constants.XML_USERS_FILE_PATH);
                    table.setUsers(writedUsers);
                    serializer.write(table, result);
                    log.info(String.format(Constants.SUCCESS_NEW_PROFILE_XML, id));
                    return true;
                }
            } else return false;
        } catch (Exception ex) {
            log.error(Constants.ERROR_INSERT_QUERY + ex.getMessage());
            return false;
        }
    }

    @Override
    public Optional<User> getProfile(String id) {
        try{
            Serializer serializer = new Persister();
            File source = new File(Constants.XML_USERS_FILE_PATH);
            Integer rows = 0;
            if (source.length() > 0) {
                XML_UsersTable table;
                table = serializer.read(XML_UsersTable.class, source);
                for (User user : table.getxmlUsers()) {
                    if (user.getId().equals(id)) {
                        rows++;
                        return Optional.of(user);
                    }
                }
            } else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_PHOTOGRAPHERS_FILE_PATH));
                return Optional.empty();
            }
            source = new File(Constants.XML_PHOTOGRAPHERS_FILE_PATH);
            if (source.length() > 0) {
                XML_PhotographersTable phTable;
                phTable = serializer.read(XML_PhotographersTable.class, source);
                for (Photographer photographer: phTable.getxmlPhotographers()) {
                    if (photographer.getId().equals(id)) {
                        rows++;
                        return Optional.of(photographer);
                    }
                }
            } else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_USERS_FILE_PATH));
                return Optional.empty();
            }
            if (rows == 0) {
                log.info(String.format(Constants.EMPTY_GET_PROFILE, id));
                return Optional.empty();
            }
        } catch (Exception ex) {
            log.error(String.format(Constants.ERROR_GET_PROFILE, id) + ex);
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public Boolean editProfileById(String id, String field, String value) {
        try {
            Serializer serializer = new Persister();
            File sourceUsers = new File(Constants.XML_USERS_FILE_PATH);
            File sourcePhotographers = new File(Constants.XML_PHOTOGRAPHERS_FILE_PATH);
            XML_UsersTable table;
            XML_PhotographersTable phTable;
            Integer rowsUsers = 0;
            Integer rowsPhotographers = 0;
            if (sourceUsers.length() > 0) {
                table = serializer.read(XML_UsersTable.class, sourceUsers);
                List<User> writedUsers = table.getxmlUsers();
                for (User user: writedUsers) {
                    if (id.equals(user.getId())) {
                        switch (field) {
                            case Constants.USERS_NAME: {
                                user.setName(value);
                                log.debug(String.format(Constants.SUCCESS_UPDATE_PROFILE_XML, id));
                                break;
                            }
                            case Constants.USERS_LAST_NAME: {
                                user.setLastName(value);
                                log.debug(String.format(Constants.SUCCESS_UPDATE_PROFILE_XML, id));
                                break;
                            }
                            case Constants.USERS_BIRTHDAY: {
                                user.setBirthDay(Formatter.normalFormatDay(value));
                                log.debug(String.format(Constants.SUCCESS_UPDATE_PROFILE_XML, id));
                                break;
                            }
                            case Constants.USERS_TOWN: {
                                user.setTown(value);
                                log.debug(String.format(Constants.SUCCESS_UPDATE_PROFILE_XML, id));
                                break;
                            }
                            default: {
                                log.warn(String.format(Constants.ERROR_WRONG_FIELD, field));
                                return false;
                            }
                        }
                        rowsUsers++;
                    }
                }
                if (rowsUsers != 0) {
                    table.setUsers(writedUsers);
                    File result = new File(Constants.XML_USERS_FILE_PATH);
                    serializer.write(table, result);
                    return true;
                }
            } else {
                log.warn(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_USERS_FILE_PATH));
            }
            if (sourcePhotographers.length() > 0) {
                phTable = serializer.read(XML_PhotographersTable.class, sourcePhotographers);
                List<Photographer> writedPhotographers = phTable.getxmlPhotographers();
                for (Photographer photographer : writedPhotographers) {
                    if (id.equals(photographer.getId())) {
                        switch (field) {
                            case Constants.USERS_NAME: {
                                photographer.setName(value);
                                log.debug(String.format(Constants.SUCCESS_UPDATE_PROFILE_XML, id));
                                break;
                            }
                            case Constants.USERS_LAST_NAME: {
                                photographer.setLastName(value);
                                log.debug(String.format(Constants.SUCCESS_UPDATE_PROFILE_XML, id));
                                break;
                            }
                            case Constants.USERS_BIRTHDAY: {
                                photographer.setBirthDay(Formatter.normalFormatDay(value));
                                log.debug(String.format(Constants.SUCCESS_UPDATE_PROFILE_XML, id));
                                break;
                            }
                            case Constants.USERS_TOWN: {
                                photographer.setTown(value);
                                log.debug(String.format(Constants.SUCCESS_UPDATE_PROFILE_XML, id));
                                break;
                            }
                            case Constants.PHOTOGRAPHERS_EXPERIENCE: {
                                photographer.setExperience(Integer.parseInt(value));
                                log.debug(String.format(Constants.SUCCESS_UPDATE_PROFILE_XML, id));
                                break;
                            }
                            case Constants.PHOTOGRAPHERS_COSTLEVEL: {
                                photographer.setCostLevel(CostLevel.valueOf(value.toUpperCase()));
                                log.debug(String.format(Constants.SUCCESS_UPDATE_PROFILE_XML, id));
                                break;
                            }
                            default: {
                                log.warn(String.format(Constants.ERROR_WRONG_FIELD, field));
                                return null;
                            }
                        }
                        rowsPhotographers++;
                    }
                }
                if (rowsPhotographers != 0) {
                    phTable.setUsers(writedPhotographers);
                    File result = new File(Constants.XML_PHOTOGRAPHERS_FILE_PATH);
                    serializer.write(phTable, result);
                    return true;
                }
            } else {
                log.warn(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_PHOTOGRAPHERS_FILE_PATH));
            }
            if (rowsPhotographers == 0){
                log.info(String.format(Constants.EMPTY_GET_PROFILE, id));
                return false;
            } else return false;
        } catch (Exception ex) {
            log.error(Constants.ERROR_UPDATE_QUERY + ex.getMessage());
            return false;
        }
    }

    @Override
    public Boolean deleteProfileById(String id) {
        try {
            Serializer serializer = new Persister();
            File sourceUsers = new File(Constants.XML_USERS_FILE_PATH);
            File sourcePhotographers = new File(Constants.XML_PHOTOGRAPHERS_FILE_PATH);
            XML_UsersTable table;
            XML_PhotographersTable phTable;
            Integer rowsUsers = 0;
            Integer rowsPhotographers = 0;
            if (sourceUsers.length() > 0) {
                table = serializer.read(XML_UsersTable.class, sourceUsers);
                List<User> writedUsers = table.getxmlUsers();
                for (User user: writedUsers){
                    if (id.equals(user.getId())){
                        writedUsers.remove(user);
                        rowsUsers++;
                        break;
                    }
                }
                if (rowsUsers != 0) {
                    log.debug(String.format(Constants.SUCCESS_DELETE_PROFILE_XML, id));
                    table.setUsers(writedUsers);
                    File result = new File(Constants.XML_USERS_FILE_PATH);
                    serializer.write(table, result);
                    return true;
                }
            } else {
                log.warn(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_USERS_FILE_PATH));
            }
            if (sourcePhotographers.length() > 0) {
                phTable = serializer.read(XML_PhotographersTable.class, sourcePhotographers);
                List<Photographer> writedPhotographers = phTable.getxmlPhotographers();
                for (Photographer photographer: writedPhotographers){
                    if (id.equals(photographer.getId())){
                        writedPhotographers.remove(photographer);
                        rowsPhotographers++;
                        break;
                    }
                }
                if (rowsPhotographers != 0){
                    log.debug(String.format(Constants.SUCCESS_DELETE_PROFILE_XML, id));
                    phTable.setUsers(writedPhotographers);
                    File result = new File(Constants.XML_PHOTOGRAPHERS_FILE_PATH);
                    serializer.write(phTable, result);
                    return true;
                }
            } else {
                log.warn(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_PHOTOGRAPHERS_FILE_PATH));
                return false;
            }
            if (rowsPhotographers == 0){
                log.info(String.format(Constants.EMPTY_GET_PROFILE, id));
                return true;
            } else return false;
        } catch (Exception ex) {
            log.error(Constants.ERROR_DELETE_QUERY + ex.getMessage());
            return false;
        }
    }

    @Override
    public Boolean createNewEvent(String title,
                                 String description,
                                 String customer,
                                 Date eventDate,
                                 Date creationDate,
                                 Integer price,
                                 Float quantity,
                                 EventType type) {
        try {
            Serializer serializer = new Persister();
            File source = new File(Constants.XML_EVENTS_FILE_PATH);
            XML_EventsTable table;
            if (source.length() == 0) {
                List<Event> events_array = new ArrayList<>();
                UUID id = UUID.randomUUID();
                EventStatus status = EventStatus.NONE;
                User userCustomer = getProfile(customer).orElse(null);
                Event<UUID> newEvent = new Event<UUID>(id, title, description, eventDate, creationDate, price, quantity, userCustomer, status, type);
                events_array.add(newEvent);
                table = new XML_EventsTable();
                table.setEvents(events_array);
                File result = new File(Constants.XML_EVENTS_FILE_PATH);
                log.info(String.format(Constants.SUCCESS_NEW_EVENT_XML, id));
                serializer.write(table, result);
                return true;
            } else {
                table = serializer.read(XML_EventsTable.class, source);
                List<Event> writedEvents = table.getxmlEvents();
                UUID id = UUID.randomUUID();
                EventStatus status = EventStatus.NONE;
                User userCustomer = getProfile(customer).orElse(null);
                Event<UUID> newEvent = new Event<UUID>(id, title, description, eventDate, creationDate, price, quantity, userCustomer, status, type);
                writedEvents.add(newEvent);
                table.setEvents(writedEvents);
                File result = new File(Constants.XML_EVENTS_FILE_PATH);
                serializer.write(table, result);
                log.info(String.format(Constants.SUCCESS_NEW_EVENT_XML, id));
                return true;
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_INSERT_QUERY + ex.getMessage());
            return false;
        }
    }

    @Override
    public Optional<Event> getEvent(String id) {
        try{
            Serializer serializer = new Persister();
            File source = new File(Constants.XML_EVENTS_FILE_PATH);
            Integer rows = 0;
            if (source.length() > 0) {
                XML_EventsTable table;
                table = serializer.read(XML_EventsTable.class, source);
                Set<String> targetSet = new HashSet<String>();
                for (Event event: table.getxmlEvents()){
                    if (event.getId().equals(id)){
                        rows++;
                        return Optional.of(event);
                    }
                }
            } else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_EVENTS_FILE_PATH));
            }
            if (rows == 0) {
                log.info(String.format(Constants.EMPTY_GET_EVENT, id));
                return Optional.empty();
            }
        } catch (Exception ex) {
            log.error(String.format(Constants.ERROR_GET_EVENT, id) + ex);
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public Boolean editEventById(String id, String field, String value) {
        try {
            Serializer serializer = new Persister();
            File source = new File(Constants.XML_EVENTS_FILE_PATH);
            XML_EventsTable table;
            Integer rows = 0;
            if (source.length() > 0) {
                table = serializer.read(XML_EventsTable.class, source);
                List<Event> writedEvents = table.getxmlEvents();
                for (Event event: writedEvents) {
                    if (id.equals(event.getId())){
                        switch (field) {
                            case Constants.EVENTS_TITLE: {
                                event.setTitle(value);
                                log.debug(String.format(Constants.SUCCESS_UPDATE_EVENT_XML, id));
                                break;
                            }
                            case Constants.EVENTS_DESCRIPTION: {
                                event.setDescription(value);
                                log.debug(String.format(Constants.SUCCESS_UPDATE_EVENT_XML, id));
                                break;
                            }
                            case Constants.EVENTS_PRICE: {
                                event.setPrice(Integer.parseInt(value));
                                log.debug(String.format(Constants.SUCCESS_UPDATE_EVENT_XML, id));
                                break;
                            }
                            case Constants.EVENTS_QUANTITY: {
                                event.setQuantity(Float.parseFloat(value));
                                log.debug(String.format(Constants.SUCCESS_UPDATE_EVENT_XML, id));
                                break;
                            }
                            default: {
                                log.warn(String.format(Constants.ERROR_WRONG_FIELD, field));
                                return false;
                            }
                        }
                        rows++;
                    }
                }
                if (rows == 0) {
                    log.info(String.format(Constants.EMPTY_GET_EVENT, id));
                    return false;
                }
                table.setEvents(writedEvents);
                File result = new File(Constants.XML_EVENTS_FILE_PATH);
                serializer.write(table, result);
                return true;
            }else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_EVENTS_FILE_PATH));
                return false;
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_UPDATE_QUERY + ex.getMessage());
            return false;
        }
    }

    @Override
    public Boolean deleteEventById(String id) {
        try {
            Serializer serializer = new Persister();
            boolean delFlag = false;
            File source = new File(Constants.XML_EVENTS_FILE_PATH);
            Integer rows = 0;
            XML_EventsTable table;
            if (source.length() > 0) {
                table = serializer.read(XML_EventsTable.class, source);
                List<Event> writedEvents = table.getxmlEvents();
                for (Event event: writedEvents){
                    if (id.equals(event.getId())){
                        writedEvents.remove(event);
                        rows++;
                        break;
                    }
                }
                if (rows != 0){
                    log.debug(String.format(Constants.SUCCESS_DELETE_EVENT_XML, id));
                    table.setEvents(writedEvents);
                    File result = new File(Constants.XML_EVENTS_FILE_PATH);
                    serializer.write(table, result);
                    return true;
                } else {
                    log.info(String.format(Constants.EMPTY_GET_EVENT, id));
                    return true;
                }
            }else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_EVENTS_FILE_PATH));
                return false;
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_DELETE_QUERY + ex.getMessage());
            return false;
        }
    }

    @Override
    public Boolean addPhoto(String id, String path) {
        try {
            Serializer serializer = new Persister();
            File source = new File(Constants.XML_PHOTOS_FILE_PATH);
            XML_PhotosTable table;

            if (source.length() == 0) {
                List<Photo> photos_array = new ArrayList<>();
                UUID newId = UUID.randomUUID();
                User user = getProfile(id).orElse(null);
                Event event = getEvent(Constants.UTIL_EMPTY_STRING).orElse(null);
                Photo photo = new Photo(newId, user, Constants.UTIL_EMPTY_STRING, Constants.UTIL_EMPTY_STRING, event, Constants.UTIL_EMPTY_STRING, path);
                photos_array.add(photo);
                table = new XML_PhotosTable();
                table.setPhotos(photos_array);
                File result = new File(Constants.XML_PHOTOS_FILE_PATH);
                log.info(String.format(Constants.SUCCESS_NEW_PHOTO_XML, newId));
                serializer.write(table, result);
                return true;
            } else {
                table = serializer.read(XML_PhotosTable.class, source);
                List<Photo> writedPhotos = table.getxmlPhotos();
                UUID newId = UUID.randomUUID();
                User user = getProfile(id).orElse(null);
                Event event = getEvent(Constants.UTIL_EMPTY_STRING).orElse(null);
                Photo photo = new Photo(newId, user, Constants.UTIL_EMPTY_STRING, Constants.UTIL_EMPTY_STRING, event, Constants.UTIL_EMPTY_STRING, path);
                writedPhotos.add(photo);
                table.setPhotos(writedPhotos);
                File result = new File(Constants.XML_PHOTOS_FILE_PATH);
                serializer.write(table, result);
                log.info(String.format(Constants.SUCCESS_NEW_PHOTO_XML, newId));
                return true;
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_INSERT_QUERY + ex.getMessage());
            return false;
        }
    }


    @Override
    public Optional<Photo> getPhoto(String id) {
        try{
            Serializer serializer = new Persister();
            File source = new File(Constants.XML_PHOTOS_FILE_PATH);
            Integer rows = 0;
            if (source.length() > 0) {
                XML_PhotosTable table;
                table = serializer.read(XML_PhotosTable.class, source);
                for (Photo photo: table.getxmlPhotos()){
                    if (photo.getId().equals(id)){
                        rows++;
                        return Optional.of(photo);
                    }
                }
            } else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_PHOTOS_FILE_PATH));
                return Optional.empty();
            }
            if (rows == 0) {
                log.info(String.format(Constants.EMPTY_GET_PHOTO, id));
                return Optional.empty();
            }
        } catch (Exception ex) {
            log.error(String.format(Constants.ERROR_GET_PHOTO, id) + ex);
            return Optional.empty();
        }
        return Optional.empty();
    }


    @Override
    public Boolean editPhotoById(String id, String field, String value) {
        try {
            Serializer serializer = new Persister();
            File source = new File(Constants.XML_PHOTOS_FILE_PATH);
            XML_PhotosTable table;
            Integer rows = 0;
            if (source.length() > 0) {
                table = serializer.read(XML_PhotosTable.class, source);
                List<Photo> writedPhotos = table.getxmlPhotos();
                for (Photo photo: writedPhotos) {
                    if (id.equals(photo.getId())){
                        switch (field) {
                            case Constants.PHOTOS_TITLE: {
                                photo.setTitle(value);
                                log.debug(String.format(Constants.SUCCESS_UPDATE_PHOTO_XML, id));
                                break;
                            }
                            case Constants.PHOTOS_DESCRIPTION: {
                                photo.setDescription(value);
                                log.debug(String.format(Constants.SUCCESS_UPDATE_PHOTO_XML, id));
                                break;
                            }
                            case Constants.PHOTOS_TAG: {
                                photo.setTag(value);
                                log.debug(String.format(Constants.SUCCESS_UPDATE_PHOTO_XML, id));
                                break;
                            }
                            case Constants.PHOTOS_PATH: {
                                photo.setPath(value);
                                log.debug(String.format(Constants.SUCCESS_UPDATE_PHOTO_XML, id));
                                break;
                            }
                            default: {
                                log.warn(String.format(Constants.ERROR_WRONG_FIELD, field));
                                return false;
                            }
                        }
                        rows++;
                    }
                }
                if (rows == 0) {
                    log.info(String.format(Constants.EMPTY_GET_PHOTO, id));
                    return false;
                }
                table.setPhotos(writedPhotos);
                File result = new File(Constants.XML_PHOTOS_FILE_PATH);
                serializer.write(table, result);
                return true;
            }else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_PHOTOS_FILE_PATH));
                return false;
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_UPDATE_QUERY + ex.getMessage());
            return false;
        }
    }


    @Override
    public Boolean deletePhotoById(String id) {
        try {
            Serializer serializer = new Persister();
            File source = new File(Constants.XML_PHOTOS_FILE_PATH);
            XML_PhotosTable table;
            Integer rows = 0;
            if (source.length() > 0) {
                table = serializer.read(XML_PhotosTable.class, source);
                List<Photo> writedPhotos = table.getxmlPhotos();
                for (Photo photo: writedPhotos){
                    if (id.equals(photo.getId())){
                        writedPhotos.remove(photo);
                        rows++;
                        break;
                    }
                }
                if (rows != 0){
                    log.info(String.format(Constants.SUCCESS_DELETE_PHOTO_XML, id));
                    table.setPhotos(writedPhotos);
                    File result = new File(Constants.XML_PHOTOS_FILE_PATH);
                    serializer.write(table, result);
                    return true;
                } else {
                    log.info(String.format(Constants.EMPTY_GET_PHOTO, id));
                    return true;
                }

            }else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_PHOTOS_FILE_PATH));
                return false;
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_DELETE_QUERY + ex.getMessage());
            return false;
        }
    }


    @Override
    public Optional<ArrayList<Photo>> getPortfolio(String userId) {
        try {
            Serializer serializer = new Persister();
            File source = new File(Constants.XML_PHOTOS_FILE_PATH);
            XML_PhotosTable table;
            if (source.length() > 0) {
                table = serializer.read(XML_PhotosTable.class, source);
                List<Photo> writedPhotos = table.getxmlPhotos();
                ArrayList<Photo> result = new ArrayList<Photo>();
                for (Photo photo: writedPhotos) {
                    if (photo.getUser().getId().equals(userId)){
                        result.add(photo);
                    }
                }
                if (result.size() == 0) {
                    log.info(String.format(Constants.EMPTY_GET_PORTFOLIO, userId));
                    return Optional.empty();
                } else return Optional.of(result);
            } else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_PHOTOS_FILE_PATH));
                return Optional.empty();
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_PORTFOLIO + ex.getMessage());
            return Optional.empty();
        }
    }


    @Override
    public String getPhotoPathById(String id) {
        try {
            Serializer serializer = new Persister();
            File source = new File(Constants.XML_PHOTOS_FILE_PATH);
            XML_PhotosTable table;
            Integer rows = 0;
            if (source.length() > 0) {
                table = serializer.read(XML_PhotosTable.class, source);
                List<Photo> writedPhotos = table.getxmlPhotos();
                for (Photo photo: writedPhotos){
                    if (id.equals(photo.getId())){
                        rows++;
                        return photo.getPath();
                    }
                }
            } else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_PHOTOS_FILE_PATH));
                return null;
            }
            if (rows == 0) {
                log.info(String.format(Constants.EMPTY_GET_PHOTO_PATH, id));
                return null;
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_PHOTO_PATH + ex.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public String getLastUserId() {
        try{
            Serializer serializer = new Persister();
            File source = new File(Constants.XML_USERS_FILE_PATH);
            if (source.length() > 0) {
                XML_UsersTable table;
                table = serializer.read(XML_UsersTable.class, source);
                List<User> users = table.getxmlUsers();
                if (users.size() != 0){
                    return users.get(users.size()-1).getId().toString();
                } else {
                    log.info(Constants.EMPTY_GET_LAST_USER);
                    return null;
                }
            } else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_USERS_FILE_PATH));
                return null;
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_LAST_PROFILE + ex.getMessage());
            return null;
        }
    }

    @Override
    public String getLastEventId() {
        try{
            Serializer serializer = new Persister();
            File source = new File(Constants.XML_EVENTS_FILE_PATH);
            if (source.length() > 0) {
                XML_EventsTable table;
                table = serializer.read(XML_EventsTable.class, source);
                List<Event> events = table.getxmlEvents();
                if (events.size() != 0){
                    return events.get(events.size()-1).getId().toString();
                } else {
                    log.info(Constants.EMPTY_GET_LAST_EVENT);
                    return null;
                }
            } else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_USERS_FILE_PATH));
                return null;
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_LAST_EVENT + ex.getMessage());
            return null;
        }
    }

    @Override
    public String getLastPhotoId() {
        try{
            Serializer serializer = new Persister();
            File source = new File(Constants.XML_PHOTOS_FILE_PATH);
            if (source.length() > 0) {
                XML_PhotosTable table;
                table = serializer.read(XML_PhotosTable.class, source);
                List<Photo> photos = table.getxmlPhotos();
                if (photos.size() != 0){
                    return photos.get(photos.size()-1).getId().toString();
                } else {
                    log.info(Constants.EMPTY_GET_LAST_PHOTO);
                    return null;
                }
            } else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_PHOTOS_FILE_PATH));
                return null;
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_LAST_PHOTO + ex.getMessage());
            return null;
        }
    }

    @Override
    public Optional<ArrayList<User>> getAllUsers() {
        try{
            Serializer serializer = new Persister();
            File source = new File(Constants.XML_USERS_FILE_PATH);
            if (source.length() > 0) {
                XML_UsersTable table;
                table = serializer.read(XML_UsersTable.class, source);
                List<User> users = table.getxmlUsers();
                if (users.size() != 0){
                    return Optional.of((ArrayList<User>) users);
                } else {
                    log.info(Constants.EMPTY_GET_ALL_USERS);
                    return Optional.empty();
                }
            } else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_USERS_FILE_PATH));
                return Optional.empty();
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_ALL_PROFILES + ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<ArrayList<Event>> getAllEvents() {
        try{
            Serializer serializer = new Persister();
            File source = new File(Constants.XML_EVENTS_FILE_PATH);
            if (source.length() > 0) {
                XML_EventsTable table;
                table = serializer.read(XML_EventsTable.class, source);
                List<Event> events = table.getxmlEvents();
                if (events.size() != 0){
                    return Optional.of((ArrayList<Event>) events);
                } else {
                    log.info(Constants.EMPTY_GET_ALL_EVENTS);
                    return Optional.empty();
                }
            } else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_EVENTS_FILE_PATH));
                return Optional.empty();
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_ALL_EVENTS + ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<ArrayList<Photo>> getAllPhotos() {
        try{
            Serializer serializer = new Persister();
            File source = new File(Constants.XML_PHOTOS_FILE_PATH);
            if (source.length() > 0) {
                XML_PhotosTable table;
                table = serializer.read(XML_PhotosTable.class, source);
                List<Photo> photos = table.getxmlPhotos();
                if (photos.size() != 0){
                    return Optional.of((ArrayList<Photo>) photos);
                } else {
                    log.info(Constants.EMPTY_GET_ALL_PHOTOS);
                    return Optional.empty();
                }
            } else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_PHOTOS_FILE_PATH));
                return Optional.empty();
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_ALL_PHOTOS + ex);
            return Optional.empty();
        }
    }

    @Override
    public Boolean addComment(String userId, String photoId, String comment, Date date) {
        try {
            Serializer serializer = new Persister();
            File source = new File(Constants.XML_COMMENTS_FILE_PATH);
            XML_CommentsTable table;
            if (source.length() == 0) {
                List<Comment> comments_array = new ArrayList<>();
                UUID newId = UUID.randomUUID();
                User user = getProfile(userId).orElse(null);
                Photo photo = getPhoto(photoId).orElse(null);
                Comment newComment = new Comment(newId,
                        user, photo, comment, date);
                comments_array.add(newComment);
                table = new XML_CommentsTable();
                table.setComments(comments_array);
                File result = new File(Constants.XML_COMMENTS_FILE_PATH);
                log.info(String.format(Constants.SUCCESS_NEW_COMMENT_XML, newId));
                serializer.write(table, result);
                return true;
            } else {
                table = serializer.read(XML_CommentsTable.class, source);
                List<Comment> writedComments = table.getxmlComments();
                UUID newId = UUID.randomUUID();
                User user = getProfile(userId).orElse(null);
                Photo photo = getPhoto(photoId).orElse(null);
                Comment newComment = new Comment(newId,
                        user, photo, comment, date);
                writedComments.add(newComment);
                table.setComments(writedComments);
                File result = new File(Constants.XML_COMMENTS_FILE_PATH);
                serializer.write(table, result);
                log.info(String.format(Constants.SUCCESS_NEW_COMMENT_XML, newId));
                return true;
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_INSERT_QUERY + ex.getMessage());
            return false;
        }
    }

    @Override
    public Optional<ArrayList<Comment>> getAllComments() {
        try{
            Serializer serializer = new Persister();
            File source = new File(Constants.XML_COMMENTS_FILE_PATH);
            if (source.length() > 0) {
                XML_CommentsTable table;
                table = serializer.read(XML_CommentsTable.class, source);
                List<Comment> comments = table.getxmlComments();
                if (comments.size() != 0){
                    return Optional.of((ArrayList<Comment>) comments);
                } else {
                    log.info(Constants.EMPTY_GET_ALL_COMMENTS);
                    return Optional.empty();
                }
            } else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_COMMENTS_FILE_PATH));
                return Optional.empty();
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_ALL_COMMENTS + ex);
            return Optional.empty();
        }
    }

    @Override
    public Boolean addRate(String userId, String photoId, Float rate, Date date) {
        return null;
    }

    @Override
    public Boolean addFeedback(String userId, String photographerId, Float rate, Date creationDate) {
        return null;
    }

    @Override
    public Boolean createOffer(String userId, String eventId, Date creationDate) {
        return null;
    }

    @Override
    public Optional<ArrayList<User>> searchUsers(String field, String value) {
        try {
            Serializer serializer = new Persister();
            File source = new File(Constants.XML_USERS_FILE_PATH);
            ArrayList<User> findedUsers = new ArrayList<User>();
            if (source.length() > 0) {
                XML_UsersTable table;
                table = serializer.read(XML_UsersTable.class, source);
                List<User> users = table.getxmlUsers();
                if (users.size() != 0) {
                    for (User user : users) {
                        switch (field.toLowerCase()) {
                            case Constants.USERS_NAME_SEARCH: {
                                if (user.getName().toLowerCase().equals(value.toLowerCase())) findedUsers.add(user);
                                break;
                            }
                            case Constants.USERS_LAST_NAME_SEARCH: {
                                if (user.getLastName().toLowerCase().equals(value.toLowerCase())) findedUsers.add(user);
                                break;
                            }
                            case Constants.USERS_TOWN_SEARCH: {
                                if (user.getTown().toLowerCase().equals(value.toLowerCase())) findedUsers.add(user);
                                break;
                            }
                            default: {
                                log.warn(String.format(Constants.ERROR_WRONG_FIELD, field));
                                return Optional.empty();
                            }
                        }
                    }
                } else{
                    log.info(Constants.EMPTY_GET_USERS_SEARCH);
                    return Optional.empty();
                }
                if (findedUsers.size() != 0) {
                    return Optional.of(findedUsers);
                } else {
                    log.info(Constants.EMPTY_GET_USERS_SEARCH);
                    return Optional.empty();
                }
                } else {
                    log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_USERS_FILE_PATH));
                    return Optional.empty();
                }
            } catch(Exception ex){
                log.error(Constants.ERROR_GET_PROFILES_SEARCH + ex);
                return Optional.empty();
            }
    }

    @Override
    public Optional<ArrayList<User>> searchPhotographers(String field, String value) {
        try {
            Serializer serializer = new Persister();
            File source = new File(Constants.XML_PHOTOGRAPHERS_FILE_PATH);
            ArrayList<User> findedUsers = new ArrayList<User>();
            if (source.length() > 0) {
                XML_PhotographersTable phTable;
                phTable = serializer.read(XML_PhotographersTable.class, source);
                List<Photographer> users = phTable.getxmlPhotographers();
                if (users.size() != 0) {
                    for (Photographer user : users) {
                        switch (field.toLowerCase()) {
                            case Constants.USERS_NAME_SEARCH: {
                                if (user.getName().toLowerCase().equals(value.toLowerCase())) findedUsers.add(user);
                                break;
                            }
                            case Constants.USERS_LAST_NAME_SEARCH: {
                                if (user.getLastName().toLowerCase().equals(value.toLowerCase())) findedUsers.add(user);
                                break;
                            }
                            case Constants.USERS_TOWN_SEARCH: {
                                if (user.getTown().toLowerCase().equals(value.toLowerCase())) findedUsers.add(user);
                                break;
                            }
                            case Constants.USERS_RATING_SEARCH: {
                                if (user.getRating().toString().equals(value)) findedUsers.add(user);
                                break;
                            }
                            case Constants.USERS_EXPERIENCE_SEARCH: {
                                if (user.getExperience().toString().equals(value)) findedUsers.add(user);
                                break;
                            }
                            case Constants.USERS_COSTLEVEL_SEARCH: {
                                if (user.getCostLevel().equals(CostLevel.valueOf(value.toUpperCase()))) findedUsers.add(user);
                                break;
                            }
                            default: {
                                log.warn(String.format(Constants.ERROR_WRONG_FIELD, field));
                                return Optional.empty();
                            }
                        }
                    }
                } else{
                    log.info(Constants.EMPTY_GET_PHOTOGRAPHERS_SEARCH);
                    return Optional.empty();
                }
                if (findedUsers.size() != 0) {
                    return Optional.of(findedUsers);
                } else {
                    log.info(Constants.EMPTY_GET_PHOTOGRAPHERS_SEARCH);
                    return Optional.empty();
                }
            } else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_USERS_FILE_PATH));
                return Optional.empty();
            }
        } catch(Exception ex){
            log.error(Constants.ERROR_GET_PROFILES_SEARCH + ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<ArrayList<Event>> searchEvents(String field, String value) {
        try {
            Serializer serializer = new Persister();
            File source = new File(Constants.XML_EVENTS_FILE_PATH);
            ArrayList<Event> findedEvents = new ArrayList<Event>();
            if (source.length() > 0) {
                XML_EventsTable table;
                table = serializer.read(XML_EventsTable.class, source);
                List<Event> events = table.getxmlEvents();
                if (events.size() != 0) {
                    for (Event event : events) {
                        switch (field.toLowerCase()) {
                            case Constants.EVENTS_TITLE_SEARCH: {
                                if (event.getTitle().toLowerCase().equals(value.toLowerCase())) findedEvents.add(event);
                                break;
                            }
                            case Constants.EVENTS_DESCRIPTION_SEARCH: {
                                if (event.getDescription().toLowerCase().equals(value.toLowerCase())) findedEvents.add(event);
                                break;
                            }
                            case Constants.EVENTS_PRICE_SEARCH: {
                                if (event.getPrice().toString().toLowerCase().equals(value.toLowerCase())) findedEvents.add(event);
                                break;
                            }
                            case Constants.EVENTS_TYPE_SEARCH: {
                                if (event.getType().toString().toLowerCase().equals(value.toLowerCase())) findedEvents.add(event);
                                break;
                            }
                            default: {
                                log.warn(String.format(Constants.ERROR_WRONG_FIELD, field));
                                return Optional.empty();
                            }
                        }
                    }
                } else{
                    log.info(Constants.EMPTY_GET_EVENTS_SEARCH);
                    return Optional.empty();
                }
                if (findedEvents.size() != 0) {
                    return Optional.of(findedEvents);
                } else {
                    log.info(Constants.EMPTY_GET_EVENTS_SEARCH);
                    return Optional.empty();
                }
            } else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_EVENTS_FILE_PATH));
                return Optional.empty();
            }
        } catch(Exception ex){
            log.error(Constants.ERROR_GET_EVENTS_SEARCH + ex);
            return Optional.empty();
        }
    }
}
