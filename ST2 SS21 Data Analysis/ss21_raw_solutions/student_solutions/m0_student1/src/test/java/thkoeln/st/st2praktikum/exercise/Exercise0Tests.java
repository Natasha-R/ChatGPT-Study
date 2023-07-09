package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;


public class Exercise0Tests {

    private MovementTests movementTests = new MovementTests();


    @Test
    public void movement1Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
            new String[]{
                "[no,5]",
                "[so,9]",
                "[we,4]",
                "[so,4]",
            },
            "(3,1)"
        );
    }



    @Test
    public void movement2Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[we,6]",
                        "[so,6]",
                        "[ea,4]",
                        "[no,3]",
                },
                "(1,4)"
        );
    }
    @Test
    public void movement3Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[ea,10]",
                        "[so,9]",
                        "[we,14]",
                        "[so,9]",
                        "[no,10]",
                        "[we,9]",

                },
                "(2,5)"
        );
    }
}
