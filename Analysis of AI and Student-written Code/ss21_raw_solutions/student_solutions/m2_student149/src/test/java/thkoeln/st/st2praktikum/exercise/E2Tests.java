package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;
import thkoeln.st.st2praktikum.exercise.exception.PositionOutOfSpaceException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class E2Tests extends MovementTests {

    private CleaningDeviceService instance;

    @BeforeEach
    public void init() {
        instance = new CleaningDeviceService();
        createWorld(instance);
    }

    @Test
    public void blockWayWithSecondCleaningDeviceTest() {
        executeCommands(instance, cleaningDevice1, new Command[]{
                new Command("[en," + space1 + "]"),
                new Command("[ea,1]")
        });

        executeCommands(instance, cleaningDevice2, new Command[]{
                new Command("[en," + space1 + "]"),
                new Command("[ea,2]")
        });

        assertPosition(instance, cleaningDevice1, space1, 1, 0);
        assertPosition(instance, cleaningDevice2, space1, 0, 0);
    }

    @Test
    public void tryToMoveOutOfBoundariesTest() {
        executeCommands(instance, cleaningDevice1, new Command[]{
                new Command("[en," + space1 + "]"),
                new Command("[we,10]"),
                new Command("[so,10]"),
                new Command("[ea,2]"),
                new Command("[no,10]"),
                new Command("[ea,10]")
        });

        assertPosition(instance, cleaningDevice1, space1, 5, 5);
    }

    @Test
    public void placeConnectionOutOfBounds() {
        assertThrows(PositionOutOfSpaceException.class,
                () -> instance.addConnection(space1, new Vector2D("(0,0)"), space2, new Vector2D("(10,10)")));
    }
}
