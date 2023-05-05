package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.springtestlib.evaluation.GenericEvaluationTests;

public class E2FeedbackTests {

    private GenericEvaluationTests genericEvaluationTests = new GenericEvaluationTests();


    @Test
    public void feedbackTest() throws Exception {
        genericEvaluationTests.evaluateExercise("2) Test Feedback");
    }
}
