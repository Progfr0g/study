package ru.sfedu.photosearch.xmlTables;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import ru.sfedu.photosearch.Models.Event;
import ru.sfedu.photosearch.Models.Feedback;

import java.util.List;

/**
 * таблица для конвертации отзывов из XML
 * @see Feedback
 */
@Root (name="feedbacks_table")
public class XML_FeedbacksTable {
    @ElementList
    private List<Feedback> feedbacks;

    public List<Feedback> getxmlFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        for (Feedback feedback: feedbacks){
            feedback.setId(feedback.getId().toString());
        }
        this.feedbacks = feedbacks;
    }
}
