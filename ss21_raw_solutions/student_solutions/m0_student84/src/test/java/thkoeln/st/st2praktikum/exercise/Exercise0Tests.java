package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;


public class Exercise0Tests {

    private MovementTests movementTests = new MovementTests();


    @Test
    public void movement1Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
            new String[]{
                "[ea,3]",
                "[we,2]",
                "[ea,4]",
                "[so,5]",
            },
            "(2,2)"
        );
    }

    @Test
    public void movement2Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[so,5]",
                        "[ea,5]",
                        "[so,4]",
                        "[no,5]",
                },
                "(5,5)"
        );
    }

    @Test
    public void leftBoundaryTest() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[we,3]"
                },
                "(0,7)");
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[we,2]"
                },
                "(0,7)");
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[we,1]"
                },
                "(0,7)");
    }

    @Test
    public void topBoundaryTest() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,3]",
                        "[no,3]"
                },
                "(1,8)");
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,2]"
                },
                "(1,8)");
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,1]"
                },
                "(1,8)");
    }

    @Test
    public void bottomBoundaryTest() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[so,7]",
                        "[so,2]"
                },
                "(1,0)");
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[so,8]"
                },
                "(1,0)");
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[so,9]"
                },
                "(1,0)");
    }

    @Test
    public void masterTest() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[ea,2]",
                        "[so,10]",
                        "[ea,3]",
                        "[no,4]",
                       "[ea,1]",
                        "[no,1]",
                        "[we,3]",
                        "[no,2]"
                }, "(3,5)");
    }
}
