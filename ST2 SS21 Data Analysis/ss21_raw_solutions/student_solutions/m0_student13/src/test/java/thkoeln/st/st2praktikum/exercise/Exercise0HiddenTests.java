package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

public class Exercise0HiddenTests {

    private MovementTests movementTests = new MovementTests();


    @Test
    public void hiddenMovement1Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[so,5]",
                        "[ea,4]",
                        "[no,1]",
                        "[we,3]",
                },
                "(3,3)"
        );
    }

    @Test
    public void hiddenMovement2Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[so,9]",
                        "[ea,5]",
                        "[no,3]",
                        "[ea,5]",
                },
                "(5,2)"
        );
    }
}
