package org.scuni.commentsservice.service;

import lombok.RequiredArgsConstructor;
import org.scuni.commentsservice.dto.CommentCreateEditDto;
import org.scuni.commentsservice.dto.CommentReadDto;
import org.scuni.commentsservice.entity.Comment;
import org.scuni.commentsservice.exception.CommentNotFoundException;
import org.scuni.commentsservice.mapper.CommentCreateDtoMapper;
import org.scuni.commentsservice.mapper.CommentReadDtoMapper;
import org.scuni.commentsservice.repository.CommentRepository;
import org.scuni.commentsservice.service.client.TracksClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TracksClient tracksClient;
    private final CommentCreateDtoMapper commentCreateDtoMapper;
    private final CommentReadDtoMapper commentReadDtoMapper;

    public CommentReadDto create(CommentCreateEditDto commentCreateEditDto) {
        Comment comment = commentCreateDtoMapper.map(commentCreateEditDto);
        comment = commentRepository.save(comment);
        tracksClient.updateTrackRating(comment.getId());
        return commentReadDtoMapper.map(comment);
    }

    public CommentReadDto getCommentById(Long id) {
        return commentRepository.findById(id)
                .map(commentReadDtoMapper::map)
                .orElseThrow(() -> new CommentNotFoundException("Failed to get comment"));
    }

    public void deleteCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Failed to get comment"));
        tracksClient.updateTrackRating(comment.getId());
        commentRepository.delete(comment);
    }

    public CommentReadDto updateComment(Long id, CommentCreateEditDto commentCreateEditDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Failed to get comment"));
        comment = commentCreateDtoMapper.map(commentCreateEditDto, comment);
        comment.setId(id);
        commentRepository.save(comment);
        return commentReadDtoMapper.map(comment);
    }

    public List<CommentReadDto> getCommentsByIds(List<Long> ids) {
        return commentRepository.findCommentsByIds(ids)
                .stream().map(commentReadDtoMapper::map)
                .collect(Collectors.toList());
    }


}
