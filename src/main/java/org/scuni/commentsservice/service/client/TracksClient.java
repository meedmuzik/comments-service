package org.scuni.commentsservice.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("tracks-service")
public interface TracksClient {

    @PostMapping(value = "api/v1/track/rating/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    void updateTrackRating(@PathVariable Long id);


}
