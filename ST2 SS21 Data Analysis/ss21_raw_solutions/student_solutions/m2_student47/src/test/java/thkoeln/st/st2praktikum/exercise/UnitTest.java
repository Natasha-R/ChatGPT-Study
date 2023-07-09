package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnitTest {

    protected UUID field1;
    protected UUID field2;

    protected UUID miningMachine1;
    protected UUID miningMachine2;

    protected UUID connection1;
    protected UUID connection2;

    protected void assertPosition(MiningMachineService service, UUID miningMachineId, UUID expectedFieldId, int expectedX, int expectedY) {
        assertEquals(expectedFieldId,
                service.getMiningMachineFieldId(miningMachineId));
        assertEquals(new Point(expectedX, expectedY),
                service.getPoint(miningMachineId));
    }

    protected void createWorld(MiningMachineService service) {
        field1 = service.addField(4,4);
        field2 = service.addField(5,5);

        miningMachine1 = service.addMiningMachine("poop");
        miningMachine2= service.addMiningMachine("scoop");

        connection1 = service.addConnection(field1,new Point(1,2),field2,new Point(0,0));
        connection2 = service.addConnection(field2,new Point(1,1),field1,new Point(0,0));
    }

    @Test
    void TestBarrierOutOfBounds(){
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        assertThrows(RuntimeException.class, () -> service.addBarrier(field1,new Barrier("(3,3)-(3,8)")));
        assertThrows(RuntimeException.class, () -> service.addBarrier(field1,new Barrier("(3,-3)-(3,3)")));
    }

    @Test
    void TestMovementOutOfBounds(){
        MiningMachineService service = new MiningMachineService();
        createWorld(service);
        service.executeCommand(miningMachine1,new Command("[en,"+ field1 +"]")); // height & width = 4

        service.executeCommand(miningMachine1,new Command("[no,10]"));
        assertPosition(service, miningMachine1, field1, 0, 3);

        service.executeCommand(miningMachine1,new Command("[we,10]"));
        assertPosition(service, miningMachine1, field1, 0, 3);

        service.executeCommand(miningMachine1,new Command("[ea,10]"));
        assertPosition(service, miningMachine1, field1, 3, 3);

        service.executeCommand(miningMachine1,new Command("[so,10]"));
        assertPosition(service, miningMachine1, field1, 3, 0);

    }

    @Test
    void TestConnectionOutOfBounds(){
        MiningMachineService service = new MiningMachineService();
        createWorld(service);
        assertThrows(RuntimeException.class, () -> service.addConnection(field1,new Point(1,1),field2,new Point(10,10)));
        assertThrows(RuntimeException.class, () -> service.addConnection(field1,new Point(10,10),field2,new Point(1,1)));
    }

    @Test
    void TestMachinesBlockingEachOther(){
        MiningMachineService service = new MiningMachineService();
        createWorld(service);
        service.executeCommand(miningMachine1,new Command("[en," +field1 +"]"));
        service.executeCommand(miningMachine1,new Command("[no,3]"));
        assertPosition(service,miningMachine1,field1,0,3);

        service.executeCommand(miningMachine2,new Command("[en," +field1 +"]"));
        service.executeCommand(miningMachine2,new Command("[no,3]"));
        //assertThrows(RuntimeException.class,() ->service.executeCommand(miningMachine2,new Command("[no,3]")) );
        assertPosition(service,miningMachine2,field1,0,2);

    }
}
