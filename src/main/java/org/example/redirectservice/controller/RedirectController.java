package org.example.redirectservice.controller;


import lombok.RequiredArgsConstructor;
import org.example.redirectservice.dto.RedirectDto;
import org.example.redirectservice.service.RedirectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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


        RedirectDto redirectDto = new RedirectDto(url, hash, campaignId, userAgent);

        if (!redirectService.isHashValid(redirectDto.getUrl(), redirectDto.getHash())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        redirectService.sendAnalyze(redirectDto);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }
}
