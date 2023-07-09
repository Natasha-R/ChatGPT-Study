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
    public void movement3Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,1]"
                },
                "(1,4)"
        );
    }

    @Test
    public void movement4Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[ea,3]"
                },
                "(3,3)"
        );
    }

    @Test
    public void movement5Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[so,2]",
                },
                "(0,0)"
        );
    }
    @Test
    public void movement6Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[so,2]",
                        "[ea,3]",
                },
                "(3,1)"
        );
    }

    @Test
    public void movement7Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[so,2]",
                        "[ea,3]",
                        "[no,9]",
                },
                "(3,9)"
        );
    }

    @Test
    public void movement8Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,1]",
                },
                "(1,4)"
        );
    }

    @Test
    public void movement9Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,1]",
                        "[ea,5]",
                },
                "(6,4)"
        );
    }

    @Test
    public void movement10Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,1]",
                        "[ea,5]",
                        "[so,5]",
                },
                "(3,0)"
        );
    }

    @Test
    public void movement11Test() {
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
}
