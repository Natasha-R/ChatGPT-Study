package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

public class Exercise0HiddenTests {

    private MovementTests movementTests = new MovementTests();


    @Test
    public void hiddenMovement1Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[ea,5]",
                        "[no,3]",
                        "[so,4]",
                        "[we,2]",
                },
                "(9,6)"
        );
    }

    @Test
    public void hiddenMovement2Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[we,9]",
                        "[so,4]",
                        "[ea,9]",
                        "[so,8]",
                },
                "(5,0)"
        );
    }
}
