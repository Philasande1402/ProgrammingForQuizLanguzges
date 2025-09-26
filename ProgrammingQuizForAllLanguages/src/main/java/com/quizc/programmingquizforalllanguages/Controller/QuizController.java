package com.quizc.programmingquizforalllanguages.Controller;

import com.quizc.programmingquizforalllanguages.Model.Quiz;
import com.quizc.programmingquizforalllanguages.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("save")
    public void save(@RequestBody Quiz quiz){
        quizService.addQuiz(quiz);
    }
}
