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

        assertTrue(service.executeCommand(miningMachine1, new Command("[en," + field1 + "]")));
        assertFalse(service.executeCommand(miningMachine2, new Command("[en," + field1 + "]")));
    }

    @Test
    public void moveToAnotherFieldAndBackTest() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeCommands(service, miningMachine1, new Command[]{
                new Command("[en," + field2 + "]"),
                new Command("[ea,1]"),
                new Command("[tr," + field3 + "]")
        });

        assertPosition(service, miningMachine1, field3, 2, 2);

        executeCommands(service, miningMachine1, new Command[]{
                new Command("[tr," + field2 + "]")
        });

        assertPosition(service, miningMachine1, field2, 1, 0);
    }

    @Test
    public void moveToAnotherFieldOnWrongPositionTest() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        service.executeCommand(miningMachine1, new Command("[en," + field1 + "]"));
        assertFalse(service.executeCommand(miningMachine1, new Command("[tr," + field2 + "]")));

        assertPosition(service, miningMachine1, field1, 0, 0);
    }

    @Test
    public void bumpIntoObstacleTest() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeCommands(service, miningMachine1, new Command[]{
                new Command("[en," + field2 + "]"),
                new Command("[ea,2]"),
                new Command("[no,3]"),
                new Command("[we,1]"),
        });

        assertPosition(service, miningMachine1, field2, 1, 1);
    }

    @Test
    public void obstacleField1() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeCommands(service, miningMachine1, new Command[]{
                new Command("[en," + field1 + "]"),
                new Command("[no,4]")
        });
        assertPosition(service, miningMachine1, field1, 0, 2);
    }

    @Test
    public void moveTwoMiningMachinesAtOnceTest() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeCommands(service, miningMachine1, new Command[]{
                new Command("[en," + field1 + "]"),
                new Command("[ea,1]"),
                new Command("[no,4]")
        });

        executeCommands(service, miningMachine2, new Command[]{
                new Command("[en," + field1 + "]"),
                new Command("[ea,2]"),
                new Command("[no,5]")
        });

        assertPosition(service, miningMachine1, field1, 1, 2);
        assertPosition(service, miningMachine2, field1, 2, 5);
    }

    @Test
    public void moveOutOfBoundariesTest() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeCommands(service, miningMachine1, new Command[]{
                new Command("[en," + field3 + "]"),
                new Command("[no,5]"),
                new Command("[ea,5]"),
                new Command("[so,5]"),
                new Command("[we,5]"),
                new Command("[no,1]")
        });

        assertPosition(service, miningMachine1, field3, 0, 1);
    }

    @Test
    public void traverseAllFieldsTest() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeCommands(service, miningMachine1, new Command[]{
                new Command("[en," + field1 + "]"),
                new Command("[no,1]"),
                new Command("[ea,1]"),
                new Command("[tr," + field2 + "]"),
                new Command("[so,2]"),
                new Command("[ea,1]"),
                new Command("[tr," + field3 + "]"),
                new Command("[we,5]"),
                new Command("[ea,2]"),
                new Command("[tr," + field2 + "]"),
                new Command("[no,4]"),
                new Command("[ea,3]")
        });

        assertPosition(service, miningMachine1, field2, 4, 1);
    }
}
