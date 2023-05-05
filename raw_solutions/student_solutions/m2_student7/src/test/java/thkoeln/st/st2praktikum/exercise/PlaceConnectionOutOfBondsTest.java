package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlaceConnectionOutOfBondsTest extends MovementTests {

    TidyUpRobotService service;


    @BeforeEach
    public void createNewWorld(){
        service = new TidyUpRobotService();
        room1 = service.addRoom(2,2);
        room2 = service.addRoom(2,2);
    }

    @Test
    public void placeWallsInBoundsTest() {
        assertDoesNotThrow(() -> service.addConnection(room1, new Vector2D("(0,0)"), room2, new Vector2D("(0,0)")));
        assertDoesNotThrow(() -> service.addConnection(room1, new Vector2D("(0,1)"), room2, new Vector2D("(0,1)")));
        assertDoesNotThrow(() -> service.addConnection(room1, new Vector2D("(1,0)"), room2, new Vector2D("(1,0)")));
        assertDoesNotThrow(() -> service.addConnection(room1, new Vector2D("(1,1)"), room2, new Vector2D("(1,1)")));
    }

    @Test
    public void placeWallsOutOfBoundsTest() {
        assertThrows(RuntimeException.class, () -> service.addConnection(room1, new Vector2D("(2,1)"), room2, new Vector2D("(0,0)")));
        assertThrows(RuntimeException.class, () -> service.addConnection(room1, new Vector2D("(0,0)"), room2, new Vector2D("(1,2)")));
    }


}
