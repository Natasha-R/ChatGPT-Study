package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;


public class Exercise0Tests {

    private MovementTests movementTests = new MovementTests();


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

    @Test
    public void movement3Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[ea,6]",
                        "[so,5]",
                        "[we,13]",
                        "[no,10]",
                        "[ea,12]",
                        "[we,12]",
                        "[so,10]",
                        "[ea,12]",
                        "[no,10]",
                },
                "(11,8)"
        );
    }

    @Test
    public void movement4Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[ea,1]",
                        "[no,4]",
                        "[we,7]",
                        "[so,1]",
                        "[ea,3]",
                },
                "(3,6)"
        );
    }

    @Test
    public void pingPongTest() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[ea,1]",
                        "[no,2]",
                        "[we,7]",
                        "[so,2]",
                        "[ea,3]",
                        "[we,7]",
                        "[ea,2]",
                        "[we,1]",
                        "[ea,1]",
                },
                "(5,3)"
        );
    }

    @Test
    public void movement5Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[ea,1]",
                        "[so,2]",
                        "[we,3]",
                        "[no,1]",
                        "[we,1]",
                        "[no,4]",
                        "[ea,4]",
                        "[so,1]",
                        "[we,4]",
                },
                "(6,4)"
        );
    }
}
