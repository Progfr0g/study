package ru.sfedu.photosearch.Models;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.enums.Role;
import ru.sfedu.photosearch.utils.Formatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Класс User
 * используется для создания профилей пользователей
 * @param <T>
 */
@Root(name="user")
public class User<T> {
    public static final Logger log = LogManager.getLogger(User.class);
    @Element
    private T id;
    @Element
    private String name;
    @Element
    private String lastName;
    @Element
    private Date birthDay;
    @Element
    private Date dateOfRegistration;
    @Element
    private Role role;
    @Element
    private String town;
    @Element
    private Float wallet;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Date dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public Float getWallet() {
        return wallet;
    }

    public void setWallet(Float wallet) {
        this.wallet = wallet;
    }

    public User() {
        super();
    }

    /**
     * Конструктор создания профиля
     * @param id - новый ID для создаваемого комментария, который будет записан (не используется в DataProviderDB)
     * @param name - имя пользователя
     * @param lastName - фамилия пользователя
     * @param birthDay - дата рождения пользователя
     * @param dateOfRegistration - дата регистрации пользователя
     * @param role - роль пользователя
     * @param town - город проживания
     * @param wallet - средства (кошелек)
     */
    public User(T id, String name, String lastName, Date birthDay, Date dateOfRegistration, Role role, String town, Float wallet) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.dateOfRegistration = dateOfRegistration;
        this.role = role;
        this.town = town;
        this.wallet = wallet;
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
     * Используется для финального вывода в коммандную строку
     * @return строка со всеми значениями объекта User (null значения заменяются пустой строкой)
     */
    public String getUserOutput(){
        String result = String.format(Constants.XML_USERS_OUTPUT,
                checkNull(getId().toString()),
                checkNull(getName()),
                checkNull(getLastName()),
                checkNull(getBirthDay().toString()),
                checkNull(getDateOfRegistration().toString()),
                checkNull(getRole().toString()),
                checkNull(getTown()),
                checkNull(String.valueOf(getWallet())));
        return result;
    }

    /**
     * Используется для конвертирования объекта в строку для записи в csv-файл
     * @return строка со всеми значениями объекта User (null-значения заменяются пустой строкой)
     */
    public String getCSVUserOutput(){
        String result = String.format(Constants.CSV_USERS_OUTPUT,
                checkNull(getId().toString()),
                checkNull(getName()),
                checkNull(getLastName()),
                checkNull(getBirthDay().toString()),
                checkNull(getDateOfRegistration().toString()),
                checkNull(getRole().toString()),
                checkNull(getTown()),
                checkNull(String.valueOf(getWallet())));
        return result;
    }
    /**
     * Используется для конвертирования объекта из строки csv-файла
     * @return List со всеми объектами User в файле
     */
    public static List<User> convertFromCSV(List<String[]> data){
        try {
            List<User> users = new ArrayList<>();
            for (String[] line : data) {
                if (line[3] != null){
                    line[3] = Formatter.birthDayToDB(Formatter.csvDateFromString(line[3]));
                }
                if (line[4] != null){
                    line[4] = Formatter.birthDayToDB(Formatter.csvDateFromString(line[4]));
                }
                users.add(new User(line));
            }
            return users;
        }catch (Exception ex){
            log.error(Constants.ERROR_USER_CONVERT + ex);
            return null;
        }
    }

    /**
     * Дополнительный конструктор User для создания объекта из csv-файла
     * @param data - данные (строка) из csv-файла
     */
    public User(String[] data){
        this.id = (T) data[0];
        this.name = data[1];
        this.lastName = data[2];
        this.birthDay = Formatter.birthDayFromDB(data[3]);
        this.dateOfRegistration = Formatter.dateOfRegistrationFromDB(data[4]);
        this.role = Role.valueOf(data[5].toUpperCase());
        this.town = data[6];
        this.wallet = Float.valueOf(data[7]);
    }
}
