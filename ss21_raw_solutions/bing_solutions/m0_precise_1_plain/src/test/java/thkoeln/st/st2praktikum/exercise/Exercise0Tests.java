package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;


public class Exercise0Tests {

    private MovementTests movementTests = new MovementTests();


    @Test
    public void movement1Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
            new String[]{
                "[ea,5]",
                "[no,3]",
                "[we,4]",
                "[so,1]",
            },
            "(3,1)"
        );
    }

    @Test
    public void movement2Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,4]",
                        "[ea,4]",
                        "[so,1]",
                        "[we,7]",
                },
                "(3,2)"
        );
    }
}
