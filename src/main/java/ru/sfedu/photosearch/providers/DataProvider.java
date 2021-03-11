package ru.sfedu.photosearch.providers;

import ru.sfedu.photosearch.enums.EventType;
import ru.sfedu.photosearch.enums.Role;
import ru.sfedu.photosearch.Models.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

/**
 * Интерфейс дата-провайдера с основными функциями приложения
 */
public interface DataProvider{
    /**
     * Создание нового профиля
     * @param name - имя пользователя
     * @param lastName - фамилия пользователя
     * @param birthDay - день рождения пользователя
     * @param dateOfRegistration - дата регистрации пользователя
     * @param role - роль пользователя
     * @param town - город проживания
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
     Boolean createNewProfile(
             String name,
             String lastName,
             Date birthDay,
             Date dateOfRegistration,
             Role role,
             String town);

    /**
     * Получить профиль
     * @param id - ID профиля
     * @return Объект Optional<User>, если данный ID существует
     */
    Optional<User> getProfile(
            String id);

    /**
     * Изменить профиль по ID
     * @param id - ID профиля
     * @param field - изменяемое поле
     * @param value - новое значение поля
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    Boolean editProfileById(String id, String field, String value);

    /**
     * Удаление профиля по ID
     * @param id - ID профиля
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    Boolean deleteProfileById(String id);

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
    Boolean createNewEvent(
            String title,
            String description,
            String customer,
            Date eventDate,
            Date creationDate,
            Integer price,
            Float quantity,
            EventType type);

    /**
     * Получение события по ID
     * @param id - искомый ID события
     * @return Объект Optional<Event>, если данный ID существует
     */
    Optional<Event> getEvent(
            String id);

    /**
     * Изменить событие по ID
     * @param id - ID события
     * @param field - изменяемое поле
     * @param value - новое значение поля
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    Boolean editEventById(String id, String field, String value);

    /**
     * Удаление события по ID
     * @param id - ID профиля
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    Boolean deleteEventById(String id);

    /**
     * Создание фотографии
     * @param id - ID пользователя
     * @param path - путь к файлу
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    Boolean addPhoto(String id, String path);

    /**
     * Получение фотографии по ID
     * @param id - искомый ID фото
     * @return Объект Optional<Photo>, если данный ID существует
     */
    Optional<Photo> getPhoto(String id);

    /**
     * Изменить фотографию по ID
     * @param id - ID фото
     * @param field - изменяемое поле
     * @param value - новое значение поля
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    Boolean editPhotoById(String id, String field, String value);

    /**
     * Удаление фотографии по ID
     * @param id - ID фото
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    Boolean deletePhotoById(String id);

    /**
     * Получение портфолио по ID пользователя
     * @param userId - ID пользователя
     * @return Объект Optional<ArrayList<Photo>> - все фотографии пользователя, если данный ID существует
     */
    Optional<ArrayList<Photo>> getPortfolio(String userId);

    /**
     * Получение путь к файлу по ID фотографии
     * в основном, используется для просмотра фотографий методом SHOW_PHOTO в главном классе
     * @see ru.sfedu.photosearch.Main
     * @param id - ID фотографии
     * @return Объект String - путь к файлу фотографии, если ID существует
     */
    String getPhotoPathById(String id);

    /**
     * Получение последнего записанного ID пользователя
     * @return Объект String - ID пользователя, если такой существует
     */
    String getLastUserId();
    /**
     * Получение последнего записанного ID события
     * @return Объект String - ID события, если такой существует
     */
    String getLastEventId();
    /**
     * Получение последнего записанного ID фотографии
     * @return Объект String - ID фотографии, если такой существует
     */
    String getLastPhotoId();
    /**
     * Получение последнего записанного ID фотографа
     * @return Объект String - ID фотографа, если такой существует
     */
    String getLastPhotographerId();
    /**
     * Получение объектов всех пользователей
     * используется в поиске searchUsers
     * @return Объект Optional<ArrayList<User>> - все объекты пользователей
     */
    Optional<ArrayList<User>> getAllUsers();
    /**
     * Получение объектов всех событий
     * используется в поиске searchEvents
     * @return Объект Optional<ArrayList<Event>> - все объекты событий
     */
    Optional<ArrayList<Event>> getAllEvents();
    /**
     * Получение объектов всех фотографий
     * используется в поиске searchPhoto
     * @return Объект Optional<ArrayList<Photo>> - все объекты фотографий
     */
    Optional<ArrayList<Photo>> getAllPhotos();

