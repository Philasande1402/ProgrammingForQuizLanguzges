package com.quizc.programmingquizforalllanguages.Controller;

import com.quizc.programmingquizforalllanguages.Model.PersonalInfo;
import com.quizc.programmingquizforalllanguages.Model.QuestionWrapper;
import com.quizc.programmingquizforalllanguages.Model.Questions;
import com.quizc.programmingquizforalllanguages.Repository.QuestionRepository;
import com.quizc.programmingquizforalllanguages.Service.QuestionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    // This method serves the HTML page using QuestionWrapper
    @GetMapping("/quiz/take")
    public String takeQuiz(@RequestParam Long quizId, Model model, HttpSession session) {
        // Add user info if available
        String userFirstName = (String) session.getAttribute("userFirstName");
        if (userFirstName != null) {
            model.addAttribute("userFirstName", userFirstName);
        }

        // Get the quiz questions using your existing service method
        ResponseEntity<List<QuestionWrapper>> response = questionService.getQuizQuestion(quizId);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null || response.getBody().isEmpty()) {
            return "redirect:/quiz/search?error=Quiz+not+found";
        }

        List<QuestionWrapper> quizzes = response.getBody();

        // Add data to the model for Thymeleaf
        model.addAttribute("quizTitle", "Programming Quiz"); // You can customize this
        model.addAttribute("totalQuestions", quizzes.size());
        model.addAttribute("questionSetId", quizId);
        model.addAttribute("quizzes", quizzes); // This uses QuestionWrapper objects

        return "take-quiz"; // This renders your HTML page
    }

    // Your existing API method - keep it as is
    @GetMapping("/get")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@RequestParam Long quizId) {
        return questionService.getQuizQuestion(quizId);
    }
}