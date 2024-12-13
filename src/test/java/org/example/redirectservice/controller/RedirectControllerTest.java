package org.example.redirectservice.controller;


import org.example.redirectservice.service.RedirectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@WebMvcTest(RedirectController.class)
class RedirectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RedirectService redirectService;

    @Test
     void testValidRedirect() throws Exception {
        String expectedUrl = "http://example.com";
        String validHash = "validHash";
        String campaignId = "123";
        String userAgent = "Mozilla/5.0";

        when(redirectService.isHashValid(expectedUrl, validHash)).thenReturn(true);


        mockMvc.perform(get("/api/v1/refLink")
                        .param("url", expectedUrl)
                        .param("hash", validHash)
                        .param("campaignId", campaignId)
                        .header("User-Agent", userAgent))
                .andExpect(status().isFound()) // 302 Found
                .andExpect(header().string("Location", expectedUrl));
    }

    @Test
     void testInvalidRedirect() throws Exception {
        String expectedUrl = "http://example.com";
        String invalidHash = "invalidHash";
        String campaignId = "123";
        String userAgent = "Mozilla/5.0";


        when(redirectService.isHashValid(expectedUrl, invalidHash)).thenReturn(false);


        mockMvc.perform(get("/api/v1/refLink")
                        .param("url", expectedUrl)
                        .param("hash", invalidHash)
                        .param("campaignId", campaignId)
                        .header("User-Agent", userAgent))
                .andExpect(status().isNotFound());
    }
}
