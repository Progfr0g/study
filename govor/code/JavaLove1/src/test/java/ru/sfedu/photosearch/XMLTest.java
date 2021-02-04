package ru.sfedu.photosearch;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.photosearch.enums.Role;
import ru.sfedu.photosearch.models.User;
import ru.sfedu.photosearch.providers.DataProviderXML;
import ru.sfedu.photosearch.utils.XML_util;
import ru.sfedu.photosearch.xmlTables.XML_UsersTable;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class XMLTest {
    public static final Logger log = LogManager.getLogger(PhotoSearchClient.class);
    @Test
    void check() throws Exception {
        this.write();
        this.read();
//        this.update();
//        this.read();
//        this.delete();
//        this.read();
    }

    @Test
    void write() throws Exception {
//        DataProviderXML provider = new DataProviderXML();
//        XML_util.createFiles();
////        provider.createNewEvent("evgeniy", "govor", 21, LocalDate.now().toString(), Role.CUSTOMER.name().toLowerCase(), "rostov");
////        provider.createNewEvent("first", "test description", "3", LocalDate.now().toString(), 129, 2.5f);
//        log.info(provider.getEvent("3b2dc08d-c768-474f-a614-eb37a354327c"));
//        provider.deleteProfileById("a9726765-c171-4c01-8910-ff093b12cec3");
    }

    @Test
    void read() throws Exception {

    }

    @Test
    void update() throws Exception {
        Serializer serializer = new Persister();
        String name_k = "";
        File source = new File(Constants.XML_USERS_FILE_PATH);
        XML_UsersTable table = new XML_UsersTable();
        table = serializer.read(XML_UsersTable.class, source);
        for (User user: table.getxmlUsers()){
            log.info(String.format("%s, %s, %s, %s",
                    user.getId(),
                    user.getName(),
                    user.getLast_name(),
                    Integer.parseInt(String.valueOf(user.getAge()))));

            if (user.getName().equals("Elena")){
                log.info(String.format("class updated and writed to xml %s", user.getName()));
                user.setName("Kristina");
                name_k = String.format("%s %s", user.getName(), user.getLast_name());
            }
        }
        assertEquals("Kristina Richter", name_k);

        File result = new File(Constants.XML_USERS_FILE_PATH);

        serializer.write(table, result);
        log.info("writed class to xml");
    }

    @Test
    void delete() throws Exception {
    }
}