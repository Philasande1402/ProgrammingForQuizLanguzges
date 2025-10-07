package com.quizc.programmingquizforalllanguages.Repository;

import com.quizc.programmingquizforalllanguages.Model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByCategory(String category);

    // MySQL version (use RAND())
    @Query(value = "SELECT * FROM Quiz_tbl q WHERE q.category = :category ORDER BY RAND() LIMIT :numQ", nativeQuery = true)
    List<Quiz> findRandomQuestionByCategory(@Param("category") String category, @Param("numQ") int numQ);
}