package ru.sfedu.photosearch;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSVTest {
    @Test
    void check() throws Exception {
        CSV csv_main = new CSV();
        String filename = "data.csv";
        csv_main.create_file(filename, "id,name,last_name,age");
        String name = "Evgeniy";
        String last_name = "Govor";
        int age = 21;

        String record = String.format("%s,%s,%d", name, last_name, age);
        PhotoSearchClient.log.info("string to write: " + record);
        csv_main.write_record(record);
        record = String.format("%s,%s,%d", name, last_name, 25);

        csv_main.write_record(record);
        csv_main.close_writer();
        csv_main.read_file(filename);

        csv_main.update_file(2,3, String.valueOf(21));
        csv_main.read_file(filename);

        csv_main.delete_record( 1);
        csv_main.read_file(filename);

    }
}