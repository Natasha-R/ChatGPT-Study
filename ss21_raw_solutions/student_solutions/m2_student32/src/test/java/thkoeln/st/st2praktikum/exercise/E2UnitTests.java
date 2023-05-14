package thkoeln.st.st2praktikum.exercise;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class E2UnitTests {

    protected MiningMachineService service;
    protected UUID field1;
    protected UUID field2;
    protected UUID miningMachine;
    protected Order order1;
    protected Order order2;
    protected Coordinate coordinate1;
    protected Coordinate coordinate2;
    protected Barrier barrier1;

    @BeforeEach
    public void setUp() {
        service = new MiningMachineService();
        field1 = service.addField(3,3);
        field2 = service.addField(5,5);
        miningMachine = service.addMiningMachine("TestMachine");
        order1 = new Order("[en," + field1 + "]");
        order2 = new Order("[no,4]");
        coordinate1 = new Coordinate("(4,4)");
        coordinate2 = new Coordinate("(1,0)");
        barrier1 = new Barrier(new Coordinate(0,2), new Coordinate(0,4));
    }

    @Test
    public void moveOutOfBoundariesTest() {
        service.executeCommand(miningMachine,order1);
        service.executeCommand(miningMachine,order2);

        assertPosition(service, miningMachine, field1, 0, 3);
    }

    @Test
    public void placingConnectionOutOfBoundariesTest() {
        assertThrows(RuntimeException.class, () -> service.addConnection(field1, coordinate1, field2, coordinate2));
    }

    @Test
    public void placingBarrierOutOfBoundariesTest() {
        assertThrows(RuntimeException.class, () -> service.addBarrier(field1, barrier1));
    }

    protected void assertPosition(MiningMachineService service, UUID miningMachineId, UUID expectedFieldId, int expectedX, int expectedY) {
        assertEquals(expectedFieldId,
                service.getMiningMachineFieldId(miningMachineId));
        assertEquals(new Coordinate(expectedX, expectedY),
                service.getCoordinate(miningMachineId));
    }

}
