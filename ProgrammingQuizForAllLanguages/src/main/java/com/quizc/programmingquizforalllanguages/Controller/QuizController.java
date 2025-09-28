package com.quizc.programmingquizforalllanguages.Controller;

import com.quizc.programmingquizforalllanguages.Model.Quiz;
import com.quizc.programmingquizforalllanguages.Repository.QuizRepository;
import com.quizc.programmingquizforalllanguages.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class QuizController {

    @Autowired
    private QuizService quizService;


    @PostMapping("save")
    public String saveQuestion(@ModelAttribute Quiz quiz) {
        quizService.save(quiz);
        return "redirect:/getAllQuiz";
    }

    @GetMapping("getAllQuiz")
    public ModelAndView getAllQuiz(){
        List<Quiz> list = quizService.getAllQuiz();
        ModelAndView m = new ModelAndView();
        m.addObject("quiz",list);
        m.setViewName("getAllQuiz");
        return m;
    }

    // Display the update form (GET request)
    @GetMapping("/updateDetails/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Quiz quiz = quizService.findById(id);
        model.addAttribute("quiz", quiz);
        return "updateDetails";
    }

    // Process the form submission (POST request)
    @PostMapping("/updateDetails/{id}")
    public String updateEmployee(@PathVariable Long id,
                                 @ModelAttribute("quiz") Quiz quiz,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "updateDetails"; // Return back to form if there are errors
        }
        quiz.setId(id); // Ensure the ID is set
        quizService.save(quiz); // Update the employee
        return "redirect:/getAllQuiz"; // Redirect to employee list or success page
    }

}
