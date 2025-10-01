package com.quizc.programmingquizforalllanguages.Service;

import com.quizc.programmingquizforalllanguages.Model.Quiz;
import com.quizc.programmingquizforalllanguages.Repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    public void save(Quiz quiz){
        quizRepository.save(quiz);
    }

    public List<Quiz> getAllQuiz(){
        return  quizRepository.findAll();
    }

    public Quiz findById(Long id){
        return  quizRepository.findById(id).get();
    }

    public void deleteById(Long id){
        quizRepository.deleteById(id);
    }

    public List<Quiz> findByCategory(String category){
        return quizRepository.findByCategory(category);
    }
}
