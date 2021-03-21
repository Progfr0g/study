package ru.sfedu.photosearch.providers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.utils.Database;
import ru.sfedu.photosearch.enums.EventType;
import ru.sfedu.photosearch.enums.Role;
import ru.sfedu.photosearch.enums.Tables;
import ru.sfedu.photosearch.Models.*;
import ru.sfedu.photosearch.utils.Formatter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

/**
 * Класс дата-провайдера для работы с базой данных
 */
public class DataProviderDatabase implements DataProvider {
    private static Logger log = LogManager.getLogger(DataProviderDatabase.class);
    public Database DB = new Database();

    /**
     * Создание нового профиля
     * @param name - имя пользователя
     * @param lastName - фамилия пользователя
     * @param birthDay - день рождение пользователя
     * @param dateOfRegistration - дата регистрации пользователя
     * @param role - роль пользователя
     * @param town - город проживания
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    @Override
    public Boolean createNewProfile(String name,
                                   String lastName,
                                   Date birthDay,
                                   Date dateOfRegistration,
                                   Role role,
                                   String town) {

        String query = String.format(
                Constants.INSERT_USERS_QUERY,
                name,
                lastName,
                Formatter.normalFormatDay(birthDay),
                Formatter.dateOfRegistration(dateOfRegistration),
                role,
                town);
        if (DB.connect() && (DB.insert(query) > 0) && DB.closeConnection()) {
            return true;
        }
        return false;
    }

    /**
     * Получить профиль
     * @param id - ID профиля
     * @return Объект Optional<User>, если данный ID существует
     */
    @Override
    public Optional<User> getProfile(String id) {
        String query = Constants.SELECT_PROFILE_QUERY + id;
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            Integer rows = 0;
            String[] strings = new String[rsMetaSize];
            while (rs.next()){
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
                rows++;
            }
            DB.closeConnection();
            if (rows == 0){
                log.info(String.format(Constants.EMPTY_GET_PROFILE, id));
                return Optional.empty();
            } else{
                if (strings[5].equals(Role.CUSTOMER.toString().toLowerCase())){
                    User resultUser = new User<String>(strings);
                    return Optional.of(resultUser);
                } else if (strings[5].equals(Role.PHOTOGRAPHER.toString().toLowerCase())){
                    Photographer resultUser = new Photographer<String>(strings);
                    return Optional.of(resultUser);
                }
            }
        } catch (SQLException ex) {
            log.error(String.format(Constants.ERROR_GET_PROFILE, id) + ex.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Изменить профиль по ID
     * @param id - ID профиля
     * @param field - изменяемое поле
     * @param value - новое значение поля
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    @Override
    public Boolean editProfileById(String id, String field, String value) {
        String query = String.format(Constants.UPDATE_PROFILE_QUERY, Tables.USERS.toString(), field, value) + id;
        if (DB.connect() && (DB.update(query) > 0) && DB.closeConnection()) {
            return true;
        }
        return false;
    }

    /**
     * Удаление профиля по ID
     * @param id - ID профиля
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    @Override
    public Boolean deleteProfileById(String id) {
        String query = String.format(Constants.DELETE_PROFILE_QUERY, Tables.USERS.toString()) + id;
        if (DB.connect() && (DB.delete(query) > 0) && DB.closeConnection()) {
            return true;
        }
        return false;
    }

    /**
     * Создание нового события
     * @param title - название события
     * @param description - описание события
     * @param customer - пользователь, создавший событие
     * @param eventDate - дата начала события
     * @param creationDate - дата создания события
     * @param price - цена события
     * @param quantity - кол-во часов
     * @param type - тип события
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    @Override
    public Boolean createNewEvent(String title,
                                  String description,
                                  String customer,
                                  Date eventDate,
                                  Date creationDate,
                                  Integer price,
                                  Float quantity,
                                  EventType type) {
        String query = String.format(
                Constants.INSERT_EVENTS_QUERY,
                title,
                description,
                customer,
                Formatter.normalFormatDay(eventDate),
                Formatter.dateOfRegistration(creationDate),
                price.toString(),
                quantity.toString(),
                type);
        if (DB.connect() && (DB.insert(query) > 0) && DB.closeConnection()) {
            return true;
        }
        return false;
    }

    /**
     * Получение события по ID
     * @param id - искомый ID события
     * @return Объект Optional<Event>, если данный ID существует
     */
    @Override
    public Optional<Event> getEvent(String id) {
        String query = Constants.SELECT_EVENT_QUERY + id;
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            Integer rows = 0;
            String[] strings = new String[rsMetaSize];
            while (rs.next()){
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
                rows++;
            }
            DB.closeConnection();
            if (rows == 0){
                log.info(String.format(Constants.EMPTY_GET_EVENT, id));
                return Optional.empty();
            } else{
                Optional<User> getCostumer = getProfile(strings[7]);
                User costumer = null;
                if (strings[7]!=null) {
                    costumer = getCostumer.orElse(null);
                }
                Optional<User> getExecutor = getProfile(strings[8]);
                User executor = null;
                if (strings[8]!=null) {
                    executor = getExecutor.orElse(null);
                }
                Event resultEvent = new Event<String>(strings, costumer, executor);
                return Optional.of(resultEvent);
            }
        } catch (SQLException ex) {
            log.error(String.format(Constants.ERROR_GET_EVENT, id) + ex.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Изменить событие по ID
     * @param id - ID события
     * @param field - изменяемое поле
     * @param value - новое значение поля
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    @Override
    public Boolean editEventById(String id, String field, String value) {
        String query = String.format(Constants.UPDATE_EVENT_QUERY, Tables.EVENTS.toString(), field, value) + id;
        if (DB.connect() && (DB.update(query) > 0) && DB.closeConnection()) {
            return true;
        }
        return false;
    }

    /**
     * Удаление события по ID
     * @param id - ID профиля
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    @Override
    public Boolean deleteEventById(String id) {
        String query = String.format(Constants.DELETE_EVENT_QUERY, Tables.EVENTS.toString()) + id;
        if (DB.connect() && (DB.delete(query) > 0) && DB.closeConnection()) {
            return true;
        }
        return false;
    }

    /**
     * Создание фотографии
     * @param userId - ID пользователя
     * @param path - путь к файлу
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    @Override
    public Boolean addPhoto(String userId, String path) {
        String query = String.format(Constants.INSERT_PHOTO_QUERY, Tables.PHOTOS.toString(), userId, path);
        if (DB.connect() && (DB.insert(query) > 0) && DB.closeConnection()) {
            return true;
        }
        return false;
    }

    /**
     * Получение фотографии по ID
     * @param id - искомый ID фото
     * @return Объект Optional<Photo>, если данный ID существует
     */
    @Override
    public Optional<Photo> getPhoto(String id) {
        String query = Constants.SELECT_PHOTO_QUERY + id;
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            Integer rows = 0;
            String[] strings = new String[rsMetaSize];
            while (rs.next()){
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
                rows++;
            }
            DB.closeConnection();
            if (rows == 0){
                log.info(String.format(Constants.EMPTY_GET_PHOTO, id));
                return Optional.empty();
            } else{
                Optional<User> getUser = getProfile(strings[1]);
                User user = null;
                if (strings[1]!=null) {
                    user = getUser.orElse(null);
                }
                Optional<Event> getEvent = getEvent(strings[2]);
                Event event = null;
                if (strings[2]!=null) {
                    event = getEvent.orElse(null);
                }
                Photo resultPhoto = new Photo<String>(strings, user, event);
                return Optional.of(resultPhoto);
            }
        } catch (SQLException ex) {
            log.error(String.format(Constants.ERROR_GET_PHOTO, id) + ex.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Изменить фотографию по ID
     * @param id - ID фото
     * @param field - изменяемое поле
     * @param value - новое значение поля
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    @Override
    public Boolean editPhotoById(String id, String field, String value) {
        String query = String.format(Constants.UPDATE_PHOTO_QUERY, Tables.PHOTOS.toString(), field, value) + id;
        if (DB.connect() && (DB.update(query) > 0) && DB.closeConnection()) {
            return true;
        }
        return false;
    }

    /**
     * Удаление фотографии по ID
     * @param id - ID профиля
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    @Override
    public Boolean deletePhotoById(String id) {
        String query = String.format(Constants.DELETE_PHOTO_QUERY, Tables.PHOTOS.toString())+ id;
        if (DB.connect() && (DB.delete(query) > 0) && DB.closeConnection()) {
            return true;
        }
        return false;
    }

    /**
     * Получение портфолио по ID пользователя
     * @param userId - ID пользователя
     * @return Объект Optional<ArrayList<Photo>> - все фотографии пользователя, если данный ID существует
     */
    @Override
    public Optional<ArrayList<Photo>> getPortfolio(String userId) {
        String query = Constants.SELECT_PORTFOLIO_QUERY + userId;
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            Integer rows = 0;
            String[] strings = new String[rsMetaSize];
            ArrayList<Photo> photos = new ArrayList<Photo>();
            while (rs.next()){
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
                Optional<User> getUser = getProfile(strings[1]);
                User user = null;
                if (strings[1]!=null) {
                    user = getUser.orElse(null);
                }
                Optional<Event> getEvent = getEvent(strings[2]);
                Event event = null;
                if (strings[2]!=null) {
                    event = getEvent.orElse(null);
                }
                Photo resultPhoto = new Photo<String>(strings, user, event);
                photos.add(resultPhoto);
            }
            DB.closeConnection();
            if (photos.size() == 0){
                log.info(String.format(Constants.EMPTY_GET_PORTFOLIO, userId));
                return Optional.empty();
            } else{
                return Optional.of(photos);
            }
        } catch (SQLException ex) {
            log.error(String.format(Constants.ERROR_GET_PORTFOLIO, userId) + ex.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Получение путь к файлу по ID фотографии
     * в основном, используется для просмотра фотографий методом SHOW_PHOTO в главном классе
     * @see ru.sfedu.photosearch.Main
     * @param id - ID фотографии
     * @return Объект String - путь к файлу фотографии, если ID существует
     */
    @Override
    public String getPhotoPathById(String id) {
        String query = Constants.SELECT_PHOTO_PATH_QUERY + id;
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            String result = Constants.UTIL_NEW_LINE;
            rs.next();
            if (rs.getString(1) != null) result = rs.getString(1);
            DB.closeConnection();
            if (result.equals(Constants.UTIL_NEW_LINE)){
                return String.format(Constants.EMPTY_GET_PHOTO_PATH, id);
            } else
                log.debug(String.format(Constants.SUCCESS_GET_PHOTO_PATH, id));
            return result;
        } catch (SQLException ex) {
            log.error(String.format(Constants.ERROR_GET_PHOTO_PATH, id) + ex.getMessage());
        }
        return null;
    }

    /**
     * Получение последнего записанного ID пользователя
     * @return Объект String - ID пользователя, если такой существует
     */
    @Override
    public String getLastUserId() {
        String query = Constants.SELECT_LAST_USER_QUERY;
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            Integer rows = 0;
            String[] strings = new String[rsMetaSize];
            while (rs.next()){
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
                rows++;
            }
            DB.closeConnection();
            if (rows == 0){
                log.info(Constants.EMPTY_GET_LAST_USER);
                return null;
            } else{
                User resultUser = new User<String>(strings);
                return resultUser.getId().toString();
            }
        } catch (SQLException ex) {
            log.error(Constants.ERROR_GET_LAST_PROFILE + ex.getMessage());
        }
        return null;
    }

    /**
     * Получение последнего записанного ID фотографа
     * @return Объект String - ID фотографа, если такой существует
     */
    @Override
    public String getLastPhotographerId() {
        String query = Constants.SELECT_LAST_PHOTOGRAPHER_QUERY;
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            Integer rows = 0;
            String[] strings = new String[rsMetaSize];
            while (rs.next()){
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
                rows++;
            }
            DB.closeConnection();
            if (rows == 0){
                log.info(Constants.EMPTY_GET_LAST_PHOTOGRAPHER);
                return null;
            } else{
                Photographer resultUser = new Photographer(strings);
                return resultUser.getId().toString();
            }
        } catch (SQLException ex) {
            log.error(Constants.ERROR_GET_LAST_PHOTOGRAPHER + ex.getMessage());
        }
        return null;
    }

    /**
     * Получение последнего записанного ID события
     * @return Объект String - ID события, если такой существует
     */
    @Override
    public String getLastEventId() {
        String query = Constants.SELECT_LAST_EVENT_QUERY;
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            Integer rows = 0;
            String[] strings = new String[rsMetaSize];
            while (rs.next()){
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
                rows++;
            }
            DB.closeConnection();
            if (rows == 0){
                log.info(Constants.EMPTY_GET_LAST_EVENT);
                return null;
            } else{
                Optional<User> getUser = getProfile(strings[7]);
                User costumer = null;
                if (strings[7]!=null) {
                    costumer = getUser.orElse(null);
                }
                Optional<User> getExecutor = getProfile(strings[8]);
                User executor = null;
                if (strings[8]!=null) {
                    executor = getExecutor.orElse(null);
                }
                Event resultEvent = new Event<String>(strings, costumer, executor);
                return resultEvent.getId().toString();
            }
        } catch (SQLException ex) {
            log.error(Constants.ERROR_GET_EVENT + ex.getMessage());
            return null;
        }
    }

    /**
     * Получение последнего записанного ID фотографии
     * @return Объект String - ID фотографии, если такой существует
     */
    @Override
    public String getLastPhotoId() {
        String query = Constants.SELECT_LAST_PHOTO_QUERY;
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            Integer rows = 0;
            String[] strings = new String[rsMetaSize];
            while (rs.next()){
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
                rows++;
            }
            DB.closeConnection();
            if (rows == 0){
                log.info(Constants.EMPTY_GET_LAST_PHOTO);
                return null;
            } else{
                Optional<User> getUser = getProfile(strings[1]);
                User user = null;
                if (strings[1]!=null) {
                    user = getUser.orElse(null);
                }
                Optional<Event> getEvent = getEvent(strings[2]);
                Event event = null;
                if (strings[2]!=null) {
                    event = getEvent.orElse(null);
                }
                Photo resultPhoto = new Photo<String>(strings, user, event);
                return resultPhoto.getId().toString();
            }
        } catch (SQLException ex) {
            log.error(Constants.ERROR_GET_LAST_PHOTO + ex.getMessage());
        }
        return null;
    }

    /**
     * Получение объектов всех пользователей
     * используется в поиске searchUsers
     * @return Объект Optional<ArrayList<User>> - все объекты пользователей
     */
    @Override
    public  Optional<ArrayList<User>> getAllUsers() {
        String query = Constants.SELECT_ALL_USERS;
        ArrayList<User> resultUsers = new ArrayList<User>();
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            while (rs.next()){
                String[] strings = new String[rsMetaSize];
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
                User resultUser = new User<String>(strings);
                resultUsers.add(resultUser);
            }
            DB.closeConnection();
            if (resultUsers.size() == 0){
                log.info(Constants.EMPTY_GET_ALL_USERS);
                return Optional.empty();
            } else{
                return Optional.of(resultUsers);
            }
        } catch (SQLException ex) {
            log.error(Constants.ERROR_GET_ALL_PROFILES + ex.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Получение объектов всех событий
     * используется в поиске searchEvents
     * @return Объект Optional<ArrayList<Event>> - все объекты событий
     */
    @Override
    public  Optional<ArrayList<Event>> getAllEvents() {
        String query = Constants.SELECT_ALL_EVENTS;
        ArrayList<Event> resultEvents = new ArrayList<Event>();
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            while (rs.next()){
                String[] strings = new String[rsMetaSize];
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
                Optional<User> getCustomer = getProfile(strings[7]);
                User customer = null;
                if (strings[7]!=null) {
                    customer = getCustomer.orElse(null);
                }
                Optional<User> getExecutor = getProfile(strings[8]);
                User executor = null;
                if (strings[8]!=null) {
                    executor = getExecutor.orElse(null);
                }
                Event resultEvent = new Event<String>(strings, customer, executor);
                resultEvents.add(resultEvent);
            }
            DB.closeConnection();
            if (resultEvents.size() == 0){
                log.info(Constants.EMPTY_GET_ALL_EVENTS);
                return Optional.empty();
            } else{
                return Optional.of(resultEvents);
            }
        } catch (SQLException ex) {
            log.error(Constants.ERROR_GET_ALL_EVENTS + ex.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Получение объектов всех фотографий
     * используется в поиске searchPhoto
     * @return Объект Optional<ArrayList<Photo>> - все объекты фотографий
     */
    @Override
    public Optional<ArrayList<Photo>> getAllPhotos() {
        String query = Constants.SELECT_ALL_PHOTOS;
        ArrayList<Photo> resultPhotos = new ArrayList<Photo>();
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            while (rs.next()){
                String[] strings = new String[rsMetaSize];
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
                Optional<User> getUser = getProfile(strings[1]);
                User user = null;
                if (strings[1]!=null) {
                    user = getUser.orElse(null);
                }
                Optional<Event> getEvent = getEvent(strings[2]);
                Event event = null;
                if (strings[2]!=null) {
                    event = getEvent.orElse(null);
                }
                Photo resultPhoto = new Photo<String>(strings, user, event);
                resultPhotos.add(resultPhoto);
            }
            DB.closeConnection();
            if (resultPhotos.size() == 0){
                log.info(Constants.EMPTY_GET_ALL_PHOTOS);
                return Optional.empty();
            } else{
                return Optional.of(resultPhotos);
            }
        } catch (SQLException ex) {
            log.error(Constants.ERROR_GET_ALL_PHOTOS + ex.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Поиск среди всех пользователей по полю и значению
     * @param field - искомое поле
     * @param value - искомое значение
     * @return Объект Optional<ArrayList<User>> - все объекты пользоватей, соответствующие критерию поиска
     */
    @Override
    public Optional<ArrayList<User>> searchUsers(String field, String value) {
        String query = String.format(Constants.SELECT_USER_SEARCH, field, value);
        ArrayList<User> resultUsers = new ArrayList<User>();
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            while (rs.next()){
                String[] strings = new String[rsMetaSize];
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
                User resultUser = new User<String>(strings);
                resultUsers.add(resultUser);
            }
            DB.closeConnection();
            if (resultUsers.size() == 0){
                log.info(Constants.EMPTY_GET_USERS_SEARCH);
                return Optional.empty();
            } else{
                return Optional.of(resultUsers);
            }
        } catch (SQLException ex) {
            log.error(Constants.ERROR_GET_PROFILES_SEARCH + ex.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Поиск среди всех фотографов по полю и значению
     * @param field - искомое поле
     * @param value - искомое значение
     * @return Объект Optional<ArrayList<User>> - все объекты фотографов, соответствующие критерию поиска
     */
    @Override
    public Optional<ArrayList<User>> searchPhotographers(String field, String value) {
        String query = String.format(Constants.SELECT_PHOTOGRAPHER_SEARCH, field, value);
        ArrayList<User> resultUsers = new ArrayList<User>();
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            while (rs.next()){
                String[] strings = new String[rsMetaSize];
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
                User resultUser = new Photographer<String>(strings);
                resultUsers.add(resultUser);
            }
            DB.closeConnection();
            if (resultUsers.size() == 0){
                log.info(Constants.EMPTY_GET_PHOTOGRAPHERS_SEARCH);
                return Optional.empty();
            } else{
                return Optional.of(resultUsers);
            }
        } catch (SQLException ex) {
            log.error(Constants.ERROR_GET_PROFILES_SEARCH + ex.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Поиск среди всех событий по полю и значению
     * @param field - искомое поле
     * @param value - искомое значение
     * @return Объект Optional<ArrayList<Event>> - все объекты событий, соответствующие критерию поиска
     */
    @Override
    public Optional<ArrayList<Event>> searchEvents(String field, String value) {
        String query = String.format(Constants.SELECT_EVENT_SEARCH, field, value);
        ArrayList<Event> resultEvents = new ArrayList<Event>();
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            while (rs.next()){
                String[] strings = new String[rsMetaSize];
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
                Optional<User> getCustomer = getProfile(strings[7]);
                User customer = null;
                if (strings[7]!=null) {
                    customer = getCustomer.orElse(null);
                }
                Optional<User> getExecutor = getProfile(strings[8]);
                User executor = null;
                if (strings[8]!=null) {
                    executor = getExecutor.orElse(null);
                }
                Event resultEvent = new Event<String>(strings, customer, executor);
                resultEvents.add(resultEvent);
            }
            DB.closeConnection();
            if (resultEvents.size() == 0){
                log.info(Constants.EMPTY_GET_EVENTS_SEARCH);
                return Optional.empty() ;
            } else{
                return Optional.of(resultEvents);
            }
        } catch (SQLException ex) {
            log.error(Constants.ERROR_GET_EVENTS_SEARCH + ex.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Создание комментария к фотографии
     * @param userId - ID пользователя, который создает комментарий
     * @param photoId - ID фотографии, к которой адресован комментарий
     * @param comment - текст комментария
     * @param date - дата создания комментария
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    @Override
    public Boolean addComment(String userId, String photoId, String comment, Date date) {
        String query = String.format(Constants.INSERT_COMMENTS_QUERY, Tables.COMMENTS.toString(),
                userId, photoId, comment, Formatter.dateOfRegistration(date));
        if (DB.connect() && (DB.insert(query) > 0) && DB.closeConnection()) {
            return true;
        }
        return false;
    }

    /**
     * Получение всех комментариев по ID фотографии
     * @param photoId - ID фотографии
     * @return Объект Optional<ArrayList<Comment>> - все объекты комментариев к данному фото
     */
    @Override
    public Optional<ArrayList<Comment>> getAllCommentsById(String photoId) {
        String query = String.format(Constants.SELECT_ALL_COMMENTS, photoId);
        ArrayList<Comment> resultComments = new ArrayList<Comment>();
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            while (rs.next()){
                String[] strings = new String[rsMetaSize];
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
                Optional<User> getUser = getProfile(strings[1]);
                User user = null;
                if (strings[1]!=null) {
                    user = getUser.orElse(null);
                }
                Optional<Photo> getPhoto = getPhoto(strings[2]);
                Photo photo = null;
                if (strings[2]!=null) {
                    photo = getPhoto.orElse(null);
                }
                Comment resultComment = new Comment(strings, user, photo);
                resultComments.add(resultComment);
            }
            DB.closeConnection();
            if (resultComments.size() == 0){
                log.info(Constants.EMPTY_GET_ALL_COMMENTS);
                return Optional.empty();
            } else{
                return Optional.of(resultComments);
            }
        } catch (SQLException ex) {
            log.error(Constants.ERROR_GET_ALL_COMMENTS + ex.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Создание оценки к фотографии
     * @param userId - ID пользователя, который ставит оценку
     * @param photoId - ID фотографии, к которой адресована оценка
     * @param rate - оценка
     * @param date - дата создания оценки
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    @Override
    public Boolean addRate(String userId, String photoId, Integer rate, Date date) {
        String query = String.format(Constants.INSERT_RATES_QUERY, Tables.RATES.toString(),
                userId, photoId, rate, Formatter.dateOfRegistration(date));
        if (DB.connect() && (DB.insert(query) > 0) && DB.closeConnection()) {
            return true;
        }
        return false;
    }

    /**
     * Получение всех оценок для ID фотографии
     * @param photoId - ID фотографии
     * @return Объект Optional<ArrayList<Rate>> - все объекты оценок к данному фото
     */
    @Override
    public Optional<ArrayList<Rate>> getAllRatesById(String photoId) {
        String query = String.format(Constants.SELECT_ALL_RATES, photoId);
        ArrayList<Rate> resultRates = new ArrayList<Rate>();
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            while (rs.next()){
                String[] strings = new String[rsMetaSize];
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
                Optional<User> getUser = getProfile(strings[1]);
                User user = null;
                if (strings[1]!=null) {
                    user = getUser.orElse(null);
                }
                Optional<Photo> getPhoto = getPhoto(strings[2]);
                Photo photo = null;
                if (strings[2]!=null) {
                    photo = getPhoto.orElse(null);
                }
                Rate resultRate = new Rate(strings, user, photo);
                resultRates.add(resultRate);
            }
            DB.closeConnection();
            if (resultRates.size() == 0){
                log.info(Constants.EMPTY_GET_ALL_RATES);
                return Optional.empty();
            } else{
                return Optional.of(resultRates);
            }
        } catch (SQLException ex) {
            log.error(Constants.ERROR_GET_ALL_RATES + ex.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Создание отзыва к фотографу
     * @param userId - ID пользователя, который оставляет отзыва
     * @param photographerId - ID фотографии, к которой адресован отзыв
     * @param rate - оценка
     * @param text - текст отзыва
     * @param date - дата создания отзыва
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    @Override
    public Boolean addFeedback(String userId, String photographerId, Integer rate, String text, Date date) {
        String query = String.format(Constants.INSERT_FEEDBACKS_QUERY, Tables.FEEDBACKS.toString(),
                userId, photographerId, rate, text, Formatter.dateOfRegistration(date));
        if (DB.connect() && (DB.insert(query) > 0) && DB.closeConnection()) {
            return true;
        }
        return false;
    }

    /**
     * Получение всех отзывов по ID фотографа
     * @param photographerId - ID фотографа
     * @return Объект Optional<ArrayList<Feedback>> - все объекты отзывов данного фотографа
     */
    @Override
    public Optional<ArrayList<Feedback>> getAllFeedbacksById(String photographerId) {
        String query = String.format(Constants.SELECT_ALL_FEEDBACKS, photographerId);
        ArrayList<Feedback> resultFeedbacks = new ArrayList<Feedback>();
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            while (rs.next()){
                String[] strings = new String[rsMetaSize];
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
                Optional<User> getUser = getProfile(strings[1]);
                User user = null;
                if (strings[1]!=null) {
                    user = getUser.orElse(null);
                }
                Optional<User> getPhotographer = getProfile(strings[2]);
                User photographer = null;
                if (strings[2]!=null) {
                    user = getPhotographer.orElse(null);
                }
                Feedback resultFeedback = new Feedback(strings, user, photographer);
                resultFeedbacks.add(resultFeedback);
            }
            DB.closeConnection();
            if (resultFeedbacks.size() == 0){
                log.info(Constants.EMPTY_GET_ALL_FEEDBACKS);
                return Optional.empty();
            } else{
                return Optional.of(resultFeedbacks);
            }
        } catch (SQLException ex) {
            log.error(Constants.ERROR_GET_ALL_FEEDBACKS + ex.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Создание заявки к событию
     * @param userId - ID пользователя (заказчика)
     * @param eventId - ID события, на который идет заявка
     * @param photographerId - ID фотографа, которой откликается на заявку
     * @param isActive - состояние заявки
     * @param creationDate - дата создания заявки
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    @Override
    public Boolean createOffer(String userId, String eventId, String photographerId, Boolean isActive, Date creationDate) {
        String query = String.format(Constants.INSERT_OFFERS_QUERY, Tables.OFFERS.toString(),
                userId, eventId, photographerId, isActive, Formatter.dateOfRegistration(creationDate));
        if (DB.connect() && (DB.insert(query) > 0) && DB.closeConnection()) {
            return true;
        }
        return false;
    }

    /**
     * Получение всех заявок по ID события
     * @param eventId - ID события
     * @return Объект Optional<ArrayList<Offer>> - все объекты отзывов данного события
     */
    @Override
    public Optional<ArrayList<Offer>> getAllOffersById(String eventId) {
        String query = String.format(Constants.SELECT_ALL_OFFERS, eventId);
        ArrayList<Offer> resultOffers = new ArrayList<Offer>();
        try {
            DB.connect();
            ResultSet rs = DB.select(query);
            int rsMetaSize = rs.getMetaData().getColumnCount();
            while (rs.next()){
                String[] strings = new String[rsMetaSize];
                for (int i = 0; i < rsMetaSize; i++) {
                    strings[i] = rs.getString(i+1);
                }
                Optional<User> getUser = getProfile(strings[1]);
                User user = null;
                if (strings[1]!=null) {
                    user = getUser.orElse(null);
                }
                Optional<Event> getEvent = getEvent(strings[2]);
                Event event = null;
                if (strings[2]!=null) {
                    event = getEvent.orElse(null);
                }
                Optional<User> getPhotographer = getProfile(strings[3]);
                User photographer = null;
                if (strings[3]!=null) {
                    user = getPhotographer.orElse(null);
                }
                Offer resultOffer = new Offer(strings, user, event, photographer);
                resultOffers.add(resultOffer);
            }
            DB.closeConnection();
            if (resultOffers.size() == 0){
                log.info(Constants.EMPTY_GET_ALL_OFFERS);
                return Optional.empty();
            } else{
                return Optional.of(resultOffers);
            }
        } catch (SQLException ex) {
            log.error(Constants.ERROR_GET_ALL_OFFERS + ex.getMessage());
        }
        return Optional.empty();
    }


}
