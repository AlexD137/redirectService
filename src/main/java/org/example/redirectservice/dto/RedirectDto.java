package org.example.redirectservice.dto;
import lombok.Builder;
import lombok.Data;


import java.sql.Timestamp;


@Data
@Builder
public class RedirectDto {

    private String url;
    private String campaignId;
    private String userAgent;
    private Timestamp redirectTime;
}
