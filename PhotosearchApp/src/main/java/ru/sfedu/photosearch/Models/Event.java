package ru.sfedu.photosearch.Models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.photosearch.Constants;
import org.simpleframework.xml.Element;
import ru.sfedu.photosearch.enums.EventStatus;
import ru.sfedu.photosearch.enums.EventType;
import ru.sfedu.photosearch.utils.Formatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Класс Event
 * используется для создания событий (предложений) в приложении
 * @param <T>
 */
public class Event<T> {
    public static final Logger log = LogManager.getLogger(Event.class);
    @Element
    private T id;
    @Element
    private String title;
    @Element
    private String description;
    @Element
    private Date date;
    @Element
    private Date creationDate;
    @Element
    private Integer price;
    @Element
    private Float quantity;
    @Element
    private User costumer;
    @Element(required = false)
    private User executor;
    @Element
    private EventStatus status;
    @Element
    private EventType type;

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public User getCostumer() {
        return costumer;
    }

    public void setCostumer(User costumer) {
        this.costumer = costumer;
    }

    public User getExecutor() {
        return executor;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public Event() { super();
    }

    /**
     * Конструктор создания события
     * @param id - новый ID для создаваемого события, который будет записан (не используется в DataProviderDB)
     * @param title - название события
     * @param description - описание события
     * @param date - дата начала события
     * @param creationDate - дата создания события
     * @param price - цена события за единицу времени
     * @param quantity - количество единиц времени (часы), которое нужно для осуществления события
     * @param costumer - пользователь (заказчик)
     * @param status - статус события
     * @param type - тип события
     */
    public Event(T id, String title, String description, Date date, Date creationDate, Integer price, Float quantity, User costumer, EventStatus status, EventType type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.creationDate = creationDate;
        this.price = price;
        this.quantity = quantity;
        this.costumer = costumer;
        this.status = status;
        this.type = type;
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
            return value.getId().toString();
        }
    }
    /**
     * Используется для финального вывода в коммандную строку
     * @return строка со всеми значениями объекта Event (null значения заменяются пустой строкой)
     */
    public String getEventOutput(){
        String result = String.format(Constants.XML_EVENTS_OUTPUT,
                checkNull(getId().toString()),
                checkNull(getTitle()),
                checkNull(getDescription()),
                checkNull(getDate().toString()),
                checkNull(getCreationDate().toString()),
                checkNull(getPrice().toString()),
                checkNull(getQuantity().toString()),
                checkNullUser(getCostumer()),
                checkNullUser(getExecutor()),
                checkNull(getStatus().toString().toLowerCase()),
                checkNull(getType().toString().toLowerCase()));
        return result;
    }
    /**
     * Используется для конвертирования объекта из строки csv-файла
     * @return List со всеми объектами Event в файле
     */
    public String getCSVEventOutput(){
        String result = String.format(Constants.CSV_EVENTS_OUTPUT,
                checkNull(getId().toString()),
                checkNull(getTitle()),
                checkNull(getDescription()),
                checkNull(getDate().toString()),
                checkNull(getCreationDate().toString()),
                checkNull(getPrice().toString()),
                checkNull(getQuantity().toString()),
                checkNullUser(getCostumer()),
                checkNullUser(getExecutor()),
                checkNull(getStatus().toString().toLowerCase()),
                checkNull(getType().toString().toLowerCase()));
        return result;
    }

    /**
     * Используется для конвертирования объекта из строки csv-файла
     * @return List со всеми объектами Event в файле
     */
    public static List<Event> convertFromCSV(List<String[]> data, List<User> costumers, List<User> executors){
        try {
            List<Event> events = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i)[3] != null){
                    data.get(i)[3] = Formatter.birthDayToDB(Formatter.csvDateFromString(data.get(i)[3]));
                }
                if (data.get(i)[4] != null){
                    data.get(i)[4] = Formatter.birthDayToDB(Formatter.csvDateFromString(data.get(i)[4]));
                }
                events.add(new Event(data.get(i), costumers.get(i), executors.get(i)));
            }
            return events;
        }catch (Exception ex){
            log.error(Constants.ERROR_EVENT_CONVERT + ex);
        }
        return null;
    }

    /**
     * Дополнительный конструктор Event для создания объекта из csv-файла
     * @param data - данные (строка) из csv-файла
     * @param costumer - пользователь (объект User)
     * @param executor - исполнитель (объект User)
     */
    public Event(String[] data, User costumer, User executor){
        this.id = (T) data[0];
        this.title = data[1];
        this.description = data[2];
        this.date = Formatter.birthDayFromDB(data[3]);
        this.creationDate = Formatter.birthDayFromDB(data[4]);
        this.price = Integer.parseInt(data[5]);
        this.quantity = Float.parseFloat(data[6]);
        this.costumer = costumer;
        this.executor = executor;
        this.status = EventStatus.valueOf(data[9].toUpperCase());
        this.type = EventType.valueOf(data[10].toUpperCase());
    }

}
