package org.example.redirectservice.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.example.redirectservice.dto.RedirectDto;
import org.example.redirectservice.kafka.KafkaPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

public class RedirectServiceImplTest {

    @Mock
    private KafkaPublisher kafkaPublisher;

    @Value("${app.salt}")
    private String salt;

    @InjectMocks
    private RedirectServiceImpl redirectService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsHashValid() {
        String url = "https://www.google.ru/";
        String validHash = DigestUtils.sha256Hex(url + salt);
        String invalidHash = "invalid_hash";

        assertTrue(redirectService.isHashValid(url, validHash));
        assertFalse(redirectService.isHashValid(url, invalidHash));
    }

    @Test
    void testSendAnalyze() {
        RedirectDto redirectDto = RedirectDto.builder()
                .url("http://example.com")
                .campaignId("campaign123")
                .userAgent("Mozilla/5.0")
                .redirectTime(Timestamp.from(Instant.now()))
                .build();

        redirectService.sendAnalyze(redirectDto);

        verify(kafkaPublisher).sendMessage(redirectDto);
    }
}