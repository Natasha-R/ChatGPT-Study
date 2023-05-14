package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

public class Exercise0HiddenTests {

    private MovementTests movementTests = new MovementTests();


    @Test
    public void hiddenMovement1Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,3]",
                        "[we,3]",
                        "[so,4]",
                        "[no,3]",
                },
                "(0,3)"
        );
    }

    @Test
    public void hiddenMovement2Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,4]",
                        "[we,1]",
                        "[so,4]",
                        "[ea,1]",
                },
                "(2,0)"
        );
    }
}
