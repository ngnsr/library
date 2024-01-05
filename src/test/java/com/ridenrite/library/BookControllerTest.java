package com.ridenrite.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createBook() throws Exception {
        Book testBook = getTestBook();

        String testBookJson = objectMapper.writeValueAsString(testBook);
        MvcResult mvcResult = mockMvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testBookJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        Book bookResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Book.class);

        assertThat(bookResult.getId(), notNullValue());
        assertThat(bookResult.getName(), equalTo(testBook.getName()));
        assertThat(bookResult.getAuthor(), equalTo(testBook.getAuthor()));
        assertThat(bookResult.getISBN(), equalTo(testBook.getISBN()));
        assertThat(bookResult.getDescription(), equalTo(testBook.getDescription()));
        assertThat(bookResult.getReleaseYear(), equalTo(testBook.getReleaseYear()));
        assertThat(bookResult.getPublisher(), equalTo(testBook.getPublisher()));
    }

    @Test
    void findBook() throws Exception {
        Book testBook = getTestBook();
        String testBookJson = objectMapper.writeValueAsString(testBook);
        mockMvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testBookJson))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(get("/book")
                        .param("page", "0")
                        .param("size", "1")
                        .param("query", "qwet"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", empty()));

        mockMvc.perform(get("/book")
                        .param("page", "0")
                        .param("size", "1")
                        .param("query", "author"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));

    }

    @NotNull
    private static Book getTestBook() {
        Book testBook = new Book();
        testBook.setName("Test name");
        testBook.setAuthor("Test author");
        testBook.setDescription("Test description");
        testBook.setPublisher("Test publisher");
        testBook.setISBN("Test isbn");
        testBook.setReleaseYear(2000);
        return testBook;
    }
}