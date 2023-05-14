package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class E2Tests{

    protected MiningMachineService service;
    protected UUID field1;
    protected UUID field2;
    protected UUID miningMachine1;
    protected UUID miningMachine2;
    protected UUID connection1;

    @BeforeEach
    protected void createWorld(){
        service = new MiningMachineService();
        field1 = service.addField(7,8);
        field2 = service.addField( 5,4);

        miningMachine1 = service.addMiningMachine("Morty");
        miningMachine2 = service.addMiningMachine("Rick");

        connection1 = service.addConnection(field1, new Coordinate( "(5,6)"), field2, new Coordinate("(3,3)"));
    }

    @AfterEach
    protected void tearDown(){
        service = null;
    }

    @Test
    protected void moveOnFieldWithAnotherMiningMachineFromSouth(){

        service.executeCommand(miningMachine1, new Task("[en," + field1 + "]"));
        service.executeCommand(miningMachine1, new Task("[no,4]"));

        service.executeCommand(miningMachine2, new Task("[en," + field1 + "]"));
        service.executeCommand(miningMachine2, new Task("[no,5]"));

        Assertions.assertEquals(service.getCoordinate(miningMachine2), new Coordinate(0,3));
    }

    @Test
    protected void moveOnFieldWithAnotherMiningMachineFromNorth(){
        service.executeCommand(miningMachine1, new Task("[en," + field1 + "]"));
        service.executeCommand(miningMachine1, new Task("[no,5]"));

        service.executeCommand(miningMachine2, new Task("[en," + field1 + "]"));
        service.executeCommand(miningMachine2, new Task("[no,3]"));

        service.executeCommand(miningMachine1, new Task("[so,3]"));

        Assertions.assertEquals(service.getCoordinate(miningMachine1), new Coordinate(0,4));
    }

    @Test
    protected void moveOnFieldWithAnotherMiningMachineFromWest(){
        service.executeCommand(miningMachine1, new Task("[en," + field1 + "]"));
        service.executeCommand(miningMachine1, new Task("[ea,4]"));

        service.executeCommand(miningMachine2, new Task("[en," + field1 + "]"));
        service.executeCommand(miningMachine2, new Task("[ea,5]"));

        Assertions.assertEquals(service.getCoordinate(miningMachine2), new Coordinate(3,0));
    }

    @Test
    protected void moveOnFieldWithAnotherMiningMachineFromEast(){
        service.executeCommand(miningMachine1, new Task("[en," + field1 + "]"));
        service.executeCommand(miningMachine1, new Task("[ea,5]"));

        service.executeCommand(miningMachine2, new Task("[en," + field1 + "]"));
        service.executeCommand(miningMachine2, new Task("[ea,3]"));

        service.executeCommand(miningMachine1, new Task("[we,4]"));

        Assertions.assertEquals(service.getCoordinate(miningMachine1), new Coordinate(4,0));
    }

    @Test
    protected void placeConnectionOutOfBoundsAtSource(){
        Assertions.assertThrows(RuntimeException.class, () -> service.addConnection(field2,new Coordinate(5,5), field1, new Coordinate(3,4)));
    }

    @Test
    protected void placeConnectionOutOfBoundsAtDestination(){
        Assertions.assertThrows(RuntimeException.class, () -> service.addConnection(field1,new Coordinate(5,5), field2, new Coordinate(8,8)));
    }

    @Test
    protected void useConnectionWhileDestinationOccupied(){
        service.executeCommand(miningMachine1, new Task("[en," + field1 + "]"));
        service.executeCommand(miningMachine1, new Task("[ea,5]"));
        service.executeCommand(miningMachine1, new Task("[no,6]"));

        service.executeCommand(miningMachine2, new Task("[en," + field2 + "]"));
        service.executeCommand(miningMachine2, new Task("[ea,3]"));
        service.executeCommand(miningMachine2, new Task("[no,3]"));

        service.executeCommand(miningMachine1,new Task("[tr," + connection1 + "]"));

        Assertions.assertEquals(service.getMiningMachineFieldId(miningMachine1), field1);
        Assertions.assertEquals(service.getCoordinate(miningMachine1), new Coordinate(5,6));

    }
}
