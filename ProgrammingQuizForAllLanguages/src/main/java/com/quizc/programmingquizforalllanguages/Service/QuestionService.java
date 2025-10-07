package com.quizc.programmingquizforalllanguages.Service;

import com.quizc.programmingquizforalllanguages.Model.Questions;
import com.quizc.programmingquizforalllanguages.Model.Quiz;
import com.quizc.programmingquizforalllanguages.Repository.QuestionRepository;
import com.quizc.programmingquizforalllanguages.Repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    public String createQuiz(String category, int numQ, String title) {

        List<Quiz> quizzes = quizRepository.findRandomQuestionByCategory(category, numQ);

        Questions questions = new Questions();
        questions.setTitle(title);
        questions.setQuizzes(quizzes);

        Questions savedQuestions = questionRepository.save(questions);

        // Redirect to the quiz taking page with the created quiz ID
        return "redirect:/quiz/take/" + savedQuestions.getId();
    }
}