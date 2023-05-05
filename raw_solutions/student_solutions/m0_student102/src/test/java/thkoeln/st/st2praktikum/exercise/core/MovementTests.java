package thkoeln.st.st2praktikum.exercise.core;

import thkoeln.st.st2praktikum.exercise.GoAble;

import static org.junit.Assert.assertEquals;

public class MovementTests {

    public String performMoves(GoAble goAble, String[] commands) {
        for (int i = 0; i < commands.length; i++) {
            if (i < commands.length - 1) {
                goAble.goTo(commands[i]);
            } else {
                return goAble.goTo(commands[i]);
            }
        }

        throw new IllegalArgumentException("The command array should not be empty");
    }

    public void performMovesAndCheckFinishedPosition(GoAble goAble, String[] commands, String expectedFinishedPosition) {
        assertEquals(expectedFinishedPosition, performMoves(goAble, commands));
    }
}