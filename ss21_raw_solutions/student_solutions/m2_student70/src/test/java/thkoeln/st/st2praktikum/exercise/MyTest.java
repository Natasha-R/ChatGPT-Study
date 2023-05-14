package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.*;

public class MyTest extends MovementTests {
    CleaningDeviceService service = new CleaningDeviceService();

    @Test
    public void connectionOutOfBoundsTest() {
        createWorld(service);
        assertThrows(RuntimeException.class, () -> service.addConnection(space1, new Point("(15,155)"), space2, new Point("(0,1)")));

    }
    @Test
    public void TR_commandOutgoingConnectionOnCurrentPositionTest() {
        createWorld(service);

        executeTasks(service, cleaningDevice1, new Task[]{
                new Task("[en," + space2 + "]"),
                new Task("[ea,2]"),
                new Task("[no,3]"),
                new Task("[we,1]"),
                new Task("[tr," + space1 + "]")
        });

        assertPosition(service, cleaningDevice1, space2, 1, 1);

    }
    @Test
    public void wallOutOfBoundsTest() {
        createWorld(service);
        assertThrows(RuntimeException.class, () -> service.addWall(space1, new Wall("(1,2)-(1,80)")));


    }
}
