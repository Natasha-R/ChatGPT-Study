package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;


public class Exercise0Tests {

    private MovementTests movementTests = new MovementTests();


    @Test
    public void movement1Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
            new String[]{
                "[we,7]",
                "[so,6]",
                "[ea,2]",
                "[no,5]",
            },
            "(6,5)"
        );
    }

    @Test
    public void movement2Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[so,1]",
                        "[we,7]",
                        "[so,1]",
                        "[ea,2]",
                },
                "(4,5)"
        );
    }
}
