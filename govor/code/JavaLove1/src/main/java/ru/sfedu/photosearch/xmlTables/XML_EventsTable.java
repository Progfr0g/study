package ru.sfedu.photosearch.xmlTables;

import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import ru.sfedu.photosearch.models.Event;
import ru.sfedu.photosearch.models.User;

import java.util.List;


@Root (name="events_table")
public class XML_EventsTable {
    @ElementList
    private List<Event> events;

    public List<Event> getxmlEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
