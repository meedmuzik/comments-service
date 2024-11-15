package org.scuni.commentsservice.repository;


import org.scuni.commentsservice.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends Neo4jRepository<Comment, Long> {
    @Query("MATCH (c:Comment) WHERE id(c) IN $ids RETURN c")
    List<Comment> findCommentsByIds(@Param("ids") List<Long> ids);
}
