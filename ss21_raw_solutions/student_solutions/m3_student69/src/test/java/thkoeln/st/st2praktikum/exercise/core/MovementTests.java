package thkoeln.st.st2praktikum.exercise.core;

import org.springframework.web.context.WebApplicationContext;
import thkoeln.st.springtestlib.core.ObjectInfoRetriever;
import thkoeln.st.st2praktikum.exercise.MiningMachineService;
import thkoeln.st.st2praktikum.exercise.Barrier;
import thkoeln.st.st2praktikum.exercise.Task;
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

    protected UUID transportTechnology1;
    protected UUID transportTechnology2;

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

        service.addBarrier(field1, new Barrier("(0,3)-(2,3)"));
        service.addBarrier(field1, new Barrier("(3,0)-(3,3)"));
        service.addBarrier(field2, new Barrier("(0,2)-(4,2)"));

        transportTechnology1 = service.addTransportTechnology("Tunnel");
        transportTechnology2 = service.addTransportTechnology("Conveyor belt");

        connection1 = service.addConnection(transportTechnology1, field1, new Coordinate("(1,1)"), field2, new Coordinate("(0,1)"));
        connection2 = service.addConnection(transportTechnology1, field2, new Coordinate("(1,0)"), field3, new Coordinate("(2,2)"));
        connection3 = service.addConnection(transportTechnology2, field3, new Coordinate("(2,2)"), field2, new Coordinate("(1,0)"));
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

    protected void executeTasks(MiningMachineService service, UUID miningMachine, Task[] tasks) {
        for (Task task : tasks) {
            service.executeCommand(miningMachine, task);
        }
    }
}
