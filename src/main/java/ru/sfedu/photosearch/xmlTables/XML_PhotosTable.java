package ru.sfedu.photosearch.xmlTables;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
//import ru.sfedu.photosearch.newModels.Event;
import ru.sfedu.photosearch.Models.Photo;
import ru.sfedu.photosearch.Models.Photographer;

import java.util.List;

/**
 * таблица для конвертации фотографий из XML
 * @see Photo
 */
@Root (name="photos_table")
public class XML_PhotosTable {
    @ElementList
    private List<Photo> photos;

    public List<Photo> getxmlPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        for (Photo photo: photos){
            photo.setId(photo.getId().toString());
        }
        this.photos = photos;
    }
}
