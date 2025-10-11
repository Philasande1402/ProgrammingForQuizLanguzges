package com.quizc.programmingquizforalllanguages.Repository;

import com.quizc.programmingquizforalllanguages.Model.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Questions,Long> {
    @Query("SELECT q.id FROM Questions q ORDER BY q.id")
    List<Long> findAllQuizIds();
}
