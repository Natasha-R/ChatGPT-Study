package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class E2Tests {
    UUID field1;
    UUID field2;

    UUID miningMachine1;

    UUID connection1;

    MiningMachineService service;

    @BeforeEach
    public void createWorld(){
        service = new MiningMachineService();
        field1 = service.addField(5,5);
        field2 = service.addField(7,7);
        miningMachine1 = service.addMiningMachine("David");
        connection1 = service.addConnection(field1 , new Point(2,2), field2, new Point( 3,4));
    }

    @Test
    public void placeWallOutOfBounds(){
        assertThrows(RuntimeException.class, () -> service.addWall(field1, new Wall(new Point(2,2), new Point(2,7))));
        assertThrows(RuntimeException.class, () -> service.addWall(field1, new Wall(new Point(-1,2), new Point(1,2))));
        assertThrows(RuntimeException.class, () -> service.addWall(field1, new Wall(new Point(0,0), new Point(6,0))));
    }

    @Test
    public void moveOutOfBounds1(){
        service.executeCommand(miningMachine1, new Task("[en," + field1 + "]"));
        service.executeCommand(miningMachine1, new Task("[ea," + 2 + "]"));
        service.executeCommand(miningMachine1, new Task("[no," + 7 + "]"));

        assertEquals(new Point(2, 4),
                service.getPoint(miningMachine1));
    }

    @Test
    public void moveOutOfBounds2(){
        service.executeCommand(miningMachine1, new Task("[en," + field1 + "]"));
        service.executeCommand(miningMachine1, new Task("[no," + 4 + "]"));
        service.executeCommand(miningMachine1, new Task("[ea," + 5 + "]"));

        assertEquals(new Point(4, 4),
                service.getPoint(miningMachine1));
    }

    @Test
    public void moveOutOfBounds3(){
        service.executeCommand(miningMachine1, new Task("[en," + field1 + "]"));
        service.executeCommand(miningMachine1, new Task("[no," + 2 + "]"));
        service.executeCommand(miningMachine1, new Task("[so," + 4 + "]"));
        service.executeCommand(miningMachine1, new Task("[we," + 1 + "]"));

        assertEquals(new Point(0, 0),
                service.getPoint(miningMachine1));
    }

    @Test
    public void transportWithNoConnectOnCurrentPos(){
        service.executeCommand(miningMachine1, new Task("[en," + field1 + "]"));
        service.executeCommand(miningMachine1, new Task("[no," + 1 + "]"));
        service.executeCommand(miningMachine1, new Task("[ea," + 2 + "]"));
        service.executeCommand(miningMachine1, new Task("[tr," + field2 + "]"));

        assertEquals(field1,
                service.getMiningMachineFieldId(miningMachine1));
        assertEquals(new Point(2, 1),
                service.getPoint(miningMachine1));
    }
}
