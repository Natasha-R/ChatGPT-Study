package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;


public class Exercise0Tests {

    private MovementTests movementTests = new MovementTests();


    @Test
    public void movement1Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
            new String[]{
                "[so,5]",
                "[we,3]",
                "[ea,5]",
                "[so,3]",
            },
            "(2,5)"
        );
    }

    @Test
    public void movement2Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[ea,1]",
                        "[so,2]",
                        "[we,4]",
                        "[so,4]",
                },
                "(0,1)"
        );
    }


    @Test
    public void movement4Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{

                        "[ea,1]",              //bis hier geht es noch, der letze command geht iwie net
                        "[so,4]",             // Problem bei east commands oben bei 2,6 und 2,7 zu 3,6 und 3,7
                        "[we,4]",
                },
                "(0,5)"
        );
    }
}
