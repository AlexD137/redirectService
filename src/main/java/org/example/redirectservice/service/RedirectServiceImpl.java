package org.example.redirectservice.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.example.redirectservice.dto.RedirectDto;
import org.example.redirectservice.kafka.KafkaPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class RedirectServiceImpl implements RedirectService {

    private final KafkaPublisher kafkaPublisher;

    public RedirectServiceImpl(KafkaPublisher kafkaPublisher) {
        this.kafkaPublisher = kafkaPublisher;
    }

    @Value("${app.salt}")
    private String salt;


    @Override
    public boolean isHashValid(String url, String hash) {
        return hash.equals(DigestUtils.sha256Hex(url + salt));
    }


    @Override
    public void sendAnalyze(RedirectDto redirectDto) {

        kafkaPublisher.sendMessage(redirectDto);
    }
}
