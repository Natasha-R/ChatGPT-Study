package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class E2OutOfBoundsTest extends MovementTests {

    private TidyUpRobotService service = new TidyUpRobotService();

    @BeforeEach
    public void setUp(){
        createWorld(service);
    }

    @Test
    public void moveOutOfBoundsTest(){
        executeCommands(service, tidyUpRobot1, new Command[]{
                new Command("[en," + room1 + "]"),
                new Command("[no,9]"),
                new Command("[ea,9]"),
                new Command("[so,9]"),
                new Command("[we,9]")
        });

        executeCommands(service, tidyUpRobot2, new Command[]{
                new Command("[en," + room2 + "]"),
                new Command("[no,9]"),
                new Command("[ea,9]"),
                new Command("[so,9]"),
                new Command("[we,9]")
        });

        assertPosition(service, tidyUpRobot1, room1, 0, 0);
        assertPosition(service, tidyUpRobot2, room2, 0, 0);

    }

    @Test
    public void placingObstacleOutOfBoundsTest(){
        assertThrows(RuntimeException.class, () -> service.addObstacle(room1, new Obstacle(new Vector2D(1, 1 ), new Vector2D(1, 7 ))));
        assertThrows(RuntimeException.class, () -> service.addObstacle(room2, new Obstacle(new Vector2D(2, 1 ), new Vector2D(2, 6 ))));
        assertThrows(RuntimeException.class, () -> service.addObstacle(room3, new Obstacle(new Vector2D(2, 1 ), new Vector2D(2, 5 ))));
    }
}