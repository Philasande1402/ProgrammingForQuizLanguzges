package com.quizc.programmingquizforalllanguages.Controller;

import com.quizc.programmingquizforalllanguages.Model.PersonalInfo;
import com.quizc.programmingquizforalllanguages.Service.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/registration")
public class PersonalController {

    private static final Logger logger = LoggerFactory.getLogger(PersonalController.class);

    @Autowired
    private PersonalService personalService;

    @PostMapping("/register")
    public String registerUser(@ModelAttribute PersonalInfo personalInfo, Model model) {
        try {
            // Validate ID number length
            if (personalInfo.getIdNumber().length() != 13) {
                model.addAttribute("error", "ID Number must be exactly 13 digits");
                return "registration-form"; // Return to form with error
            }

            personalService.addUser(personalInfo);
            return "redirect:/searchQuiz";

        } catch (DataIntegrityViolationException e) {
            logger.error("Database error while saving user: {}", e.getMessage());
            model.addAttribute("error", "User with this ID number or email already exists");
            return "registration-form";
        } catch (Exception e) {
            logger.error("Error while saving user: {}", e.getMessage());
            model.addAttribute("error", "An error occurred during registration. Please try again.");
            return "registration-form";
        }
    }

    // Optional: Add a GET method to show the registration form
    @GetMapping("/form")
    public String showRegistrationForm(Model model) {
        model.addAttribute("personalInfo", new PersonalInfo());
        return "registration";
    }
}