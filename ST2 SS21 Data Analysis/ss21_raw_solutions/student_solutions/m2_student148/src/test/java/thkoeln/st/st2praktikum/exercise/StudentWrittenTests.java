package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.*;


public class StudentWrittenTests extends MovementTests {

    MiningMachineService service;

    @BeforeEach
    void setupWorldForTests() {
        service = new MiningMachineService();
        createWorld(service);
    }

    @Test
    public void placingABarrierOutOfBoundsTest() {
        assertThrows(RuntimeException.class, () -> service.addBarrier(field1, new Barrier(new Coordinate(10, 3), new Coordinate(2, 5))));
        assertThrows(RuntimeException.class, () -> service.addBarrier(field2, new Barrier(new Coordinate(-3, 5), new Coordinate(0, 2))));
    }

    @Test
    public void placingAConnectionOutOfBoundsTest() {
        assertThrows(RuntimeException.class, () -> service.addConnection(field1, new Coordinate("(7,7)"), field2, new Coordinate("(5,5)")));
        assertThrows(RuntimeException.class, () -> service.addConnection(field3, new Coordinate("(4,1)"), field2, new Coordinate("(0,2)")));
    }

    @Test
    public void transportMachineAtANonTransportLocationTest() {
        executeCommands(service, miningMachine2, new Command[]{
                new Command("[en," + field2 + "]"),
                new Command("[no,3]"),
                new Command("[ea,4]"),
                new Command("[so,1]"),
                new Command("[we,3]"),
                new Command("[so,2]")
        });

        assertFalse(service.executeCommand(miningMachine2, new Command("[tr," + field2 + "]")));
        assertTrue(service.executeCommand(miningMachine2, new Command("[tr," + field3 + "]")));
        assertFalse(service.executeCommand(miningMachine2, new Command("[tr," + field1 + "]")));

        assertPosition(service, miningMachine2, field3, 2, 2);
    }
}
