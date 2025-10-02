package com.quizc.programmingquizforalllanguages.Service;

import com.quizc.programmingquizforalllanguages.Model.Questions;
import com.quizc.programmingquizforalllanguages.Model.Quiz;
import com.quizc.programmingquizforalllanguages.Repository.QuestionRepository;
import com.quizc.programmingquizforalllanguages.Repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Quiz> quizzes = quizRepository.findRandomQuestionByCategory(category,numQ);

        Questions questions = new Questions();

        questions.setTitle(title);
        questions.setQuizzes(quizzes);

        questionRepository.save(questions);

        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }
}
