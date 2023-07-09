package thkoeln.st.st2praktikum.exercise;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class E2UnitTests {

    private MiningMachineService service;

    private UUID field1;
    private UUID field2;

    private UUID miningMachine1;
    private UUID miningMachine2;

    private UUID connection1;

    @Before
    public void setup(){
        service = new MiningMachineService();
        miningMachine1 = service.addMiningMachine("The First");
        miningMachine2 = service.addMiningMachine("The Second");

        field1 = service.addField(5, 5);
        field2 = service.addField(6, 6);

        connection1 = service.addConnection(field1, new Vector2D(0,0), field2, new Vector2D(3,4));
    }
    @Test
    public void miningMachineHitAnotherMiningMachine(){
        service.executeCommand(miningMachine1, new Order("[en," + field1 + "]"));
        service.executeCommand(miningMachine1, new Order("[ea,1]"));
        service.executeCommand(miningMachine2, new Order("[en," + field1 + "]"));
        service.executeCommand(miningMachine2, new Order("[ea,5]"));

        assertEquals(new Vector2D(0, 0), service.getVector2D(miningMachine2));
        assertEquals(new Vector2D(1, 0), service.getVector2D(miningMachine1));

        service.executeCommand(miningMachine2, new Order("[no,2]"));
        service.executeCommand(miningMachine2, new Order("[ea,1]"));
        service.executeCommand(miningMachine1, new Order("[no,5]"));

        assertEquals(new Vector2D(1, 2), service.getVector2D(miningMachine2));
        assertEquals(new Vector2D(1, 1), service.getVector2D(miningMachine1));
    }

    @Test
    public void placeConnectionOutOfBounds(){
        assertThrows(RuntimeException.class, () -> service.addConnection(
                field1, new Vector2D("(4,4)"),
                field2, new Vector2D("(7,2)")));
        assertThrows(RuntimeException.class, () -> service.addConnection(
                field1, new Vector2D("(8,4)"),
                field2, new Vector2D("(3,2)")));
    }

    @Test
    public void connectionIsOccupied(){
        service.executeCommand(miningMachine1, new Order("[en," + field1 + "]"));
        service.executeCommand(miningMachine1, new Order("[tr," + field2 + "]"));
        service.executeCommand(miningMachine2, new Order("[en," + field1 + "]"));

        assertFalse(service.executeCommand(miningMachine2, new Order("[tr," + field2 + "]")));
    }
}
