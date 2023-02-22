package integration.crolopez.thecrmservice.customer.infrastructure.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import crolopez.thecrmservice.Main;
import crolopez.thecrmservice.shared.domain.dtos.CustomerDto;
import crolopez.thecrmservice.shared.infrastructure.configuration.HibernateConfiguration;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

@Ignore("Must be adapted to use OAuth2")
@SpringBootTest(classes = Main.class)
@ContextConfiguration(classes= HibernateConfiguration.class)
@AutoConfigureMockMvc
public class ControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String POST_PATH = "/v1/customer";

    /*
    @Test
    public void whenGetCustomers_thenTheExpectedResponseIsReturned() throws Exception {
        var response = mockMvc.perform(
                        get(POST_PATH)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        List<CustomerDto> customerList = objectMapper
                .readValue(response.getContentAsString(), List.class);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(0, customerList.stream().count());
    }
    */

}
