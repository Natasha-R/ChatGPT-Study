package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;
import thkoeln.st.st2praktikum.exercise.field.Field;
import thkoeln.st.st2praktikum.exercise.field.FieldConnection;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

public class E3CustomTests extends MovementTests {
    private MiningMachineService service;

    @BeforeEach
    public void setupGame() {
        service = new MiningMachineService();
        createWorld(service);
    }

    @Test
    public void moveOutOfBoundsTest() {
        executeOrders(service, miningMachine1, new Order[] {
                new Order("[en," + field1 + "]"),
                new Order("[no,30]"),
                new Order("[ea,50]"),
                new Order("[so,3]"),
                new Order("[we,1]")
        });

        executeOrders(service, miningMachine2, new Order[] {
                new Order("[en," + field2 + "]"),
                new Order("[ea,7]"),
                new Order("[no,50]"),
                new Order("[we,9]"),
                new Order("[so,6]")
        });

        assertPosition(service, miningMachine1, field1, 1, 0);
        assertPosition(service, miningMachine2, field2, 0, 2);
    }

    @Test
    public void placeFieldConnectionOutOfBoundsTest() {
        assertThrows(RuntimeException.class, () -> new FieldConnection(new Field(UUID.randomUUID(), 6, 6), new Coordinate(0, -5), new Field(UUID.randomUUID(), 9, 2), new Coordinate(2, 3)));
    }

    @Test
    public void connectionDestinationIsOccupiedTest() {
        executeOrders(service, miningMachine1, new Order[]{

        });

        executeOrders(service, miningMachine1, new Order[]{
                new Order("[en," + field2 + "]"),
                new Order("[ea,1]"),
                new Order("[tr," + field3 + "]")
        });

        assertPosition(service, miningMachine1, field3, 2, 2);

        executeOrders(service, miningMachine2, new Order[]{
                new Order("[en," + field2 + "]"),
                new Order("[ea,1]"),
                new Order("[tr," + field3 + "]")
        });

        assertPosition(service, miningMachine2, field2, 1, 0);
    }

    @AfterEach
    public void finish() {
        service = null;
    }
}
