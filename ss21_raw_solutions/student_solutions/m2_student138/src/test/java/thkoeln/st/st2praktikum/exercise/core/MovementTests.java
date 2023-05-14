package thkoeln.st.st2praktikum.exercise.core;

import thkoeln.st.st2praktikum.exercise.CleaningDeviceService;
import thkoeln.st.st2praktikum.exercise.Obstacle;
import thkoeln.st.st2praktikum.exercise.Command;
import thkoeln.st.st2praktikum.exercise.Vector2D;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public abstract class MovementTests {

    protected UUID space1;
    protected UUID space2;
    protected UUID space3;

    protected UUID cleaningDevice1;
    protected UUID cleaningDevice2;

    protected UUID connection1;
    protected UUID connection2;
    protected UUID connection3;


    protected void createWorld(CleaningDeviceService service) {
        space1 = service.addSpace(6,6);
        space2 = service.addSpace(5,5);
        space3 = service.addSpace(3,3);

        cleaningDevice1 = service.addCleaningDevice("marvin");
        cleaningDevice2 = service.addCleaningDevice("darwin");

        service.addObstacle(space1, new Obstacle("(0,3)-(2,3)"));
        service.addObstacle(space1, new Obstacle("(3,0)-(3,3)"));
        service.addObstacle(space2, new Obstacle("(0,2)-(4,2)"));

        connection1 = service.addConnection(space1, new Vector2D("(1,1)"), space2, new Vector2D("(0,1)"));
        connection2 = service.addConnection(space2, new Vector2D("(1,0)"), space3, new Vector2D("(2,2)"));
        connection3 = service.addConnection(space3, new Vector2D("(2,2)"), space2, new Vector2D("(1,0)"));
    }

    protected void assertPosition(CleaningDeviceService service, UUID cleaningDeviceId, UUID expectedSpaceId, int expectedX, int expectedY) {
        assertEquals(expectedSpaceId,
                service.getCleaningDeviceSpaceId(cleaningDeviceId));
        assertEquals(new Vector2D(expectedX, expectedY),
                service.getVector2D(cleaningDeviceId));
    }

    protected void executeCommands(CleaningDeviceService service, UUID cleaningDevice, Command[] Commands) {
        for (Command command : Commands) {
            service.executeCommand(cleaningDevice, command);
        }
    }
}
