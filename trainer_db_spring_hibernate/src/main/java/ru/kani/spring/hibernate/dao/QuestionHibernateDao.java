package ru.kani.spring.hibernate.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kani.domain.model.OpenQuestionCard;
import ru.kani.domain.repo.QuestionRepository;
import ru.kani.spring.hibernate.entity.OpenQuestionCardEntity;
import ru.kani.spring.hibernate.mapper.QuestionMapper;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class QuestionHibernateDao implements QuestionRepository {
    private static final Logger logger = LogManager.getLogger(QuestionHibernateDao.class);
    private final QuestionMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    public QuestionHibernateDao(QuestionMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OpenQuestionCard> findAll() {
        logger.debug("Выбираем всех");
        List<OpenQuestionCardEntity> questionCardEntities = entityManager
                .createQuery("SELECT question FROM OpenQuestionCardEntity question", OpenQuestionCardEntity.class)
                .getResultList();
        return mapper.mapToModel(questionCardEntities);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OpenQuestionCard> findById(Long id) {
        List<OpenQuestionCardEntity> entity = entityManager.createQuery("SELECT question FROM OpenQuestionCardEntity question WHERE question.code = ?1", OpenQuestionCardEntity.class)
                .setParameter(1, id)
                .getResultList();
        if (!entity.isEmpty()) {
            OpenQuestionCard question = mapper.mapToModel(entity.get(0));
            return Optional.of(question);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void add(OpenQuestionCard question) {
        OpenQuestionCardEntity entity = mapper.mapToEntity(question);
        entityManager.persist(entity);
    }

    @Override
    public void update(OpenQuestionCard question) {
        OpenQuestionCardEntity entity = mapper.mapToEntity(question);
        entityManager.merge(entity);
    }

    @Override
    public void remove(Long id) {
        OpenQuestionCardEntity entity = entityManager.createQuery("SELECT question FROM OpenQuestionCardEntity question WHERE question.code = ?1", OpenQuestionCardEntity.class)
                .setParameter(1, id)
                .getSingleResult();
        entityManager.remove(entity);
    }
}
