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
                        "[no,3]",//5,0
                        "[ea,5]",//5,5
                        "[so,1]",//5,5
                },
                "(5,5)"
        );
    }

    @Test
    public void movement4Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,2]",//0,4
                        "[ea,5]",//5,4
                        "[no,1]",//5,4
                },
                "(5,4)"
        );
    }

    @Test
    public void movement5Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,10]",
                        "[ea,7]",
                },
                "(6,8)"
        );
    }

    @Test
    public void movement6Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[we,1]",
                },
                "(0,2)"
        );
    }

    @Test
    public void movement7Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,2]",
                        "[ea,20]",
                },
                "(11,4)"
        );
    }

    @Test
    public void movement8Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[so,5]",
                },
                "(0,0)"
        );
    }
}
