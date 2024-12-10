package org.example.redirectservice.kafka;



import org.example.redirectservice.dto.RedirectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaPublisher implements MessagePublisher {

    private final KafkaTemplate<String, RedirectDto> kafkaTemplate;
    private final String topic;

    @Autowired
    public KafkaPublisher(KafkaTemplate<String, RedirectDto> kafkaTemplate, @Value("${kafka.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }


    @Override
    public void sendMessage(RedirectDto message) {
        kafkaTemplate.send(topic, message);
    }
}
