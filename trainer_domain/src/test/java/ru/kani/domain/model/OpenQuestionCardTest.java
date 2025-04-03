package ru.kani.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenQuestionCardTest {

    private OpenQuestionCard card;

    @BeforeEach
    void setUp() {
        card = new OpenQuestionCard(1L,"Какой цвет у неба?", "Голубой");
    }

    @Test
    @DisplayName("Создание карты с корректными данными")
    void testCreateCardWithValidData() {
        assertNotNull(card);
        assertEquals("Какой цвет у неба?", card.getQuestion());
    }

    @Test
    @DisplayName("Проверка правильного ответа")
    void testCheckCorrectAnswer() {
        assertTrue(card.checkAnswer("Голубой"));
    }

    @Test
    @DisplayName("Проверка правильного ответа с разным регистром")
    void testCheckCorrectAnswerIgnoreCase() {
        assertTrue(card.checkAnswer("голубой"));
    }

    @Test
    @DisplayName("Проверка правильного ответа с лишними пробелами")
    void testCheckCorrectAnswerWithSpaces() {
        assertTrue(card.checkAnswer("  Голубой  "));
    }

    @Test
    @DisplayName("Проверка неправильного ответа")
    void testCheckIncorrectAnswer() {
        assertFalse(card.checkAnswer("Зелёный"));
    }

    @Test
    @DisplayName("Проверка исключения при пустом вопросе")
    void testExceptionForEmptyQuestion() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new OpenQuestionCard(1L,"", "Ответ"));
        assertEquals("question не может быть пустым", exception.getMessage());
    }

    @Test
    @DisplayName("Проверка исключения при пустом ответе")
    void testExceptionForEmptyAnswer() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new OpenQuestionCard(1L,"Вопрос", ""));
        assertEquals("expectedAnswer не может быть пустым", exception.getMessage());
    }

    @Test
    @DisplayName("Проверка исключения при null в вопросе")
    void testExceptionForNullQuestion() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new OpenQuestionCard(1L,null, "Ответ"));
        assertEquals("question не может быть пустым", exception.getMessage());
    }

    @Test
    @DisplayName("Проверка исключения при null в ответе")
    void testExceptionForNullAnswer() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new OpenQuestionCard(1L,"Вопрос", null));
        assertEquals("expectedAnswer не может быть пустым", exception.getMessage());
    }
}
