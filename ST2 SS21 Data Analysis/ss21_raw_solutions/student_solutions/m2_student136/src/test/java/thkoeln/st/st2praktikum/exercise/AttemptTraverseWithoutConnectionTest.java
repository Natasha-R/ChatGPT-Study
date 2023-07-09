package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class AttemptTraverseWithoutConnectionTest extends MovementTests
{
    @Test
    public void attemptTraverseWithoutConnectionTest() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        service.executeCommand(miningMachine1, new Order("[en," + field2 + "]"));
        assertFalse(service.executeCommand(miningMachine1, new Order("[tr," + field3 + "]")));

        /*executeOrders(service, miningMachine1, new Order[]{
                new Order("[en," + field2 + "]"),
                new Order("[tr," + field3 + "]")
        });
        assertPosition(service, miningMachine1, field2, 0, 0);*/
    }
}