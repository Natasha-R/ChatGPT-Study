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
    //auf der wand liegen süden
    @Test
    public void movement3Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,4]",
                        "[ea,6]",
                        "[so,1]",
                },
                "(6,5)"
        );
    }
    //vor der wand stehen bleiben osten
    @Test
    public void movement4Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,5]",
                        "[ea,8]",


                },
                "(6,7)"
        );
    }
    //Nördliche äußere wand
    @Test
    public void movement5Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,10]",

                },
                "(0,8)"
        );
    }
    //westliche äußere wand
    @Test
    public void movement6Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[we,10]",

                },
                "(0,2)"
        );
    }
    //südliche äußere wand
    @Test
    public void movement7Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[so,10]",

                },
                "(0,0)"
        );
    }
    //TunnelTest
    @Test
    public void movement8Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,2]",
                        "[ea,7]"

                },
                "(7,4)"
        );
    }
    //östliche äußere wand
    @Test
    public void movement10Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,2]",
                        "[ea,14]"

                },
                "(11,4)"
        );
    }
    //Tunneltest
    @Test
    public void movement8TeilTest() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,2]"

                },
                "(0,4)"
        );
    }
    //Nach Tunnel an der Wand legen westen
    @Test
    public void movement9Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,2]",
                        "[ea,7]",
                        "[so,4]",
                        "[we,2]"

                },
                "(5,0)"
        );
    }
    //vor der Wand stehenbleiben Norden
    @Test
    public void movement11Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,2]",
                        "[ea,5]",
                        "[no,5]"

                },
                "(5,4)"
        );
    }
    //Ursprung
    @Test
    public void movement12Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[we,200]",
                        "[so,14]"

                },
                "(0,0)"
        );
    }
    //Oben rechte Ecke
    @Test
    public void movement13Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,2]",
                        "[ea,14]",
                        "[no,10]",


                },
                "(11,8)"
        );
    }
    //Tunnel hin und zurück
    @Test
    public void movement14Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[no,2]",
                        "[ea,14]",
                        "[we,14]",


                },
                "(0,4)"
        );
    }
    @Test
    public void movement15Test() {
        movementTests.performMovesAndCheckFinishedPosition(new Exercise0(),
                new String[]{
                        "[so,2]",
                        "[ea,14]",
                        "[we,14]",


                },
                "(0,0)"
        );
    }

}
