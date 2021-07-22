package com.tdtin.springdatacommon.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.OffsetDateTime;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.tdtin.springdatacommon.dto.AddressDto;
import com.tdtin.springdatacommon.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    /**
     * set up before each test
     *
     * @throws Exception exception
     */
    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    /**
     * Should create user
     * @throws Exception exception
     */
    @Test
    public void create_shouldCreateUser() throws Exception {
        this.mockMvc
                .perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)

                        .content(
                                asJsonString(createPayload()))
                        .header("tenantId", "fintech")
                )

                .andDo(print()).andExpect(status().isOk());

    }

    /**
     * Create payload body
     * @return UserDto
     */
    public UserDto createPayload() {
        return UserDto.builder()
                .username(Faker.instance().name().username())
                .validFrom(OffsetDateTime.now())
                .address(List.of(AddressDto
                        .builder()
                        .city(Faker.instance().address().city()).build()))
                .build();
    }

    /**
     * Transform dto to json
     * @param userDto {@link UserDto}
     * @return json
     * @throws JsonProcessingException exception
     */
    private String asJsonString(UserDto userDto) throws JsonProcessingException {
        return objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(userDto);
    }
}
