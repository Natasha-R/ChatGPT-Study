package thkoeln.st.st2praktikum.exercise;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class someUnitTests {

    CleaningDeviceService service = new CleaningDeviceService();

    protected UUID space1;
    protected UUID space2;
    protected UUID space3;

    protected UUID cleaningDevice1;
    protected UUID cleaningDevice2;


    protected UUID connection1;


    @BeforeEach
    public void setUp(){
        space1 = service.addSpace(4,4);
        space2 = service.addSpace(3,3);
        space3 = service.addSpace(2,2);

        cleaningDevice1 = service.addCleaningDevice("marvin");
        cleaningDevice2 = service.addCleaningDevice("darwin");

        connection1 = service.addConnection(space1, new Coordinate("(3,0)"), space2, new Coordinate("(1,2)"));

    }

    protected void assertPosition(CleaningDeviceService service, UUID cleaningDeviceId, UUID expectedSpaceId, int expectedX, int expectedY) {
        assertEquals(expectedSpaceId,
                service.getCleaningDeviceSpaceId(cleaningDeviceId));
        assertEquals(new Coordinate(expectedX, expectedY),
                service.getCoordinate(cleaningDeviceId));
    }

    protected void executeCommands(CleaningDeviceService service, UUID cleaningDevice, Command[] Commands) {
        for (Command command : Commands) {
            service.executeCommand(cleaningDevice, command);
        }
    }


    @Test
    public void placingBarrierOutOfBoundsTest(){

        setUp();
        assertThrows(RuntimeException.class, () -> service.addBarrier(space1, new Barrier("(2,3)-(5,3)")));
        assertThrows(RuntimeException.class, () -> service.addBarrier(space2, new Barrier("(1,1)-(1,4)")));
        assertThrows(RuntimeException.class, () -> service.addBarrier(space3, new Barrier("(3,1)-(3,3)")));

    }

    @Test
    public void placingConnectionOutOfBoundsTest(){

        setUp();
        assertThrows(RuntimeException.class, () -> service.addConnection(space1,new Coordinate(3,3),space2, new Coordinate(1,4)));
        assertThrows(RuntimeException.class, () -> service.addConnection(space2,new Coordinate(3,0),space1, new Coordinate(1,1)));
        assertThrows(RuntimeException.class, () -> service.addConnection(space1,new Coordinate(2,0),space2, new Coordinate(3,1)));

    }

    @Test
    public void transportCommandTest(){

        setUp();
        executeCommands(service, cleaningDevice2,new Command[]{
                new Command("[en," + space2 + "]"),
                new Command("[ea,1]"),
                new Command("[no,2]"),
        });
        assertPosition(service, cleaningDevice2,space2, 1,2);

        assertThrows(RuntimeException.class, () -> executeCommands(service, cleaningDevice1,new Command[]{
                new Command("[en," + space1 + "]"),
                new Command("[ea,3]"),
                new Command("[tr," + space2 + "]"),
        }));
        assertPosition(service, cleaningDevice1,space1, 3,0);
    }
}
