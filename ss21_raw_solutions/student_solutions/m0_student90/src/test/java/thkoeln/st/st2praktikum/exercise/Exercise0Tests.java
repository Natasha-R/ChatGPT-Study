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
                "[we,3]",
                "[so,2]",
                "[ea,1]",
            },
            "(2,3)"
        );
    }

    @Test
    public void movement2Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,9]",
                        "[ea,2]",
                        "[so,6]",
                        "[we,9]",
                },
                "(0,6)"
        );
    }

    @Test
    public void movement3Tests(){
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[we,9]",
                        "[no,9]",
                },
                "(1,5)"

                );
    }

    @Test
    public void movement4Tests(){
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,6]",
                        "[we,3]",
                        "[no,1]",
                },
                "(1,7)"

        );
    }

    @Test
    public void movement5Tests(){
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,6]",
                        "[we,2]",
                        "[ea,1]",
                        "[ea,2]",
                        "[ea,1]",
                        "[so,1]",
                        "[no,1]",
                        "[ea,1]",
                        "[so,2]",
                        "[we,1]",
                        "[so,5]",
                        "[we,9]"

                },
                "(1,0)"

        );
    }

    @Test
    public void movement6Tests(){
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[ea,2]",
                        "[no,5]",
                        "[no,1]",


                },
                "(6,5)"

        );
    }

    @Test
    public void movement7Tests(){
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,5]",
                        "[we,3]",
                        "[we,1]"


                },
                "(1,5)"

        );
    }

}
