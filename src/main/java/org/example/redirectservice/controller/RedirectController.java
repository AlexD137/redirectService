package org.example.redirectservice.controller;


import lombok.RequiredArgsConstructor;
import org.example.redirectservice.dto.RedirectDto;
import org.example.redirectservice.service.RedirectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RedirectController {

    private final RedirectService redirectService;

    @GetMapping("/refLink")
    public ResponseEntity<Void> checkLink(@RequestParam("url") String url,
                                          @RequestParam("hash") String hash,
                                          @RequestParam("campaignId") String campaignId,
                                          @RequestHeader("User-Agent") String userAgent) {

        if (!redirectService.isHashValid(url, hash)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        redirectService.sendAnalyze(
                RedirectDto.builder()
                        .url(url)
                        .campaignId(campaignId)
                        .userAgent(userAgent)
                        .redirectTime(Timestamp.valueOf(LocalDateTime.now()))
                        .build()
        );

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }
}
