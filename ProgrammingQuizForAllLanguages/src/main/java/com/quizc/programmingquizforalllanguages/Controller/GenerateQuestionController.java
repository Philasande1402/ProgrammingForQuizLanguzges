package com.quizc.programmingquizforalllanguages.Controller;

import com.quizc.programmingquizforalllanguages.Model.PersonalInfo;
import com.quizc.programmingquizforalllanguages.Model.Questions;
import com.quizc.programmingquizforalllanguages.Repository.QuestionRepository;
import com.quizc.programmingquizforalllanguages.Service.QuestionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller  // Changed from @RestController
@RequestMapping("/quiz")
public class GenerateQuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/create")
    public String createQuestion(@RequestParam String category,
                                 @RequestParam int numQ,
                                 @RequestParam String title) {
        // Call service and return the view name
        return questionService.createQuiz(category, numQ, title);
    }

    @GetMapping("/take/{id}")
    public String takeQuiz(@PathVariable Long id, Model model, HttpSession session) {
        Questions questions = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        // Get user from session
        PersonalInfo user = (PersonalInfo) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("userFirstName", user.getFirstName());
        }

        model.addAttribute("quizTitle", questions.getTitle());
        model.addAttribute("quizzes", questions.getQuizzes());
        model.addAttribute("totalQuestions", questions.getQuizzes().size());
        model.addAttribute("questionSetId", id);

        return "take-quiz";
    }
}