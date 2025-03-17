package ru.kani.domain.service;

import org.springframework.stereotype.Service;
import ru.kani.domain.model.OpenQuestionCard;
import ru.kani.domain.repo.QuestionRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository repository;
    public QuestionService(QuestionRepository repository) { this.repository = repository; }

    public boolean contains(OpenQuestionCard task) {
        if (isTaskInvalid(task)) {
            return false;
        }
        return repository.findById(task.getId()).isPresent();
    }
    public List<OpenQuestionCard> getAll() { return repository.findAll(); }
    public Optional<OpenQuestionCard> getById(Long id) { return repository.findById(id); }

    public void save(OpenQuestionCard task) {
        if (isTaskInvalid(task)) {
            return;
        }
        if (contains(task)) {
            repository.update(task);
        } else {
            repository.add(task);
        }
    }
    public void delete(Long id) { repository.remove(id); }
    private boolean isTaskInvalid(OpenQuestionCard task) {
        return Objects.isNull(task) || Objects.isNull(task.getId());
    }
}

