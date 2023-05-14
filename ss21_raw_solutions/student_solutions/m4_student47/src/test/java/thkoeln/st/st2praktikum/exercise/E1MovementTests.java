package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.WebApplicationContext;
import thkoeln.st.st2praktikum.exercise.miningmachine.application.MiningMachineService;
import thkoeln.st.st2praktikum.exercise.field.application.FieldService;
import thkoeln.st.st2praktikum.exercise.transportsystem.application.TransportSystemService;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class E1MovementTests extends MovementTests {

    private MiningMachineService miningMachineService;
    private FieldService fieldService;
    private TransportSystemService transportSystemService;


    @Autowired
    public E1MovementTests(WebApplicationContext appContext,
                           MiningMachineService miningMachineService,
                           FieldService fieldService,
                           TransportSystemService transportSystemService) {
        super(appContext);

        this.miningMachineService = miningMachineService;
        this.fieldService = fieldService;
        this.transportSystemService = transportSystemService;
    }

    @BeforeEach
    public void init() {
        createWorld(miningMachineService, fieldService, transportSystemService);
    }

    @Test
    @Transactional
    public void miningMachinesSpawnOnSamePositionTest() {
        assertTrue(miningMachineService.executeCommand(miningMachine1, Command.fromString("[en," + field1 + "]")));
        assertFalse(miningMachineService.executeCommand(miningMachine2, Command.fromString("[en," + field1 + "]")));
    }

    @Test
    @Transactional
    public void moveToAnotherFieldAndBackTest() throws Exception {
        executeCommands(miningMachineService, miningMachine1, new Command[]{
                Command.fromString("[en," + field2 + "]"),
                Command.fromString("[ea,1]"),
                Command.fromString("[tr," + field3 + "]")
        });

        assertPosition(miningMachine1, field3, 2, 2);

        executeCommands(miningMachineService, miningMachine1, new Command[]{
                Command.fromString("[tr," + field2 + "]")
        });

        assertPosition(miningMachine1, field2, 1, 0);
    }

    @Test
    @Transactional
    public void moveToAnotherFieldOnWrongPositionTest() throws Exception {
        miningMachineService.executeCommand(miningMachine1, Command.fromString("[en," + field1 + "]"));
        assertFalse(miningMachineService.executeCommand(miningMachine1, Command.fromString("[tr," + field2 + "]")));

        assertPosition(miningMachine1, field1, 0, 0);
    }

    @Test
    @Transactional
    public void bumpIntoBarrierTest() throws Exception {
        executeCommands(miningMachineService, miningMachine1, new Command[]{
                Command.fromString("[en," + field2 + "]"),
                Command.fromString("[ea,2]"),
                Command.fromString("[no,3]"),
                Command.fromString("[we,1]"),
        });

        assertPosition(miningMachine1, field2, 1, 1);
    }

    @Test
    @Transactional
    public void moveTwoMiningMachinesAtOnceTest() throws Exception {
        executeCommands(miningMachineService, miningMachine1, new Command[]{
                Command.fromString("[en," + field1 + "]"),
                Command.fromString("[ea,1]"),
                Command.fromString("[no,4]")
        });

        executeCommands(miningMachineService, miningMachine2, new Command[]{
                Command.fromString("[en," + field1 + "]"),
                Command.fromString("[ea,2]"),
                Command.fromString("[no,5]")
        });

        assertPosition(miningMachine1, field1, 1, 2);
        assertPosition(miningMachine2, field1, 2, 5);
    }

    @Test
    @Transactional
    public void moveOutOfBoundariesTest() throws Exception {
        executeCommands(miningMachineService, miningMachine1, new Command[]{
                Command.fromString("[en," + field3 + "]"),
                Command.fromString("[no,5]"),
                Command.fromString("[ea,5]"),
                Command.fromString("[so,5]"),
                Command.fromString("[we,5]"),
                Command.fromString("[no,1]")
        });

        assertPosition(miningMachine1, field3, 0, 1);
    }

    @Test
    @Transactional
    public void traverseOnOneFieldTest() throws Exception {
        miningMachineService.executeCommand(miningMachine1, Command.fromString("[en," + field1 + "]"));
        assertPosition(miningMachine1, field1, 0, 0);

        miningMachineService.executeCommand(miningMachine1, Command.fromString("[ea,2]"));
        assertPosition(miningMachine1, field1, 2, 0);

        miningMachineService.executeCommand(miningMachine1, Command.fromString("[no,4]"));
        assertPosition(miningMachine1, field1, 2, 4);

        miningMachineService.executeCommand(miningMachine1, Command.fromString("[we,5]"));
        assertPosition(miningMachine1, field1, 0, 4);
    }

    @Test
    @Transactional
    public void traverseAllFieldsTest() throws Exception {
        executeCommands(miningMachineService, miningMachine1, new Command[]{
                Command.fromString("[en," + field1 + "]"),
                Command.fromString("[no,1]"),
                Command.fromString("[ea,1]"),
                Command.fromString("[tr," + field2 + "]"),
                Command.fromString("[so,2]"),
                Command.fromString("[ea,1]"),
                Command.fromString("[tr," + field3 + "]"),
                Command.fromString("[we,5]"),
                Command.fromString("[ea,2]"),
                Command.fromString("[tr," + field2 + "]"),
                Command.fromString("[no,4]"),
                Command.fromString("[ea,3]")
        });

        assertPosition(miningMachine1, field2, 4, 1);
    }
}
