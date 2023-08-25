package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;


public class Exercise0Tests {

    private MovementTests movementTests = new MovementTests();


    @Test
    public void movement1Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
            new String[]{
                "[no,5]",
                "[so,9]",
                "[we,4]",
                "[so,4]",
            },
            "(3,1)"
        );
    }

    @Test
    public void movement2Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[we,6]",
                        "[so,6]",
                        "[ea,4]",
                        "[no,3]",
                },
                "(1,4)"
        );
    }

    @Test
    public void movement3Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[ea,6]",
                        "[so,6]",
                        "[ea,4]",
                        "[no,3]",
                        "[no,1]",
                        "[we,9]",
                        "[no,3]",
                        "[so,6]",
                        "[ea,9]"
                },
                "(9,1)"
        );
    }

    @Test
    public void movement4Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[we,7]",
                        "[so,11]",
                        "[ea,12]",
                        "[no,10]",
                        "[ea,2]"
                },
                "(10,7)"
        );
    }





}
