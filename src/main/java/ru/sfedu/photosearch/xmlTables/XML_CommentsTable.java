package ru.sfedu.photosearch.xmlTables;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import ru.sfedu.photosearch.newModels.Comment;


import java.util.List;


@Root (name="comments_table")
public class XML_CommentsTable {
    @ElementList
    private List<Comment> comments;

    public List<Comment> getxmlComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        for (Comment comment: comments){
            comment.setId(comment.getId().toString());
        }
        this.comments = comments;
    }
}
