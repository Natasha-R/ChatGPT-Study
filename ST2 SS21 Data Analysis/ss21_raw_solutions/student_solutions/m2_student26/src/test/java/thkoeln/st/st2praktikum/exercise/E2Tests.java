package thkoeln.st.st2praktikum.exercise;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class E2Tests {


    private CleaningDeviceService service;
    private UUID space1;
    private UUID space2;
    private UUID space3;
    private UUID cleaningDevice1;
    private UUID cleaningDevice2;


    @BeforeEach
    public void init(){

        service = new CleaningDeviceService();

        space1 = service.addSpace(8,8);
        space2 = service.addSpace(9,9);
        space3 = service.addSpace(5,5);

        cleaningDevice1 = service.addCleaningDevice("hans");
        cleaningDevice2 = service.addCleaningDevice("peter");


    }

    @Test
    public void PlaceConnectionOutOfBoundsTest(){
        assertThrows(RuntimeException.class, () -> service.addConnection(space1, new Coordinate(9,9), space2, new Coordinate(8,8)));
        assertThrows(RuntimeException.class, () -> service.addConnection(space2, new Coordinate(4,3), space3, new Coordinate(7,5)));

    }

    @Test
    public void PlaceWallOutOfBoundsTest(){
        assertThrows(RuntimeException.class, () -> service.addWall(space1, new Wall(new Coordinate(7,7),new Coordinate(7,9))));
        assertThrows(RuntimeException.class, () -> service.addWall(space2, new Wall(new Coordinate(3,3),new Coordinate(10,3))));

    }

    @Test
    public void TraverseOccupiedConnectionTest(){

        service.addConnection(space1, new Coordinate(0,3), space2, new Coordinate(4,4));
        service.executeCommand(cleaningDevice2, new Command(CommandType.ENTER, space1));
        service.executeCommand(cleaningDevice2, new Command(CommandType.NORTH,3));
        service.executeCommand(cleaningDevice2, new Command(CommandType.TRANSPORT, space2));

        service.executeCommand(cleaningDevice1, new Command(CommandType.ENTER, space1));
        service.executeCommand(cleaningDevice1, new Command(CommandType.NORTH,3));
        assertFalse(service.executeCommand(cleaningDevice1, new Command(CommandType.TRANSPORT, space2)));
    }
}
