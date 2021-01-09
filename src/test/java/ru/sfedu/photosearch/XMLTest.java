package ru.sfedu.photosearch;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class XMLTest {
    @Test
    void check() throws Exception {
        this.write();
        this.read();
        this.update();
        this.read();
        this.delete();
        this.read();
    }

    @Test
    void write() throws Exception {
        PhotoSearchClient mainClient = new PhotoSearchClient();
        List<XML_user> users = new ArrayList<XML_user>();

        users.add(new XML_user("Evgeniy", "Govor", String.valueOf(21)));
        users.add(new XML_user("Elena", "Richter", String.valueOf(42)));
        for (int i = 0; i < 1000; i++) {
            users.add(new XML_user("nikita", "Richter", String.valueOf(12)));
        }

        mainClient.log_info("created new records");

        XML_table table = new XML_table();


        XML_user[] users_array = new XML_user[users.size()];
        for (int i = 0; i < users.size(); i++) {
            users_array[i] = users.get(i);
        }

        table.setUsers(users_array);

        Serializer serializer = new Persister();
        File result = new File("example.xml");

        serializer.write(table, result);
        mainClient.log_info("writed class to xml");

    }

    @Test
    void read() throws Exception {
        int errors = 0;
        PhotoSearchClient mainClient = new PhotoSearchClient();
        Serializer serializer = new Persister();
        File source = new File("example.xml");
        XML_table table = new XML_table();
        table = serializer.read(XML_table.class, source);
        Set<String> targetSet = new HashSet<String>();
        for (XML_user user: table.getXML_users()){
            mainClient.log_info(String.format("%s, %s, %s, %d",
                    user.getId(),
                    user.getName(),
                    user.getLast_name(),
                    Integer.parseInt(user.getAge())));
            mainClient.log_info("created new class from xml");

            if (targetSet.add(user.getId()) == false) {
                // your duplicate element
                errors += 1;
            }

        }
        assertEquals(0, errors);
    }

    @Test
    void update() throws Exception {
        PhotoSearchClient mainClient = new PhotoSearchClient();
        Serializer serializer = new Persister();
        String name_k = "";
        File source = new File("example.xml");
        XML_table table = new XML_table();
        table = serializer.read(XML_table.class, source);
        for (XML_user user: table.getXML_users()){
            mainClient.log_info(String.format("%s, %s, %s, %d",
                    user.getId(),
                    user.getName(),
                    user.getLast_name(),
                    Integer.parseInt(user.getAge())));

            if (user.getName().equals("Elena")){
                mainClient.log_info(String.format("class updated and writed to xml %s", user.getName()));
                user.setName("Kristina");
                name_k = String.format("%s %s", user.getName(), user.getLast_name());
            }
        }
        assertEquals("Kristina Richter", name_k);

        File result = new File("example.xml");

        serializer.write(table, result);
        mainClient.log_info("writed class to xml");
    }

    @Test
    void delete() throws Exception {
        PhotoSearchClient mainClient = new PhotoSearchClient();
        Serializer serializer = new Persister();
        File source = new File("example.xml");
        XML_table table = new XML_table();
        table = serializer.read(XML_table.class, source);
        for (XML_user user: table.getXML_users()){
            mainClient.log_info(String.format("%s, %s, %s, %d",
                    user.getId(),
                    user.getName(),
                    user.getLast_name(),
                    Integer.parseInt(user.getAge())));

            if (Integer.parseInt(user.getAge()) > 30){
                mainClient.log_info(String.format("deleted class with age > 30 %s %s %d",
                        user.getName(),
                        user.getLast_name(),
                        Integer.parseInt(user.getAge())));
                int index = ArrayUtils.indexOf(table.getXML_users(), user);
                table.setUsers(ArrayUtils.remove(table.getXML_users(), index));

            }
        }

        assertEquals(1001, table.getXML_users().length);
        File result = new File("example.xml");

        serializer.write(table, result);
        mainClient.log_info("writed class to xml");
    }
}