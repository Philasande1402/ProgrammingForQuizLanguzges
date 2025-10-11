package com.quizc.programmingquizforalllanguages.Controller;

import com.quizc.programmingquizforalllanguages.Model.PersonalInfo;
import com.quizc.programmingquizforalllanguages.Model.QuestionWrapper;
import com.quizc.programmingquizforalllanguages.Model.Questions;
import com.quizc.programmingquizforalllanguages.Repository.QuestionRepository;
import com.quizc.programmingquizforalllanguages.Service.QuestionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                                 @RequestParam String title,
                                 Model model) {

        // Call service to create quiz and get the created quiz object
        Questions createdQuiz = questionService.createQuiz(category, numQ, title);

        // Get ALL quiz IDs from the database
        List<Long> allQuizIds = questionService.getAllQuizIds();

        // Add attributes to model for the success page
        model.addAttribute("title", createdQuiz.getTitle());
        model.addAttribute("category", category);
        model.addAttribute("numQ", numQ);
        model.addAttribute("quizId", createdQuiz.getId());
        model.addAttribute("allQuizIds", allQuizIds); // Add all IDs

        // Return the success page view name
        return "success"; // This will resolve to success.html
    }

    //I have to change this method but i won't delete it now i will start by create the that
    // i'm thinking it will help me on this project to display on the screen the way i want it to be

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

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Long id){
       return questionService.getQuizQuestion(id);
    }
}