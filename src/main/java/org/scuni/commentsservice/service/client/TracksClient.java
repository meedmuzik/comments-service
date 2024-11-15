package org.scuni.commentsservice.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("tracks-service")
@RequestMapping("api/v1")
public interface TracksClient {

    @PatchMapping(value = "/track/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    void updateTrackRating(@PathVariable Long id);


}
