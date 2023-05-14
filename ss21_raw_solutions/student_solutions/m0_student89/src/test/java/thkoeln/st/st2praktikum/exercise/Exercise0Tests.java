package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;


public class Exercise0Tests {

    private MovementTests movementTests = new MovementTests();


    @Test
    public void movement1Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
            new String[]{
                    "[so,15]",
                    "[we,15]",
                    "[ea,15]",

            },

                "(9,1)"
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
}
