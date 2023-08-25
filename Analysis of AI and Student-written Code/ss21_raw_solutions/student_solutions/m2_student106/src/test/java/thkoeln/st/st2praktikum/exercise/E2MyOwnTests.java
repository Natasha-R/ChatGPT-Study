package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class E2MyOwnTests extends MovementTests {

    protected MiningMachineService service;

    @BeforeEach
    public void setUpService(){
        service = new MiningMachineService();
        createWorld(service);
    }

    @Test
    public void positionAConnectionOutOfBounds() {
        assertThrows(RuntimeException.class, () -> service.addConnection(field3, new Point("(5,5)"), field2, new Point("(0,0)")));
        assertThrows(RuntimeException.class, () -> service.addConnection(field3, new Point("(0,0)"), field2, new Point("(8,8)")));
        assertThrows(RuntimeException.class, () -> service.addConnection(field3, new Point("(-2,0)"), field2, new Point("(0,0)")));
        assertThrows(RuntimeException.class, () -> service.addConnection(field3, new Point("(0,-2)"), field2, new Point("(0,0)")));
        assertThrows(RuntimeException.class, () -> service.addConnection(field3, new Point("(0,0)"), field2, new Point("(-2,0)")));
        assertThrows(RuntimeException.class, () -> service.addConnection(field3, new Point("(0,0)"), field2, new Point("(0,-2)")));
    }

    @Test
    public void placingAObstacleOutOfBounds(){
        assertThrows(RuntimeException.class, () -> service.addObstacle(field3, new Obstacle("(0,9)-(2,9)")));
        assertThrows(RuntimeException.class, () -> service.addObstacle(field3, new Obstacle("(0,10)-(2,10)")));
        assertThrows(RuntimeException.class, () -> service.addObstacle(field3, new Obstacle("(9,0)-(9,2)")));
        assertThrows(RuntimeException.class, () -> service.addObstacle(field3, new Obstacle("(10,0)-(10,2)")));
        assertThrows(RuntimeException.class, () -> service.addObstacle(field3, new Obstacle("(-2,0)-(0,0)")));
        assertThrows(RuntimeException.class, () -> service.addObstacle(field3, new Obstacle("(0,-2)-(0,0)")));
        assertThrows(RuntimeException.class, () -> service.addObstacle(field3, new Obstacle("(0,0)-(-2,0)")));
        assertThrows(RuntimeException.class, () -> service.addObstacle(field3, new Obstacle("(0,0)-(0,-2)")));
    }

    @Test
    public void hitAnotherMiningMachineWhilePerformingATask(){
        executeTasks(service, miningMachine1, new Task[]{
                new Task("[en," + field3 + "]"),
                new Task("[ea,2]")
        });
        executeTasks(service, miningMachine2, new Task[]{
                new Task("[en," + field3 + "]"),
                new Task("[ea,2]")
        });
        assertPosition(service, miningMachine1, field3, 2, 0);
        assertPosition(service, miningMachine2, field3, 1, 0);
    }
}
