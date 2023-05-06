package thkoeln.st.st2praktikum.exercise.core;

import thkoeln.st.st2praktikum.exercise.Walkable;

import static org.junit.Assert.assertEquals;

public class MovementTests {

    public String performMoves(Walkable walkable, String[] commands) {
        for (int i = 0; i < commands.length; i++) {
            if (i < commands.length - 1) {
                walkable.walk(commands[i]);
            } else {
                return walkable.walk(commands[i]);
            }
        }

        throw new IllegalArgumentException("The command array should not be empty");
    }

    public void performMovesAndCheckFinishedPosition(Walkable walkable, String[] commands, String expectedFinishedPosition) {
        assertEquals(expectedFinishedPosition, performMoves(walkable, commands));
    }
}
