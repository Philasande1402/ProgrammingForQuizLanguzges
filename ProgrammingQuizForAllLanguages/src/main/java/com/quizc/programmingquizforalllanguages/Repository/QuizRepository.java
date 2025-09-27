package com.quizc.programmingquizforalllanguages.Repository;

import com.quizc.programmingquizforalllanguages.Model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> {
    List<Quiz> findByCategory(String category);
    List<Quiz> findByDifficultyLevel(String difficultyLevel);
    List<Quiz> findByCategoryAndDifficultyLevel(String category, String difficultyLevel);
}
