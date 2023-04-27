package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@WebMvcTest(TrelloController.class)
class TrelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrelloFacade trelloFacade;

    @Test
    void getTrelloBoards() throws Exception {
        //Given
        List<TrelloListDto> trelloListDtoList = List.of(
                new TrelloListDto("1", "Test list 1", false),
                new TrelloListDto("2", "Test list 2", false)
        );
        List<TrelloBoardDto> trelloBoardDtoList = List.of(
                new TrelloBoardDto("1", "Test Task 1", trelloListDtoList),
                new TrelloBoardDto("2", "Test Task 2", Collections.emptyList())
        );
        when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoardDtoList);

        //When & Then
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/trello/boards")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is("1")))
                .andExpect(jsonPath("$[0].name", Matchers.is("Test Task 1")))
                .andExpect(jsonPath("$[0].lists", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].lists[0].id", Matchers.is("1")))
                .andExpect(jsonPath("$[0].lists[0].name", Matchers.is("Test list 1")))
                .andExpect(jsonPath("$[0].lists[0].closed", Matchers.is(false)))
                .andExpect(jsonPath("$[0].lists[1].id", Matchers.is("2")))
                .andExpect(jsonPath("$[0].lists[1].name", Matchers.is("Test list 2")))
                .andExpect(jsonPath("$[0].lists[1].closed", Matchers.is(false)))
                .andExpect(jsonPath("$[1].id", Matchers.is("2")))
                .andExpect(jsonPath("$[1].name", Matchers.is("Test Task 2")))
                .andExpect(jsonPath("$[1].lists", Matchers.hasSize(0)));

    }

    @Test
    void shouldFetchEmptyTrelloBoards() throws Exception {
        //Given
        when(trelloFacade.fetchTrelloBoards()).thenReturn(List.of());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/trello/boards")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)) // or isOk()
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchTrelloBoards() throws Exception {
        //Given
        List<TrelloListDto> trelloLists = List.of(new TrelloListDto("1", "Test list", false));
        List<TrelloBoardDto> trelloBoards = List.of(new TrelloBoardDto("1", "Test Task", trelloLists));
        when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoards);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/trello/boards")
                        .contentType(MediaType.APPLICATION_JSON))
                // Trello board fields
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is("1")))
                .andExpect(jsonPath("$[0].name", Matchers.is("Test Task")))
                // Trello list fields
                .andExpect(jsonPath("$[0].lists", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].lists[0].id", Matchers.is("1")))
                .andExpect(jsonPath("$[0].lists[0].name", Matchers.is("Test list")))
                .andExpect(jsonPath("$[0].lists[0].closed", Matchers.is(false)));
    }

    @Test
    void shouldCreateTrelloCard() throws Exception {
        //Given
        TrelloCardDto trelloCardDto =
                new TrelloCardDto("Test", "Test description", "top", "1");

        CreatedTrelloCardDto createdTrelloCardDto =
                new CreatedTrelloCardDto("232", "Test", "http://test.com");

        when(trelloFacade.createCard(any(TrelloCardDto.class))).thenReturn(createdTrelloCardDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(trelloCardDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/trello/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(jsonPath("$.id", Matchers.is("232")))
                .andExpect(jsonPath("$.name", Matchers.is("Test")))
                .andExpect(jsonPath("$.shortUrl", Matchers.is("http://test.com")));
    }
}