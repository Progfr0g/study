//package ru.sfedu.photosearch.providers;
//
//import com.opencsv.CSVReader;
//import com.opencsv.CSVWriter;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.simpleframework.xml.Serializer;
//import org.simpleframework.xml.core.Persister;
//import ru.sfedu.photosearch.Constants;
//import ru.sfedu.photosearch.enums.Role;
//import ru.sfedu.photosearch.enums.Status;
//import ru.sfedu.photosearch.enums.Tables;
//import ru.sfedu.photosearch.models.Event;
//import ru.sfedu.photosearch.models.Photo;
//import ru.sfedu.photosearch.models.User;
//import ru.sfedu.photosearch.xmlTables.XML_PhotosTable;
//
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.util.*;
//
//public class DataProviderCSV implements DataProvider {
//    private static Logger log = LogManager.getLogger(DataProviderCSV.class);
//
//
//    @Override
//    public String createNewProfile(String name, String last_name, Integer age, String date_of_registration, String role, String town) {
//        try {
//            CSVReader reader;
//            File source = new File(Constants.CSV_USERS_FILE_PATH);
//            if (source.length() > 0) {
//                reader = new CSVReader(new FileReader(Constants.CSV_USERS_FILE_PATH), ',' , '"' , 0);
//                List<String[]> csvBody = reader.readAll();
//                String id = UUID.randomUUID().toString();
//                String[] record_to_write = new User(id, name, last_name, age, date_of_registration,
//                        Role.valueOf(role.toUpperCase()), town, Constants.DEFAULT_RATING).getCSVUserOutput().split(",");
//                csvBody.add(record_to_write);
//                reader.close();
//                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_USERS_FILE_PATH),',','"');
//                writer.writeAll(csvBody);
//                writer.flush();
//                writer.close();
//                log.info(String.format(Constants.SUCCESS_NEW_PROFILE_XML, id));
//                return id;
//            } else {
//                reader = new CSVReader(new FileReader(Constants.CSV_USERS_FILE_PATH), ',' , '"' , 1);
//                List<String[]> csvBody = reader.readAll();
//                String id = UUID.randomUUID().toString();
//                String[] record_to_write = new User(id, name, last_name, age, date_of_registration,
//                        Role.valueOf(role.toUpperCase()), town, Constants.DEFAULT_RATING).getCSVUserOutput().split(",");
//                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_USERS_FILE_PATH),',','"');
//                writer.writeNext(record_to_write);
//                writer.close();
//                log.info(String.format(Constants.SUCCESS_NEW_PROFILE_XML, id));
//                return id;
//            }
//
//        } catch (Exception ex) {
//            log.error(Constants.ERROR_INSERT_QUERY + ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    public String createNewEvent(String title, String description, String customer, String event_date, Integer price, Float quantity) {
//        try {
//            CSVReader reader;
//            File source = new File(Constants.CSV_EVENTS_FILE_PATH);
//            if (source.length() > 0) {
//                reader = new CSVReader(new FileReader(Constants.CSV_EVENTS_FILE_PATH), ',', '"', 0);
//                List<String[]> csvBody = reader.readAll();
//                String id = UUID.randomUUID().toString();
//                String[] record_to_write = new Event(id, title, description, customer, Constants.UTIL_EMPTY_STRING, event_date, price, quantity, Constants.UTIL_EMPTY_STRING, false, Constants.UTIL_EMPTY_STRING, Status.UNCOMPLETED).getCSVEventOutput().split(",");
//                csvBody.add(record_to_write);
//                reader.close();
//                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_EVENTS_FILE_PATH), ',', '"');
//                writer.writeAll(csvBody);
//                writer.flush();
//                writer.close();
//                log.info(String.format(Constants.SUCCESS_NEW_EVENT_XML, id));
//                return id;
//            } else {
//                String id = UUID.randomUUID().toString();
//                String[] record_to_write = new Event(id, title, description, customer, Constants.UTIL_EMPTY_STRING, event_date, price, quantity, Constants.UTIL_EMPTY_STRING, false, Constants.UTIL_EMPTY_STRING, Status.UNCOMPLETED).getCSVEventOutput().split(",");
//                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_EVENTS_FILE_PATH), ',', '"');
//                writer.writeNext(record_to_write);
//                writer.close();
//                log.info(String.format(Constants.SUCCESS_NEW_EVENT_XML, id));
//                return id;
//            }
//        } catch (Exception ex){
//            log.error(Constants.ERROR_INSERT_QUERY + ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    public String getProfile(String id) {
//        try {
//            CSVReader reader;
//            File source = new File(Constants.CSV_USERS_FILE_PATH);
//            if (source.length() > 0) {
//                reader = new CSVReader(new FileReader(Constants.CSV_USERS_FILE_PATH), ',', '"', 1);
//                List<String[]> csvBody = reader.readAll();
//                List<User> users = User.convertFromCSV(csvBody);
//                Set<String> targetSet = new HashSet<String>();
//                for (User user: users) {
//                    if (!targetSet.add(user.getId())) {
//                        return String.format(Constants.ERROR_CSV_SAME_ID, Tables.USERS.name().toLowerCase());
//                    }
//                    if (user.getId().equals(id)) {
//                        log.info(String.format(Constants.SUCCESS_GET_PROFILE, id));
//                        return user.getUserOutput();
//                    }
//                }
//                reader.close();
//                log.info(String.format(Constants.EMPTY_GET_PROFILE, id));
//            }
//        } catch (Exception ex) {
//            log.error(Constants.ERROR_GET_PROFILE + ex.getMessage());
//            return null;
//        }
//        return null;
//    }
//
//    @Override
//    public String getEvent(String id) {
//        try {
//            CSVReader reader;
//            File source = new File(Constants.CSV_EVENTS_FILE_PATH);
//            if (source.length() > 0) {
//                reader = new CSVReader(new FileReader(Constants.CSV_EVENTS_FILE_PATH), ',', '"', 1);
//                List<String[]> csvBody = reader.readAll();
//                List<Event> events = Event.convertFromCSV(csvBody);
//                Set<String> targetSet = new HashSet<String>();
//                for (Event event: events) {
//                    if (!targetSet.add(event.getId())) {
//                        return String.format(Constants.ERROR_CSV_SAME_ID, Tables.EVENTS.name().toLowerCase());
//                    }
//                    if (event.getId().equals(id)) {
//                        log.info(String.format(Constants.SUCCESS_GET_EVENT, id));
//                        return event.getEventOutput();
//                    }
//                }
//                reader.close();
//                log.info(String.format(Constants.EMPTY_GET_EVENT, id));
//            }
//        } catch (Exception ex) {
//            log.error(Constants.ERROR_GET_EVENT + ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    public void editProfileById(String id, String field, String value) {
//        try {
//            CSVReader reader;
//            File source = new File(Constants.CSV_USERS_FILE_PATH);
//            if (source.length() > 0) {
//                reader = new CSVReader(new FileReader(Constants.CSV_USERS_FILE_PATH), ',', '"', 1);
//                List<String[]> csvBody = reader.readAll();
//                List<User> users = User.convertFromCSV(csvBody);
//                List<String[]> usersWrite = new ArrayList<>();
//                for (User user: users) {
//                    if (user.getId().equals(id)) {
//                        switch (field){
//                            case Constants.USERS_NAME:{
//                                user.setName(value);
//                                log.debug(String.format(Constants.SUCCESS_UPDATE_PROFILE_XML, id));
//                                break;
//                            }
//                            case Constants.USERS_LAST_NAME:{
//                                user.setLast_name(value);
//                                log.debug(String.format(Constants.SUCCESS_UPDATE_PROFILE_XML, id));
//                                break;
//                            }
//                            case Constants.USERS_AGE:{
//                                user.setAge(Integer.parseInt(value));
//                                log.debug(String.format(Constants.SUCCESS_UPDATE_PROFILE_XML, id));
//                                break;
//                            }
//                            case Constants.USERS_TOWN:{
//                                user.setTown(value);
//                                log.debug(String.format(Constants.SUCCESS_UPDATE_PROFILE_XML, id));
//                                break;
//                            }
//                            default: {
//                                log.warn(String.format(Constants.ERROR_WRONG_FIELD, field));
//                            }
//                        }
//                    }
//                    usersWrite.add(user.getCSVUserOutput().split(","));
//                }
//                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_USERS_FILE_PATH), ',', '"');
//                usersWrite.add(0, Constants.CSV_USERS_COLUMNS.split(", "));
//                if (usersWrite.size() < 2){
//                    log.info(String.format(Constants.EMPTY_GET_PROFILE, id));
//                }
//                writer.writeAll(usersWrite);
//                writer.close();
//                reader.close();
//            }else {
//                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.CSV_EVENTS_FILE_PATH));
//            }
//        } catch (Exception ex) {
//            log.error(Constants.ERROR_UPDATE_QUERY + ex.getMessage());
//        }
//    }
//
//    @Override
//    public void editEventById(String id, String field, String value) {
//        try {
//            CSVReader reader;
//            File source = new File(Constants.CSV_EVENTS_FILE_PATH);
//            if (source.length() > 0) {
//                reader = new CSVReader(new FileReader(Constants.CSV_EVENTS_FILE_PATH), ',', '"', 1);
//                List<String[]> csvBody = reader.readAll();
//                List<Event> events = Event.convertFromCSV(csvBody);
//                List<String[]> eventsWrite = new ArrayList<>();
//                for (Event event: events) {
//                    if (event.getId().equals(id)) {
//                        switch (field) {
//                            case Constants.EVENTS_TITLE: {
//                                event.setTitle(value);
//                                log.debug(String.format(Constants.SUCCESS_UPDATE_EVENT_XML, id));
//                                break;
//                            }
//                            case Constants.EVENTS_DESCRIPTION: {
//                                event.setDescription(value);
//                                log.debug(String.format(Constants.SUCCESS_UPDATE_EVENT_XML, id));
//                                break;
//                            }
//                            case Constants.EVENTS_PRICE: {
//                                event.setPrice(Integer.parseInt(value));
//                                log.debug(String.format(Constants.SUCCESS_UPDATE_EVENT_XML, id));
//                                break;
//                            }
//                            case Constants.EVENTS_QUANTITY: {
//                                event.setQuantity(Float.parseFloat(value));
//                                log.debug(String.format(Constants.SUCCESS_UPDATE_EVENT_XML, id));
//                                break;
//                            }
//                            default: {
//                                log.warn(String.format(Constants.ERROR_WRONG_FIELD, field));
//                            }
//                        }
//                        log.info(String.format(Constants.SUCCESS_UPDATE_EVENT_XML, id));
//                    }
//                    eventsWrite.add(event.getCSVEventOutput().split(","));
//                }
//                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_EVENTS_FILE_PATH), ',', '"');
//                eventsWrite.add(0, Constants.CSV_EVENTS_COLUMNS.split(", "));
//                if (eventsWrite.size() < 2){
//                    log.info(String.format(Constants.EMPTY_GET_EVENT, id));
//                }
//                writer.writeAll(eventsWrite);
//                writer.close();
//                reader.close();
//            }else {
//                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.CSV_EVENTS_FILE_PATH));
//            }
//        } catch (Exception ex) {
//            log.error(Constants.ERROR_UPDATE_QUERY + ex.getMessage());
//        }
//    }
//
//    @Override
//    public void deleteProfileById(String id) {
//        try {
//            CSVReader reader;
//            File source = new File(Constants.CSV_USERS_FILE_PATH);
//            if (source.length() > 0) {
//                reader = new CSVReader(new FileReader(Constants.CSV_USERS_FILE_PATH), ',', '"', 1);
//                List<String[]> csvBody = reader.readAll();
//                List<User> users = User.convertFromCSV(csvBody);
//                List<String[]> usersWrite = new ArrayList<>();
//                for (User user: users) {
//                    if (user.getId().equals(id)) {
//                        users.remove(user);
//                        break;
//                    }
//                    usersWrite.add(user.getCSVUserOutput().split(","));
//                }
//                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_USERS_FILE_PATH), ',', '"');
//                usersWrite.add(0, Constants.CSV_USERS_COLUMNS.split(", "));
//                if (usersWrite.size() < 2){
//                    log.info(String.format(Constants.EMPTY_GET_PROFILE, id));
//                }
//                writer.writeAll(usersWrite);
//                writer.close();
//                reader.close();
//            }else {
//                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_USERS_FILE_PATH));
//            }
//        } catch (Exception ex) {
//            log.error(Constants.ERROR_DELETE_QUERY + ex.getMessage());
//        }
//    }
//
//    @Override
//    public void deleteEventById(String id) {
//        try {
//            CSVReader reader;
//            File source = new File(Constants.CSV_EVENTS_FILE_PATH);
//            if (source.length() > 0) {
//                reader = new CSVReader(new FileReader(Constants.CSV_EVENTS_FILE_PATH), ',', '"', 1);
//                List<String[]> csvBody = reader.readAll();
//                List<Event> events = Event.convertFromCSV(csvBody);
//                List<String[]> eventsWrite = null;
//                for (Event event: events) {
//                    if (event.getId().equals(id)) {
//                        events.remove(event);
//                        break;
//                    }
//                    eventsWrite.add(event.getCSVEventOutput().split(","));
//                }
//                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_EVENTS_FILE_PATH), ',', '"');
//                eventsWrite.add(0, Constants.CSV_EVENTS_COLUMNS.split(", "));
//                if (eventsWrite.size() < 2){
//                    log.info(String.format(Constants.EMPTY_GET_EVENT, id));
//                }
//                writer.writeAll(eventsWrite);
//                writer.close();
//                reader.close();
//            }else {
//                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.CSV_EVENTS_FILE_PATH));
//            }
//        } catch (Exception ex) {
//            log.error(Constants.ERROR_DELETE_QUERY + ex.getMessage());
//        }
//    }
//
//    @Override
//    public String addPhoto(String id, String path) {
//        try {
//            CSVReader reader;
//            File source = new File(Constants.CSV_PHOTOS_FILE_PATH);
//            if (source.length() > 0) {
//                reader = new CSVReader(new FileReader(Constants.CSV_PHOTOS_FILE_PATH), ',', '"', 0);
//                List<String[]> csvBody = reader.readAll();
//                String new_id = UUID.randomUUID().toString();
//                String[] record_to_write = new Photo(
//                        new_id,
//                        id,
//                        Constants.UTIL_EMPTY_STRING,
//                        Constants.UTIL_EMPTY_STRING,
//                        Constants.UTIL_EMPTY_STRING,
//                        Constants.UTIL_EMPTY_STRING,
//                        path,
//                        Constants.DEFAULT_RATING).getCSVPhotoOutput().split(",");
//                csvBody.add(record_to_write);
//                reader.close();
//                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_PHOTOS_FILE_PATH), ',', '"');
//                writer.writeAll(csvBody);
//                writer.flush();
//                writer.close();
//                log.info(String.format(Constants.SUCCESS_NEW_PHOTO_XML, id));
//                return new_id;
//            } else {
//                String new_id = UUID.randomUUID().toString();
//                String[] record_to_write = new Photo(
//                        new_id,
//                        id,
//                        Constants.UTIL_EMPTY_STRING,
//                        Constants.UTIL_EMPTY_STRING,
//                        Constants.UTIL_EMPTY_STRING,
//                        Constants.UTIL_EMPTY_STRING,
//                        path,
//                        Constants.DEFAULT_RATING).getCSVPhotoOutput().split(",");
//                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_PHOTOS_FILE_PATH), ',', '"');
//                writer.writeNext(record_to_write);
//                writer.close();
//                log.info(String.format(Constants.SUCCESS_NEW_PHOTO_XML, id));
//                return new_id;
//            }
//        } catch (Exception ex){
//            log.error(Constants.ERROR_INSERT_QUERY + ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    public String getPhoto(String id) {
//        try {
//            CSVReader reader;
//            File source = new File(Constants.CSV_PHOTOS_FILE_PATH);
//            if (source.length() > 0) {
//                reader = new CSVReader(new FileReader(Constants.CSV_PHOTOS_FILE_PATH), ',', '"', 1);
//                List<String[]> csvBody = reader.readAll();
//                List<Photo> photos = Photo.convertFromCSV(csvBody);
//                Set<String> targetSet = new HashSet<String>();
//                for (Photo photo: photos) {
//                    if (!targetSet.add(photo.getId())) {
//                        return String.format(Constants.ERROR_CSV_SAME_ID, Tables.PHOTOS.name().toLowerCase());
//                    }
//                    if (photo.getId().equals(id)) {
//                        log.info(String.format(Constants.SUCCESS_GET_PHOTO, id));
//                        return photo.getPhotoOutput();
//                    }
//                }
//                reader.close();
//                log.info(String.format(Constants.EMPTY_GET_PHOTO, id));
//            }
//        } catch (Exception ex) {
//            log.error(Constants.ERROR_GET_PHOTO + ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    public void editPhotoById(String id, String field, String value) {
//        try {
//            CSVReader reader;
//            File source = new File(Constants.CSV_PHOTOS_FILE_PATH);
//            if (source.length() > 0) {
//                reader = new CSVReader(new FileReader(Constants.CSV_PHOTOS_FILE_PATH), ',', '"', 1);
//                List<String[]> csvBody = reader.readAll();
//                List<Photo> photos = Photo.convertFromCSV(csvBody);
//                List<String[]> photosWrite = new ArrayList<>();
//                for (Photo photo: photos) {
//                    if (photo.getId().equals(id)) {
//                        switch (field) {
//                            case Constants.PHOTOS_TITLE: {
//                                photo.setTitle(value);
//                                log.debug(String.format(Constants.SUCCESS_UPDATE_PHOTO_XML, id));
//                                break;
//                            }
//                            case Constants.PHOTOS_DESCRIPTION: {
//                                photo.setDescription(value);
//                                log.debug(String.format(Constants.SUCCESS_UPDATE_PHOTO_XML, id));
//                                break;
//                            }
//                            case Constants.PHOTOS_TAG: {
//                                photo.setTag(value);
//                                log.debug(String.format(Constants.SUCCESS_UPDATE_PHOTO_XML, id));
//                                break;
//                            }
//                            case Constants.PHOTOS_PATH: {
//                                photo.setPhoto_path(value);
//                                log.debug(String.format(Constants.SUCCESS_UPDATE_PHOTO_XML, id));
//                                break;
//                            }
//                            default: {
//                                log.warn(String.format(Constants.ERROR_WRONG_FIELD, field));
//                            }
//                        }
//                        log.info(String.format(Constants.SUCCESS_UPDATE_PHOTO_XML, id));
//                    }
//                    photosWrite.add(photo.getCSVPhotoOutput().split(","));
//                }
//                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_PHOTOS_FILE_PATH), ',', '"');
//                photosWrite.add(0, Constants.CSV_PHOTOS_COLUMNS.split(", "));
//                if (photosWrite.size() < 2){
//                    log.info(String.format(Constants.EMPTY_GET_PHOTO, id));
//                }
//                writer.writeAll(photosWrite);
//                writer.close();
//                reader.close();
//            }else {
//                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.CSV_PHOTOS_FILE_PATH));
//            }
//        } catch (Exception ex) {
//            log.error(Constants.ERROR_UPDATE_QUERY + ex.getMessage());
//        }
//    }
//
//    @Override
//    public void deletePhotoById(String id) {
//        try {
//            CSVReader reader;
//            File source = new File(Constants.CSV_PHOTOS_FILE_PATH);
//            if (source.length() > 0) {
//                reader = new CSVReader(new FileReader(Constants.CSV_PHOTOS_FILE_PATH), ',', '"', 1);
//                List<String[]> csvBody = reader.readAll();
//                List<Photo> photos = Photo.convertFromCSV(csvBody);
//                List<String[]> photosWrite = null;
//                for (Photo photo: photos) {
//                    if (photo.getId().equals(id)) {
//                        photos.remove(photo);
//                    }
//                    photosWrite.add(photo.getCSVPhotoOutput().split(","));
//                }
//                CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_PHOTOS_FILE_PATH), ',', '"');
//                photosWrite.add(0, Constants.CSV_PHOTOS_COLUMNS.split(", "));
//                if (photosWrite.size() < 2){
//                    log.info(String.format(Constants.CSV_PHOTOS_FILE_PATH, id));
//                }
//                writer.writeAll(photosWrite);
//                writer.close();
//                reader.close();
//            }else {
//                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.CSV_PHOTOS_FILE_PATH));
//            }
//        } catch (Exception ex) {
//            log.error(Constants.ERROR_DELETE_QUERY + ex.getMessage());
//        }
//    }
//
//    @Override
//    public String getPortfolio(String user_id) {
//        try {
//            CSVReader reader;
//            File source = new File(Constants.CSV_PHOTOS_FILE_PATH);
//            String result = Constants.UTIL_EMPTY_STRING;
//            if (source.length() > 0) {
//                reader = new CSVReader(new FileReader(Constants.CSV_PHOTOS_FILE_PATH), ',', '"', 1);
//                List<String[]> csvBody = reader.readAll();
//                List<Photo> photos = Photo.convertFromCSV(csvBody);
//                for (Photo photo: photos) {
//                    result += photo.getPhotoOutput();
//                    result += Constants.UTIL_SEPARATOR
//                            + Constants.UTIL_NEW_LINE;
//                }
//                return result;
//            } else {
//                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_PHOTOS_FILE_PATH));
//            }
//        } catch (Exception ex) {
//            log.error(Constants.ERROR_GET_PHOTO_PATH + ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    public String getPhotoPathById(String id) {
//        try {
//            CSVReader reader;
//            File source = new File(Constants.CSV_PHOTOS_FILE_PATH);
//            String result = Constants.UTIL_EMPTY_STRING;
//            if (source.length() > 0) {
//                reader = new CSVReader(new FileReader(Constants.CSV_PHOTOS_FILE_PATH), ',', '"', 1);
//                List<String[]> csvBody = reader.readAll();
//                List<Photo> photos = Photo.convertFromCSV(csvBody);
//                for (Photo photo: photos) {
//                    if (id.equals(photo.getId())){
//                        return photo.getPhoto_path();
//                    }
//                }
//            } else {
//                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_PHOTOS_FILE_PATH));
//            }
//        } catch (Exception ex) {
//            log.error(Constants.ERROR_GET_PHOTO_PATH + ex.getMessage());
//        }
//        return null;
//    }
//}
