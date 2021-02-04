package ru.sfedu.photosearch.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.enums.PaymentMethod;
import ru.sfedu.photosearch.enums.Role;
import ru.sfedu.photosearch.enums.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Root(name="event")
public class Event {
    public static final Logger log = LogManager.getLogger(Event.class);
    @Attribute
    private String id;
    @Element
    private String title;
    @Element
    private String description;
    @Element
    private String customer;
    @Element(required = false)
    private String executor;
    @Element
    private String event_date;
    @Element
    private int price;
    @Element
    private float quantity;
    @Element(required = false)
    private PaymentMethod payment_method;
    @Element
    private boolean paid;
    @Element(required = false)
    private String payment_date;
    @Element
    private Status status;

    public Event() {
        super();
    }

    public Event(String id, String title, String description, String customer, String executor, String event_date, int price, float quantity, String payment_method, boolean paid, String payment_date, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.customer = checkNull(customer);
        this.executor = checkNull(executor);
        this.event_date = event_date;
        this.price = price;
        this.quantity = quantity;
        if (payment_method.equals(Constants.UTIL_EMPTY_STRING)){
            this.payment_method = PaymentMethod.NONE;
        } else {
            this.payment_method = PaymentMethod.valueOf(payment_method);
        }
        this.paid = paid;
        this.payment_date = checkNull(payment_date);
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public PaymentMethod getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = PaymentMethod.valueOf(payment_method);
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String checkNull(String value){
        if (value == null){
            return Constants.UTIL_EMPTY_STRING;
        } else {
            return value;
        }

    }

//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.customer = customer;
//        this.executor = executor;
//        this.event_date = event_date;
//        this.price = price;
//        this.quantity = quantity;
//        this.payment_method = payment_method;
//        this.paid = paid;
//        this.payment_date = payment_date;
//        this.status = status;
    public String getEventOutput(){
        String result = String.format(Constants.XML_EVENTS_OUTPUT,
                checkNull(String.valueOf(getId())),
                checkNull(getTitle()),
                checkNull(getDescription()),
                checkNull(getCustomer()),
                checkNull(getExecutor()),
                checkNull(getEvent_date()),
                checkNull(String.valueOf(getQuantity())),
                checkNull(String.valueOf(getPrice())),
                checkNull(getPayment_method().toString()),
                checkNull(String.valueOf(isPaid())),
                checkNull(getPayment_date()),
                checkNull(getStatus().toString().toLowerCase()));
            ;
        return result;
    }

    public String getCSVEventOutput(){
        String result = String.format(Constants.CSV_EVENTS_OUTPUT,
                checkNull(String.valueOf(getId())),
                checkNull(getTitle()),
                checkNull(getDescription()),
                checkNull(getCustomer()),
                checkNull(getExecutor()),
                checkNull(getEvent_date()),
                checkNull(String.valueOf(getQuantity())),
                checkNull(String.valueOf(getPrice())),
                checkNull(getPayment_method().toString()),
                checkNull(String.valueOf(isPaid())),
                checkNull(getPayment_date()),
                checkNull(getStatus().toString().toLowerCase()));
        return result;
    }

    public static List<Event> convertFromCSV(List<String[]> data){
        try {
            List<Event> events = new ArrayList<>();
            for (String[] line : data) {
                events.add(new Event(line));
            }
            return events;
        }catch (Exception ex){
            log.error(Constants.ERROR_EVENT_CONVERT + ex);
        }
        return null;
    }

    public Event(String[] data){
        this.id = data[0];
        this.title = data[1];
        this.description = data[2];
        this.customer = checkNull(data[3]);
        this.executor = checkNull(data[4]);
        this.event_date = data[5];
        this.price = Integer.parseInt(data[7]);
        this.quantity = Float.parseFloat(data[6]);
        if (data[8].equals(Constants.UTIL_EMPTY_STRING)){
            this.payment_method = PaymentMethod.NONE;
        } else {
            this.payment_method = PaymentMethod.valueOf(data[8]);
        }
        this.paid = Boolean.parseBoolean(data[9]);
        this.payment_date = checkNull(data[10]);
        this.status = Status.valueOf(data[11].toUpperCase());
    }



}
