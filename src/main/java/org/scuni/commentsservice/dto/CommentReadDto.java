package org.scuni.commentsservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentReadDto {
    private Long id;

    private Long userId;

    private String body;

    private Long trackId;

    private Long rating;
}

