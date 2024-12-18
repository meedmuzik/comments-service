package org.scuni.commentsservice.controller;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.scuni.commentsservice.dto.CommentCreateEditDto;
import org.scuni.commentsservice.dto.CommentReadDto;
import org.scuni.commentsservice.dto.QueryDto;
import org.scuni.commentsservice.service.CommentService;
import org.scuni.commentsservice.service.RecommendationsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class CommentController {
    private final CommentService commentService;
    private final RecommendationsService recommendationsService;

    @PostMapping("/comment")
    public ResponseEntity<Object> create(@RequestBody CommentCreateEditDto commentCreateEditDto) {
        CommentReadDto commentReadDto = commentService.create(commentCreateEditDto);
        recommendationsService.saveEmbeddedComment(commentReadDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("commentId", commentReadDto.getId(),
                        "message", "Comment was added"));
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<CommentReadDto> getCommentById(@PathVariable Long id) {
        CommentReadDto comment = commentService.getCommentById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(comment);
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<Object> deleteTrackById(@PathVariable("id") @Min(1) Long id) {
        commentService.deleteCommentById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<CommentReadDto> updateCommentById(@PathVariable("id") @Min(1) Long id, @RequestBody CommentCreateEditDto commentCreateEditDto) {
        CommentReadDto comment = commentService.updateComment(id, commentCreateEditDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(comment);
    }

    @GetMapping("/comments/user")
    public ResponseEntity<Object> getUserComments(@RequestParam("ids") List<Long> ids) {
        List<CommentReadDto> comments = commentService.getCommentsByIds(ids);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("comments", comments));
    }

    @GetMapping("/comments/track")
    public ResponseEntity<Object> getTrackComments(@RequestParam("ids") List<Long> ids) {
        List<CommentReadDto> comments = commentService.getCommentsByIds(ids);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("comments", comments));
    }
//
//    @GetMapping("/test/{query}")
//    public ResponseEntity<Object> test(@PathVariable("query") String query) {
//        recommendationsService.saveEmbeddings(query);
//        return ResponseEntity.noContent().build();
//    }

    @PostMapping("/recommendations/comments")
    public ResponseEntity<Object> getRecommendedComments(@RequestBody QueryDto query) {
        List<Long> recommendationCommentsIds = recommendationsService.getRecommendationCommentsIds(query);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("commentsIds", recommendationCommentsIds));
    }

}
