package com.ridenrite.library.controller;

import com.ridenrite.library.AbstractController;
import com.ridenrite.library.dto.ClientDto;
import com.ridenrite.library.entity.Client;
import com.ridenrite.library.repository.ClientRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ClientControllerTest extends AbstractController {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void createClient() throws Exception {
        ClientDto clientDto = mockClient();
        createAndAssert(clientDto);
    }

    private Client createAndAssert(ClientDto dto) throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", not(nullValue())))
                .andExpect(jsonPath("$.firstName", is(dto.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(dto.getLastName())))
                .andExpect(jsonPath("$.email", is(dto.getEmail())))
                .andExpect(jsonPath("$.phone", is(dto.getPhone())))
                .andReturn();

        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Client.class);
    }

    @AfterEach
    void clearAfter() {
        clientRepository.deleteAll();
    }

    @Test
    void getClient() throws Exception {
        ClientDto testDto = mockClient();
        Client client = createAndAssert(testDto);

        mockMvc.perform(get("/client/" + client.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(client.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(testDto.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(testDto.getLastName())))
                .andExpect(jsonPath("$.email", is(testDto.getEmail())))
                .andExpect(jsonPath("$.phone", is(testDto.getPhone())));
    }

    @Test
    void getAllClients() throws Exception {
        ClientDto testDto1 = mockClient();
        ClientDto testDto2 = new ClientDto();
        testDto2.setFirstName("Bohdan");
        testDto2.setLastName("Khmelnytskyi");
        testDto2.setEmail("bohdan@gmail.com");
        testDto2.setPhone("+380981234567");

        createAndAssert(testDto2);
        createAndAssert(testDto1);

//        get all clients page
        mockMvc.perform(get("/client")
                        .param("page", "0")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content.[*].firstName",
                        containsInAnyOrder(testDto1.getFirstName(), testDto2.getFirstName())))
                .andExpect(jsonPath("$.content.[*].phone",
                        containsInAnyOrder(testDto1.getPhone(), testDto2.getPhone())));

//        get page with filter by name
        mockMvc.perform(get("/client")
                        .param("page", "0")
                        .param("size", "10")
                        .param("query", testDto1.getFirstName().toLowerCase()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[0].firstName", is(testDto1.getFirstName())));

//        get page with filter by phone
        mockMvc.perform(get("/client")
                        .param("page", "0")
                        .param("size", "10")
                        .param("query", testDto2.getPhone()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[0].phone", is(testDto2.getPhone())));
    }

    @Test
    void editClient() throws Exception {
        ClientDto dto = mockClient();
        Client client = createAndAssert(dto);
        dto.setFirstName("Alex");
        mockMvc.perform(put("/client/" + client.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(client.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(dto.getFirstName())));
    }

    @Test
    void deleteClient() throws Exception {
        ClientDto dto = mockClient();
        Client client = createAndAssert(dto);

        mockMvc.perform(get("/client/" + client.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/client/" + client.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/client/" + client.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void addExistingClient() throws Exception{
        ClientDto dto = mockClient();
        createAndAssert(dto);

        mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status", equalTo(HttpStatus.CONFLICT.name())))
                .andExpect(jsonPath("$.errors", containsInAnyOrder("Client already exist")));
    }

    private ClientDto mockClient() {
        ClientDto clientDto = new ClientDto();
        clientDto.setFirstName("Oleg");
        clientDto.setLastName("Neijpapa");
        clientDto.setEmail("example@gmail.com");
        clientDto.setPhone("+380123456789");
        return clientDto;
    }

}