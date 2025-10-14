package com.quizc.programmingquizforalllanguages.Service;

import com.quizc.programmingquizforalllanguages.Model.Quiz;
import com.quizc.programmingquizforalllanguages.Repository.QuestionRepository;
import com.quizc.programmingquizforalllanguages.Repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private static final Logger logger = LoggerFactory.getLogger(QuizService.class);

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuestionRepository questionRepository;

    public void save(Quiz quiz) {
        try {
            quizRepository.save(quiz);
            logger.debug("Quiz saved successfully with ID: {}", quiz.getId());
        } catch (Exception e) {
            logger.error("Error saving quiz: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save quiz", e);
        }
    }

    public List<Quiz> getAllQuiz() {
        try {
            List<Quiz> quizzes = quizRepository.findAll();
            logger.debug("Retrieved {} quiz questions from database", quizzes.size());
            return quizzes;
        } catch (Exception e) {
            logger.error("Error retrieving all quiz questions: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve quiz questions", e);
        }
    }

    public Quiz findById(Long id) {
        try {
            Optional<Quiz> quiz = quizRepository.findById(id);
            if (quiz.isPresent()) {
                logger.debug("Found quiz with ID: {}", id);
                return quiz.get();
            } else {
                logger.warn("Quiz not found with ID: {}", id);
                throw new RuntimeException("Quiz not found with ID: " + id);
            }
        } catch (RuntimeException e) {
            throw e; // Re-throw our custom exception
        } catch (Exception e) {
            logger.error("Error finding quiz by ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve quiz", e);
        }
    }

    public void deleteById(Long id) {
        try {
            if (quizRepository.existsById(id)) {
                quizRepository.deleteById(id);
                logger.debug("Deleted quiz with ID: {}", id);
            } else {
                logger.warn("Attempted to delete non-existent quiz with ID: {}", id);
                throw new RuntimeException("Quiz not found with ID: " + id);
            }
        } catch (RuntimeException e) {
            throw e; // Re-throw our custom exception
        } catch (Exception e) {
            logger.error("Error deleting quiz with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to delete quiz", e);
        }
    }

    public List<Quiz> findByCategory(String category) {
        try {
            List<Quiz> quizzes = quizRepository.findByCategory(category);
            logger.debug("Found {} quiz questions for category: {}", quizzes.size(), category);
            return quizzes;
        } catch (Exception e) {
            logger.error("Error finding quiz by category '{}': {}", category, e.getMessage(), e);
            throw new RuntimeException("Failed to search quiz by category", e);
        }
    }

    //Get all questions ID
    public List<Long> getAllQuizIds() {
        return questionRepository.findAllQuizIds();
    }
}