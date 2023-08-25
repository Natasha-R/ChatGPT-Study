package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;


public class Exercise0Tests {

    private MovementTests movementTests = new MovementTests();

    @Test
    public void movementSouthTest() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[so,3]"
                },
                "(8,2)"
        );
    }

    @Test
    public void movementNorthTest() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,3]"
                },
                "(8,4)"
        );
    }

    @Test
    public void movementWestTest() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[we,3]"
                },
                "(6,3)"
        );
    }

    @Test
    public void movementEastTest() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[ea,5]"
                },
                "(11,3)"
        );
    }

    @Test
    public void movement1Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
            new String[]{
                "[so,3]",
                "[we,3]",
                "[no,3]",
                "[ea,5]",
            },
            "(11,4)"
        );
    }

    @Test
    public void movement2Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[ea,3]",
                        "[no,2]",
                        "[we,8]",
                        "[so,2]",
                },
                "(4,3)"
        );
    }
}
