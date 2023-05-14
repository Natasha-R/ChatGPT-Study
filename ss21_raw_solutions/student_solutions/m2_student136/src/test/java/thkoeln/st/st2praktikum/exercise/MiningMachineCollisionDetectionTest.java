package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

public class MiningMachineCollisionDetectionTest extends MovementTests
{
    @Test
    public void miningMachineCollisionDetectionTest() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeOrders(service, miningMachine1, new Order[]{
                new Order("[en," + field3 + "]"),
                new Order("[ea,1]"),
                new Order("[no,1]")
        });

        executeOrders(service, miningMachine2, new Order[]{
                new Order("[en," + field3 + "]"),
                new Order("[ea,1]"),
                new Order("[no,1]")
        });
        assertPosition(service, miningMachine2, field3, 1, 0);

        executeOrders(service, miningMachine2, new Order[]{
                new Order("[we,1]"),
                new Order("[no,1]"),
                new Order("[ea,1]")
        });
        assertPosition(service, miningMachine2, field3, 0, 1);

        executeOrders(service, miningMachine2, new Order[]{
                new Order("[no,1]"),
                new Order("[ea,1]"),
                new Order("[so,1]")
        });
        assertPosition(service, miningMachine2, field3, 1, 2);

        executeOrders(service, miningMachine2, new Order[]{
                new Order("[ea,1]"),
                new Order("[so,1]"),
                new Order("[we,1]")
        });
        assertPosition(service, miningMachine2, field3, 2, 1);
    }
}