package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class MiningMachineMovementTests {

    private MiningMachineService miningMachineService = new MiningMachineService();
    private UUID field1;
    private UUID field2;

    private UUID miningMachine1;
    private UUID miningMachine2;

    private Task task1;
    private Task task2;
    private Task task3;
    private Task task4;


    @BeforeEach
    public void setUp() {
        field1 = miningMachineService.addField(5,9);
        field2 = miningMachineService.addField(6,10);

        task1 = new Task(TaskType.ENTER,field1);
        task2 = new Task(TaskType.EAST,4);
        task3 = new Task(TaskType.NORTH,10);
        task4 = new Task(TaskType.TRANSPORT,field2);

        miningMachine1 = miningMachineService.addMiningMachine("herald");
        miningMachineService.executeCommand(miningMachine1,task1);

        miningMachineService.addConnection(field1,new Point(4,0),field2,new Point(0,3));
    }

    //Hitting another miningMachine during a Move-Command has to interrupt the current movement
    @Test
    public void hitAnotherMiningMachineTest(){
        miningMachine2 = miningMachineService.addMiningMachine("arnold");

        miningMachineService.executeCommand(miningMachine1,task2);
        miningMachineService.executeCommand(miningMachine2,task1);
        miningMachineService.executeCommand(miningMachine2,task2);

        assertTrue(miningMachineService.getPoint(miningMachine2).equals(new Point(3,0)));

    }

    //Trying to move out of bounds must not be possible
    @Test
    public void moveOutBoundsTest(){
        miningMachineService.executeCommand(miningMachine1,task2);
        miningMachineService.executeCommand(miningMachine1,task3);
        assertTrue(miningMachineService.getPoint(miningMachine1).equals(new Point(4,8)));
    }

    //Trying to move out of bounds must not be possible
    @Test
    public void transportNotPossibleTest(){
        assertFalse(miningMachineService.executeCommand(miningMachine1,task4));
    }
}

