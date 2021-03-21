package ru.sfedu.photosearch.Models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Element;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.utils.Formatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Класс Offer
 * используется для создания заявки на выполнение события
 * @param <T>
 */
public class Offer<T> {
    public static final Logger log = LogManager.getLogger(Offer.class);
    @Element
    private T id;
    @Element
    private User user;
    @Element
    private Event event;
    @Element
    private User photographer;
    @Element
    private Boolean isActive;
    @Element
    private Date date;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() { return event; }

    public void setEvent(Event event) { this.event = event; }

    public User getPhotographer() {
        return photographer;
    }

    public void setPhotographer(User photographer) {
        this.photographer = photographer;
    }

    public Boolean getActive() { return isActive; }

    public void setActive(Boolean active) { isActive = active; }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Offer() {
    }

    /**
     * Конструктор создания заявки (предложения)
     * @param id - новый ID для создаваемой заяки, который будет записан (не используется в DataProviderDB)
     * @param user - пользователь, который создал заявку
     * @param event - событие пользователя
     * @param photographer - фотограф, исполнитель
     * @param isActive - статус заявки (активна\неактивна)
     * @param date - дата создания заявки
     */
    public Offer(T id, User user,Event event, User photographer, Boolean isActive, Date date) {
        this.id = id;
        this.user = user;
        this.event = event;
        this.photographer = photographer;
        this.isActive = isActive;
        this.date = date;
    }

    /**
     * Используется для проверки на null значение различных типов объектов
     * @param value - входящее значение
     * @return если объект == null, выходящее значение - '' (пустая строка)
     *         если объект != null, выходящее значение - значение объекта
     */
    public String checkNull(String value){
        if (value == null){
            return Constants.UTIL_EMPTY_STRING;
        } else {
            return value;
        }
    }
    /**
     * Используется для проверки на null значение объектов типа User
     * @see User
     * @param value - входящее знаечение
     * @return если объект == null, выходящее значение - '' (пустая строка)
     *         если объект != null, выходящее значение - ID пользователя
     */
    public String checkNullUser(User value){
        if (value == null){
            return Constants.UTIL_EMPTY_STRING;
        } else {
            String result = value.getId().toString();
            return result;
        }
    }
    /**
     * Используется для проверки на null значение объектов типа Event
     * @see Event
     * @param value - входящее знаечение
     * @return если объект == null, выходящее значение - '' (пустая строка)
     *         если объект != null, выходящее значение - ID фотографии
     */
    public String checkNullEvent(Event value){
        if (value == null){
            return Constants.UTIL_EMPTY_STRING;
        } else {
            String result = value.getId().toString();
            return result;
        }
    }
    /**
     * Используется для финального вывода в коммандную строку
     * @return строка со всеми значениями объекта Offer (null значения заменяются пустой строкой)
     */
    public String getOfferOutput(){
        String result = String.format(Constants.XML_OFFERS_OUTPUT,
                checkNull(getId().toString()),
                checkNullUser(getUser()),
                checkNullEvent(getEvent()),
                checkNullUser(getPhotographer()),
                checkNull(getActive().toString()),
                checkNull(getDate().toString()));
        return result;
    }
    /**
     * Используется для конвертирования объекта в строку для записи в csv-файл
     * @return строка со всеми значениями объекта Offer (null-значения заменяются пустой строкой)
     */
    public String getCSVOfferOutput(){
        String result = String.format(Constants.CSV_OFFERS_OUTPUT,
                checkNull(getId().toString()),
                checkNullUser(getUser()),
                checkNullEvent(getEvent()),
                checkNullUser(getPhotographer()),
                checkNull(getActive().toString()),
                checkNull(getDate().toString()));
        return result;
    }
    /**
     * Используется для конвертирования объекта из строки csv-файла
     * @return List со всеми объектами Offer в файле
     */
    public static List<Offer> convertFromCSV(List<String[]> data, List<User> costumers, List<User> photographers,
                                             List<Event> events){
        try {
            List<Offer> offers = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i)[5] != null){
                    data.get(i)[5] = Formatter.birthDayToDB(Formatter.csvDateFromString(data.get(i)[5]));
                }
                offers.add(new Offer(data.get(i), costumers.get(i), events.get(i), photographers.get(i)));
            }
            return offers;
        }catch (Exception ex){
            log.error(Constants.ERROR_OFFER_CONVERT + ex);
            return null;
        }
    }

    /**
     * Дополнительный конструктор Offer для создания объекта из csv-файла
     * @param data - данные (строка) из csv-файла
     * @param user - пользователь (объект User)
     * @param event - событие (объект Event)
     * @param photographer - фотограф (объект Photographer)
     */
    public Offer(String[] data, User user, Event event, User photographer){
        this.id = (T) data[0];
        this.user = user;
        this.event = event;
        this.photographer = photographer;
        this.isActive = true;
        this.date = Formatter.birthDayFromDB(data[5]);
    }
}
