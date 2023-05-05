package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;
import thkoeln.st.st2praktikum.exercise.exceptions.InvalidConnectionException;
import thkoeln.st.st2praktikum.exercise.exceptions.InvalidRoomException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class E2ConnectionTests extends MovementTests {

    private TidyUpRobotService service = new TidyUpRobotService();

    @BeforeEach
    public void setUp(){
        createWorld(service);
    }

    @Test
    public void placingConnectingOutOfBoundsTest(){
        assertThrows( RuntimeException.class, () -> service.addConnection(room1, new Vector2D(3,7), room2, new Vector2D(6,4)) );
        assertThrows( RuntimeException.class, () -> service.addConnection(room2, new Vector2D(2,1), room3, new Vector2D(1,4)) );
        assertThrows( RuntimeException.class, () -> service.addConnection(room3, new Vector2D(1,1), room1, new Vector2D(7,3)) );
    }

    @Test
    public void traversingConnectinWhenOccupiedTest(){
        executeCommands(service, tidyUpRobot2, new Command[]{
                new Command("[en," + room2 + "]"),
                new Command("[no,1]"),
        });

        executeCommands(service, tidyUpRobot1, new Command[]{
                new Command("[en," + room1 + "]"),
                new Command("[no,1]"),
                new Command("[ea,1]"),
                new Command("[tr," + room2 + "]")
        });

        assertThrows( InvalidRoomException.class, () -> service.tidyUpRobotController.changeRoom(room2) );
    }

    @Test
    public void transportWithoutConnectionTest(){
        executeCommands(service, tidyUpRobot1, new Command[]{
                new Command("[en," + room1 + "]"),
                new Command("[no,3]"),
                new Command("[ea,2]"),
                new Command("[tr," + room2 + "]")
        });

        assertThrows( InvalidConnectionException.class, () -> service.tidyUpRobotController.changeRoom(room2) );
    }

}