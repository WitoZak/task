package com.crud.tasks.trello.client;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class TrelloMapperTest {

    private TrelloMapper trelloMapper;

    @BeforeEach
    public void setUp() {
        trelloMapper = new TrelloMapper();
    }

    @Test
    void mapToBoardTest() {

        //Given
        List<TrelloListDto> listDtos = new ArrayList<>();
        listDtos.add(new TrelloListDto("1", "Test list", false));

        List<TrelloBoardDto> boardDtos = new ArrayList<>();
        boardDtos.add(new TrelloBoardDto("1", "Test board", listDtos));

        //When
        List<TrelloBoard> boards = trelloMapper.mapToBoards(boardDtos);

        //Then
        assertEquals(1, boardDtos.size());
        assertEquals("1", boardDtos.get(0).getId());
        assertEquals("Test list", boardDtos.get(0).getName());
        assertEquals(1, boardDtos.get(0).getLists().size());
        assertEquals("1", boardDtos.get(0).getLists().get(0).getId());
        assertEquals("Test board", boardDtos.get(0).getLists().get(0).getName());
        assertFalse(boardDtos.get(0).getLists().get(0).isClosed());
    }

    @Test
    void mapToBoardsDtoTest() {
        //Given
        List<TrelloList> lists = new ArrayList<>();
        lists.add(new TrelloList("1", "Test list", false));

        List<TrelloBoard> boards = new ArrayList<>();
        boards.add(new TrelloBoard("1", "Test board", lists));

        //When
        List<TrelloBoardDto> boardDtos = trelloMapper.mapToBoardsDto(boards);

        //Then
        assertEquals(1, boardDtos.size());
        assertEquals("1", boardDtos.get(0).getId());
        assertEquals("Test board", boardDtos.get(0).getName());
        assertEquals(1, boardDtos.get(0).getLists().size());
        assertEquals("1", boardDtos.get(0).getLists().get(0).getId());
        assertEquals("Test list", boardDtos.get(0).getLists().get(0).getName());
        assertEquals(false, boardDtos.get(0).getLists().get(0).isClosed());
    }

    @Test
    void mapToListTest() {
        //Given
        List<TrelloListDto> listDtos = new ArrayList<>();
        listDtos.add(new TrelloListDto("1", "Test list", false));

        //When
        List<TrelloList> lists = trelloMapper.mapToList(listDtos);

        //Then
        assertEquals(1, lists.size());
        assertEquals("1", lists.get(0).getId());
        assertEquals("Test list", lists.get(0).getName());
        assertEquals(false, lists.get(0).isClosed());
    }

    @Test
    void mapToListDtoTest() {
        //Given
        List<TrelloList>  lists = new ArrayList<>();
        lists.add(new TrelloList("1", "Test list", false));

        //When
        List<TrelloListDto> listDtos = trelloMapper.mapToListDto(lists);

        //Then
        assertEquals(1, listDtos.size());
        assertEquals("1", listDtos.get(0).getId());
        assertEquals("Test list", listDtos.get(0).getName());
        assertEquals(false, listDtos.get(0).isClosed());
    }

    @Test
    void mapToCardTest() {
        //Given
        TrelloCardDto cardDto = new TrelloCardDto("Test card", "Test description", "top", "2");

        //When
        TrelloCard card = trelloMapper.mapToCard(cardDto);

        //Then
        assertEquals("Test card", card.getName());
        assertEquals("Test description", card.getDescription());
        assertEquals("top", card.getPos());
        assertEquals("2", card.getListId());
    }

    @Test
    void mapToCardDtoTest() {
        //Given
        TrelloCard card = new TrelloCard("Test card", "Test description", "top", "2");

        //When
        TrelloCardDto cardDto = trelloMapper.mapToCardDto(card);

        //Then
        assertEquals("Test card", cardDto.getName());
        assertEquals("Test description", cardDto.getDescription());
        assertEquals("top", cardDto.getPos());
        assertEquals("2", cardDto.getListId());
    }
}
