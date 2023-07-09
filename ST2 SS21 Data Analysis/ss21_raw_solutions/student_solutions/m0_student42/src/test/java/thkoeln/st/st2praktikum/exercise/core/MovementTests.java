package thkoeln.st.st2praktikum.exercise.core;

import thkoeln.st.st2praktikum.exercise.Moveable;

import static org.junit.Assert.assertEquals;

public class MovementTests {

    public String performMoves(Moveable moveable, String[] commands) {
        for (int i = 0; i < commands.length; i++) {
            if (i < commands.length - 1) {
                moveable.move(commands[i]);
            } else {
                return moveable.move(commands[i]);
            }
        }

        throw new IllegalArgumentException("The command array should not be empty");
    }

    public void performMovesAndCheckFinishedPosition(Moveable moveable, String[] commands, String expectedFinishedPosition) {
        assertEquals(expectedFinishedPosition, performMoves(moveable, commands));
    }
}
