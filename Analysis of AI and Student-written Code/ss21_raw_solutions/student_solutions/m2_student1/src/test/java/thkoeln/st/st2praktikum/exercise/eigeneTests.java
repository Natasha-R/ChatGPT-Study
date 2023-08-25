package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class eigeneTests extends MovementTests {
    MiningMachineService service;

    @BeforeEach
    public void createWorld(){
        service = new MiningMachineService();
        createWorld(service);
    }

    @Test
    public void transportNotPossibleDestinationOccupied() {
        executeCommands(service, miningMachine1, new Command[]{
                new Command("[en," + field2 + "]"),
                new Command("[no,1]")
        });
        executeCommands(service, miningMachine2, new Command[]{
                new Command("[en," + field1 + "]"),
                new Command("[no,1]"),
                new Command("[ea,1]"),
                new Command("[tr," + field2 + "]")
        });

        assertPosition(service,miningMachine1,field2,0,1);
        assertPosition(service,miningMachine2,field1,1,1);
    }

    @Test
    public void barrierNotOnFieldNoPlacement(){
        assertThrows(RuntimeException.class, () -> service.addBarrier(field1, new Barrier("(-17,3)-(7,3)")));
        assertThrows(RuntimeException.class, () -> service.addBarrier(field1, new Barrier("(17,3)-(17,6)")));
        assertThrows(RuntimeException.class, () -> service.addBarrier(field1, new Barrier("(3,-4)-(3,7)")));
        assertThrows(RuntimeException.class, () -> service.addBarrier(field1, new Barrier("(1,30)-(12,30)")));

    }

    @Test
    public void connectionNotOnFieldNoPlacement(){
        assertThrows(RuntimeException.class, () -> service.addConnection(field1,new Point("(-3,1)"),field3, new Point("(7,19)")));
        assertThrows(RuntimeException.class, () -> service.addConnection(field1,new Point("(30,1)"),field3, new Point("(11,1)")));
        assertThrows(RuntimeException.class, () -> service.addConnection(field1,new Point("(3,-1)"),field2, new Point("(12,-3)")));
        assertThrows(RuntimeException.class, () -> service.addConnection(field1,new Point("(3,20)"),field2, new Point("(0,1)")));

    }
}
