package ru.sfedu.photosearch;

import java.awt.Desktop;
import java.io.File;

public class PhotoViewer {
    public static void main(String[] args) throws Exception {
        File f = new File("C:\\Users\\cucumber\\Downloads\\test1.jpg");
        Desktop dt = Desktop.getDesktop();
        dt.open(f);
        System.out.println("Done.");
    }
}
