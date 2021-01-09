package ru.sfedu.photosearch;

import ru.sfedu.photosearch.enums.Tables;
import ru.sfedu.photosearch.providers.DataProvider;


public class DataProviderDatabase implements DataProvider {
    public Database DB = new Database();


//        ArrayList<String> a;
//        String[] b;
//        List<String> c;
//        Map<String, Object> d = new HashMap<>();
//
//        Object value = d.get("cock");
//        if (value instanceof Integer){
//            Integer n = (Integer) value;
//        } else if (value instanceof String){
//
//        } else {
//            HashMap<String, Object> nn = (HashMap<String, Object>) value;
//            nn.get("offset_y");
//        }


    @Override
    public boolean createNewProfile(String name, String last_name, Integer age, String date_of_registration, String role, String town) {
        String query = String.format(Constants.INSERT_USERS_QUERY, name, last_name, age.toString(), date_of_registration, role, town);
        return DB.connect() && (DB.insert(query) > 0) && DB.closeConnection();
    }

    @Override
    public boolean createNewEvent(String title, String description, String customer, String event_date, Integer price, Float quantity) {
        String query = String.format(Constants.INSERT_EVENTS_QUERY, title, description, customer, event_date, price.toString(), quantity.toString());
        return DB.connect() && (DB.insert(query) > 0) && DB.closeConnection();
    }

    @Override
    public boolean editProfileById(String id, String field, String value) {
        String query = "";
        query = String.format("UPDATE %s SET %s = %s WHERE id = %s", Tables.USERS.toString(), field, value, String.valueOf(id));
        return DB.connect() && (DB.update(query)>0) && DB.closeConnection();
    }

    @Override
    public boolean editEventById(String id, String field, String value) {
        String query = "";
        query = String.format("UPDATE %s SET %s = %s WHERE id = %s", Tables.EVENTS.toString(), field, value, String.valueOf(id));
        return DB.connect() && (DB.update(query)>0) && DB.closeConnection();
    }

    @Override
    public void addPhotoByProfileId(String id, String field, String path) {

    }


}
