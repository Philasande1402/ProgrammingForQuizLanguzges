package com.quizc.programmingquizforalllanguages.Repository;

import com.quizc.programmingquizforalllanguages.Model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Integer> {
}
