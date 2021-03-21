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
 * Класс Comment
 * используется для создания комментариев и оценок к фотографиям
 * @param <T>
 */
public class Comment<T> {
    public static final Logger log = LogManager.getLogger(Comment.class);
    @Element
    private T id;
    @Element
    private User user;
    @Element
    private Photo photo;
    @Element
    private String text;
    @Element
    private Integer rate;
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

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getRate() { return rate;}

    public void setRate(Integer rate) { this.rate = rate;}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Comment() {
    }

    /**
     * Конструктор создания комментария
     * @param id - новый ID для создаваемого комментария, который будет записан (не используется в DataProviderDB)
     * @param user - пользователь, который создал комментарий
     * @param photo - фотография, к которому создавался комментарий
     * @param text - текст комментария
     * @param date - дата создания комментария
     */
    public Comment(T id, User user, Photo photo, String text, Integer rate, Date date) {
        this.id = id;
        this.user = user;
        this.photo = photo;
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
     * Используется для проверки на null значение объектов типа Photo
     * @see Photo
     * @param value - входящее знаечение
     * @return если объект == null, выходящее значение - '' (пустая строка)
     *         если объект != null, выходящее значение - ID фотографии
     */
    public String checkNullPhoto(Photo value){
        if (value == null){
            return Constants.UTIL_EMPTY_STRING;
        } else {
            String result = value.getId().toString();
            return result;
        }
    }

    /**
     * Используется для финального вывода в коммандную строку
     * @return строка со всеми значениями объекта Comment (null значения заменяются пустой строкой)
     */
    public String getCommentOutput(){
        String result = String.format(Constants.XML_COMMENTS_OUTPUT,
                checkNull(getId().toString()),
                checkNullUser(getUser()),
                checkNullPhoto(getPhoto()),
                checkNull(getText()),
                checkNull(getRate().toString()),
                checkNull(getDate().toString()));
        return result;
    }
    /**
     * Используется для конвертирования объекта в строку для записи в csv-файл
     * @return строка со всеми значениями объекта Comment (null-значения заменяются пустой строкой)
     */
    public String getCSVCommentOutput(){
        String result = String.format(Constants.CSV_COMMENTS_OUTPUT,
                checkNull(getId().toString()),
                checkNullUser(getUser()),
                checkNullPhoto(getPhoto()),
                checkNull(getText()),
                checkNull(getRate().toString()),
                checkNull(getDate().toString()));
        return result;
    }
    /**
     * Используется для конвертирования объекта из строки csv-файла
     * @return List со всеми объектами Comment в файле
     */
    public static List<Comment> convertFromCSV(List<String[]> data, List<User> costumers, List<Photo> photos){
        try {
            List<Comment> events = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                if (!data.get(i)[5].equals(null)){
                    data.get(i)[5] = Formatter.birthDayToDB(Formatter.csvDateFromString(data.get(i)[5]));
                }
                events.add(new Comment(data.get(i), costumers.get(i), photos.get(i)));
            }
            return events;
        }catch (Exception ex){
            log.error(Constants.ERROR_COMMENT_CONVERT + ex);
            return null;
        }
    }

    /**
     * Дополнительный конструктор Comment для создания объекта из csv-файла
     * @param data - данные (строка) из csv-файла
     * @param user - пользователь (объект User)
     * @param photo - фотография (объект Photo)
     */
    public Comment(String[] data, User user, Photo photo){
        this.id = (T) data[0];
        this.user = user;
        this.photo = photo;
        this.text = data[3];
        this.rate = Integer.parseInt(data[4]);
        this.date = Formatter.birthDayFromDB(data[5]);
    }
}
