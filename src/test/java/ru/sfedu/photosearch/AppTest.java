package ru.sfedu.photosearch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    private static Logger log = LogManager.getLogger(AppTest.class);
    @Test
    void connect(){
        Database db = new Database();
        assertTrue(db.connect());
    }

    @Test
    void crud() throws SQLException {
        Database db = new Database();
        db.connect();
        String query = "CREATE TABLE IF NOT EXISTS test_table (" +
                "id IDENTITY," +
                "name VARCHAR(20)," +
                "last_name VARCHAR(20)," +
                "age INT)";

        assertTrue(db.run(query));
        assertEquals(1, db.insert("INSERT INTO test_table (name, last_name, age) VALUES ('Maksim', 'Gorky', 35)"));

        ResultSet test = db.select("SELECT * FROM test_table");
        String result_string = "";

        log.debug(String.format("select contains %d columns", test.getMetaData().getColumnCount()));
        while (test.next()){
            for (int i = 0; i < test.getMetaData().getColumnCount(); i++) {
                result_string += test.getString(i+1) + ' ';
            }
            log.debug(String.format("%s", result_string));
            assertEquals("1 Maksim Gorky 35 ", result_string);
            result_string = "";
        }

        assertEquals(1, db.update("UPDATE test_table SET name='Aleksey' WHERE name='Maksim'"));

        test = db.select("SELECT * FROM test_table");
        result_string = "";
        while (test.next()){
            for (int i = 0; i < test.getMetaData().getColumnCount(); i++) {
                result_string += test.getString(i+1) + ' ';
            }
            log.debug(String.format("%s", result_string));
            assertEquals("1 Aleksey Gorky 35 ", result_string);
            result_string = "";
        }

        db.delete("DELETE FROM test_table where id>0");
        db.run("DROP TABLE IF EXISTS test_table");

//        while (test.next()) {
//            test.getMetaData();
//            result_string += test.getString(test.getRow());
//        }
        log.info("test completed");
    }

}