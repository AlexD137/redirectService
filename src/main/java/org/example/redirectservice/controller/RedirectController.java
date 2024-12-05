package org.example.redirectservice.controller;




import org.example.redirectservice.model.RedirectRequest;
import org.example.redirectservice.service.RedirectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/redirect")
public class RedirectController {

    private final RedirectService redirectService;

    // Внедрение сервиса через конструктор
    public RedirectController(RedirectService redirectService) {
        this.redirectService = redirectService;
    }

    // Обработчик GET запроса
    @GetMapping
    public ResponseEntity<Void> handleRedirect(@RequestParam("url") String url,
                                               @RequestParam("hash") String hash,
                                               @RequestParam("campaignId") String campaignId,
                                               @RequestHeader("User-Agent") String userAgent) {

        // Создаем объект запроса
        RedirectRequest request = new RedirectRequest(url, hash, campaignId, userAgent);

        // Переносим проверку хеша в сервис
        if (!redirectService.isHashValid(request.getUrl(), request.getHash())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Выполняем редирект
        URI redirectUri = redirectService.getRedirectUri(request.getUrl());
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(redirectUri)
                .build();
    }
}
