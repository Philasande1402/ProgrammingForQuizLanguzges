package com.quizc.programmingquizforalllanguages.Controller;

import com.quizc.programmingquizforalllanguages.Model.PersonalInfo;
import com.quizc.programmingquizforalllanguages.Service.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/registration")
public class PersonalController {

    @Autowired
    private PersonalService personalService;

    @PostMapping("/register")
    public String registerUser(@ModelAttribute PersonalInfo personalInfo, Model model) {
        try {
            // Validate and save user
            if (personalInfo.getIdNumber().length() != 13) {
                model.addAttribute("error", "ID Number must be exactly 13 digits");
                return "userRegistration";
            }

            personalService.addUser(personalInfo);

            // Add username to model
            model.addAttribute("userFirstName", personalInfo.getFirstName());

            // Return the view name directly (not redirect)
            return "searchQuiz"; // This should match your HTML file name

        } catch (Exception e) {
            model.addAttribute("error", "An error occurred during registration. Please try again.");
            return "userRegistration";
        }
    }
}