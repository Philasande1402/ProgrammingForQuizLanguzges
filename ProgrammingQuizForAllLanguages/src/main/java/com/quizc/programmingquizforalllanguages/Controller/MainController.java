package com.quizc.programmingquizforalllanguages.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/addQuiz")
    public String addQuiz(){
        return "addQuiz";
    }

    @RequestMapping("/success")
    public String success(){
        return "success";
    }

    @RequestMapping("/updateDetails")
    public String updateDetails(){
        return "updateDetails";
    }

    @RequestMapping("searchByCategory")
    public String searchByCategory(){
        return "searchByCategory";
    }

    @RequestMapping("searchOutPutQuiz")
    public String searchOutPutQuiz(){
        return "searchOutPutQuiz";
    }
}
