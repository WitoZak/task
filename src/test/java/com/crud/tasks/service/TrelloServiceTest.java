package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Mock
    private AdminConfig adminConfig;

    @Test
    void shouldFetchTrelloBoards() {
        //Given
        List<TrelloBoardDto> boardDtos = new ArrayList<>();

        when(trelloClient.getTrelloBoards()).thenReturn(boardDtos);
        //When
        List<TrelloBoardDto> returnedDtoBoards = trelloService.fetchTrelloBoards();
        //Then
        assertNotNull(returnedDtoBoards);
        assertEquals(boardDtos, returnedDtoBoards);
        assertEquals(0, returnedDtoBoards.size());
    }

    @Test
    void shouldCreateTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto();
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto();
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        when(adminConfig.getAdminMail()).thenReturn("email");

        //When
        CreatedTrelloCardDto testedCard = trelloService.createTrelloCard(trelloCardDto);
        //Then
        assertNotNull(testedCard);
        verify(simpleEmailService).send(any());
        assertEquals(createdTrelloCardDto, testedCard);
    }
}