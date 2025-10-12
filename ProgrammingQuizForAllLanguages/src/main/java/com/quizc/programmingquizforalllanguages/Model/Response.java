package com.quizc.programmingquizforalllanguages.Model;

import lombok.Data;

@Data
public class Response {
    private Long questionId;
    private String response;

    public Response() {}

    public Response(Long questionId, String response) {
        this.questionId = questionId;
        this.response = response;
    }

    // Getters and setters
    public Long getQuestionId() { return questionId; }
    public void setQuestionId(Long questionId) { this.questionId = questionId; }

    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }
}