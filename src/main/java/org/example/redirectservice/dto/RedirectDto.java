package org.example.redirectservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RedirectDto {

    private String url;
    private String hash;
    private String campaignId;
    private String userAgent;

}
