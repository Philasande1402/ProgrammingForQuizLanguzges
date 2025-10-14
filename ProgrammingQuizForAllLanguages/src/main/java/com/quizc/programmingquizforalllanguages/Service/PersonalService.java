package com.quizc.programmingquizforalllanguages.Service;

import com.quizc.programmingquizforalllanguages.Model.PersonalInfo;
import com.quizc.programmingquizforalllanguages.Repository.PersonalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PersonalService {

    private static final Logger logger = LoggerFactory.getLogger(PersonalService.class);

    @Autowired
    private PersonalRepository personalRepository;

    public void addUser(PersonalInfo personalInfo) {
        try {
            // Validate input
            if (personalInfo == null) {
                throw new IllegalArgumentException("PersonalInfo cannot be null");
            }

            if (personalInfo.getIdNumber() == null || personalInfo.getIdNumber().length() != 13) {
                throw new IllegalArgumentException("ID Number must be exactly 13 digits");
            }

            // Check if user already exists
            PersonalInfo existingUser = personalRepository.findByIdNumber(personalInfo.getIdNumber());
            if (existingUser != null) {
                logger.warn("User already exists with ID: {}", personalInfo.getIdNumber());
                throw new RuntimeException("User already registered with this ID number");
            }

            personalRepository.save(personalInfo);
            logger.debug("User saved successfully with ID: {}", personalInfo.getIdNumber());

        } catch (IllegalArgumentException e) {
            logger.error("Validation error while adding user: {}", e.getMessage());
            throw new RuntimeException("Invalid user data: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error adding user with ID {}: {}",
                    personalInfo != null ? personalInfo.getIdNumber() : "unknown",
                    e.getMessage(), e);
            throw new RuntimeException("Failed to register user", e);
        }
    }

    public PersonalInfo findByIdNumber(String idNumber) {
        try {
            // Validate input
            if (idNumber == null || idNumber.trim().isEmpty()) {
                throw new IllegalArgumentException("ID Number cannot be null or empty");
            }

            if (idNumber.length() != 13) {
                throw new IllegalArgumentException("ID Number must be exactly 13 digits");
            }

            PersonalInfo person = personalRepository.findByIdNumber(idNumber);

            if (person == null) {
                logger.debug("No user found with ID: {}", idNumber);
                return null;
            }

            logger.debug("User found with ID: {}", idNumber);
            return person;

        } catch (IllegalArgumentException e) {
            logger.error("Validation error while finding user: {}", e.getMessage());
            throw new RuntimeException("Invalid search criteria: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error finding user by ID {}: {}", idNumber, e.getMessage(), e);
            throw new RuntimeException("Failed to find user", e);
        }
    }
}