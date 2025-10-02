package com.quizc.programmingquizforalllanguages.Repository;

import com.quizc.programmingquizforalllanguages.Model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> {
    List<Quiz> findByCategory(String category);
    List<Quiz> findByDifficultyLevel(String difficultyLevel);
    List<Quiz> findByCategoryAndDifficultyLevel(String category, String difficultyLevel);

    @Query(value = "SELECT * " +
                   "FROM quiz q " +
                   "WHERE q.category = :category " +
                   "ORDER BY RAND() LIMIT :numQ", nativeQuery = true)
    List<Quiz> findRandomQuestionByCategory(@Param("category") String category, @Param("numQ") int numQ);

}
