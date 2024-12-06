package org.example.redirectservice.service;

import org.example.redirectservice.dto.RedirectDto;


public interface RedirectService {

    boolean isHashValid(String url, String hash);

    void sendAnalyze(RedirectDto redirectDto);
}
