package com.quizc.programmingquizforalllanguages.Service;

import com.quizc.programmingquizforalllanguages.Model.QuestionWrapper;
import com.quizc.programmingquizforalllanguages.Model.Questions;
import com.quizc.programmingquizforalllanguages.Model.Quiz;
import com.quizc.programmingquizforalllanguages.Repository.QuestionRepository;
import com.quizc.programmingquizforalllanguages.Repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    public Questions createQuiz(String category, int numQ, String title) {
        List<Quiz> quizzes = quizRepository.findRandomQuestionByCategory(category, numQ);

        Questions questions = new Questions();
        questions.setTitle(title);
        questions.setQuizzes(quizzes);

        Questions savedQuestions = questionRepository.save(questions);

        // Return the saved questions object instead of redirecting
        return savedQuestions;
    }

    public List<Long> getAllQuizIds() {
        return questionRepository.findAllQuizIds();
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(Long questionSetId) {
        Optional<Questions> questions = questionRepository.findById(questionSetId);

        if (questions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

        return new ResponseEntity<>(userQuestionsWrapper, HttpStatus.OK);
    }
}