package thkoeln.st.st2praktikum.exercise.E2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.Connection;
import thkoeln.st.st2praktikum.exercise.Exception.ConnectionException;
import thkoeln.st.st2praktikum.exercise.RectangularSpace;
import thkoeln.st.st2praktikum.exercise.Space;
import thkoeln.st.st2praktikum.exercise.Vector2D;

import static org.junit.jupiter.api.Assertions.*;

public class E2_3ConnectionOutOfBoundsTest {

    Space sourceSpace, destinationSpace;
    Vector2D entry, exit;

    @BeforeEach
    private void init() {
        sourceSpace = new RectangularSpace(10, 10);
        destinationSpace = new RectangularSpace(10, 10);
        entry = new Vector2D(5, 5);
        exit = new Vector2D(5, 5);
    }

    @Test
    public void createConnectionOutOfSpaceBounds () {
        assertThrows(ConnectionException.class, () ->
                new Connection(sourceSpace, new Vector2D(10, 9), destinationSpace, exit));
        assertThrows(ConnectionException.class, () ->
                new Connection(sourceSpace, entry, destinationSpace, new Vector2D(9, 10)));
        assertDoesNotThrow(() ->
                new Connection(sourceSpace, entry, destinationSpace, new Vector2D(9, 9)));
    }

    @Test
    public void createConnectionWithMissingSpaces () {
        assertThrows(ConnectionException.class, () ->
                new Connection(null, entry, destinationSpace, exit));
        assertThrows(ConnectionException.class, () ->
                new Connection(sourceSpace, entry, null, exit));
        new Connection(sourceSpace, entry, destinationSpace, exit);
    }
}
