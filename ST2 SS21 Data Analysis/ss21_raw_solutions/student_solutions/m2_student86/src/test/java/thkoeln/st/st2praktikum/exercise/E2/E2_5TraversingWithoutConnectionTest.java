package thkoeln.st.st2praktikum.exercise.E2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.Connection;
import thkoeln.st.st2praktikum.exercise.ConnectionNetwork;
import thkoeln.st.st2praktikum.exercise.Exception.CleaningDeviceException;
import thkoeln.st.st2praktikum.exercise.Exception.ConnectionException;
import thkoeln.st.st2praktikum.exercise.RectangularSpace;
import thkoeln.st.st2praktikum.exercise.Space;
import thkoeln.st.st2praktikum.exercise.Tile;
import thkoeln.st.st2praktikum.exercise.Vector2D;

import static org.junit.jupiter.api.Assertions.*;

public class E2_5TraversingWithoutConnectionTest {

    CleaningDevice cleaningDevice;
    Space sourceSpace, destinationSpace;
    Vector2D entry, exit;
    ConnectionNetwork connectionNetwork;
    Connection connection;

    @BeforeEach
    private void init() {
        sourceSpace = new RectangularSpace(10, 10);
        destinationSpace = new RectangularSpace(10, 10);

        cleaningDevice = new CleaningDevice("wall-e");

        connectionNetwork = new ConnectionNetwork();
        entry = new Vector2D(5, 5);
        exit = new Vector2D(3, 3);
        connection = new Connection(sourceSpace, entry, destinationSpace, exit);
        connectionNetwork.addConnection(connection);
    }

    @Test
    public void transferDeviceWithoutConnection () {
        cleaningDevice.enterSpace(sourceSpace);
        assertThrows(ConnectionException.class, () ->
                cleaningDevice.transportToSpace(connectionNetwork, destinationSpace));
    }

    @Test
    public void transferDeviceFromConnectionExit () {
        cleaningDevice.enterSpace(sourceSpace);
        cleaningDevice.move(Tile.Direction.EAST, 5);
        cleaningDevice.move(Tile.Direction.NORTH, 5);
        cleaningDevice.transportToSpace(connectionNetwork, destinationSpace);

        assertThrows(ConnectionException.class, () ->
                cleaningDevice.transportToSpace(connectionNetwork, sourceSpace));
    }

    @Test
    public void transferDeviceFromOutsideOfASpace () {
        assertThrows(CleaningDeviceException.class, () ->
                cleaningDevice.transportToSpace(connectionNetwork, sourceSpace));
    }

    @Test
    public void transferDeviceToNull () {
        cleaningDevice.enterSpace(sourceSpace);
        assertThrows(ConnectionException.class, () ->
                cleaningDevice.transportToSpace(connectionNetwork, null));
    }
}
