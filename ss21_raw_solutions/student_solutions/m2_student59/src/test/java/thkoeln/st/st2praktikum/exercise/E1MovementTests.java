package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class E1MovementTests extends MovementTests {

    @Test
    public void miningMachineSpawnOnSamePositionTest() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        assertTrue(service.executeCommand(miningMachine1, new Order("[en," + field1 + "]")));
        assertFalse(service.executeCommand(miningMachine2, new Order("[en," + field1 + "]")));
    }

    @Test
    public void moveToAnotherFieldAndBackTest() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeOrders(service, miningMachine1, new Order[]{
                new Order("[en," + field2 + "]"),
                new Order("[ea,1]"),
                new Order("[tr," + field3 + "]")
        });

        assertPosition(service, miningMachine1, field3, 2, 2);

        executeOrders(service, miningMachine1, new Order[]{
                new Order("[tr," + field2 + "]")
        });

        assertPosition(service, miningMachine1, field2, 1, 0);
    }

    @Test
    public void moveToAnotherFieldOnWrongPositionTest() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        service.executeCommand(miningMachine1, new Order("[en," + field1 + "]"));
        assertFalse(service.executeCommand(miningMachine1, new Order("[tr," + field2 + "]")));

        assertPosition(service, miningMachine1, field1, 0, 0);
    }

    @Test
    public void bumpIntoBarrierTest() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeOrders(service, miningMachine1, new Order[]{
                new Order("[en," + field2 + "]"),
                new Order("[ea,2]"),
                new Order("[no,3]"),
                new Order("[we,1]"),
        });

        assertPosition(service, miningMachine1, field2, 1, 1);
    }

    @Test
    public void moveTwoMiningMachinesAtOnceTest() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeOrders(service, miningMachine1, new Order[]{
                new Order("[en," + field1 + "]"),
                new Order("[ea,1]"),
                new Order("[no,4]")
        });

        executeOrders(service, miningMachine2, new Order[]{
                new Order("[en," + field1 + "]"),
                new Order("[ea,2]"),
                new Order("[no,5]")
        });

        assertPosition(service, miningMachine1, field1, 1, 2);
        assertPosition(service, miningMachine2, field1, 2, 5);
    }

    @Test
    public void moveOutOfBoundariesTest() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeOrders(service, miningMachine1, new Order[]{
                new Order("[en," + field3 + "]"),
                new Order("[no,5]"),
                new Order("[ea,5]"),
                new Order("[so,5]"),
                new Order("[we,5]"),
                new Order("[no,1]")
        });

        assertPosition(service, miningMachine1, field3, 0, 1);
    }

    @Test
    public void traverseAllFieldsTest() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeOrders(service, miningMachine1, new Order[]{
                new Order("[en," + field1 + "]"),
                new Order("[no,1]"),
                new Order("[ea,1]"),
                new Order("[tr," + field2 + "]"),
                new Order("[so,2]"),
                new Order("[ea,1]"),
                new Order("[tr," + field3 + "]"),
                new Order("[we,5]"),
                new Order("[ea,2]"),
                new Order("[tr," + field2 + "]"),
                new Order("[no,4]"),
                new Order("[ea,3]")
        });

        assertPosition(service, miningMachine1, field2, 4, 1);
    }
}
