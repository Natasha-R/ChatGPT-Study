package thkoeln.st.st2praktikum.exercise;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import java.util.IllegalFormatException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestsForM2 extends MovementTests{

    @Test
    public void addTwoMiningMaschinesAndLetThemCollide(){
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeCommands(service, miningMachine1, new Command[]{
                new Command("[en," + field2 + "]"),
                new Command("[ea,1]")
        });
        executeCommands(service, miningMachine2, new Command[]{
                new Command("[en," + field2 + "]"),
                new Command("[ea,1]")
        });

        assertPosition(service, miningMachine1, field2, 1, 0);
        assertPosition(service, miningMachine2, field2, 0, 0);
    }

    @Test
    public void checkBorders(){
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeCommands(service, miningMachine1, new Command[]{
                new Command("[en," + field3 + "]"),
                new Command("[ea,5]")
        });

        assertPosition(service, miningMachine1, field3, 2, 0);

        executeCommands(service, miningMachine1, new Command[]{
                new Command("[no,5]")
        });

        assertPosition(service, miningMachine1, field3, 2, 2);

        executeCommands(service, miningMachine1, new Command[]{
                new Command("[we,5]")
        });

        assertPosition(service, miningMachine1, field3, 0, 2);

        executeCommands(service, miningMachine1, new Command[]{
                new Command("[so,5]")
        });

        assertPosition(service, miningMachine1, field3, 0, 0);
    }

    @Test
    public void tryTROnNoConnection(){
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeCommands(service, miningMachine1, new Command[]{
                new Command("[en," + field1 + "]")
        });

        assertFalse(service.executeCommand(miningMachine1, new Command("[tr," + field2 + "]")));
    }

    @Test
    public void traversierungDarfNichtgemachtWerden(){
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeCommands(service, miningMachine1, new Command[]{
                new Command("[en," + field1 + "]"),
                new Command("[ea,1]"),
                new Command("[tr," + field2 + "]")
        });
        executeCommands(service, miningMachine2, new Command[]{
                new Command("[en," + field1 + "]"),
                new Command("[ea,1]")
        });
        assertFalse(service.executeCommand(miningMachine2, new Command("[tr," + field2 + "]")));
    }
}
