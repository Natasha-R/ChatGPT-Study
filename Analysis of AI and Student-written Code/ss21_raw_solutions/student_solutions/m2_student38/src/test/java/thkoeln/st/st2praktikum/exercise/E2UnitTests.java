package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.*;

public class E2UnitTests extends MovementTests {
    private MiningMachineService miningMachineService;

    @BeforeEach
    public void initMachineAndWorld(){
        miningMachineService = new MiningMachineService();
        createWorld(miningMachineService);
    }

    @Test
    public void testOutOfBounds(){
        executeCommands(miningMachineService, miningMachine1, new Command[]{
                new Command("[en," + field3 + "]"),
                new Command("[no,6]"),
                new Command("[ea,4]"),
                new Command("[so,8]"),
                new Command("[we,8]")});
        assertPosition(miningMachineService,miningMachine1,field3,0,0);
    }

    @Test
    public void testConnectionOutOfBounds(){
        assertThrows(RuntimeException.class, () -> miningMachineService.addConnection(field1,
                new Point(2,3), field3, new Point(7,2)));
        assertThrows(RuntimeException.class, () -> miningMachineService.addConnection(field1,
                new Point(2,3), field2, new Point(5,2)));
        assertThrows(RuntimeException.class, () -> miningMachineService.addConnection(field2,
                new Point(2,3), field3, new Point(5,2)));
    }

    @Test
    public void testTransportNotPossible(){
        executeCommands(miningMachineService,miningMachine1,new Command[]{
                new Command("[en,"+field3+"]"),
                new Command("[no,4]"),
                new Command("[ea,3]"),
                new Command("[so,1]")
        });
        assertThrows(RuntimeException.class, () -> miningMachineService.executeCommand(miningMachine1,
                new Command("[tr," + field3 + "]")));
        assertThrows(RuntimeException.class, () -> miningMachineService.executeCommand(miningMachine1,
                new Command("[tr," + field1 + "]")));
    }
}
