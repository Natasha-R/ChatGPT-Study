package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.springtestlib.evaluation.GenericEvaluationTests;

public class E3CleanCodeTests {

    private GenericEvaluationTests genericEvaluationTests = new GenericEvaluationTests();


    @Test
    public void meaningfulNamesTest() throws Exception {
        genericEvaluationTests.evaluateExercise("3a) Meaningful names");
    }

    @Test
    public void commentsOnlyWhereNecessaryTest() throws Exception {
        genericEvaluationTests.evaluateExercise("3b) Comments only where necessary");
    }

    @Test
    public void keepYourMethodsSmallTest() throws Exception {
        genericEvaluationTests.evaluateExercise("3c) Keep your methods small");
    }

    @Test
    public void noSideEffectInAFunctionTest() throws Exception {
        genericEvaluationTests.evaluateExercise("3d) No side effect in a function");
    }

    @Test
    public void onlyOneLevelOfAbstractionWithinAMethodTest() throws Exception {
        genericEvaluationTests.evaluateExercise("3e) Only one level of abstraction within a method");
    }

    @Test
    public void stepDownRuleTest() throws Exception {
        genericEvaluationTests.evaluateExercise("3f) Stepdown rule");
    }

    @Test
    public void properErrorHandlingUsingExceptionsTest() throws Exception {
        genericEvaluationTests.evaluateExercise("3g) Proper error handling using exceptions");
    }
}
