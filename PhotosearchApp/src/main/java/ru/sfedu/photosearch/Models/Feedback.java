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
 * Класс Feedback
 * используется для создания отзыва фотографам
 * @param <T>
 */
public class Feedback<T> {
    public static final Logger log = LogManager.getLogger(Feedback.class);
    @Element
    private T id;
    @Element
    private User user;
    @Element
    private User photographer;
    @Element
    private Integer rate;
    @Element
    private String text;
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

    public User getPhotographer() {
        return photographer;
    }

    public void setPhotographer(User photographer) {
        this.photographer = photographer;
    }

    public Integer getRate() { return rate; }

    public void setRate(Integer rate) { this.rate = rate; }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }



    public Feedback() {
    }

    /**
     * Конструктор создания отзыва
     * @param id - новый ID для создаваемого отзыва, который будет записан (не используется в DataProviderDB)
     * @param user - пользователь, который создал отзыв
     * @param photographer - фотограф, которому отзыв адресован
     * @param rate - оценка
     * @param text - текст отзыва
     * @param date - дата создания отзыва
     */
    public Feedback(T id, User user, User photographer, Integer rate, String text, Date date) {
        this.id = id;
        this.user = user;
        this.photographer = photographer;
        this.text = text;
        this.rate = rate;
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
     * Используется для финального вывода в коммандную строку
     * @return строка со всеми значениями объекта Feedback (null значения заменяются пустой строкой)
     */
    public String getFeedbackOutput(){
        String result = String.format(Constants.XML_FEEDBACKS_OUTPUT,
                checkNull(getId().toString()),
                checkNullUser(getUser()),
                checkNullUser(getPhotographer()),
                checkNull(getRate().toString()),
                checkNull(getText()),
                checkNull(getDate().toString()));
        return result;
    }
    /**
     * Используется для конвертирования объекта в строку для записи в csv-файл
     * @return строка со всеми значениями объекта Feedback (null-значения заменяются пустой строкой)
     */
    public String getCSVFeedbackOutput(){
        String result = String.format(Constants.CSV_FEEDBACKS_OUTPUT,
                checkNull(getId().toString()),
                checkNullUser(getUser()),
                checkNullUser(getPhotographer()),
                checkNull(getRate().toString()),
                checkNull(getText()),
                checkNull(getDate().toString()));
        return result;
    }
    /**
     * Используется для конвертирования объекта из строки csv-файла
     * @return List со всеми объектами Feedback в файле
     */
    public static List<Feedback> convertFromCSV(List<String[]> data, List<User> costumers, List<User> photographers){
        try {
            List<Feedback> feedbacks = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i)[5] != null){
                    data.get(i)[5] = Formatter.birthDayToDB(Formatter.csvDateFromString(data.get(i)[5]));
                }
                feedbacks.add(new Feedback(data.get(i), costumers.get(i), photographers.get(i)));
            }
            return feedbacks;
        }catch (Exception ex){
            log.error(Constants.ERROR_FEEDBACK_CONVERT + ex);
            return null;
        }
    }
    /**
     * Дополнительный конструктор Feedback для создания объекта из csv-файла
     * @param data - данные (строка) из csv-файла
     * @param user - пользователь (объект User)
     * @param photographer - фотограф (объект User)
     */
    public Feedback(String[] data, User user, User photographer){
        this.id = (T) data[0];
        this.user = user;
        this.photographer = photographer;
        this.rate = Integer.parseInt(data[3]);
        this.text = data[4];
        this.date = Formatter.birthDayFromDB(data[5]);
    }
}
