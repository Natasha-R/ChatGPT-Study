package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class E2Tests extends MovementTests {
    protected CleaningDeviceService service;
    protected UUID space1,space2, cleaningDevice1;

    @BeforeEach
    public void TestInit(){
        service = new CleaningDeviceService();
        space1 = service.addSpace(6,6);
        space2 = service.addSpace(5,5);
        cleaningDevice1 = service.addCleaningDevice("Peter");
    }
    @Test
    public void SetWallOutOfBounds(){
        assertThrows(RuntimeException.class, () -> service.addWall(space1,new Wall(new Point(2,2), new Point(8,2))));
        assertThrows(RuntimeException.class, () -> service.addWall(space1,new Wall(new Point(7,7), new Point(7,9))));
    }
    @Test
    public void SetConnectorOutOfBounds(){
        assertThrows(RuntimeException.class, () -> service.addConnection(space1,new Point(7,7), space2,new Point(4,5)));
        assertThrows(RuntimeException.class, () -> service.addConnection(space2,new Point(6,6),space1, new Point(7,9)));
    }
    @Test
    public void MoveOutOfBounds(){
        executeCommands(service, cleaningDevice1, new Command[]{
                new Command("[en," + space1 + "]"),
                new Command("[no,7]"),
                new Command("[ea,7]"),
                new Command("[so,7]"),
                new Command("[we,7]"),
                new Command("[no,1]")
        });
        assertPosition(service,cleaningDevice1,space1,0,1);
    }
}
