package thkoeln.st.st2praktikum.exercise.meineTests;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.Barrier;
import thkoeln.st.st2praktikum.exercise.MiningMachineService;
import thkoeln.st.st2praktikum.exercise.Order;
import thkoeln.st.st2praktikum.exercise.Point;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class E2_my_unit_tests extends MovementTests {

    @Test
    public void HittingAnotherMiningMachineDuringAMoveCommandHasToInterruptTheCurrentMovementTest() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeOrders(service, miningMachine1, new Order[]{
                new Order("[en," + field1 + "]"),
                new Order("[no,1]"),
        });
        assertPosition(service, miningMachine1, field1, 0, 1);

        executeOrders(service, miningMachine2, new Order[]{
                new Order("[en," + field1 + "]"),
                new Order("[no,1]"),
        });
        assertPosition(service, miningMachine2, field1, 0, 0);
    }
    @Test
    public void TryingToMoveOutOfBoundsMustNotBePossibleTest() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeOrders(service, miningMachine1, new Order[]{
                new Order("[en," + field3 + "]"),
                new Order("[no,3]"),
        });
        assertPosition(service, miningMachine1, field3, 0, 2);
        executeOrders(service, miningMachine1, new Order[]{
                new Order("[ea,3]"),
        });
        assertPosition(service, miningMachine1, field3, 2, 2);
        executeOrders(service, miningMachine1, new Order[]{
                new Order("[so,3]"),
        });
        assertPosition(service, miningMachine1, field3, 2, 0);
        executeOrders(service, miningMachine1, new Order[]{
                new Order("[we,3]"),
        });
        assertPosition(service, miningMachine1, field3, 0, 0);

    }
//    @Test
//    public void PlacingAConnectionOutOfBoundsMustNotBePossibleTest() {
//        MiningMachineService service = new MiningMachineService();
//        createWorld(service);
//
//        assertThrows(NoSquareFoundException.class, () -> service.addConnection(field3, new Point("(5,5)"), field2, new Point("(6,6)")));
//    }
    @Test
    public void TraversingAConnectionMustNotBePossibleInCaseTheDestinationIsOccupiedTest() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeOrders(service, miningMachine1, new Order[]{
                new Order("[en," + field3 + "]"),
                new Order("[no,2]"),
                new Order("[ea,2]"),
        });
        assertPosition(service, miningMachine1, field3, 2, 2);

        executeOrders(service, miningMachine2, new Order[]{
                new Order("[en," + field2 + "]"),
                new Order("[ea,1]"),
                new Order("[tr," + field3 + "]")
        });
        assertPosition(service, miningMachine2, field2, 1, 0);
    }
    @Test
    public void UsingThe_TR_CommandMustNotBePossibleIfThereIsNoOutgoingConnectionOnTheCurrentPosition() {
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeOrders(service, miningMachine2, new Order[]{
                new Order("[en," + field2 + "]"),
                new Order("[ea,3]"),
                new Order("[tr," + field3 + "]")
        });
        assertPosition(service, miningMachine2, field2, 3, 0);
    }
//    @Test
//    public void PlacingABarrierOutOfBoundsMustNotBePossible() {
//        MiningMachineService service = new MiningMachineService();
//        createWorld(service);
//
//        assertThrows(NoSquareFoundException.class, () -> service.addBarrier(field1, new Barrier("(1000,100)-(200,100)")));
//        assertThrows(NoSquareFoundException.class, () -> service.addBarrier(field1, new Barrier("(0,0)-(200,0)")));
//    }

    @Test
    public void SuperHeavyTest(){
        assertTrue(true);
    }

    @Test
    public void movement(){
        MiningMachineService service = new MiningMachineService();
        createWorld(service);

        executeOrders(service, miningMachine2, new Order[]{
                new Order("[en," + field2 + "]"),
                new Order("[ea,1]")
        });
        assertPosition(service, miningMachine2, field2, 1, 0);
    }
}
