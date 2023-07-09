package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class E2MyTests extends MovementTests {

    private MiningMachineService service;

    @BeforeEach
    private void beforeEachTest(){
        service = new MiningMachineService();
    }

    @Test
    public void miningMachineMoveOutOfBoundsTest(){
        createWorld(service);

        assertTrue(service.executeCommand(miningMachine1, new Command("[en," + field1 + "]")));
        assertTrue(service.executeCommand(miningMachine1, new Command("[ea," + 1 + "]")));
        assertTrue(service.executeCommand(miningMachine1, new Command("[so," + 2 + "]")));
        assertPosition(service, miningMachine1, field1, 1, 0);

        assertTrue(service.executeCommand(miningMachine2, new Command("[en," + field1 + "]")));
        assertTrue(service.executeCommand(miningMachine2, new Command("[we," + 2 + "]")));
        assertPosition(service, miningMachine2, field1, 0, 0);
    }

    @Test
    public void miningMachineTraversingToOccupiedDestinationTest(){
        createWorld(service);

        executeCommands(service, miningMachine1, new Command[]{
                new Command("[en," + field1 + "]"),
                new Command("[no,1]"),
                new Command("[ea,1]"),
                new Command("[tr," + field2 + "]"),
        });
        assertPosition(service, miningMachine1, field2, 0, 1);

        executeCommands(service, miningMachine2, new Command[]{
                new Command("[en," + field1 + "]"),
                new Command("[no,1]"),
                new Command("[ea,1]"),

        });
        assertFalse(service.executeCommand(miningMachine2, new Command("[tr," + field2 + "]")));
    }

    @Test
    public void ObstacleOutOfBoundsTest() {
        field1 = service.addField(6,6);

        assertThrows(RuntimeException.class, () -> service.addObstacle(field1, new Obstacle(new Coordinate(60, 1 ), new Coordinate(60, 2 ))));
        assertThrows(RuntimeException.class, () -> service.addObstacle(field1, new Obstacle(new Coordinate(2, 60 ), new Coordinate(5, 60 ))));
    }

}
