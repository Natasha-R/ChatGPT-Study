package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

public class Exercise0HiddenTests {

    private MovementTests movementTests = new MovementTests();


    @Test
    public void hiddenMovement1Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[we,6]",
                        "[so,9]",
                        "[ea,9]",
                        "[no,9]",
                },
                "(10,7)"
        );
    }

    @Test
    public void hiddenMovement2Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[so,3]",
                        "[no,2]",
                        "[so,5]",
                        "[we,6]",
                },
                "(2,1)"
        );
    }
}
