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
        assertTrue(service.executeCommand(miningMachine1, new Command("[en," + field1 + "]")));
        assertFalse(service.executeCommand(miningMachine2, new Command("[en," + field1 + "]")));
    }

    @Test
    @Transactional
    public void moveToAnotherFieldAndBackTest() throws Exception {
        executeCommands(service, miningMachine1, new Command[]{
                new Command("[en," + field2 + "]"),
                new Command("[ea,1]"),
                new Command("[tr," + field3 + "]")
        });

        assertPosition(miningMachine1, field3, 2, 2);

        executeCommands(service, miningMachine1, new Command[]{
                new Command("[tr," + field2 + "]")
        });

        assertPosition(miningMachine1, field2, 1, 0);
    }

    @Test
    @Transactional
    public void moveToAnotherFieldOnWrongPositionTest() throws Exception {
        service.executeCommand(miningMachine1, new Command("[en," + field1 + "]"));
        assertFalse(service.executeCommand(miningMachine1, new Command("[tr," + field2 + "]")));

        assertPosition(miningMachine1, field1, 0, 0);
    }

    @Test
    @Transactional
    public void bumpIntoWallTest() throws Exception {
        executeCommands(service, miningMachine1, new Command[]{
                new Command("[en," + field2 + "]"),
                new Command("[ea,2]"),
                new Command("[no,3]"),
                new Command("[we,1]"),
        });

        assertPosition(miningMachine1, field2, 1, 1);
    }

    @Test
    @Transactional
    public void moveTwoMiningMachinesAtOnceTest() throws Exception {
        executeCommands(service, miningMachine1, new Command[]{
                new Command("[en," + field1 + "]"),
                new Command("[ea,1]"),
                new Command("[no,4]")
        });

        executeCommands(service, miningMachine2, new Command[]{
                new Command("[en," + field1 + "]"),
                new Command("[ea,2]"),
                new Command("[no,5]")
        });

        assertPosition(miningMachine1, field1, 1, 2);
        assertPosition(miningMachine2, field1, 2, 5);
    }

    @Test
    @Transactional
    public void moveOutOfBoundariesTest() throws Exception {
        executeCommands(service, miningMachine1, new Command[]{
                new Command("[en," + field3 + "]"),
                new Command("[no,5]"),
                new Command("[ea,5]"),
                new Command("[so,5]"),
                new Command("[we,5]"),
                new Command("[no,1]")
        });

        assertPosition(miningMachine1, field3, 0, 1);
    }

    @Test
    @Transactional
    public void traverseOnOneFieldTest() throws Exception {
        service.executeCommand(miningMachine1, new Command("[en," + field1 + "]"));
        assertPosition(miningMachine1, field1, 0, 0);

        service.executeCommand(miningMachine1, new Command("[ea,2]"));
        assertPosition(miningMachine1, field1, 2, 0);

        service.executeCommand(miningMachine1, new Command("[no,4]"));
        assertPosition(miningMachine1, field1, 2, 4);

        service.executeCommand(miningMachine1, new Command("[we,5]"));
        assertPosition(miningMachine1, field1, 0, 4);
    }

    @Test
    @Transactional
    public void traverseAllFieldsTest() throws Exception {
        executeCommands(service, miningMachine1, new Command[]{
                new Command("[en," + field1 + "]"),
                new Command("[no,1]"),
                new Command("[ea,1]"),
                new Command("[tr," + field2 + "]"),
                new Command("[so,2]"),
                new Command("[ea,1]"),
                new Command("[tr," + field3 + "]"),
                new Command("[we,5]"),
                new Command("[ea,2]"),
                new Command("[tr," + field2 + "]"),
                new Command("[no,4]"),
                new Command("[ea,3]")
        });

        assertPosition(miningMachine1, field2, 4, 1);
    }
}
