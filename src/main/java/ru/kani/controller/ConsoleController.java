package ru.kani.controller;

import org.springframework.stereotype.Component;
import ru.kani.domain.model.OpenQuestionCard;
import ru.kani.domain.service.QuestionService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class ConsoleController {
    private static final String MENU = """
            Введите [1], чтобы показать все вопросы
            Введите [2], чтобы добавить вопрос
            Введите [3], чтобы удалить вопрос
            Введите [4], чтобы найти вопрос
            Введите [5], чтобы выйти
            """;
    private final QuestionService service;
    private final Scanner scanner;

    public ConsoleController(QuestionService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void interactWithUser() {
        while (true) {
            printMenu();
            String operationCode = scanner.nextLine();
            switch (operationCode) {
                case "1" -> printAllQuestions();
                case "2" -> addQuestion();
                case "3" -> removeQuestion();
                case "4" -> findQuestion();
                case "5" -> { return; }
                default -> System.out.println("Неизвестная команда");
            }
        }
    }

    private void printMenu() {
        System.out.println(MENU);
    }

    private void printAllQuestions() {
        List<OpenQuestionCard> questions = service.getAll();
        if (questions.isEmpty()) {
            System.out.println("Список вопросов пуст");
        } else {
            questions.forEach(System.out::println);
        }
    }

    private void addQuestion() {
        System.out.println("Введите ID вопроса");
        Long id = Long.parseLong(scanner.nextLine());
        System.out.println("Введите текст вопроса");
        String question = scanner.nextLine();
        System.out.println("Введите ожидаемый ответ");
        String expectedAnswer = scanner.nextLine();
        OpenQuestionCard questionCard = new OpenQuestionCard(id, question, expectedAnswer);
        service.save(questionCard);
    }

    private void removeQuestion() {
        System.out.println("Введите ID вопроса, который хотите удалить");
        Long id = Long.parseLong(scanner.nextLine());
        Optional<OpenQuestionCard> question = service.getById(id);
        if (question.isPresent()) {
            System.out.println("Введите [Y], если точно хотите удалить вопрос: " + question.get().getQuestion());
            String confirmation = scanner.nextLine();
            if ("Y".equalsIgnoreCase(confirmation)) {
                service.delete(id);
            }
        } else {
            System.out.println("Такого вопроса нет");
        }
    }

    private void findQuestion() {
        System.out.println("Введите ID вопроса, который хотите найти");
        Long id = Long.parseLong(scanner.nextLine());
        Optional<OpenQuestionCard> question = service.getById(id);
        if (question.isPresent()) {
            System.out.println(question.get());
        } else {
            System.out.println("Такого вопроса нет");
        }
    }
}

