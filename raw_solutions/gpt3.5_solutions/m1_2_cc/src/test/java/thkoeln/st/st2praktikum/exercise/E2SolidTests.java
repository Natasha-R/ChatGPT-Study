package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.springtestlib.evaluation.GenericEvaluationTests;

public class E2SolidTests {

    private GenericEvaluationTests genericEvaluationTests = new GenericEvaluationTests();


    @Test
    public void singleResponsibilityTest() throws Exception {
        genericEvaluationTests.evaluateExercise("2a) Single Responsibility");
    }

    @Test
    public void openClosedInterfaceSegregationTest() throws Exception {
        genericEvaluationTests.evaluateExercise("2b) Open-Closed, Interface Segregation");
    }

    @Test
    public void dependencyInversionTest() throws Exception {
        genericEvaluationTests.evaluateExercise("2c) Dependency inversion");
    }
}
