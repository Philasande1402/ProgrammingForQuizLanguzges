package com.quizc.programmingquizforalllanguages.Service;

import com.quizc.programmingquizforalllanguages.Model.PersonalInfo;
import com.quizc.programmingquizforalllanguages.Repository.PersonalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalService {

    @Autowired
    private PersonalRepository personalRepository;

    public void addUser(PersonalInfo personalInfo) {
        personalRepository.save(personalInfo);
    }

    public PersonalInfo findByIdNumber(String idNumber){
        return personalRepository.findByIdNumber(idNumber);
    }
}