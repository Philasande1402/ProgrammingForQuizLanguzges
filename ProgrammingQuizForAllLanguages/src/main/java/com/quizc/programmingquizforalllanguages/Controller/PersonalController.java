package com.quizc.programmingquizforalllanguages.Controller;

import com.quizc.programmingquizforalllanguages.Model.PersonalInfo;
import com.quizc.programmingquizforalllanguages.Service.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
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
            logger.info("Attempting to register user with ID: {}", personalInfo.getIdNumber());

            // Validate ID number length
            if (personalInfo.getIdNumber() == null || personalInfo.getIdNumber().length() != 13) {
                model.addAttribute("error", "ID Number must be exactly 13 digits");
                logger.warn("Invalid ID number length: {}", personalInfo.getIdNumber());
                return "userRegistration";
            }

            // Validate other required fields
            if (personalInfo.getFirstName() == null || personalInfo.getFirstName().trim().isEmpty()) {
                model.addAttribute("error", "First name is required");
                return "userRegistration";
            }

            if (personalInfo.getLastName() == null || personalInfo.getLastName().trim().isEmpty()) {
                model.addAttribute("error", "Last name is required");
                return "userRegistration";
            }

            personalService.addUser(personalInfo);
            logger.info("User registered successfully with ID: {}", personalInfo.getIdNumber());

            // Add username to model
            model.addAttribute("userFirstName", personalInfo.getFirstName());

            // Return the view name directly (not redirect)
            return "searchQuiz";

        } catch (Exception e) {
            logger.error("Error during user registration for ID {}: {}",
                    personalInfo != null ? personalInfo.getIdNumber() : "unknown",
                    e.getMessage(), e);
            model.addAttribute("error", "An error occurred during registration. Please try again.");
            return "userRegistration";
        }
    }

    @GetMapping("/getUserById")
    public String findByIdNumber(@RequestParam String idNumber, Model model) {
        try {
            logger.info("Searching for user with ID: {}", idNumber);

            // Validate input
            if (idNumber == null || idNumber.trim().isEmpty()) {
                model.addAttribute("error", "ID Number is required");
                return "error";
            }

            if (idNumber.length() != 13) {
                model.addAttribute("error", "ID Number must be exactly 13 digits");
                return "error";
            }

            PersonalInfo person = personalService.findByIdNumber(idNumber);

            if (person == null) {
                logger.warn("User not found with ID: {}", idNumber);
                model.addAttribute("error", "User not found with ID: " + idNumber);
                return "error";
            }

            model.addAttribute("userFirstName", person.getFirstName());
            logger.info("User found: {}", person.getFirstName());

            return "searchQuiz";

        } catch (Exception e) {
            logger.error("Error finding user by ID {}: {}", idNumber, e.getMessage(), e);
            model.addAttribute("error", "An error occurred while searching for user. Please try again.");
            return "error";
        }
    }
}