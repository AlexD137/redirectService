package org.example.redirectservice.kafka;

import org.example.redirectservice.dto.RedirectDto;

public interface MessagePublisher {
    void sendMessage(RedirectDto message);
}
