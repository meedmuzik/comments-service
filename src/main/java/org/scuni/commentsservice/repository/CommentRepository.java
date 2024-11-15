package org.scuni.commentsservice.repository;


import org.scuni.commentsservice.entity.Comment;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends Neo4jRepository<Comment, Long> {
    @Query("MATCH (c:Comment) WHERE id(c) IN $ids RETURN c")
    List<Comment> findCommentsByIds(@Param("ids") List<Long> ids);

    @Query("""
                MATCH (u:User), (t:Track), (c:Comment)
                WHERE id(u) = $userId AND id(t) = $trackId AND id(c) = $commentId
                CREATE (u)-[:AUTHORED]->(c)
                CREATE (c)-[:COMMENTED_ON]->(t)
            """)
    void createCommentRelationships(@Param("userId") Long userId,
                                    @Param("trackId") Long trackId,
                                    @Param("commentId") Long commentId);

    @Query("""
                MATCH (c:Comment)-[r1:AUTHORED]->(u:User),
                      (c)-[r2:COMMENTED_ON]->(t:Track)
                WHERE id(c) = $commentId
                DELETE r1, r2
            """)
    void deleteCommentRelationships(@Param("commentId") Long commentId);


}
