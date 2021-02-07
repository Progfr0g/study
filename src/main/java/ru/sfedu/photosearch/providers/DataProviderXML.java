//package ru.sfedu.photosearch.providers;
//
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
//import ru.sfedu.photosearch.xmlTables.XML_EventsTable;
//import ru.sfedu.photosearch.xmlTables.XML_PhotosTable;
//import ru.sfedu.photosearch.xmlTables.XML_UsersTable;
//
//import java.io.File;
//import java.util.*;
//
//public class DataProviderXML implements DataProvider {
//    private static Logger log = LogManager.getLogger(DataProviderXML.class);
//    @Override
//    public String createNewProfile(String name, String last_name, Integer age, String date_of_registration, String role, String town) {
//        try {
//            Serializer serializer = new Persister();
//            File source = new File(Constants.XML_USERS_FILE_PATH);
//            double rating = Constants.DEFAULT_RATING;
//            XML_UsersTable table;
//            if (source.length() == 0) {
//                List<User> users_array = new ArrayList<>();
//                String id = UUID.randomUUID().toString();
//                users_array.add(new User(id, name, last_name, age, date_of_registration, Role.valueOf(role.toUpperCase()), town, rating));
//                table = new XML_UsersTable();
//                table.setUsers(users_array);
//                File result = new File(Constants.XML_USERS_FILE_PATH);
//                log.info(String.format(Constants.SUCCESS_NEW_PROFILE_XML, id));
//                serializer.write(table, result);
//                return id;
//            } else {
//                table = serializer.read(XML_UsersTable.class, source);
//                List<User> writedUsers = table.getxmlUsers();
//                String id = UUID.randomUUID().toString();
//                writedUsers.add(new User(id, name, last_name, age, date_of_registration, Role.valueOf(role.toUpperCase()), town, rating));
//                File result = new File(Constants.XML_USERS_FILE_PATH);
//                table.setUsers(writedUsers);
//                serializer.write(table, result);
//                log.info(String.format(Constants.SUCCESS_NEW_PROFILE_XML, id));
//                return id;
//            }
//
//        } catch (Exception ex) {
//            log.error(Constants.ERROR_INSERT_QUERY + ex.getMessage());
//        }
//        return null;
//
//    }
//
//    @Override
//    public String createNewEvent(String title, String description, String customer, String event_date, Integer price, Float quantity) {
//        try {
//            Serializer serializer = new Persister();
//            File source = new File(Constants.XML_EVENTS_FILE_PATH);
//            XML_EventsTable table;
//            if (source.length() == 0) {
//                List<Event> events_array = new ArrayList<>();
//                String id = UUID.randomUUID().toString();
//                events_array.add(new Event(id, title, description, customer, Constants.UTIL_EMPTY_STRING, event_date, price, quantity, Constants.UTIL_EMPTY_STRING, false, Constants.UTIL_EMPTY_STRING, Status.UNCOMPLETED));
//                table = new XML_EventsTable();
//                table.setEvents(events_array);
//                File result = new File(Constants.XML_EVENTS_FILE_PATH);
//                log.info(String.format(Constants.SUCCESS_NEW_EVENT_XML, id));
//
//                serializer.write(table, result);
//                return id;
//            } else {
//                table = serializer.read(XML_EventsTable.class, source);
//                List<Event> writedEvents = table.getxmlEvents();
//                String id = UUID.randomUUID().toString();
//                writedEvents.add(new Event(id, title, description, customer, Constants.UTIL_EMPTY_STRING, event_date, price, quantity, Constants.UTIL_EMPTY_STRING, false, Constants.UTIL_EMPTY_STRING, Status.UNCOMPLETED));
//                table.setEvents(writedEvents);
//                File result = new File(Constants.XML_EVENTS_FILE_PATH);
//
//                serializer.write(table, result);
//                log.info(String.format(Constants.SUCCESS_NEW_EVENT_XML, id));
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
//    public String getProfile(String id) {
//        try{
//            Serializer serializer = new Persister();
//            File source = new File(Constants.XML_USERS_FILE_PATH);
//            if (source.length() > 0) {
//                XML_UsersTable table;
//                table = serializer.read(XML_UsersTable.class, source);
//                Set<String> targetSet = new HashSet<String>();
//                for (User user: table.getxmlUsers()) {
//                    if (!targetSet.add(user.getId())) {
//                        return String.format(Constants.ERROR_XML_SAME_ID, Tables.USERS.name().toLowerCase());
//                    }
//                    if (user.getId().equals(id)) {
//                        log.info(String.format(Constants.SUCCESS_GET_PROFILE, id));
//                        return user.getUserOutput();
//                    }
//                }
//            } else {
//                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_USERS_FILE_PATH));
//            }
//            log.info(String.format(Constants.EMPTY_GET_PROFILE, id));
//        } catch (Exception ex) {
//            log.error(String.format(Constants.ERROR_GET_PROFILE, id) + ex);
//        }
//        return null;
//    }
//
//    @Override
//    public String getEvent(String id) {
//        try{
//            Serializer serializer = new Persister();
//            File source = new File(Constants.XML_EVENTS_FILE_PATH);
//            if (source.length() > 0) {
//                XML_EventsTable table;
//                table = serializer.read(XML_EventsTable.class, source);
//                Set<String> targetSet = new HashSet<String>();
//                for (Event event: table.getxmlEvents()){
//                    if (!targetSet.add(String.valueOf(event.getId()))) {
//                        return String.format(Constants.ERROR_XML_SAME_ID, Tables.EVENTS.name().toLowerCase());
//                    }
//                    if (String.valueOf(event.getId()).equals(id)){
//                        log.info(String.format(Constants.SUCCESS_GET_EVENT, id));
//                        return event.getEventOutput();
//                    }
//                }
//            } else {
//                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_EVENTS_FILE_PATH));
//            }
//
//            log.info(String.format(Constants.EMPTY_GET_EVENT, id));
//        } catch (Exception ex) {
//            log.error(String.format(Constants.ERROR_GET_EVENT, id) + ex);
//        }
//        return null;
//    }
//
//    @Override
//    public void editProfileById(String id, String field, String value) {
//        try {
//            Serializer serializer = new Persister();
//            File source = new File(Constants.XML_USERS_FILE_PATH);
//            XML_UsersTable table;
//            if (source.length() > 0) {
//                table = serializer.read(XML_UsersTable.class, source);
//                List<User> writedUsers = table.getxmlUsers();
//                for (User user: writedUsers) {
//                    if (id.equals(user.getId())){
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
//
//                }
//
//                table.setUsers(writedUsers);
//                File result = new File(Constants.XML_USERS_FILE_PATH);
//                serializer.write(table, result);
//            }else {
//                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_USERS_FILE_PATH));
//            }
//
//        } catch (Exception ex) {
//            log.error(Constants.ERROR_UPDATE_QUERY + ex.getMessage());
//        }
//    }
//
//    @Override
//    public void editEventById(String id, String field, String value) {
//        try {
//            Serializer serializer = new Persister();
//            File source = new File(Constants.XML_EVENTS_FILE_PATH);
//            XML_EventsTable table;
//            if (source.length() > 0) {
//                table = serializer.read(XML_EventsTable.class, source);
//                List<Event> writedEvents = table.getxmlEvents();
//                for (Event event: writedEvents) {
//                    if (id.equals(event.getId())){
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
//                    }
//                }
//
//                table.setEvents(writedEvents);
//                File result = new File(Constants.XML_EVENTS_FILE_PATH);
//                serializer.write(table, result);
//            }else {
//                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_EVENTS_FILE_PATH));
//            }
//        } catch (Exception ex) {
//            log.error(Constants.ERROR_UPDATE_QUERY + ex.getMessage());
//        }
//    }
//
//    @Override
//    public void deleteProfileById(String id) {
//        try {
//            boolean delFlag = false;
//            Serializer serializer = new Persister();
//            File source = new File(Constants.XML_USERS_FILE_PATH);
//            XML_UsersTable table;
//            if (source.length() > 0) {
//                table = serializer.read(XML_UsersTable.class, source);
//                List<User> writedUsers = table.getxmlUsers();
//                for (User user: writedUsers){
//                    if (id.equals(user.getId())){
//                        writedUsers.remove(user);
//                        delFlag = true;
//                        break;
//                    }
//                }
//                table.setUsers(writedUsers);
//                File result = new File(Constants.XML_USERS_FILE_PATH);
//                serializer.write(table, result);
//                if (delFlag){
//                    log.debug(String.format(Constants.SUCCESS_DELETE_PROFILE_XML, id));
//                } else {
//                    log.info(String.format(Constants.EMPTY_GET_PROFILE, id));
//                }
//
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
//            Serializer serializer = new Persister();
//            boolean delFlag = false;
//            File source = new File(Constants.XML_EVENTS_FILE_PATH);
//            XML_EventsTable table;
//            if (source.length() > 0) {
//                table = serializer.read(XML_EventsTable.class, source);
//                List<Event> writedEvents = table.getxmlEvents();
//                for (Event event: writedEvents){
//                    if (id.equals(event.getId())){
//                        writedEvents.remove(event);
//                        delFlag = true;
//                        break;
//                    }
//                }
//                table.setEvents(writedEvents);
//                File result = new File(Constants.XML_EVENTS_FILE_PATH);
//                serializer.write(table, result);
//                if (delFlag){
//                    log.info(String.format(Constants.SUCCESS_DELETE_EVENT_XML, id));
//                } else {
//                    log.info(String.format(Constants.EMPTY_GET_EVENT, id));
//                }
//
//            }else {
//                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_EVENTS_FILE_PATH));
//            }
//        } catch (Exception ex) {
//            log.error(Constants.ERROR_DELETE_QUERY + ex.getMessage());
//        }
//    }
//
//    @Override
//    public String addPhoto(String id, String path) {
//        try {
//            Serializer serializer = new Persister();
//            File source = new File(Constants.XML_PHOTOS_FILE_PATH);
//            XML_PhotosTable table;
//
//            if (source.length() == 0) {
//                List<Photo> photos_array = new ArrayList<>();
//                String new_id = UUID.randomUUID().toString();
//                photos_array.add(new Photo(
//                        new_id,
//                        id,
//                        Constants.UTIL_EMPTY_STRING,
//                        Constants.UTIL_EMPTY_STRING,
//                        Constants.UTIL_EMPTY_STRING,
//                        Constants.UTIL_EMPTY_STRING,
//                        path,
//                        Constants.DEFAULT_RATING));
//                table = new XML_PhotosTable();
//                table.setPhotos(photos_array);
//                File result = new File(Constants.XML_PHOTOS_FILE_PATH);
//                log.info(String.format(Constants.SUCCESS_NEW_PHOTO_XML, id));
//                serializer.write(table, result);
//                return new_id;
//            } else {
//                table = serializer.read(XML_PhotosTable.class, source);
//                List<Photo> writedPhotos = table.getxmlPhotos();
//                String new_id = UUID.randomUUID().toString();
//                writedPhotos.add(new Photo(
//                        new_id,
//                        id,
//                        Constants.UTIL_EMPTY_STRING,
//                        Constants.UTIL_EMPTY_STRING,
//                        Constants.UTIL_EMPTY_STRING,
//                        Constants.UTIL_EMPTY_STRING,
//                        path,
//                        Constants.DEFAULT_RATING));
//                table.setPhotos(writedPhotos);
//                File result = new File(Constants.XML_PHOTOS_FILE_PATH);
//
//                serializer.write(table, result);
//                log.info(String.format(Constants.SUCCESS_NEW_PHOTO_XML, id));
//                return new_id;
//            }
//        } catch (Exception ex) {
//            log.error(Constants.ERROR_INSERT_QUERY + ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    public String getPhoto(String id) {
//        try{
//            Serializer serializer = new Persister();
//            File source = new File(Constants.XML_PHOTOS_FILE_PATH);
//            if (source.length() > 0) {
//                XML_PhotosTable table;
//                table = serializer.read(XML_PhotosTable.class, source);
//                Set<String> targetSet = new HashSet<String>();
//                for (Photo photo: table.getxmlPhotos()){
//                    if (!targetSet.add(String.valueOf(photo.getId()))) {
//                        return String.format(Constants.ERROR_XML_SAME_ID, Tables.PHOTOS.name().toLowerCase());
//                    }
//                    if (String.valueOf(photo.getId()).equals(id)){
//                        log.info(String.format(Constants.SUCCESS_GET_PHOTO, id));
//                        return photo.getPhotoOutput();
//                    }
//                }
//            } else {
//                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_PHOTOS_FILE_PATH));
//            }
//
//            log.info(String.format(Constants.EMPTY_GET_PHOTO, id));
//        } catch (Exception ex) {
//            log.error(String.format(Constants.ERROR_GET_PHOTO, id) + ex);
//        }
//        return null;
//    }
//
//    @Override
//    public void editPhotoById(String id, String field, String value) {
//        try {
//            Serializer serializer = new Persister();
//            File source = new File(Constants.XML_PHOTOS_FILE_PATH);
//            XML_PhotosTable table;
//            if (source.length() > 0) {
//                table = serializer.read(XML_PhotosTable.class, source);
//                List<Photo> writedPhotos = table.getxmlPhotos();
//                for (Photo photo: writedPhotos) {
//                    if (id.equals(photo.getId())){
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
//                    }
//                }
//
//                table.setPhotos(writedPhotos);
//                File result = new File(Constants.XML_PHOTOS_FILE_PATH);
//                serializer.write(table, result);
//            }else {
//                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_PHOTOS_FILE_PATH));
//            }
//        } catch (Exception ex) {
//            log.error(Constants.ERROR_UPDATE_QUERY + ex.getMessage());
//        }
//    }
//
//    @Override
//    public void deletePhotoById(String id) {
//        try {
//            Serializer serializer = new Persister();
//            boolean delFlag = false;
//            File source = new File(Constants.XML_PHOTOS_FILE_PATH);
//            XML_PhotosTable table;
//            if (source.length() > 0) {
//                table = serializer.read(XML_PhotosTable.class, source);
//                List<Photo> writedPhotos = table.getxmlPhotos();
//                for (Photo photo: writedPhotos){
//                    if (id.equals(photo.getId())){
//                        writedPhotos.remove(photo);
//                        delFlag = true;
//                        break;
//                    }
//                }
//                table.setPhotos(writedPhotos);
//                File result = new File(Constants.XML_PHOTOS_FILE_PATH);
//                serializer.write(table, result);
//                if (delFlag){
//                    log.info(String.format(Constants.SUCCESS_DELETE_PHOTO_XML, id));
//                } else {
//                    log.info(String.format(Constants.EMPTY_GET_PHOTO, id));
//                }
//
//            }else {
//                log.info(String.format(Constants.ERROR_XML_EMPTY_FILE, Constants.XML_PHOTOS_FILE_PATH));
//            }
//        } catch (Exception ex) {
//            log.error(Constants.ERROR_DELETE_QUERY + ex.getMessage());
//        }
//    }
//
//    @Override
//    public String getPortfolio(String user_id) {
//        try {
//            Serializer serializer = new Persister();
//            File source = new File(Constants.XML_PHOTOS_FILE_PATH);
//            XML_PhotosTable table;
//            String result = Constants.UTIL_EMPTY_STRING;
//            if (source.length() > 0) {
//                table = serializer.read(XML_PhotosTable.class, source);
//                List<Photo> writedPhotos = table.getxmlPhotos();
//                for (Photo photo: writedPhotos){
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
//            Serializer serializer = new Persister();
//            File source = new File(Constants.XML_PHOTOS_FILE_PATH);
//            XML_PhotosTable table;
//            if (source.length() > 0) {
//                table = serializer.read(XML_PhotosTable.class, source);
//                List<Photo> writedPhotos = table.getxmlPhotos();
//                for (Photo photo: writedPhotos){
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
