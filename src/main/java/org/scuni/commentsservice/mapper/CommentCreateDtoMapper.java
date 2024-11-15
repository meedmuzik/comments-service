package org.scuni.commentsservice.mapper;

import org.scuni.commentsservice.dto.CommentCreateEditDto;
import org.scuni.commentsservice.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentCreateDtoMapper implements Mapper<CommentCreateEditDto, Comment> {

    @Override
    public Comment map(CommentCreateEditDto object) {
        Comment comment = new Comment();
        Comment.builder()
                .body(object.getBody())
                .rating(comment.getRating())
                .trackId(object.getTrackId())
                .userId(object.getUserId())
                .build();
        return comment;
    }

    public Comment map(CommentCreateEditDto object, Comment comment) {
        Comment.builder()
                .body(object.getBody())
                .rating(comment.getRating())
                .trackId(object.getTrackId())
                .userId(object.getUserId())
                .build();
        comment.setBody(object.getBody());
        comment.setRating(object.getRating());
        return comment;
    }
}
