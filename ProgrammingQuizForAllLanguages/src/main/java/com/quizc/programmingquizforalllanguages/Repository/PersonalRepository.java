package com.quizc.programmingquizforalllanguages.Repository;

import com.quizc.programmingquizforalllanguages.Model.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalRepository extends JpaRepository<PersonalInfo, Long> {
    boolean existsByIdNumber(String idNumber);
    boolean existsByEmail(String email);
}