package ru.sfedu.photosearch.providers;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.enums.*;
import ru.sfedu.photosearch.newModels.Comment;
import ru.sfedu.photosearch.newModels.Event;
import ru.sfedu.photosearch.newModels.Photo;
import ru.sfedu.photosearch.newModels.User;
import ru.sfedu.photosearch.utils.Formatter;
import ru.sfedu.photosearch.xmlTables.XML_PhotosTable;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class DataProviderCSV implements DataProvider {
    private static Logger log = LogManager.getLogger(DataProviderCSV.class);

    @Override
    public Boolean createNewProfile(String name,
                                    String lastName,
                                    Date birthDay,
                                    Date dateOfRegistration,
                                    Role role,
                                    String town) {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_USERS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_USERS_FILE_PATH), ',' , '"' , 0);
                List<String[]> csvBody = reader.readAll();
                UUID id = UUID.randomUUID();
                User<UUID> newUser = new User<UUID>(id, name, lastName, birthDay, dateOfRegistration, role, town, Constants.DEFAULT_WALLET);
                String[] record_to_write = newUser.getCSVUserOutput().split(",");
                csvBody.add(record_to_write);
                reader.close();
                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_USERS_FILE_PATH),',','"');
                writer.writeAll(csvBody);
                writer.flush();
                writer.close();
                log.info(String.format(Constants.SUCCESS_NEW_PROFILE_XML, id));
                return true;
            } else {
                log.error(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.CSV_USERS_FILE_PATH));
                return false;
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_INSERT_QUERY + ex.getMessage());
            return false;
        }

    }

    @Override
    public User getProfile(String id) {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_USERS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_USERS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> users = User.convertFromCSV(csvBody);
                Integer rows = 0;
                for (User user : users) {
                    if (user.getId().equals(id)) {
                        rows++;
                        return user;
                    }
                }
                reader.close();
                if (rows == 0) {
                    log.debug(String.format(Constants.EMPTY_GET_PROFILE, id));
                    return null;
                }
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_PROFILE + ex.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public Boolean editProfileById(String id, String field, String value) {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_USERS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_USERS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> users = User.convertFromCSV(csvBody);
                List<String[]> usersWrite = new ArrayList<>();
                Integer rows = 0;
                for (User user: users) {
                    if (user.getId().equals(id)) {
                        switch (field){
                            case Constants.USERS_NAME:{
                                user.setName(value);
                                log.debug(String.format(Constants.SUCCESS_UPDATE_PROFILE_XML, id));
                                break;
                            }
                            case Constants.USERS_LAST_NAME:{
                                user.setLastName(value);
                                log.debug(String.format(Constants.SUCCESS_UPDATE_PROFILE_XML, id));
                                break;
                            }
                            case Constants.USERS_BIRTHDAY:{
                                user.setBirthDay(Formatter.normalFormatDay(value));
                                log.debug(String.format(Constants.SUCCESS_UPDATE_PROFILE_XML, id));
                                break;
                            }
                            case Constants.USERS_TOWN:{
                                user.setTown(value);
                                log.debug(String.format(Constants.SUCCESS_UPDATE_PROFILE_XML, id));
                                break;
                            }
//                            case Constants.USERS_EXPERIENCE:{
//                                user.setExpirien(value);
//                                log.debug(String.format(Constants.SUCCESS_UPDATE_PROFILE_XML, id));
//                                break;
//                            }
                            default: {
                                log.warn(String.format(Constants.ERROR_WRONG_FIELD, field));
                            }
                        }
                        rows++;
                    }
                    usersWrite.add(user.getCSVUserOutput().split(","));
                }
                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_USERS_FILE_PATH), ',', '"');
                usersWrite.add(0, Constants.CSV_USERS_COLUMNS.split(", "));
                if (usersWrite.size() < 2){
                    log.info(String.format(Constants.EMPTY_GET_PROFILE, id));
                    return true;
                }
                writer.writeAll(usersWrite);
                writer.close();
                reader.close();
                return true;
            }else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.CSV_USERS_FILE_PATH));
                return false;
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_UPDATE_QUERY + ex.getMessage());
            return false;
        }
    }

    @Override
    public Boolean deleteProfileById(String id) {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_USERS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_USERS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> users = User.convertFromCSV(csvBody);
                Set<String> targetSet = new HashSet<String>();
                List<String[]> usersWrite = new ArrayList<>();
                Integer rows = 0;
                for (User user: users) {
                    if (!user.getId().equals(id)) {
                        usersWrite.add(user.getCSVUserOutput().split(","));
                    } else {
                        rows++;
                    }
                }
                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_USERS_FILE_PATH), ',', '"');
                usersWrite.add(0, Constants.CSV_USERS_COLUMNS.split(", "));
                if (usersWrite.size() == 1){
                    log.info(String.format(Constants.EMPTY_GET_PROFILE, id));
                    return true;
                } else {
                    writer.writeAll(usersWrite);
                    writer.close();
                    reader.close();
                    return true;
                }
            }else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.CSV_USERS_FILE_PATH));
                return false;
            }
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
            CSVReader reader;
            File source = new File(Constants.CSV_EVENTS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_EVENTS_FILE_PATH), ',', '"', 0);
                List<String[]> csvBody = reader.readAll();
                UUID id = UUID.randomUUID();
                EventStatus status = EventStatus.NONE;
                User userCustomer = getProfile(customer);
                Event<UUID> newEvent = new Event<UUID>(id, title, description, eventDate, creationDate, price, quantity, userCustomer, status, type);
                String[] record_to_write = newEvent.getCSVEventOutput().split(",");
                csvBody.add(record_to_write);
                reader.close();
                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_EVENTS_FILE_PATH), ',', '"');
                writer.writeAll(csvBody);
                writer.flush();
                writer.close();
                log.info(String.format(Constants.SUCCESS_NEW_EVENT_XML, id));
                return true;
            } else {
                UUID id = UUID.randomUUID();
                EventStatus status = EventStatus.NONE;
                User userCustomer = getProfile(customer);
                Event<UUID> newEvent = new Event<UUID>(id, title, description, eventDate, creationDate, price, quantity, userCustomer, status, type);
                String[] record_to_write = newEvent.getCSVEventOutput().split(",");
                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_EVENTS_FILE_PATH), ',', '"');
                writer.writeNext(record_to_write);
                writer.close();
                log.info(String.format(Constants.SUCCESS_NEW_EVENT_XML, id));
                return true;
            }
        } catch (Exception ex){
            log.error(Constants.ERROR_INSERT_QUERY + ex.getMessage());
            return false;
        }
    }

    @Override
    public Event getEvent(String id) {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_EVENTS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_EVENTS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> customers = new ArrayList<>();
                List<User> executors = new ArrayList<>();
                for (String[] line : csvBody) {
                    User customer;
                    if (line[7]!=null) {
                        customer = getProfile(line[7]);
                    } else
                        customer = null;
                    User executor;
                    if (line[8]!=null) {
                        executor = getProfile(line[8]);
                    } else {
                        executor = null;
                    }
                    customers.add(customer);
                    executors.add(executor);
                }
                List<Event> events = Event.convertFromCSV(csvBody, customers, executors);
                Integer rows = 0;
                for (Event event: events) {
                    if (event.getId().equals(id)) {
                        rows++;
                        return event;
                    }
                }
                reader.close();
                if (rows == 0) {
                    log.debug(String.format(Constants.EMPTY_GET_EVENT, id));
                    return null;
                }
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_EVENT + ex.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public Boolean editEventById(String id, String field, String value) {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_EVENTS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_EVENTS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> customers = new ArrayList<>();
                List<User> executors = new ArrayList<>();
                for (String[] line : csvBody) {
                    User customer;
                    if (line[7]!=null) {
                        customer = getProfile(line[7]);
                    } else
                        customer = null;
                    User executor;
                    if (line[8]!=null) {
                        executor = getProfile(line[8]);
                    } else {
                        executor = null;
                    }
                    customers.add(customer);
                    executors.add(executor);
                }
                List<Event> events = Event.convertFromCSV(csvBody, customers, executors);
                List<String[]> eventsWrite = new ArrayList<>();
                Integer rows = 0;
                for (Event event: events) {
                    if (event.getId().equals(id)) {
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
                            }
                        }
                        rows++;
                    }
                    eventsWrite.add(event.getCSVEventOutput().split(","));
                }
                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_EVENTS_FILE_PATH), ',', '"');
                eventsWrite.add(0, Constants.CSV_EVENTS_COLUMNS.split(", "));
                if (eventsWrite.size() < 2){
                    log.info(String.format(Constants.EMPTY_GET_EVENT, id));
                }
                writer.writeAll(eventsWrite);
                writer.close();
                reader.close();
                return true;
            } else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.CSV_EVENTS_FILE_PATH));
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
            CSVReader reader;
            File source = new File(Constants.CSV_EVENTS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_EVENTS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> customers = new ArrayList<>();
                List<User> executors = new ArrayList<>();
                for (String[] line : csvBody) {
                    User customer;
                    if (line[7]!=null) {
                        customer = getProfile(line[7]);
                    } else
                        customer = null;
                    User executor;
                    if (line[8]!=null) {
                        executor = getProfile(line[8]);
                    } else {
                        executor = null;
                    }
                    customers.add(customer);
                    executors.add(executor);
                }
                List<Event> events = Event.convertFromCSV(csvBody, customers, executors);
                List<String[]> eventsWrite = new ArrayList<>();
                Integer rows = 0;
                for (Event event: events) {
                    if (!event.getId().equals(id)) {
                        eventsWrite.add(event.getCSVEventOutput().split(","));
                    } else {
                        rows++;
                    }
                }
                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_EVENTS_FILE_PATH), ',', '"');
                eventsWrite.add(0, Constants.CSV_EVENTS_COLUMNS.split(", "));
                if (eventsWrite.size() == 1){
                    log.info(String.format(Constants.EMPTY_GET_EVENT, id));
                    return true;
                } else {
                    writer.writeAll(eventsWrite);
                    writer.close();
                    reader.close();
                    return true;
                }
            }else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.CSV_EVENTS_FILE_PATH));
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
            CSVReader reader;
            File source = new File(Constants.CSV_PHOTOS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_PHOTOS_FILE_PATH), ',', '"', 0);
                List<String[]> csvBody = reader.readAll();
                UUID newId = UUID.randomUUID();
                User user = getProfile(id);
                Event event = getEvent(Constants.UTIL_EMPTY_STRING);
                Photo photo = new Photo(newId, user, Constants.UTIL_EMPTY_STRING, Constants.UTIL_EMPTY_STRING, event, Constants.UTIL_EMPTY_STRING, path);
                String[] record_to_write = photo.getCSVPhotoOutput().split(",");
                csvBody.add(record_to_write);
                reader.close();
                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_PHOTOS_FILE_PATH), ',', '"');
                writer.writeAll(csvBody);
                writer.flush();
                writer.close();
                return true;
            } else {
                UUID newId = UUID.randomUUID();
                User user = getProfile(id);
                Event event = getEvent(Constants.UTIL_EMPTY_STRING);
                Photo photo = new Photo(newId, user, Constants.UTIL_EMPTY_STRING, Constants.UTIL_EMPTY_STRING, event, Constants.UTIL_EMPTY_STRING, path);
                String[] record_to_write = photo.getCSVPhotoOutput().split(",");
                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_PHOTOS_FILE_PATH), ',', '"');
                writer.writeNext(record_to_write);
                writer.close();
                log.info(String.format(Constants.SUCCESS_NEW_PHOTO_XML, id));
                return true;
            }
        } catch (Exception ex){
            log.error(Constants.ERROR_INSERT_QUERY + ex.getMessage());
            return false;
        }
    }

    @Override
    public Photo getPhoto(String id) {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_PHOTOS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_PHOTOS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> users = new ArrayList<>();
                List<Event> events = new ArrayList<>();
                for (String[] line : csvBody) {
                    User customer;
                    if (line[1]!=null) {
                        customer = getProfile(line[1]);
                    } else
                        customer = null;
                    Event event;
                    if (line[2]!=null) {
                        event = getEvent(line[2]);
                    } else {
                        event = null;
                    }
                    users.add(customer);
                    events.add(event);
                }
                List<Photo> photos = Photo.convertFromCSV(csvBody, users, events);
                Integer rows = 0;
                for (Photo photo: photos) {
                    if (photo.getId().equals(id)) {
                        rows++;
                        return photo;
                    }
                }
                reader.close();
                if (rows == 0) {
                    log.info(String.format(Constants.EMPTY_GET_PHOTO, id));
                    return null;
                }
            } else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_PHOTOS_FILE_PATH));
                return null;
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_PHOTO + ex.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public Boolean editPhotoById(String id, String field, String value) {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_PHOTOS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_PHOTOS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> users = new ArrayList<>();
                List<Event> events = new ArrayList<>();
                Integer rows = 0;
                for (String[] line : csvBody) {
                    User customer;
                    if (line[1]!=null) {
                        customer = getProfile(line[1]);
                    } else
                        customer = null;
                    Event event;
                    if (line[2]!=null) {
                        event = getEvent(line[2]);
                    } else {
                        event = null;
                    }
                    users.add(customer);
                    events.add(event);
                }
                List<Photo> photos = Photo.convertFromCSV(csvBody, users, events);
                List<String[]> photosWrite = new ArrayList<>();
                for (Photo photo: photos) {
                    if (photo.getId().equals(id)) {
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
                            }
                        }
                        rows++;
                    }
                    photosWrite.add(photo.getCSVPhotoOutput().split(","));
                }
                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_PHOTOS_FILE_PATH), ',', '"');
                photosWrite.add(0, Constants.CSV_PHOTOS_COLUMNS.split(", "));
                if (photosWrite.size() < 2){
                    log.info(String.format(Constants.EMPTY_GET_PHOTO, id));
                }
                writer.writeAll(photosWrite);
                writer.close();
                reader.close();
                return true;
            }else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.CSV_PHOTOS_FILE_PATH));
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
            CSVReader reader;
            File source = new File(Constants.CSV_PHOTOS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_PHOTOS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> users = new ArrayList<>();
                List<Event> events = new ArrayList<>();
                for (String[] line : csvBody) {
                    User customer;
                    if (line[1]!=null) {
                        customer = getProfile(line[1]);
                    } else
                        customer = null;
                    Event event;
                    if (line[2]!=null) {
                        event = getEvent(line[2]);
                    } else {
                        event = null;
                    }
                    users.add(customer);
                    events.add(event);
                }
                List<Photo> photos = Photo.convertFromCSV(csvBody, users, events);
                List<String[]> photosWrite = new ArrayList<>();
                Integer rows = 0;
                for (Photo photo: photos) {
                    if (!photo.getId().equals(id)) {
                        photosWrite.add(photo.getCSVPhotoOutput().split(","));
                    } else {
                        rows++;
                    }
                }
                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_PHOTOS_FILE_PATH), ',', '"');
                photosWrite.add(0, Constants.CSV_PHOTOS_COLUMNS.split(", "));
                if (photosWrite.size() == 1){
                    log.info(String.format(Constants.EMPTY_GET_PHOTO, id));
                    return true;
                } else {
                    writer.writeAll(photosWrite);
                    writer.close();
                    reader.close();
                    return true;
                }
            }else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.CSV_PHOTOS_FILE_PATH));
                return false;
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_DELETE_QUERY + ex.getMessage());
            return false;
        }
    }

    @Override
    public ArrayList<Photo> getPortfolio(String userId) {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_PHOTOS_FILE_PATH);
            ArrayList<Photo> result = new ArrayList<Photo>();
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_PHOTOS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                Integer rows = 0;
                List<User> users = new ArrayList<>();
                List<Event> events = new ArrayList<>();
                for (String[] line : csvBody) {
                    User customer;
                    if (line[1]!=null) {
                        customer = getProfile(line[1]);
                    } else
                        customer = null;
                    Event event;
                    if (line[2]!=null) {
                        event = getEvent(line[2]);
                    } else {
                        event = null;
                    }
                    users.add(customer);
                    events.add(event);
                }
                List<Photo> photos = Photo.convertFromCSV(csvBody, users, events);
                for (Photo photo: photos) {
                    if (photo.getUser().equals(userId)){
                        result.add(photo);
                        rows++;
                    }
                }
                if (rows == 1){
                    log.info(String.format(Constants.EMPTY_GET_PORTFOLIO, userId));
                    return null;
                } else {
                    return result;
                }
            } else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_PHOTOS_FILE_PATH));
                return null;
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_PORTFOLIO + ex.getMessage());
            return null;
        }
    }

    @Override
    public String getPhotoPathById(String id) {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_PHOTOS_FILE_PATH);
            String result = Constants.UTIL_EMPTY_STRING;
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_PHOTOS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> users = new ArrayList<>();
                List<Event> events = new ArrayList<>();
                for (String[] line : csvBody) {
                    User customer;
                    if (line[1]!=null) {
                        customer = getProfile(line[1]);
                    } else
                        customer = null;
                    Event event;
                    if (line[2]!=null) {
                        event = getEvent(line[2]);
                    } else {
                        event = null;
                    }
                    users.add(customer);
                    events.add(event);
                }
                List<Photo> photos = Photo.convertFromCSV(csvBody, users, events);
                Integer rows = 0;
                for (Photo photo: photos) {
                    if (photo.getId().equals(id)) {
                        rows++;
                        return photo.getPath();
                    }
                }
                reader.close();
                if (rows == 0) {
                    log.info(String.format(Constants.EMPTY_GET_PHOTO_PATH, id));
                    return null;
                }
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_PHOTO_PATH + ex.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public String getLastUserId() {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_USERS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_USERS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> users = User.convertFromCSV(csvBody);
                if (users.size() != 0){
                    return users.get(users.size()-1).getId().toString();
                } else {
                    log.info(Constants.EMPTY_GET_LAST_USER);
                    reader.close();
                    return null;
                }
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_LAST_PROFILE + ex.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public String getLastEventId() {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_EVENTS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_EVENTS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> customers = new ArrayList<>();
                List<User> executors = new ArrayList<>();
                for (String[] line : csvBody) {
                    User customer;
                    if (line[7]!=null) {
                        customer = getProfile(line[7]);
                    } else
                        customer = null;
                    User executor;
                    if (line[8]!=null) {
                        executor = getProfile(line[8]);
                    } else {
                        executor = null;
                    }
                    customers.add(customer);
                    executors.add(executor);
                }
                List<Event> events = Event.convertFromCSV(csvBody, customers, executors);
                if (events.size() != 0){
                    return events.get(events.size()-1).getId().toString();
                } else {
                    log.info(Constants.EMPTY_GET_LAST_EVENT);
                    reader.close();
                    return null;
                }
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_LAST_EVENT + ex.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public String getLastPhotoId() {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_PHOTOS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_PHOTOS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> users = new ArrayList<>();
                List<Event> events = new ArrayList<>();
                for (String[] line : csvBody) {
                    User customer;
                    if (line[1]!=null) {
                        customer = getProfile(line[1]);
                    } else
                        customer = null;
                    Event event;
                    if (line[2]!=null) {
                        event = getEvent(line[2]);
                    } else {
                        event = null;
                    }
                    users.add(customer);
                    events.add(event);
                }
                List<Photo> photos = Photo.convertFromCSV(csvBody, users, events);
                if (photos.size() != 0){
                    return photos.get(photos.size()-1).getId().toString();
                } else {
                    log.info(Constants.EMPTY_GET_LAST_PHOTO);
                    reader.close();
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
    public ArrayList<User> getAllUsers() {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_USERS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_USERS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> users = User.convertFromCSV(csvBody);
                if (users.size() != 0){
                    return (ArrayList<User>) users;
                } else {
                    log.info(Constants.EMPTY_GET_ALL_USERS);
                    reader.close();
                    return null;
                }
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_ALL_PROFILES + ex.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public ArrayList<Event> getAllEvents() {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_EVENTS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_EVENTS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> customers = new ArrayList<>();
                List<User> executors = new ArrayList<>();
                for (String[] line : csvBody) {
                    User customer;
                    if (line[7]!=null) {
                        customer = getProfile(line[7]);
                    } else
                        customer = null;
                    User executor;
                    if (line[8]!=null) {
                        executor = getProfile(line[8]);
                    } else {
                        executor = null;
                    }
                    customers.add(customer);
                    executors.add(executor);
                }
                List<Event> events = Event.convertFromCSV(csvBody, customers, executors);
                if (events.size() != 0){
                    return (ArrayList<Event>) events;
                } else {
                    log.info(Constants.EMPTY_GET_ALL_EVENTS);
                    reader.close();
                    return null;
                }
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_ALL_EVENTS + ex.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public ArrayList<Photo> getAllPhotos() {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_PHOTOS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_PHOTOS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> users = new ArrayList<>();
                List<Event> events = new ArrayList<>();
                for (String[] line : csvBody) {
                    User customer;
                    if (line[1]!=null) {
                        customer = getProfile(line[1]);
                    } else
                        customer = null;
                    Event event;
                    if (line[2]!=null) {
                        event = getEvent(line[2]);
                    } else {
                        event = null;
                    }
                    users.add(customer);
                    events.add(event);
                }
                List<Photo> photos = Photo.convertFromCSV(csvBody, users, events);
                if (photos.size() != 0){
                    return (ArrayList<Photo>) photos;
                } else {
                    log.info(Constants.EMPTY_GET_ALL_PHOTOS);
                    reader.close();
                    return null;
                }
            } else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_PHOTOS_FILE_PATH));
                return null;
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_ALL_PHOTOS + ex.getMessage());
            return null;
        }
    }

    @Override
    public Boolean addComment(String userId, String photoId, String comment, Date date) {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_COMMENTS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_COMMENTS_FILE_PATH), ',', '"', 0);
                List<String[]> csvBody = reader.readAll();
                UUID newId = UUID.randomUUID();
                User user = getProfile(userId);
                Photo photo = getPhoto(photoId);
                Comment newComment = new Comment(newId,
                        user, photo, comment, date);
                String[] record_to_write = newComment.getCSVPhotoOutput().split(",");
                csvBody.add(record_to_write);
                reader.close();
                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_COMMENTS_FILE_PATH), ',', '"');
                writer.writeAll(csvBody);
                writer.flush();
                writer.close();
                log.info(String.format(Constants.SUCCESS_NEW_COMMENT_XML, newId));
                return true;
            }
        } catch (Exception ex){
            log.error(Constants.ERROR_INSERT_QUERY + ex.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public ArrayList<Comment> getAllComments() {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_COMMENTS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_COMMENTS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> users = new ArrayList<>();
                List<Photo> photos = new ArrayList<>();
                for (String[] line : csvBody) {
                    User user;
                    if (line[1]!=null) {
                        user = getProfile(line[1]);
                    } else
                        user = null;
                    Photo photo;
                    if (line[2]!=null) {
                        photo = getPhoto(line[2]);
                    } else {
                        photo = null;
                    }
                    users.add(user);
                    photos.add(photo);
                }
                List<Comment> comments = Comment.convertFromCSV(csvBody, users, photos);
                if (photos.size() != 0){
                    return (ArrayList<Comment>) comments;
                } else {
                    log.info(Constants.EMPTY_GET_ALL_COMMENTS);
                    reader.close();
                    return null;
                }
            } else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_COMMENTS_FILE_PATH));
                return null;
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_ALL_COMMENTS + ex.getMessage());
            return null;
        }
    }

    @Override
    public ArrayList<User> searchUsers(String field, String value) {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_USERS_FILE_PATH);
            List<String[]> findedRows = new ArrayList<String[]>();
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_USERS_FILE_PATH), ',', '"', 0);
                List<String[]> csvBody = reader.readAll();
                String[] first_row = csvBody.get(0);
                csvBody.remove(0);
                Integer jj = first_row.length;
                for (int i = 0; i < first_row.length; i++) {
                    String ll = first_row[i];
                    if (first_row[i].toLowerCase().equals(field.toLowerCase())){
                        for (int j = 1; j < csvBody.size(); j++){
                            if (csvBody.get(j)[i].toLowerCase().equals(value.toLowerCase())){
                                findedRows.add(csvBody.get(j));
                            }
                        }
                    }
                }
                List<User> users = User.convertFromCSV(findedRows);
                if (users.size() != 0){
                    return (ArrayList<User>) users;
                } else {
                    log.info(Constants.EMPTY_GET_USERS_SEARCH);
                    reader.close();
                    return null;
                }
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_PROFILES_SEARCH + ex.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public ArrayList<Event> searchEvents(String field, String value) {
        return null;
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

}
