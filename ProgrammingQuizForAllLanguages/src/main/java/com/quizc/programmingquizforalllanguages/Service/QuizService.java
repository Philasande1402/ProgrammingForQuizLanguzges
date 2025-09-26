package com.quizc.programmingquizforalllanguages.Service;

import com.quizc.programmingquizforalllanguages.Model.Quiz;
import com.quizc.programmingquizforalllanguages.Repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    public void addQuiz(Quiz quiz){
        quizRepository.save(quiz);
    }
}
