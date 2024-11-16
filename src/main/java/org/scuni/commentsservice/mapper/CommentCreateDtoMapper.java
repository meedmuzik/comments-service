package org.scuni.commentsservice.mapper;

import org.scuni.commentsservice.dto.CommentCreateEditDto;
import org.scuni.commentsservice.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentCreateDtoMapper implements Mapper<CommentCreateEditDto, Comment> {

    @Override
    public Comment map(CommentCreateEditDto object) {
        return Comment.builder()
                .body(object.getBody())
                .rating(object.getRating())
                .trackId(object.getTrackId())
                .userId(object.getUserId())
                .build();
    }

    public Comment map(CommentCreateEditDto object, Comment comment) {
        comment.setBody(object.getBody());
        comment.setRating(object.getRating());
        return comment;
    }
}
