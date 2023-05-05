package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;
import static org.junit.jupiter.api.Assertions.*;

public class E2CustomUnitTests extends MovementTests {

    private CleaningDeviceService service;

    @BeforeEach
    public void createNewServiceClass() {
        service = new CleaningDeviceService();
        createWorld(service);
    }

//    Hitting another cleaningDevice during a Move-Command has to interrupt the current movement
    @Test
    public void moveCleaningDeviceWithHittingAnotherCleaningDevice() {
        service.executeCommand(cleaningDevice1, new Task("[en," + space1 + "]"));
        service.executeCommand(cleaningDevice1, new Task("[no,1]"));

        service.executeCommand(cleaningDevice2, new Task("[en," + space1 + "]"));
        service.executeCommand(cleaningDevice2, new Task("[no,1]"));

        assertPosition(service, cleaningDevice1, space1, 0, 1);
        assertPosition(service, cleaningDevice2, space1, 0, 0);
    }


//    Trying to move out of bounds must not be possible
    @Test
    public void moveCleaningDeviceOutOfBounds() {
        service.executeCommand(cleaningDevice1, new Task("[en," + space1 + "]"));
        service.executeCommand(cleaningDevice1, new Task("[so,9]"));
        assertPosition(service, cleaningDevice1, space1, 0, 0);
    }


//    Placing a connection out of bounds must not be possible
    @Test
    public void placeConnectionOutOfBounds() {
        assertThrows(RuntimeException.class, () -> service.addConnection(space1, new Vector2D(9, 0), space2, new Vector2D(6, 0)));
        assertThrows(RuntimeException.class, () -> service.addConnection(space2, new Vector2D(0, 8), space3, new Vector2D(0, 4)));
    }


//    Traversing a connection must not be possible in case the destination is occupied
    @Test
    public void traverseConnectionWithOccupiedDestination(){
        service.addConnection(space1, new Vector2D(0, 0), space2, new Vector2D(0, 0));

        executeTasks(service, cleaningDevice1, new Task[]{
            new Task("[en," + space1 + "]"),
            new Task("[tr," + space2 + "]")
        });

        executeTasks(service, cleaningDevice2, new Task[]{
            new Task("[en," + space1 + "]"),
            new Task("[tr," + space2 + "]")
        });

        assertPosition(service, cleaningDevice1, space2, 0, 0);
        assertPosition(service, cleaningDevice2, space1, 0, 0);
    }

//    Placing a wall out of bounds must not be possible
    @Test
    public void placeWallOutOfBounds(){
        assertThrows(RuntimeException.class, () -> service.addWall(space1, new Wall("(1,1)-(1,7)")));
        assertThrows(RuntimeException.class, () -> service.addWall(space2, new Wall("(1,1)-(1,6)")));
        assertThrows(RuntimeException.class, () -> service.addWall(space3, new Wall("(1,1)-(1,4)")));
    }

//    Using the TR-command must not be possible if there is no outgoing connection on the current position
    @Test
    public void traverseWithoutOutgoingConnectionOnTheCurrentPosition() {
        executeTasks(service, cleaningDevice1, new Task[]{
                new Task("[en," + space1 + "]"),
                new Task("[tr," + space2 + "]")
        });

        assertPosition(service, cleaningDevice1, space1, 0, 0);
    }
}
