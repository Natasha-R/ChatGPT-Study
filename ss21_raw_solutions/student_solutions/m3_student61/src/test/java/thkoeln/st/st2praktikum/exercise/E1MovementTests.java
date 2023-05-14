package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.WebApplicationContext;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class E1MovementTests extends MovementTests {

    private MiningMachineService service;

    @Autowired
    public E1MovementTests(WebApplicationContext appContext, MiningMachineService service) {
        super(appContext);

        this.service = service;
    }

    @BeforeEach
    public void init() {
        createWorld(service);
    }

    @Test
    @Transactional
    public void miningMachinesSpawnOnSamePositionTest() {
        assertTrue(service.executeCommand(miningMachine1, new Order("[en," + field1 + "]")));
        assertFalse(service.executeCommand(miningMachine2, new Order("[en," + field1 + "]")));
    }

    @Test
    @Transactional
    public void moveToAnotherFieldAndBackTest() throws Exception {
        executeOrders(service, miningMachine1, new Order[]{
                new Order("[en," + field2 + "]"),
                new Order("[ea,1]"),
                new Order("[tr," + field3 + "]")
        });

        assertPosition(miningMachine1, field3, 2, 2);

        executeOrders(service, miningMachine1, new Order[]{
                new Order("[tr," + field2 + "]")
        });

        assertPosition(miningMachine1, field2, 1, 0);
    }

    @Test
    @Transactional
    public void moveToAnotherFieldOnWrongPositionTest() throws Exception {
        service.executeCommand(miningMachine1, new Order("[en," + field1 + "]"));
        assertFalse(service.executeCommand(miningMachine1, new Order("[tr," + field2 + "]")));

        assertPosition(miningMachine1, field1, 0, 0);
    }

    @Test
    @Transactional
    public void bumpIntoObstacleTest() throws Exception {
        executeOrders(service, miningMachine1, new Order[]{
                new Order("[en," + field2 + "]"),
                new Order("[ea,2]"),
                new Order("[no,3]"),
                new Order("[we,1]"),
        });

        assertPosition(miningMachine1, field2, 1, 1);
    }

    @Test
    @Transactional
    public void moveTwoMiningMachinesAtOnceTest() throws Exception {
        executeOrders(service, miningMachine1, new Order[]{
                new Order("[en," + field1 + "]"),
                new Order("[ea,1]"),
                new Order("[no,4]")
        });

        executeOrders(service, miningMachine2, new Order[]{
                new Order("[en," + field1 + "]"),
                new Order("[ea,2]"),
                new Order("[no,5]")
        });

        assertPosition(miningMachine1, field1, 1, 2);
        assertPosition(miningMachine2, field1, 2, 5);
    }

    @Test
    @Transactional
    public void moveOutOfBoundariesTest() throws Exception {
        executeOrders(service, miningMachine1, new Order[]{
                new Order("[en," + field3 + "]"),
                new Order("[no,5]"),
                new Order("[ea,5]"),
                new Order("[so,5]"),
                new Order("[we,5]"),
                new Order("[no,1]")
        });

        assertPosition(miningMachine1, field3, 0, 1);
    }

    @Test
    @Transactional
    public void traverseOnOneFieldTest() throws Exception {
        service.executeCommand(miningMachine1, new Order("[en," + field1 + "]"));
        assertPosition(miningMachine1, field1, 0, 0);

        service.executeCommand(miningMachine1, new Order("[ea,2]"));
        assertPosition(miningMachine1, field1, 2, 0);

        service.executeCommand(miningMachine1, new Order("[no,4]"));
        assertPosition(miningMachine1, field1, 2, 4);

        service.executeCommand(miningMachine1, new Order("[we,5]"));
        assertPosition(miningMachine1, field1, 0, 4);
    }

    @Test
    @Transactional
    public void traverseAllFieldsTest() throws Exception {
        executeOrders(service, miningMachine1, new Order[]{
                new Order("[en," + field1 + "]"),
                new Order("[no,1]"),
                new Order("[ea,1]"),
                new Order("[tr," + field2 + "]"),
                new Order("[so,2]"),
                new Order("[ea,1]"),
                new Order("[tr," + field3 + "]"),
                new Order("[we,5]"),
                new Order("[ea,2]"),
                new Order("[tr," + field2 + "]"),
                new Order("[no,4]"),
                new Order("[ea,3]")
        });

        assertPosition(miningMachine1, field2, 4, 1);
    }
}
