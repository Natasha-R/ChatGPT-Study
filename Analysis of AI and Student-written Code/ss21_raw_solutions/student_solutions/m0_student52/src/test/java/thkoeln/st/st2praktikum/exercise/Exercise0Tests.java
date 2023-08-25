package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;


public class Exercise0Tests {

    private MovementTests movementTests = new MovementTests();


    @Test
    public void movement1Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
            new String[]{
                "[ea,3]",
                "[we,2]",
                "[ea,4]",
                "[so,5]",
            },
            "(2,2)"
        );
    }

    @Test
    public void movement2Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[so,5]",
                        "[ea,5]",
                        "[so,4]",
                        "[no,5]",
                },
                "(5,5)"
        );
    }
}
