package ru.sfedu.photosearch;

import ru.sfedu.photosearch.enums.PaymentMethod;
import ru.sfedu.photosearch.enums.Status;

public class Events {
    private int id;
    private String title;
    private String description;
    private int customer;
    private int executor;
    private String event_date;
    private int price;
    private float quantity;
    private PaymentMethod payment_method;
    private boolean paid;
    private String payment_date;
    private Status status;

}
