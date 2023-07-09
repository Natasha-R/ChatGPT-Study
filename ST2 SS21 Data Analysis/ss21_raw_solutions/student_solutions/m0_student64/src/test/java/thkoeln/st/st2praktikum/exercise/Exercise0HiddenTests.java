package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

public class Exercise0HiddenTests {

    private MovementTests movementTests = new MovementTests();


    @Test
    public void hiddenMovement1Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,6]",
                        "[we,4]",
                        "[so,8]",
                        "[ea,2]",
                },
                "(0,0)"
        );
    }

    @Test
    public void hiddenMovement2Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,4]",
                        "[ea,8]",
                        "[we,2]",
                        "[no,5]",
                },
                "(4,7)"
        );
    }
}
