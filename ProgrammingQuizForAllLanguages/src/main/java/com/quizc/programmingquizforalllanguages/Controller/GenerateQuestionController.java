package com.quizc.programmingquizforalllanguages.Controller;

import com.quizc.programmingquizforalllanguages.Model.PersonalInfo;
import com.quizc.programmingquizforalllanguages.Model.QuestionWrapper;
import com.quizc.programmingquizforalllanguages.Model.Questions;
import com.quizc.programmingquizforalllanguages.Model.Response;
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

    //Create quiz questions
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

    //Get quiz questions
    @GetMapping("/get")
    public String getQuizQuestions(@RequestParam Long quizId, Model model, HttpSession session) {
        try {
            List<QuestionWrapper> questionWrapper = questionService.getQuizQuestion(quizId);

            if (questionWrapper.isEmpty()) {
                model.addAttribute("error", "No questions found for this quiz ID: " + quizId);
                return "take-quiz"; // Return to the same page with error message
            }

            // Add data to the model for Thymeleaf template
            model.addAttribute("quizzes", questionWrapper);//this line have all questions
            model.addAttribute("totalQuestions", questionWrapper.size());
            model.addAttribute("questionSetId", quizId);

            // Add quiz title (you might want to fetch this from your service)
            model.addAttribute("quizTitle", "Quiz #" + quizId);

            // Add user info if available in session
            String userFirstName = (String) session.getAttribute("userFirstName");
            if (userFirstName != null) {
                model.addAttribute("userFirstName", userFirstName);
            }

            return "take-quiz"; // Return the template name without redirect

        } catch (Exception e) {
            model.addAttribute("error", "Error loading quiz: " + e.getMessage());
            return "take-quiz";
        }
    }

    @PostMapping("/submit")
    public String submit(@RequestParam Long questionSetId,
                         @RequestParam List<Response> responses,  // Remove @RequestBody
                         Model model) {
        int score = questionService.calculateQuiz(questionSetId, responses);
        int totalQuestions = responses.size();

        model.addAttribute("score", score);
        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("percentage", (score * 100) / totalQuestions);

        return "result";
    }

}