package com.quizc.programmingquizforalllanguages.Controller;

import com.quizc.programmingquizforalllanguages.Model.Quiz;
import com.quizc.programmingquizforalllanguages.Repository.QuizRepository;
import com.quizc.programmingquizforalllanguages.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class QuizController {

    @Autowired
    private QuizService quizService;


    @PostMapping("save")
    public String saveQuestion(@ModelAttribute Quiz quiz) {
        quizService.save(quiz);
        return "redirect:/success";
    }

}
