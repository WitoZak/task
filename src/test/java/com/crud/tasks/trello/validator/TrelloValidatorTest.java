package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrelloValidatorTest {

    @Test
    void shouldValidateTrelloBoardWithTestInName() {
        //Given
        TrelloValidator trelloValidator = new TrelloValidator();
        TrelloBoard trelloBoard1 = new TrelloBoard("id", "name", new ArrayList<>());
        TrelloBoard trelloBoard2 = new TrelloBoard("id", "test", new ArrayList<>());
        //When
        List<TrelloBoard> returnedList = trelloValidator.validateTrelloBoards(List.of(trelloBoard1, trelloBoard2));
        //Then
        assertEquals(1, returnedList.size());
    }

    @Test
    void shouldValidateTrelloBoardWithoutTestInName() {
        //Given
        TrelloValidator trelloValidator = new TrelloValidator();
        TrelloBoard trelloBoard1 = new TrelloBoard("id", "name1", new ArrayList<>());
        TrelloBoard trelloBoard2 = new TrelloBoard("id", "test2", new ArrayList<>());
        //When
        List<TrelloBoard> returnedList = trelloValidator.validateTrelloBoards(List.of(trelloBoard1, trelloBoard2));
        //Then
        assertEquals(2, returnedList.size());

    }
}