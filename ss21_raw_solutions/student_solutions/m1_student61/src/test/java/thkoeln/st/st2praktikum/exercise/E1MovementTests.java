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

        assertTrue(service.executeCommand(miningMachine1, "[en," + field1 + "]"));
        assertFalse(service.executeCommand(miningMachine2, "[en," + field1 + "]"));
    }

    @Test
    public void moveToAnotherFieldAndBackTest() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeCommands(service, miningMachine1, new String[]{
                "[en," + field2 + "]",
                "[ea,1]",
                "[tr," + field3 + "]"
        });

        assertPosition(service, miningMachine1, field3, 2, 2);

        executeCommands(service, miningMachine1, new String[]{
                "[tr," + field2 + "]"
        });

        assertPosition(service, miningMachine1, field2, 1, 0);
    }

    @Test
    public void moveToAnotherFieldOnWrongPositionTest() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        service.executeCommand(miningMachine1, "[en," + field1 + "]");
        assertFalse(service.executeCommand(miningMachine1, "[tr," + field2 + "]"));

        assertPosition(service, miningMachine1, field1, 0, 0);
    }

    @Test
    public void bumpIntoObstacleTest() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeCommands(service, miningMachine1, new String[]{
                "[en," + field2 + "]",
                "[ea,2]",
                "[no,3]",
                "[we,1]"
        });

        assertPosition(service, miningMachine1, field2, 1, 1);
    }

    @Test
    public void moveTwoMiningMachinesAtOnceTest() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeCommands(service, miningMachine1, new String[]{
                "[en," + field1 + "]",
                "[ea,1]",
                "[no,4]"
        });

        executeCommands(service, miningMachine2, new String[]{
                "[en," + field1 + "]",
                "[ea,2]",
                "[no,5]"
        });

        assertPosition(service, miningMachine1, field1, 1, 2);
        assertPosition(service, miningMachine2, field1, 2, 5);
    }

    @Test
    public void moveOutOfBoundariesTest() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeCommands(service, miningMachine1, new String[]{
                "[en," + field3 + "]",
                "[no,5]",
                "[ea,5]",
                "[so,5]",
                "[we,5]",
                "[no,1]"
        });

        assertPosition(service, miningMachine1, field3, 0, 1);
    }

    @Test
    public void traverseAllFieldsTest() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeCommands(service, miningMachine1, new String[]{
                "[en," + field1 + "]",
                "[no,1]",
                "[ea,1]",
                "[tr," + field2 + "]",
                "[so,2]",
                "[ea,1]",
                "[tr," + field3 + "]",
                "[we,5]",
                "[ea,2]",
                "[tr," + field2 + "]",
                "[no,4]",
                "[ea,3]"
        });

        assertPosition(service, miningMachine1, field2, 4, 1);
    }
}
