package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;


public class Exercise0Tests {

    private MovementTests movementTests = new MovementTests();


    @Test
    public void movement1Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
            new String[]{
                "[no,2]",
                "[we,8]",
                "[no,1]",
                "[ea,9]",
            },
            "(5,6)"
        );
    }

    @Test
    public void movement2Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[we,4]",
                        "[so,3]",
                        "[ea,5]",
                        "[no,6]",
                },
                "(6,2)"
        );
    }


    @Test
    public void movement3Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,4]",
                        "[we,2]",
                        "[so,5]",
                        "[no,6]",
                },
                "(3,5)"
        );
    }

    @Test
    public void movement4Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,4]",
                        "[we,3]",
                        "[so,8]",
                        "[ea,10]",
                        "[no,5]",
                        "[we,9]",
                },
                "(6,5)"
        );
    }


}