    /**
     * Создание комментария к фотографии
     * @param userId - ID пользователя, который создает комментарий
     * @param photoId - ID фотографии, к которой адресован комментарий
     * @param comment - текст комментария
     * @param date - дата создания комментария
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    Boolean addComment(String userId, String photoId, String comment, Date date);

    /**
     * Получение всех комментариев по ID фотографии
     * @param photoId - ID фотографии
     * @return Объект Optional<ArrayList<Comment>> - все объекты комментариев к данному фото
     */
    Optional<ArrayList<Comment>> getAllCommentsById(String photoId);

    /**
     * Создание оценки к фотографии
     * @param userId - ID пользователя, который ставит оценку
     * @param photoId - ID фотографии, к которой адресована оценка
     * @param rate - оценка
     * @param date - дата создания оценки
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    Boolean addRate(String userId, String photoId, Integer rate, Date date);

    /**
     * Получение всех оценок для ID фотографии
     * @param photoId - ID фотографии
     * @return Объект Optional<ArrayList<Rate>> - все объекты оценок к данному фото
     */
    Optional<ArrayList<Rate>> getAllRatesById(String photoId);

    /**
     * Создание отзыва к фотографу
     * @param userId - ID пользователя, который оставляет отзыв
     * @param photographerId - ID фотографии, к которой адресован отзыв
     * @param rate - оценка
     * @param text - текст отзыва
     * @param creationDate - дата создания отзыва
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    Boolean addFeedback(String userId,
                        String photographerId,
                        Integer rate,
                        String text,
                        Date creationDate);

    /**
     * Получение всех отзывов по ID фотографа
     * @param photographerId - ID фотографа
     * @return Объект Optional<ArrayList<Feedback>> - все объекты отзывов данного фотографа
     */
    Optional<ArrayList<Feedback>> getAllFeedbacksById(String photographerId);

    /**
     * Создание заявки к событию
     * @param userId - ID пользователя (заказчика)
     * @param eventId - ID события, на которое идет заявка
     * @param photographerId - ID фотографа, которой откликается на заявку
     * @param isActive - состояние заявки
     * @param creationDate - дата создания заявки
     * @return Boolean: true - метод выполнен успешно
     *                  false - метод выполнен с ошибками
     */
    Boolean createOffer(String userId,
                        String eventId,
                        String photographerId,
                        Boolean isActive,
                        Date creationDate);

    /**
     * Получение всех заявок по ID события
     * @param eventId - ID события
     * @return Объект Optional<ArrayList<Offer>> - все объекты заявок данного события
     */
    Optional<ArrayList<Offer>> getAllOffersById(String eventId);

    /**
     * Поиск среди всех пользователей по полю и значению
     * @param field - искомое поле
     * @param value - искомое значение
     * @return Объект Optional<ArrayList<User>> - все объекты пользоватей, соответствующие критерию поиска
     */
    Optional<ArrayList<User>> searchUsers(String field,
                                          String value);

    /**
     * Поиск среди всех фотографов по полю и значению
     * @param field - искомое поле
     * @param value - искомое значение
     * @return Объект Optional<ArrayList<User>> - все объекты фотографов, соответствующие критерию поиска
     */
    Optional<ArrayList<User>> searchPhotographers(String field, String value);

    /**
     * Поиск среди всех событий по полю и значению
     * @param field - искомое поле
     * @param value - искомое значение
     * @return Объект Optional<ArrayList<Event>> - все объекты событий, соответствующие критерию поиска
     */
    Optional<ArrayList<Event>> searchEvents(String field, String value);

}
