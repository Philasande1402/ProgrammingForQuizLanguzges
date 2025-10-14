package com.quizc.programmingquizforalllanguages.Service;

import com.quizc.programmingquizforalllanguages.Model.QuestionWrapper;
import com.quizc.programmingquizforalllanguages.Model.Questions;
import com.quizc.programmingquizforalllanguages.Model.Quiz;
import com.quizc.programmingquizforalllanguages.Model.Response;
import com.quizc.programmingquizforalllanguages.Repository.QuestionRepository;
import com.quizc.programmingquizforalllanguages.Repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    //Create quiz questions
    public Questions createQuiz(String category, int numQ, String title) {
        List<Quiz> quizzes = quizRepository.findRandomQuestionByCategory(category, numQ);

        Questions questions = new Questions();
        questions.setTitle(title);
        questions.setQuizzes(quizzes);

        Questions savedQuestions = questionRepository.save(questions);

        // Return the saved questions object instead of redirecting
        return savedQuestions;
    }

    //Get all questions ID
    public List<Long> getAllQuizIds() {
        return questionRepository.findAllQuizIds();
    }

    //Get quiz questions
    public List<QuestionWrapper> getQuizQuestion(Long questionSetId) {
        Optional<Questions> questions = questionRepository.findById(questionSetId);

        if (questions.isEmpty()) {
            return Collections.emptyList();
        }

        List<Quiz> quizzesDBS = questions.get().getQuizzes();
        List<QuestionWrapper> userQuestionsWrapper = new ArrayList<>();

        for(Quiz quiz: quizzesDBS){
            QuestionWrapper qw = new QuestionWrapper(
                    quiz.getId(),
                    quiz.getQuestionTitle(),
                    quiz.getOption1(),
                    quiz.getOption2(),
                    quiz.getOption3(),
                    quiz.getOption4()
            );
            userQuestionsWrapper.add(qw);
        }

        return userQuestionsWrapper;
    }

    //Submit quiz questions
    public int calculateQuiz(Long id, List<Response> responses) {
        Optional<Questions> questions = questionRepository.findById(id);
        List<Quiz> quizzes = questions.get().getQuizzes();
        int right = 0;
        int i = 0;

        for(Response response : responses) {
            if(response.getResponse().equals(quizzes.get(i).getRightAnswer())) {
                right++;
            }
            i++;
        }
        return right;
    }

}