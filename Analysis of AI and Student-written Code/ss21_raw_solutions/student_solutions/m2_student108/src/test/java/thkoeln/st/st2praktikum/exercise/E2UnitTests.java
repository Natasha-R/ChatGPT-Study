package thkoeln.st.st2praktikum.exercise;

import org.junit.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

public class E2UnitTests extends MovementTests {

    @Test
    public void collideWithOtherMiningMachineWhileMovement() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeOrders(service, miningMachine1, new Order[]{
                new Order("[en," + field1 + "]"),
                new Order("[ea,1]")
        });

        executeOrders(service, miningMachine2, new Order[]{
                new Order("[en," + field1 + "]"),
                new Order("[ea,3]")
        });

        assertPosition(service, miningMachine2, field1, 0, 0);
    }

    @Test
    public void moveOutOfBounds() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeOrders(service, miningMachine1, new Order[]{
                new Order("[en," + field1 + "]"),
                new Order("[we,4]")
        });

        assertPosition(service, miningMachine1, field1, 0, 0);
    }

    @Test
    public void useConnectionWithOccupiedDestinationField() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeOrders(service, miningMachine1, new Order[]{
                new Order("[en," + field2 + "]"),
                new Order("[ea,1]"),
                new Order("[tr," + field3 + "]")
        });

        executeOrders(service, miningMachine2, new Order[]{
                new Order("[en," + field2 + "]"),
                new Order("[ea,1]"),
                new Order("[tr," + field3 + "]")
        });

        assertPosition(service, miningMachine2, field2, 1, 0);
    }
}
