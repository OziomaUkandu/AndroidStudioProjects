package com.example.numbergame;

public class Questions {

    public String mQuestions[] = {
            "Which number is greater?",
            "Which number is greater?",
            "Which number is greater?",
            "Which number is greater?",
            "Which number is greater?",
            "Which number is greater?",
            "Which number is greater?",
            "Which number is greater?",
            "Which number is greater?"
    };

    public  String mChoices [][] = {
            {"2", "7"},
            {"4", "14"},
            {"21", "6"},
            {"28", "8"},
            {"10", "35"},
            {"12", "42"},
            {"49", "14"},
            {"16", "56"},
            {"63", "18"}
    };

    public String mCorrectAnswers[] = {"7","14", "21", "28", "35","42", "49", "56","63"};

    public String getQuestion(int a){
        String question = mQuestions[a];
        return question;
    }

    public String getChoice1(int a){
        String choice = mChoices[a][0];
        return choice;
    }

    public String getChoice2(int a){
        String choice = mChoices[a][1];
        return choice;
    }

    public String getCorrectAnswer(int a){
        String answer = mCorrectAnswers[a];
        return answer;
    }
}
