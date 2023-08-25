package thkoeln.st.st2praktikum.exercise.core;

import org.springframework.data.repository.CrudRepository;
import org.springframework.web.context.WebApplicationContext;
import thkoeln.st.springtestlib.core.GenericTests;
import thkoeln.st.st2praktikum.exercise.miningmachine.application.MiningMachineService;
import thkoeln.st.st2praktikum.exercise.field.application.FieldService;
import thkoeln.st.st2praktikum.exercise.field.domain.Wall;
import thkoeln.st.st2praktikum.exercise.transportcategory.application.TransportCategoryService;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import java.lang.reflect.Method;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


public abstract class MovementTests extends GenericTests {

    protected static final String ROBOT_NAME_1 = "Marvin";
    protected static final String ROBOT_NAME_2 = "Darvin";

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


    protected MovementTests(WebApplicationContext appContext) {
        super(appContext);
    }


    protected void createWorld(MiningMachineService miningMachineService, FieldService fieldService, TransportCategoryService transportCategoryService) {
        field1 = fieldService.addField(6,6);
        field2 = fieldService.addField(5,5);
        field3 = fieldService.addField(3,3);

        fieldService.addWall(field1, Wall.fromString("(0,3)-(2,3)"));
        fieldService.addWall(field1, Wall.fromString("(3,0)-(3,3)"));
        fieldService.addWall(field2, Wall.fromString("(0,2)-(4,2)"));

        miningMachine1 = miningMachineService.addMiningMachine(ROBOT_NAME_1);
        miningMachine2 = miningMachineService.addMiningMachine(ROBOT_NAME_2);

        transportCategory1 = transportCategoryService.addTransportCategory("Tunnel");
        transportCategory2 = transportCategoryService.addTransportCategory("Conveyor belt");

        connection1 = transportCategoryService.addConnection(transportCategory1, field1, Coordinate.fromString("(1,1)"), field2, Coordinate.fromString("(0,1)"));
        connection2 = transportCategoryService.addConnection(transportCategory1, field2, Coordinate.fromString("(1,0)"), field3, Coordinate.fromString("(2,2)"));
        connection3 = transportCategoryService.addConnection(transportCategory2, field3, Coordinate.fromString("(2,2)"), field2, Coordinate.fromString("(1,0)"));
    }

    protected void assertPosition(UUID miningMachineId, UUID expectedFieldId, int expectedX, int expectedY) throws Exception {
        Object miningMachine = getMiningMachineRepository().findById(miningMachineId).get();

        // Assert Grid
        Method getFieldMethod = miningMachine.getClass().getMethod("getFieldId");
        assertEquals(expectedFieldId,
                getFieldMethod.invoke(miningMachine));

        // Assert Pos
        Method getCoordinateMethod = miningMachine.getClass().getMethod("getCoordinate");
        assertEquals(new Coordinate(expectedX, expectedY),
                getCoordinateMethod.invoke(miningMachine));
    }

    protected CrudRepository<Object, UUID> getMiningMachineRepository() throws Exception {
        return oir.getRepository("thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine");
    }

    protected void executeOrders(MiningMachineService service, UUID miningMachine, Order[] orders) {
        for (Order order : orders) {
            service.executeCommand(miningMachine, order);
        }
    }
}
