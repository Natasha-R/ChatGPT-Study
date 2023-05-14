package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;


public class Exercise0Tests {

    private MovementTests movementTests = new MovementTests();


    @Test
    public void movement1Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
            new String[]{
                "[no,5]",//Should be (4,5)
                "[we,3]",//Should be (1,5)
                "[so,2]",//Should be (1,3)
                "[ea,1]",//Should be (2,3)
            },
            "(2,3)"
        );
    }

    @Test
    public void movement2Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,9]",//(4,7)
                        "[ea,2]",//(6,7)
                        "[so,6]",//(6,6)
                        "[we,9]",//(0,6)
                },
                "(0,6)"
        );
    }
    @Test
    public void movement3Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[ea,3]",
                        "[no,6]",
                        "[we,3]",
                        "[so,6]"
                },
                "(4,0)"
        );
    }

    @Test
    public void movement4Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,6]",//4,6
                        "[we,5]",//0,6
                        "[so,6]",//0,0
                        "[ea,3]",//0,0
                        "[no,6]"//0,6
                },
                "(0,6)"
        );
    }

    @Test
    public void movement5Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[we,3]",
                        "[no,6]",
                        "[ea,6]",
                        "[so,6]"
                },
                "(6,0)"
        );
    }

    @Test
    public void movement6Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[we,-1]",
                        "[ea,-1]",
                        "[no,-1]",
                        "[so,-1]",
                },
                "(4,0)"
        );
    }

    @Test
    public void movement7Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[ea,20]",
                        "[no,36]",
                        "[we,17]",
                        "[so,23]",
                },
                "(0,0)"
        );
    }
}
