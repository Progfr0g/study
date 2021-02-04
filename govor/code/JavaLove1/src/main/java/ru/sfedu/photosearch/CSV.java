package ru.sfedu.photosearch;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.opencsv.*;


public class CSV {
    PhotoSearchClient mainClient = new PhotoSearchClient();
    FileWriter file;
    CSVWriter writer;
    String filename;
    CSVReader reader;

    void create_file(String filename,String columns) throws Exception {
        this.filename = filename;
        file = new FileWriter(filename);
        writer = new CSVWriter(file);
        write_columns(columns);
        String [] record = columns.split(",");
    }

    void write_columns(String columns) throws Exception {
        String[] record = columns.split(",");
        //Write the record to file
        writer.writeNext(record);
    }

    void write_record(String record) throws IOException {
        writer = new CSVWriter(file);
        final String uuid = UUID.randomUUID().toString();
        String[] record_to_write = String.format("%s,%s", uuid.toString(), record).split(",");
        writer.writeNext(record_to_write);
    }
    void close_writer() throws IOException {
        writer.close();
    }

    void read_file(String filename) throws IOException {
        reader = new CSVReader(new FileReader(filename), ',' , '"' , 1);
//        final CSVParser parser =
//                new CSVParserBuilder()
//                        .withSeparator(',')
//                        .withIgnoreQuotations(true)
//                        .build();
//        final CSVReader reader =
//                new CSVReaderBuilder(readed_file)
//                        .withSkipLines(1)
//                        .withCSVParser(parser)
//                        .build();
        //Read CSV line by line and use the string array as you want
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            //Verifying the read data here
            mainClient.log_info(Arrays.toString(nextLine));
        }
        reader.close();
    }
//UUID id, String replace
    void update_file( int row, int column, String value) throws IOException {
        reader = new CSVReader(new FileReader(filename), ',' , '"' , 0);
        List<String[]> csvBody = reader.readAll();
        csvBody.get(row)[column] = value;
        mainClient.log_info(String.format("csv_file changed by column %d and row %d", column, row));
        reader.close();
//        while ((nextLine = reader.readNext()) != null) {
//            //Verifying the read data here
//            mainClient.log_info(Arrays.toString(nextLine));
//        }
//        if
//        csvBody.get(row)[col]=replace;
//        reader.close();
//
        CSVWriter writer = new CSVWriter(new FileWriter(filename),',','"');
        writer.writeAll(csvBody);
        writer.flush();
        writer.close();
    }

    void delete_record(int row) throws IOException {
        reader = new CSVReader(new FileReader(filename), ',' , '"' , 0);
        List<String[]> csvBody = reader.readAll();
        csvBody.remove(row);
        reader.close();
//        while ((nextLine = reader.readNext()) != null) {
//            //Verifying the read data here
//            mainClient.log_info(Arrays.toString(nextLine));
//        }
//        if
//        csvBody.get(row)[col]=replace;
//        reader.close();
//
        CSVWriter writer = new CSVWriter(new FileWriter(filename),',','"');
        mainClient.log_info("row " + String.valueOf(row) + " was deleted in csv_file");
        writer.writeAll(csvBody);
        writer.flush();
        writer.close();
    }


}
