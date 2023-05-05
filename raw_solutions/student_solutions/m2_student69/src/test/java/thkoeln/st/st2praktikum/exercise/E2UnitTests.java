package thkoeln.st.st2praktikum.exercise;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

//2, 3, 5
public class E2UnitTests extends MovementTests {
    private MiningMachineService service;

    @BeforeEach
    public void start() {
        service = new MiningMachineService();
        createWorld(service);
    }

    @Test
    public void connectionOutOfBoundTest() {
        assertThrows(RuntimeException.class, () -> service.addConnection(field1, new Coordinate(20, 20), field3, new Coordinate(0, 0)));
    }

    @Test
    public void transportWithoutDestinationTest() {
        assertThrows(RuntimeException.class, () -> service.addConnection(field1, new Coordinate(1, 1), field3, null));
    }

    @AfterEach
    public void stop() {
        service.FieldList.clear();
        service.machineDirections.getMachineList().clear();
    }

    @Test
    public void moveOutOfBarriers() {
        service = new MiningMachineService();
        createWorld(service);
        executeTasks(service, miningMachine1, new Task[]{
                new Task("[en," + field1 + "]"),
                new Task("[ea,17]"),
                new Task("[no,3]"),
                new Task("[so,12]"),
                new Task("[we,9]")
        });

        executeTasks(service, miningMachine2, new Task[]{
                new Task("[en," + field1 + "]"),
                new Task("[no,16]"),
                new Task("[ea,15]"),
                new Task("[so,3]")
        });

        assertPosition(service, miningMachine1, field1, 3, 0);
        assertPosition(service, miningMachine2, field1, 2, 0);
    }
}
