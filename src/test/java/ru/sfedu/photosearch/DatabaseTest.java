package ru.sfedu.photosearch;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {


    @Test
    void check() throws SQLException {
        PhotoSearchClient mainClient = new PhotoSearchClient();
        Database db = new Database();
        db.connect();
//        ResultSet affected_rows = db.insert("INSERT INTO test_table (name, last_name, age)" +
//                " VALUES" +
//                " ('Sergey', 'Kovalev', 45)," +
//                " ('Kristina', 'Asmus', 24);");
//        db.mainClient.log_info("INSERT suceeded;");
//
//        affected_rows = db.update("UPDATE test_table SET age=34" +
//                " WHERE name='Kristina';");
//        db.mainClient.log_info("UPDATE suceeded;");
//
//        ResultSet rows = db.select("SELECT * FROM test_table;");
//        rows.last();
//        int length = rows.getRow();
//        db.mainClient.log_info("Table contains " + length + " rows");
//        assertEquals(2, length);

//        affected_rows = db.delete("DELETE FROM test_table WHERE id>0;");
//        db.close_connection();
    }
}