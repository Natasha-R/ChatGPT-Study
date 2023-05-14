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
                "[we,3]",
                "[so,2]",
                "[ea,1]",
            },
            "(2,3)"
        );
    }

    @Test
    public void movement2Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,9]",
                        "[ea,2]",
                        "[so,6]",
                        "[we,9]",
                },
                "(0,6)"
        );
    }
}
