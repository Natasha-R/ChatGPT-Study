package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;


public class Exercise0Tests {

    private MovementTests movementTests = new MovementTests();


    @Test
    public void movement1Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
            new String[]{
                "[so,5]",
                "[we,3]",
                "[ea,5]",
                "[so,3]",
            },
            "(2,5)"
        );
    }

    @Test
    public void movement2Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[ea,1]",
                        "[so,2]",
                        "[we,4]",
                        "[so,4]",
                },
                "(0,1)"
        );
    }

    @Test
    public void movement3Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[ea,1]",
                        "[so,2]",
                        "[ea,6]",
                        "[so,4]",
                        "[ea,2]",
                        "[so,4]",
                        "[we,6]",

                },
                "(9,1)"
        );
    }
}
