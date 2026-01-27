package com.quizc.programmingquizforalllanguages.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    // Your EXACT credentials
    private static final String VALID_USERNAME = "Philasande@1402";
    private static final String VALID_PASSWORD = "Philasande@1202";

//    // 1. Show login page
    @GetMapping("/logins")
    public String showLoginPage() {
        System.out.println("📱 Serving login page");
        return "login";
    }

    // 2. Handle login - SIMPLE METHOD
    @PostMapping("/login")
    @ResponseBody
    public String handleLogin(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("=== LOGIN ATTEMPT ===");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        if (VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password)) {
            System.out.println("✅ LOGIN SUCCESS!");
            HttpSession session = request.getSession();
            session.setAttribute("user", username);
            return "success";
        } else {
            System.out.println("❌ LOGIN FAILED!");
            return "error";
        }
    }

    // 3. Admin page
    @GetMapping("/adminPage")
    public String showAdminPage(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            System.out.println("⚠️ Not logged in, redirecting");
            return "redirect:/?error=1";
        }

        System.out.println("✅ User logged in: " + session.getAttribute("user"));
        return "adminPage"; // Your existing adminPage.html
    }

    // 4. Logout
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}