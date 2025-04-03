package ru.kani.dao;


import org.springframework.stereotype.Repository;
import ru.kani.domain.model.OpenQuestionCard;
import ru.kani.domain.repo.QuestionRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class QuestionJdbcDao implements QuestionRepository {
    private static final String DDL_QUERY = """
          CREATE TABLE questions (ID int AUTO_INCREMENT PRIMARY KEY, question TEXT NOT NULL, expected_answer TEXT NOT NULL)
          """;
    private static final String FIND_ALL_QUERY = """
          SELECT id, question, expected_answer FROM questions
          """;
    private static final String FIND_BY_CODE_QUERY = """
          SELECT id, question, expected_answer FROM questions WHERE id = ?
          """;
    private static final String INSERT_QUERY = """
          INSERT INTO questions (id, question, expected_answer) VALUES (?, ?, ?)
          """;
    private static final String UPDATE_QUERY = """
          UPDATE questions SET question=?, expected_answer=? WHERE id=?
          """;
    private static final String DELETE_QUERY = """
          DELETE FROM questions WHERE id=?
          """;
    private final DataSource dataSource;

    public QuestionJdbcDao(DataSource dataSource) {
        this.dataSource = dataSource;
        initDataBase();
    }

    public void initDataBase() {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(DDL_QUERY)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OpenQuestionCard> findAll() {
        List<OpenQuestionCard> questions = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                OpenQuestionCard question = new OpenQuestionCard(resultSet.getLong("id"),
                        resultSet.getString("question"),
                        resultSet.getString("expected_answer"));
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    @Override
    public Optional<OpenQuestionCard> findById(Long id) {
        List<OpenQuestionCard> questions = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_CODE_QUERY)) {
            statement.setString(1, String.valueOf(id));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                OpenQuestionCard question = new OpenQuestionCard(resultSet.getLong("id"),
                        resultSet.getString("question"),
                        resultSet.getString("expected_answer")
                );
                questions.add(question);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions.isEmpty() ? Optional.empty() : Optional.of(questions.get(0));
    }

    @Override
    public void add(OpenQuestionCard question) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setLong(1, question.getId());
            statement.setString(2, question.getQuestion());
            statement.setString(3, question.getExpectedAnswer());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(OpenQuestionCard question) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, question.getQuestion());
            statement.setString(2, question.getExpectedAnswer());
            statement.setLong(3, question.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
