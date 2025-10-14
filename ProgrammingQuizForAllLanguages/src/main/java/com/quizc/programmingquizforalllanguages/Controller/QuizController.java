package com.quizc.programmingquizforalllanguages.Controller;

import com.quizc.programmingquizforalllanguages.Model.Quiz;
import com.quizc.programmingquizforalllanguages.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
public class QuizController {

    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    @Autowired
    private QuizService quizService;

    @PostMapping("save")
    public String saveQuestion(@ModelAttribute Quiz quiz) {
        try {
            quizService.save(quiz);
            logger.info("Question saved successfully with ID: {}", quiz.getId());
        } catch (Exception e) {
            logger.error("Error saving question: {}", e.getMessage(), e);
            return "error"; // Redirect to error page
        }
        return "redirect:/getAllQuiz";
    }

    @GetMapping("getAllQuiz")
    public ModelAndView getAllQuiz() {
        ModelAndView m = new ModelAndView();
        try {
            List<Quiz> list = quizService.getAllQuiz();
            m.addObject("quiz", list);
            m.setViewName("getAllQuiz");
            logger.info("Retrieved {} quiz questions", list.size());
        } catch (Exception e) {
            logger.error("Error retrieving all quiz questions: {}", e.getMessage(), e);
            m.addObject("error", "Unable to load quiz questions. Please try again.");
            m.setViewName("error");
        }
        return m;
    }

    // Display the update form (GET request)
    @GetMapping("/updateDetails/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        try {
            Quiz quiz = quizService.findById(id);
            model.addAttribute("quiz", quiz);
            logger.info("Displaying update form for quiz ID: {}", id);
        } catch (Exception e) {
            logger.error("Error retrieving quiz for update with ID {}: {}", id, e.getMessage(), e);
            model.addAttribute("error", "Quiz not found with ID: " + id);
            return "error";
        }
        return "updateDetails";
    }

    // Process the form submission (POST request)
    @PostMapping("/updateDetails/{id}")
    public String updateEmployee(@PathVariable Long id,
                                 @ModelAttribute("quiz") Quiz quiz,
                                 BindingResult result,
                                 Model model) {
        if (result.hasErrors()) {
            return "updateDetails";
        }

        try {
            quiz.setId(id);
            quizService.save(quiz);
            logger.info("Quiz updated successfully with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error updating quiz with ID {}: {}", id, e.getMessage(), e);
            model.addAttribute("error", "Unable to update quiz. Please try again.");
            return "error";
        }
        return "redirect:/getAllQuiz";
    }

    //Delete method
    @RequestMapping("/delete_quiz/{id}")
    public String deleteById(@PathVariable Long id, Model model) {
        try {
            quizService.deleteById(id);
            logger.info("Quiz deleted successfully with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error deleting quiz with ID {}: {}", id, e.getMessage(), e);
            model.addAttribute("error", "Unable to delete quiz. Please try again.");
            return "error";
        }
        return "redirect:/getAllQuiz";
    }

    // Display the search form
    @GetMapping("/searchByCategory")
    public String showSearchForm() {
        return "searchByCategory";
    }

    // Handle the search - using RequestParam instead of PathVariable
    @GetMapping("/getByCategory")
    public ModelAndView findByCategory(@RequestParam String category) {
        ModelAndView m = new ModelAndView();
        try {
            List<Quiz> list = quizService.findByCategory(category);
            m.addObject("quiz", list);
            m.addObject("selectedCategory", category);
            m.setViewName("searchOutPutQuiz");
            logger.info("Found {} quiz questions for category: {}", list.size(), category);
        } catch (Exception e) {
            logger.error("Error searching quiz by category '{}': {}", category, e.getMessage(), e);
            m.addObject("error", "Unable to search quiz questions. Please try again.");
            m.setViewName("error");
        }
        return m;
    }
}