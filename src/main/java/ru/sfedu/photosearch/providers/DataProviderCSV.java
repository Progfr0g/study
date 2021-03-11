package ru.sfedu.photosearch.providers;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.enums.*;
import ru.sfedu.photosearch.newModels.*;
import ru.sfedu.photosearch.utils.Formatter;

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
                if (Role.PHOTOGRAPHER.equals(role)){
                    reader = new CSVReader(new FileReader(Constants.CSV_PHOTOGRAPHERS_FILE_PATH), ',' , '"' , 0);
                    List<String[]> csvBody = reader.readAll();
                    UUID id = UUID.randomUUID();
                    User<UUID> newPhotographer = new Photographer<UUID>(id, name, lastName, birthDay, dateOfRegistration, role, town,
                            Constants.DEFAULT_WALLET,
                            Constants.DEFAULT_RATING,
                            0,
                            CostLevel.NONE);
                    String[] record_to_write = newPhotographer.getCSVUserOutput().split(",");
                    csvBody.add(record_to_write);
                    reader.close();
                    CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_PHOTOGRAPHERS_FILE_PATH),',','"');
                    writer.writeAll(csvBody);
                    writer.flush();
                    writer.close();
                    log.info(String.format(Constants.SUCCESS_NEW_PROFILE_XML, id));
                    return true;
                } else if (Role.CUSTOMER.equals(role)){
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
                } else return false;
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
    public Optional<User> getProfile(String id) {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_USERS_FILE_PATH);
            Integer rows = 0;
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_USERS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> users = User.convertFromCSV(csvBody);
                for (User user : users) {
                    if (user.getId().equals(id)) {
                        rows++;
                        return Optional.of(user);
                    }
                }
                reader.close();
            }
            source = new File(Constants.CSV_PHOTOGRAPHERS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_PHOTOGRAPHERS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> photographers = Photographer.convertFromCSV(csvBody);
                for (User photographer : photographers) {
                    if (photographer.getId().equals(id)) {
                        rows++;
                        return Optional.of(photographer);
                    }
                }
                reader.close();
            }
            if (rows == 0) {
                log.debug(String.format(Constants.EMPTY_GET_PROFILE, id));
                return Optional.empty();
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_PROFILE + ex.getMessage());
            return Optional.empty();
        }
        return Optional.empty();
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
                                return false;
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

                for (User user: users) {
                    if (!user.getId().equals(id)) {
                        usersWrite.add(user.getCSVUserOutput().split(","));
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
                User userCustomer = getProfile(customer).orElse(null);
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
                User userCustomer = getProfile(customer).orElse(null);
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
    public Optional<Event> getEvent(String id) {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_EVENTS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_EVENTS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> customers = new ArrayList<>();
                List<User> executors = new ArrayList<>();
                for (String[] line : csvBody) {
                    Optional<User> getCustomer = getProfile(line[7]);
                    User customer = null;
                    if (line[7]!=null) {
                        customer = getCustomer.orElse(null);
                    }
                    Optional<User> getExecutor = getProfile(line[8]);
                    User executor = null;
                    if (line[8]!=null) {
                        executor = getExecutor.orElse(null);
                    }
                    customers.add(customer);
                    executors.add(executor);
                }
                List<Event> events = Event.convertFromCSV(csvBody, customers, executors);
                Integer rows = 0;
                for (Event event: events) {
                    if (event.getId().equals(id)) {
                        rows++;
                        return Optional.of(event);
                    }
                }
                reader.close();
                if (rows == 0) {
                    log.debug(String.format(Constants.EMPTY_GET_EVENT, id));
                    return Optional.empty();
                }
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_EVENT + ex.getMessage());
            return Optional.empty();
        }
        return Optional.empty();
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
                    Optional<User> getCustomer = getProfile(line[7]);
                    User customer = null;
                    if (line[7]!=null) {
                        customer = getCustomer.orElse(null);
                    }
                    Optional<User> getExecutor = getProfile(line[8]);
                    User executor = null;
                    if (line[8]!=null) {
                        executor = getExecutor.orElse(null);
                    }
                    customers.add(customer);
                    executors.add(executor);
                }
                List<Event> events = Event.convertFromCSV(csvBody, customers, executors);
                List<String[]> eventsWrite = new ArrayList<>();
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
                                return false;
                            }
                        }
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
                    Optional<User> getCustomer = getProfile(line[7]);
                    User customer = null;
                    if (line[7]!=null) {
                        customer = getCustomer.orElse(null);
                    }
                    Optional<User> getExecutor = getProfile(line[8]);
                    User executor = null;
                    if (line[8]!=null) {
                        executor = getExecutor.orElse(null);
                    }
                    customers.add(customer);
                    executors.add(executor);
                }
                List<Event> events = Event.convertFromCSV(csvBody, customers, executors);
                List<String[]> eventsWrite = new ArrayList<>();
                for (Event event: events) {
                    if (!event.getId().equals(id)) {
                        eventsWrite.add(event.getCSVEventOutput().split(","));
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
                User user = getProfile(id).orElse(null);
                Event event = getEvent(Constants.UTIL_EMPTY_STRING).orElse(null);
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
                User user = getProfile(id).orElse(null);
                Event event = getEvent(Constants.UTIL_EMPTY_STRING).orElse(null);
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
    public Optional<Photo> getPhoto(String id) {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_PHOTOS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_PHOTOS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> users = new ArrayList<>();
                List<Event> events = new ArrayList<>();
                for (String[] line : csvBody) {
                    Optional<User> getCustomer = getProfile(line[1]);
                    User customer = null;
                    if (line[1]!=null) {
                        customer = getCustomer.orElse(null);
                    }
                    Optional<Event> getEvent = getEvent(line[2]);
                    Event event = null;
                    if (line[2]!=null) {
                        event = getEvent.orElse(null);
                    }
                    users.add(customer);
                    events.add(event);
                }
                List<Photo> photos = Photo.convertFromCSV(csvBody, users, events);
                Integer rows = 0;
                for (Photo photo: photos) {
                    if (photo.getId().equals(id)) {
                        rows++;
                        return Optional.of(photo);
                    }
                }
                reader.close();
                if (rows == 0) {
                    log.info(String.format(Constants.EMPTY_GET_PHOTO, id));
                    return Optional.empty();
                }
            } else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_PHOTOS_FILE_PATH));
                return Optional.empty();
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_PHOTO + ex.getMessage());
            return Optional.empty();
        }
        return Optional.empty();
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
                for (String[] line : csvBody) {
                    Optional<User> getCustomer = getProfile(line[1]);
                    User customer = null;
                    if (line[1]!=null) {
                        customer = getCustomer.orElse(null);
                    }
                    Optional<Event> getEvent = getEvent(line[2]);
                    Event event = null;
                    if (line[2]!=null) {
                        event = getEvent.orElse(null);
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
                                return false;
                            }
                        }
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
                    Optional<User> getCustomer = getProfile(line[1]);
                    User customer = null;
                    if (line[1]!=null) {
                        customer = getCustomer.orElse(null);
                    }
                    Optional<Event> getEvent = getEvent(line[2]);
                    Event event = null;
                    if (line[2]!=null) {
                        event = getEvent.orElse(null);
                    }
                    users.add(customer);
                    events.add(event);
                }
                List<Photo> photos = Photo.convertFromCSV(csvBody, users, events);
                List<String[]> photosWrite = new ArrayList<>();

                for (Photo photo: photos) {
                    if (!photo.getId().equals(id)) {
                        photosWrite.add(photo.getCSVPhotoOutput().split(","));
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
    public Optional<ArrayList<Photo>> getPortfolio(String userId) {
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
                    Optional<User> getCustomer = getProfile(line[1]);
                    User customer = null;
                    if (line[1]!=null) {
                        customer = getCustomer.orElse(null);
                    }
                    Optional<Event> getEvent = getEvent(line[2]);
                    Event event = null;
                    if (line[2]!=null) {
                        event = getEvent.orElse(null);
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
                    return Optional.empty();
                } else {
                    return Optional.of(result);
                }
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
            CSVReader reader;
            File source = new File(Constants.CSV_PHOTOS_FILE_PATH);
            String result = Constants.UTIL_EMPTY_STRING;
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_PHOTOS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> users = new ArrayList<>();
                List<Event> events = new ArrayList<>();
                for (String[] line : csvBody) {
                    Optional<User> getCustomer = getProfile(line[1]);
                    User customer = null;
                    if (line[1]!=null) {
                        customer = getCustomer.orElse(null);
                    }
                    Optional<Event> getEvent = getEvent(line[2]);
                    Event event = null;
                    if (line[2]!=null) {
                        event = getEvent.orElse(null);
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
    public String getLastPhotographerId() {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_PHOTOGRAPHERS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_PHOTOGRAPHERS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> photographers = Photographer.convertFromCSV(csvBody);
                if (photographers.size() != 0){
                    return photographers.get(photographers.size()-1).getId().toString();
                } else {
                    log.info(Constants.EMPTY_GET_LAST_PHOTOGRAPHER);
                    reader.close();
                    return null;
                }
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_LAST_PHOTOGRAPHER + ex.getMessage());
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
                    Optional<User> getCustomer = getProfile(line[7]);
                    User customer = null;
                    if (line[7]!=null) {
                        customer = getCustomer.orElse(null);
                    }
                    Optional<User> getExecutor = getProfile(line[8]);
                    User executor = null;
                    if (line[8]!=null) {
                        executor = getExecutor.orElse(null);
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
                    Optional<User> getCustomer = getProfile(line[1]);
                    User customer = null;
                    if (line[1]!=null) {
                        customer = getCustomer.orElse(null);
                    }
                    Optional<Event> getEvent = getEvent(line[2]);
                    Event event = null;
                    if (line[2]!=null) {
                        event = getEvent.orElse(null);
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
    public Optional<ArrayList<User>> getAllUsers() {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_USERS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_USERS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> users = User.convertFromCSV(csvBody);
                if (users.size() != 0){
                    return Optional.of((ArrayList<User>) users);
                } else {
                    log.info(Constants.EMPTY_GET_ALL_USERS);
                    reader.close();
                    return Optional.empty();
                }
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_ALL_PROFILES + ex.getMessage());
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public Optional<ArrayList<Event>> getAllEvents() {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_EVENTS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_EVENTS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> customers = new ArrayList<>();
                List<User> executors = new ArrayList<>();
                for (String[] line : csvBody) {
                    Optional<User> getCustomer = getProfile(line[7]);
                    User customer = null;
                    if (line[7]!=null) {
                        customer = getCustomer.orElse(null);
                    }
                    Optional<User> getExecutor = getProfile(line[8]);
                    User executor = null;
                    if (line[8]!=null) {
                        executor = getExecutor.orElse(null);
                    }
                    customers.add(customer);
                    executors.add(executor);
                }
                List<Event> events = Event.convertFromCSV(csvBody, customers, executors);
                if (events.size() != 0){
                    return Optional.of((ArrayList<Event>) events);
                } else {
                    log.info(Constants.EMPTY_GET_ALL_EVENTS);
                    reader.close();
                    return Optional.empty();
                }
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_ALL_EVENTS + ex.getMessage());
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public Optional<ArrayList<Photo>> getAllPhotos() {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_PHOTOS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_PHOTOS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> users = new ArrayList<>();
                List<Event> events = new ArrayList<>();
                for (String[] line : csvBody) {
                    Optional<User> getCustomer = getProfile(line[1]);
                    User customer = null;
                    if (line[1]!=null) {
                        customer = getCustomer.orElse(null);
                    }
                    Optional<Event> getEvent = getEvent(line[2]);
                    Event event = null;
                    if (line[2]!=null) {
                        event = getEvent.orElse(null);
                    }
                    users.add(customer);
                    events.add(event);
                }
                List<Photo> photos = Photo.convertFromCSV(csvBody, users, events);
                if (photos.size() != 0){
                    return Optional.of((ArrayList<Photo>) photos);
                } else {
                    log.info(Constants.EMPTY_GET_ALL_PHOTOS);
                    reader.close();
                    return Optional.empty();
                }
            } else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_PHOTOS_FILE_PATH));
                return Optional.empty();
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_ALL_PHOTOS + ex.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<ArrayList<User>> searchUsers(String field, String value) {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_USERS_FILE_PATH);
            List<String[]> findedRows = new ArrayList<String[]>();
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_USERS_FILE_PATH), ',', '"', 0);
                List<String[]> csvBody = reader.readAll();
                String[] first_row = csvBody.get(0);
                csvBody.remove(0);
                for (int i = 0; i < first_row.length; i++) {
                    if (first_row[i].toLowerCase().equals(field.toLowerCase())){
                        for (int j = 0; j < csvBody.size(); j++){
                            if (csvBody.get(j)[i].toLowerCase().equals(value.toLowerCase())){
                                findedRows.add(csvBody.get(j));
                            }
                        }
                    }
                }
                List<User> users = User.convertFromCSV(findedRows);
                if (users.size() != 0){
                    return Optional.of((ArrayList<User>) users);
                } else {
                    log.info(Constants.EMPTY_GET_USERS_SEARCH);
                    reader.close();
                    return Optional.empty();
                }
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_PROFILES_SEARCH + ex.getMessage());
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public Optional<ArrayList<User>> searchPhotographers(String field, String value) {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_PHOTOGRAPHERS_FILE_PATH);
            List<String[]> findedRows = new ArrayList<String[]>();
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_PHOTOGRAPHERS_FILE_PATH), ',', '"', 0);
                List<String[]> csvBody = reader.readAll();
                String[] first_row = csvBody.get(0);
                csvBody.remove(0);
                for (int i = 0; i < first_row.length; i++) {
                    if (first_row[i].toLowerCase().equals(field.toLowerCase())){
                        for (int j = 0; j < csvBody.size(); j++){
                            if (csvBody.get(j)[i].toLowerCase().equals(value.toLowerCase())){
                                findedRows.add(csvBody.get(j));
                            }
                        }
                    }
                }
                List<User> users = Photographer.convertFromCSV(findedRows);
                if (users.size() != 0){
                    return Optional.of((ArrayList<User>) users);
                } else {
                    log.info(Constants.EMPTY_GET_PHOTOGRAPHERS_SEARCH);
                    reader.close();
                    return Optional.empty();
                }
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_PROFILES_SEARCH + ex.getMessage());
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public Optional<ArrayList<Event>> searchEvents(String field, String value) {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_EVENTS_FILE_PATH);
            List<String[]> findedRows = new ArrayList<String[]>();
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_EVENTS_FILE_PATH), ',', '"', 0);
                List<String[]> csvBody = reader.readAll();
                String[] first_row = csvBody.get(0);
                csvBody.remove(0);
                List<User> customers = new ArrayList<>();
                List<User> executors = new ArrayList<>();
                for (String[] line : csvBody) {
                    Optional<User> getCustomer = getProfile(line[7]);
                    User customer = null;
                    if (line[7]!=null) {
                        customer = getCustomer.orElse(null);
                    }
                    Optional<User> getExecutor = getProfile(line[8]);
                    User executor = null;
                    if (line[8]!=null) {
                        executor = getExecutor.orElse(null);
                    }
                    customers.add(customer);
                    executors.add(executor);
                }
                for (int i = 0; i < first_row.length; i++) {
                    if (first_row[i].toLowerCase().equals(field.toLowerCase())){
                        for (int j = 0; j < csvBody.size(); j++){
                            if (csvBody.get(j)[i].toLowerCase().equals(value.toLowerCase())){
                                findedRows.add(csvBody.get(j));
                            }
                        }
                    }
                }
                List<Event> events = Event.convertFromCSV(findedRows, customers, executors);
                if (events.size() != 0){
                    return Optional.of((ArrayList<Event>) events);
                } else {
                    log.info(Constants.EMPTY_GET_EVENTS_SEARCH);
                    reader.close();
                    return Optional.empty();
                }
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_EVENTS_SEARCH + ex.getMessage());
            return Optional.empty();
        }
        return Optional.empty();
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
                User user = getProfile(userId).orElse(null);
                Photo photo = getPhoto(photoId).orElse(null);
                Comment newComment = new Comment(newId,
                        user, photo, comment, date);
                String[] record_to_write = newComment.getCSVCommentOutput().split(",");
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
    public Optional<ArrayList<Comment>> getAllCommentsById(String photoId) {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_COMMENTS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_COMMENTS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> users = new ArrayList<>();
                List<Photo> photos = new ArrayList<>();
                for (int i=0; i < csvBody.size(); i++) {
                    if (!csvBody.get(i)[2].equals(photoId)){
                        csvBody.remove(i);
                    }
                }
                for (String[] line : csvBody) {
                    Optional<User> getUser = getProfile(line[1]);
                    User user = null;
                    if (line[1]!=null) {
                        user = getUser.orElse(null);
                    }
                    Optional<Photo> getPhoto = getPhoto(line[2]);
                    Photo photo = null;
                    if (line[2]!=null) {
                        photo = getPhoto.orElse(null);
                    }
                    users.add(user);
                    photos.add(photo);
                }
                List<Comment> comments = Comment.convertFromCSV(csvBody, users, photos);
                if (photos.size() != 0){
                    return Optional.of((ArrayList<Comment>) comments);
                } else {
                    log.info(Constants.EMPTY_GET_ALL_COMMENTS);
                    reader.close();
                    return Optional.empty();
                }
            } else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_COMMENTS_FILE_PATH));
                return Optional.empty();
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_ALL_COMMENTS + ex.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Boolean addRate(String userId, String photoId, Integer rate, Date date) {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_RATES_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_RATES_FILE_PATH), ',', '"', 0);
                List<String[]> csvBody = reader.readAll();
                UUID newId = UUID.randomUUID();
                User user = getProfile(userId).orElse(null);
                Photo photo = getPhoto(photoId).orElse(null);
                Rate newRate= new Rate(newId,
                        user, photo, rate, date);
                String[] record_to_write = newRate.getCSVRateOutput().split(",");
                csvBody.add(record_to_write);
                reader.close();
                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_RATES_FILE_PATH), ',', '"');
                writer.writeAll(csvBody);
                writer.flush();
                writer.close();
                log.info(String.format(Constants.SUCCESS_NEW_RATE_XML, newId));
                return true;
            }
        } catch (Exception ex){
            log.error(Constants.ERROR_INSERT_QUERY + ex.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public Optional<ArrayList<Rate>> getAllRatesById(String photoId) {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_RATES_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_RATES_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> users = new ArrayList<>();
                List<Photo> photos = new ArrayList<>();
                for (int i=0; i < csvBody.size(); i++) {
                    if (!csvBody.get(i)[2].equals(photoId)){
                        csvBody.remove(i);
                    }
                }
                for (String[] line : csvBody) {
                    Optional<User> getUser = getProfile(line[1]);
                    User user = null;
                    if (line[1]!=null) {
                        user = getUser.orElse(null);
                    }
                    Optional<Photo> getPhoto = getPhoto(line[2]);
                    Photo photo = null;
                    if (line[2]!=null) {
                        photo = getPhoto.orElse(null);
                    }
                    users.add(user);
                    photos.add(photo);
                }
                List<Rate> rates = Rate.convertFromCSV(csvBody, users, photos);
                if (photos.size() != 0){
                    return Optional.of((ArrayList<Rate>) rates);
                } else {
                    log.info(Constants.EMPTY_GET_ALL_RATES);
                    reader.close();
                    return Optional.empty();
                }
            } else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_RATES_FILE_PATH));
                return Optional.empty();
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_ALL_RATES + ex.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Boolean addFeedback(String userId, String photographerId, Integer rate, String text, Date date) {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_FEEDBACKS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_FEEDBACKS_FILE_PATH), ',', '"', 0);
                List<String[]> csvBody = reader.readAll();
                UUID newId = UUID.randomUUID();
                User user = getProfile(userId).orElse(null);
                User photographer = getProfile(photographerId).orElse(null);
                Feedback newFeedback= new Feedback(newId,
                        user, photographer, rate, text, date);
                String[] record_to_write = newFeedback.getCSVFeedbackOutput().split(",");
                csvBody.add(record_to_write);
                reader.close();
                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_FEEDBACKS_FILE_PATH), ',', '"');
                writer.writeAll(csvBody);
                writer.flush();
                writer.close();
                log.info(String.format(Constants.SUCCESS_NEW_FEEDBACK_XML, newId));
                return true;
            }
        } catch (Exception ex){
            log.error(Constants.ERROR_INSERT_QUERY + ex.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public Optional<ArrayList<Feedback>> getAllFeedbacksById(String photographerId) {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_FEEDBACKS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_FEEDBACKS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> users = new ArrayList<>();
                List<User> photographers = new ArrayList<>();
                for (int i=0; i < csvBody.size(); i++) {
                    if (!csvBody.get(i)[2].equals(photographerId)){
                        csvBody.remove(i);
                    }
                }
                for (String[] line : csvBody) {
                    Optional<User> getUser = getProfile(line[1]);
                    User user = null;
                    if (line[1]!=null) {
                        user = getUser.orElse(null);
                    }
                    Optional<User> getPhotographer = getProfile(line[2]);
                    User photographer = null;
                    if (line[2]!=null) {
                        photographer = getPhotographer.orElse(null);
                    }
                    users.add(user);
                    photographers.add(photographer);
                }
                List<Feedback> feedbacks = Feedback.convertFromCSV(csvBody, users, photographers);
                if (feedbacks.size() != 0){
                    return Optional.of((ArrayList<Feedback>) feedbacks);
                } else {
                    log.info(Constants.EMPTY_GET_ALL_FEEDBACKS);
                    reader.close();
                    return Optional.empty();
                }
            } else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_FEEDBACKS_FILE_PATH));
                return Optional.empty();
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_ALL_FEEDBACKS + ex.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Boolean createOffer(String userId, String eventId, String photographerId, Boolean isActive, Date creationDate) {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_OFFERS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_OFFERS_FILE_PATH), ',', '"', 0);
                List<String[]> csvBody = reader.readAll();
                UUID newId = UUID.randomUUID();
                User user = getProfile(userId).orElse(null);
                Event event = getEvent(eventId).orElse(null);
                User photographer = getProfile(photographerId).orElse(null);
                Offer newOffer = new Offer(newId,
                        user, event, photographer, isActive, creationDate);
                String[] record_to_write = newOffer.getCSVOfferOutput().split(",");
                csvBody.add(record_to_write);
                reader.close();
                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_OFFERS_FILE_PATH), ',', '"');
                writer.writeAll(csvBody);
                writer.flush();
                writer.close();
                log.info(String.format(Constants.SUCCESS_NEW_OFFER_XML, newId));
                return true;
            }
        } catch (Exception ex){
            log.error(Constants.ERROR_INSERT_QUERY + ex.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public Optional<ArrayList<Offer>> getAllOffersById(String eventId) {
        try {
            CSVReader reader;
            File source = new File(Constants.CSV_OFFERS_FILE_PATH);
            if (source.length() > 0) {
                reader = new CSVReader(new FileReader(Constants.CSV_OFFERS_FILE_PATH), ',', '"', 1);
                List<String[]> csvBody = reader.readAll();
                List<User> users = new ArrayList<>();
                List<Event> events = new ArrayList<>();
                List<User> photographers = new ArrayList<>();
                for (int i=0; i < csvBody.size(); i++) {
                    if (!csvBody.get(i)[2].equals(eventId)){
                        csvBody.remove(i);
                    }
                }
                for (String[] line : csvBody) {
                    Optional<User> getUser = getProfile(line[1]);
                    User user = null;
                    if (line[1]!=null) {
                        user = getUser.orElse(null);
                    }
                    Optional<Event> getEvent = getEvent(line[2]);
                    Event event = null;
                    if (line[2]!=null) {
                        event = getEvent.orElse(null);
                    }
                    Optional<User> getPhotographer = getProfile(line[3]);
                    User photographer = null;
                    if (line[3]!=null) {
                        photographer = getPhotographer.orElse(null);
                    }
                    users.add(user);
                    events.add(event);
                    photographers.add(photographer);

                }
                List<Offer> offers = Offer.convertFromCSV(csvBody, users, photographers, events);
                if (offers.size() != 0){
                    return Optional.of((ArrayList<Offer>) offers);
                } else {
                    log.info(Constants.EMPTY_GET_ALL_OFFERS);
                    reader.close();
                    return Optional.empty();
                }
            } else {
                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_OFFERS_FILE_PATH));
                return Optional.empty();
            }
        } catch (Exception ex) {
            log.error(Constants.ERROR_GET_ALL_OFFERS + ex.getMessage());
            return Optional.empty();
        }
    }
}
