package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;


public class Exercise0Tests {

    private MovementTests movementTests = new MovementTests();


    @Test
    public void movement1Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
            new String[]{
                "[so,2]",
                "[ea,3]",
                "[no,9]",
                "[ea,5]",
            },
            "(6,8)"
        );
    }

    @Test
    public void movement2Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,1]",
                        "[ea,5]",
                        "[so,5]",
                        "[we,2]",
                },
                "(3,0)"
        );
    }

    @Test
    public void hiddenMovement1Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,2]",
                        "[ea,8]"
                },
                "(8,4)"
        );
    }

    @Test
    public void hiddenMovement2Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,2]",
                        "[no,2]",
                        "[ea,4]",
                        "[ea,4]",
                },
                "(6,6)"
        );
    }
}
