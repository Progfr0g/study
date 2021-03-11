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
 * Класс Rate
 * используется для создания оценки к фотографиям
 * @param <T>
 */
public class Rate<T> {
    public static final Logger log = LogManager.getLogger(Rate.class);
    @Element
    private T id;
    @Element
    private User user;
    @Element
    private Photo photo;
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

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Rate() {
    }

    /**
     * Конструктор создания оценки
     * @param id - новый ID для создаваемой оценки, которая будет записан (не используется в DataProviderDB)
     * @param user - пользователь, который создал оценку
     * @param photo - фотография, к которому создавалась оценка
     * @param rate - оценка
     * @param date - дата создания оценки
     */
    public Rate(T id, User user, Photo photo, Integer rate, Date date) {
        this.id = id;
        this.user = user;
        this.photo = photo;
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
     * @return строка со всеми значениями объекта Rate (null значения заменяются пустой строкой)
     */
    public String getRateOutput(){
        String result = String.format(Constants.XML_RATES_OUTPUT,
                checkNull(getId().toString()),
                checkNullUser(getUser()),
                checkNullPhoto(getPhoto()),
                checkNull(getRate().toString()),
                checkNull(getDate().toString()));
        return result;
    }
    /**
     * Используется для конвертирования объекта в строку для записи в csv-файл
     * @return строка со всеми значениями объекта Rate (null-значения заменяются пустой строкой)
     */
    public String getCSVRateOutput(){
        String result = String.format(Constants.CSV_RATES_OUTPUT,
                checkNull(getId().toString()),
                checkNullUser(getUser()),
                checkNullPhoto(getPhoto()),
                checkNull(getRate().toString()),
                checkNull(getDate().toString()));
        return result;
    }
    /**
     * Используется для конвертирования объекта из строки csv-файла
     * @return List со всеми объектами Rate в файле
     */
    public static List<Rate> convertFromCSV(List<String[]> data, List<User> costumers, List<Photo> photos){
        try {
            List<Rate> rates = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i)[4] != null){
                    data.get(i)[4] = Formatter.birthDayToDB(Formatter.csvDateFromString(data.get(i)[4]));
                }
                rates.add(new Rate(data.get(i), costumers.get(i), photos.get(i)));
            }
            return rates;
        }catch (Exception ex){
            log.error(Constants.ERROR_RATE_CONVERT + ex);
            return null;
        }
    }
    /**
     * Дополнительный конструктор Rate для создания объекта из csv-файла
     * @param data - данные (строка) из csv-файла
     * @param user - пользователь (объект User)
     * @param photo - фотография (объект Photo)
     */
    public Rate(String[] data, User user, Photo photo){
        this.id = (T) data[0];
        this.user = user;
        this.photo = photo;
        this.rate = Integer.parseInt(data[3]);
        this.date = Formatter.birthDayFromDB(data[4]);
    }
}
