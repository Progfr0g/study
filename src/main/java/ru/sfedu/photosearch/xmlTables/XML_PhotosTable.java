package ru.sfedu.photosearch.xmlTables;

import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
//import ru.sfedu.photosearch.models.Event;
import ru.sfedu.photosearch.models.Photo;
import ru.sfedu.photosearch.models.User;

import java.util.List;


@Root (name="photos_table")
public class XML_PhotosTable {
    @ElementList
    private List<Photo> photos;

    public List<Photo> getxmlPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
