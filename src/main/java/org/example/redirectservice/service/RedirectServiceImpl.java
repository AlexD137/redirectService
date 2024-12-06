package org.example.redirectservice.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.example.redirectservice.dto.RedirectDto;
import org.springframework.stereotype.Service;


@Service
public class RedirectServiceImpl implements RedirectService {


    @Override
    public boolean isHashValid(String url, String hash) {
        return hash.equals(DigestUtils.sha256Hex(url));
    }


    @Override
    public void sendAnalyze(RedirectDto redirectDto) {
        //TODO вызов kafka
    }
}
