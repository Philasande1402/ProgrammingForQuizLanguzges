function submitQuiz() {
    // Get all selected answers
    const selectedAnswers = [];
    const questionSetId = document.getElementById('questionSetId').value;

    // Find all checked radio buttons
    const checkedRadios = document.querySelectorAll('input[type="radio"]:checked');

    checkedRadios.forEach(radio => {
        selectedAnswers.push({
            questionId: radio.getAttribute('data-question-id'),
            response: radio.value
        });
    });

    // Check if all questions are answered
    if (selectedAnswers.length !== document.querySelectorAll('.question-container').length) {
        alert('Please answer all questions before submitting!');
        return;
    }

    // Submit to backend
    fetch('/quiz/submit?questionSetId=' + questionSetId, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(selectedAnswers)
    })
        .then(response => {
            if (response.redirected) {
                window.location.href = response.url;
            } else {
                return response.text();
            }
        })
        .then(html => {
            if (html) {
                document.open();
                document.write(html);
                document.close();
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error submitting quiz. Please try again.');
        });
}