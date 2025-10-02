package com.quizc.programmingquizforalllanguages.Controller;

import com.quizc.programmingquizforalllanguages.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("quiz/question")
public class GenerateQuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("create")
    public ResponseEntity<String> createQuestion(@RequestParam String category,@RequestParam int numQ,@RequestParam String title){
         return questionService.createQuiz(category,numQ,title);
    }
}
