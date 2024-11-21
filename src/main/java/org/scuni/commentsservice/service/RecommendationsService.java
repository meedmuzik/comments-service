package org.scuni.commentsservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.scuni.commentsservice.dto.CommentReadDto;
import org.scuni.commentsservice.dto.QueryDto;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.mistralai.MistralAiEmbeddingModel;
import org.springframework.ai.mistralai.MistralAiEmbeddingOptions;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationsService {

    private final MistralAiEmbeddingModel embeddingModel;
    private final CommentService commentService;
    private final VectorStore vectorStore;

    public void saveEmbeddedComment(CommentReadDto commentReadDto) {
        float[] vector = getVectorFromModel(commentReadDto.getBody());
        Document document = new Document(commentReadDto.getBody(), Map.of("id", commentReadDto.getId()));
        document.setEmbedding(vector);
        vectorStore.add(List.of(document));
        log.info("Vector: {}", vector);
        log.info("Vector: {}", vector);
    }

    public void deleteEmbeddedContent(Long id) {
        CommentReadDto comment = commentService.getCommentById(id);
        List<Document> documents = vectorStore.similaritySearch(SearchRequest.query(comment.getBody()).withTopK(1));
        vectorStore.delete(List.of(documents.get(0).getId()));
    }

    public List<Long> getRecommendationCommentsIds(QueryDto query) {
        List<Document> documents = vectorStore.similaritySearch(SearchRequest.query(query.getQuery()).withTopK(1));
        return documents.stream()
                .map(Document::getMetadata)
                .map(metadata -> Long.parseLong(String.valueOf(metadata.get("id"))))
                .collect(Collectors.toList());
    }

    private float[] getVectorFromModel(String query) {
        EmbeddingResponse embeddingResponse = embeddingModel.call(
                new EmbeddingRequest(List.of(query),
                        MistralAiEmbeddingOptions.builder()
                                .withModel("mistral-embed")
                                .withEncodingFormat("float")
                                .build()));
        Embedding results = embeddingResponse.getResult();
        return results.getOutput();
    }
}
