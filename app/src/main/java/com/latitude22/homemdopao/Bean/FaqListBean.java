package com.latitude22.homemdopao.Bean;

/**
 * Created by Anuved on 25-08-2016.
 */
public class FaqListBean
{
    String  FaqID;
    String FaqQuestion;

    public String getFaqAnswer() {
        return FaqAnswer;
    }

    public void setFaqAnswer(String faqAnswer) {
        FaqAnswer = faqAnswer;
    }

    public String getFaqQuestion() {
        return FaqQuestion;
    }

    public void setFaqQuestion(String faqQuestion) {
        FaqQuestion = faqQuestion;
    }

    public String getFaqID() {
        return FaqID;
    }

    public void setFaqID(String faqID) {
        FaqID = faqID;
    }

    String FaqAnswer;
}
