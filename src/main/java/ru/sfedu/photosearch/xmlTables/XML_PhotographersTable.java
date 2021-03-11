package ru.sfedu.photosearch.xmlTables;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import ru.sfedu.photosearch.Models.Offer;
import ru.sfedu.photosearch.Models.Photographer;

import java.util.List;

/**
 * таблица для конвертации фотографов из XML
 * @see Photographer
 */
@Root (name="photographers_table")
public class XML_PhotographersTable {
    @ElementList
    private List<Photographer> photographers;

    public List<Photographer> getxmlPhotographers() {
        return photographers;
    }

    public void setUsers(List<Photographer> photographers) {
        for (Photographer photographer: photographers){
            photographer.setId(photographer.getId().toString());
        }
        this.photographers = photographers;
    }
}
