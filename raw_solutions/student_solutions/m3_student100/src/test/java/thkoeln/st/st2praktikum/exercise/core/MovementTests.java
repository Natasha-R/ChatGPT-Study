package thkoeln.st.st2praktikum.exercise.core;

import org.springframework.web.context.WebApplicationContext;
import thkoeln.st.springtestlib.core.ObjectInfoRetriever;
import thkoeln.st.st2praktikum.exercise.MiningMachineService;
import thkoeln.st.st2praktikum.exercise.Wall;
import thkoeln.st.st2praktikum.exercise.Order;
import thkoeln.st.st2praktikum.exercise.Coordinate;

import java.lang.reflect.Method;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


public abstract class MovementTests {

    protected UUID field1;
    protected UUID field2;
    protected UUID field3;

    protected UUID miningMachine1;
    protected UUID miningMachine2;

    protected UUID transportCategory1;
    protected UUID transportCategory2;

    protected UUID connection1;
    protected UUID connection2;
    protected UUID connection3;

    private ObjectInfoRetriever objectInfoRetriever;


    protected MovementTests(WebApplicationContext appContext) {
        objectInfoRetriever = new ObjectInfoRetriever(appContext);
    }


    protected void createWorld(MiningMachineService service) {
        field1 = service.addField(6,6);
        field2 = service.addField(5,5);
        field3 = service.addField(3,3);

        miningMachine1 = service.addMiningMachine("marvin");
        miningMachine2 = service.addMiningMachine("darwin");

        service.addWall(field1, new Wall("(0,3)-(2,3)"));
        service.addWall(field1, new Wall("(3,0)-(3,3)"));
        service.addWall(field2, new Wall("(0,2)-(4,2)"));

        transportCategory1 = service.addTransportCategory("Tunnel");
        transportCategory2 = service.addTransportCategory("Conveyor belt");

        connection1 = service.addConnection(transportCategory1, field1, new Coordinate("(1,1)"), field2, new Coordinate("(0,1)"));
        connection2 = service.addConnection(transportCategory1, field2, new Coordinate("(1,0)"), field3, new Coordinate("(2,2)"));
        connection3 = service.addConnection(transportCategory2, field3, new Coordinate("(2,2)"), field2, new Coordinate("(1,0)"));
    }

    protected void assertPosition(UUID miningMachineId, UUID expectedFieldId, int expectedX, int expectedY) throws Exception {
        Object miningMachine = objectInfoRetriever.getRepository("thkoeln.st.st2praktikum.exercise.MiningMachine").findById(miningMachineId).get();

        // Assert Grid
        Method getFieldMethod = miningMachine.getClass().getMethod("getFieldId");
        assertEquals(expectedFieldId,
                getFieldMethod.invoke(miningMachine));

        // Assert Pos
        Method getCoordinateMethod = miningMachine.getClass().getMethod("getCoordinate");
        assertEquals(new Coordinate(expectedX, expectedY),
                getCoordinateMethod.invoke(miningMachine));
    }

    protected void executeOrders(MiningMachineService service, UUID miningMachine, Order[] orders) {
        for (Order order : orders) {
            service.executeCommand(miningMachine, order);
        }
    }
}
