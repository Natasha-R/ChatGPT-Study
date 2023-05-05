package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

public class TraverseConnectionDestinationOccupiedTest extends MovementTests
{
    @Test
    public void traverseConnectionDestinationOccupiedTest() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeOrders(service, miningMachine1, new Order[]{
                new Order("[en," + field2 + "]"),
                new Order("[ea,1]"),
                new Order("[tr," + field3 + "]")
        });
        assertPosition(service, miningMachine1, field3, 2, 2);

        executeOrders(service, miningMachine2, new Order[]{
                new Order("[en," + field2 + "]"),
                new Order("[ea,1]"),
                new Order("[tr," + field3 + "]")
        });
        assertPosition(service, miningMachine2, field2, 1, 0);
    }
}