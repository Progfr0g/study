package ru.sfedu.photosearch.xmlTables;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import ru.sfedu.photosearch.Models.Comment;
import ru.sfedu.photosearch.Models.Event;

import java.util.List;

/**
 * таблица для конвертации событий из XML
 * @see Event
 */
@Root (name="events_table")
public class XML_EventsTable {
    @ElementList
    private List<Event> events;

    public List<Event> getxmlEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        for (Event event:events){
            event.setId(event.getId().toString());
        }
        this.events = events;
    }
}
