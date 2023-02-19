package integration.crolopez.thecrmservice.customers.infrastructure.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import crolopez.thecrmservice.Main;
import crolopez.thecrmservice.model.CustomerDTODto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class ControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String POST_PATH = "/v1/customer";

    @Test
    public void whenGetCustomers_thenTheExpectedResponseIsReturned() throws Exception {
        var response = mockMvc.perform(
                        get(POST_PATH)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        List<CustomerDTODto> customerList = objectMapper
                .readValue(response.getContentAsString(), List.class);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(0, customerList.stream().count());
    }

}
