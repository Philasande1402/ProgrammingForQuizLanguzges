package com.quizc.programmingquizforalllanguages.Controller;

import com.quizc.programmingquizforalllanguages.Dao.LoginInfos;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    @RequestMapping("/logins")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public String handleLogin(@RequestParam String username,
                              @RequestParam String password) {
        LoginInfos login = new LoginInfos();

        if (login.isValid(username, password)) {
            return "success"; // Return plain text "success"
        } else {
            return "error"; // Return plain text "error"
        }
    }

    // Admin page after successful login
    @GetMapping("/adminPage")
    public String showAdminPage() {
        return "adminPage"; // Make sure you have adminPage.html
    }
}
