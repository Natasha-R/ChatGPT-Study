package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

public class CompleteMoveOutOfBoundariesTest extends MovementTests
{
    @Test
    public void moveOutOfBoundariesTest() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeOrders(service, miningMachine1, new Order[]{
                new Order("[en," + field3 + "]"),
                new Order("[no,5]")
        });
        assertPosition(service, miningMachine1, field3, 0, 2);

        executeOrders(service, miningMachine1, new Order[]{
                new Order("[ea,5]")
        });
        assertPosition(service, miningMachine1, field3, 2, 2);

        executeOrders(service, miningMachine1, new Order[]{
                new Order("[so,5]")
        });
        assertPosition(service, miningMachine1, field3, 2, 0);

        executeOrders(service, miningMachine1, new Order[]{
                new Order("[we,5]")
        });
        assertPosition(service, miningMachine1, field3, 0, 0);

        executeOrders(service, miningMachine1, new Order[]{
                new Order("[no,1]")
        });
        assertPosition(service, miningMachine1, field3, 0, 1);
    }
}