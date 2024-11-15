package org.scuni.commentsservice.mapper;

import org.scuni.commentsservice.dto.CommentReadDto;
import org.scuni.commentsservice.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentReadDtoMapper implements Mapper<Comment, CommentReadDto> {

    @Override
    public CommentReadDto map(Comment object) {
        return CommentReadDto.builder()
                .id(object.getId())
                .body(object.getBody())
                .trackId(object.getTrackId())
                .userId(object.getUserId())
                .rating(object.getRating())
                .build();
    }
}
