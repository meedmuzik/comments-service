package org.scuni.commentsservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentCreateEditDto {
    private Long id;

    private Long userId;

    private Long trackId;

    private String body;

    private Long rating;
}
