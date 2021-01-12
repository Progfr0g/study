package ru.sfedu.photosearch.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.sfedu.photosearch.Constants;
import ru.sfedu.photosearch.enums.PaymentMethod;
import ru.sfedu.photosearch.enums.Status;

@Root(name="event")
public class Events {
    @Attribute
    private int id;
    @Element
    private String title;
    @Element
    private String description;
    @Element
    private int customer;
    @Element
    private int executor;
    @Element
    private String event_date;
    @Element
    private int price;
    @Element
    private float quantity;
    @Element
    private PaymentMethod payment_method;
    @Element
    private boolean paid;
    @Element
    private String payment_date;
    @Element
    private Status status;

    public Events() {
        super();
    }

    public Events(int id, String title, String description, int customer, int executor, String event_date, int price, float quantity, PaymentMethod payment_method, boolean paid, String payment_date, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.customer = customer;
        this.executor = executor;
        this.event_date = event_date;
        this.price = price;
        this.quantity = quantity;
        this.payment_method = payment_method;
        this.paid = paid;
        this.payment_date = payment_date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getCustomer() {
        return customer;
    }

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public int getExecutor() {
        return executor;
    }

    public void setExecutor(int executor) {
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

    public void setPayment_method(PaymentMethod payment_method) {
        this.payment_method = payment_method;
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
        String result = String.format(Constants.XML_EVENTS_OUTPUT, checkNull(String.valueOf(getId())),
                checkNull(getTitle()),
                checkNull(getDescription()),
                checkNull(String.valueOf(getCustomer())),
                checkNull(String.valueOf(getExecutor())),
                checkNull(getEvent_date()),
                checkNull(String.valueOf(getPrice())),
                checkNull(String.valueOf(getPayment_method())),
                checkNull(String.valueOf(isPaid())),
                checkNull(getPayment_date()),
                checkNull(getStatus().toString().toLowerCase()));
            ;
        return result;
    }


}
