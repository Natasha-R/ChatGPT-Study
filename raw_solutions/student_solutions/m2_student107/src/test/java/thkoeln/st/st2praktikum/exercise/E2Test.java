package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.util.UUID;

public class E2Test {

    private CleaningDeviceService service;

    private UUID space1;
    private UUID space2;
    private UUID space3;
    private UUID space4;

    private UUID device1;
    private UUID device2;



    @BeforeEach
    private void setup() {
        service = new CleaningDeviceService();
        space1 = service.addSpace(5,5);
        space2 = service.addSpace(3,3);
        space3 = service.addSpace(7,3);
        space4 = service.addSpace(5,5);

        service.addObstacle(space1, new Obstacle(new Coordinate(3,3),new Coordinate(4,3)));
        service.addObstacle(space1, new Obstacle(new Coordinate(3,3),new Coordinate(3,4)));

        device1 = service.addCleaningDevice("C-3PO");
        device2 = service.addCleaningDevice("R2D2");


    }

    @Test
    public void collideWithOtherDeviceTest(){
        service.executeCommand(device1 ,new Order("[en," + space3 + "]"));

        service.executeCommand(device1, new Order("[ea,1]"));
        service.executeCommand(device1, new Order("[no,3]"));
        System.out.println(service.getCoordinate(device1));


        service.executeCommand(device2 ,new Order("[en," + space3 + "]"));
        service.executeCommand(device2, new Order("[ea,1]"));
        service.executeCommand(device2, new Order("[no,3]"));
        System.out.println(service.getCoordinate(device2));
        assertEquals(service.getCoordinate(device2), new Coordinate(1, 2));
    }



    @Test
    public void moveOutOfBoundsTest(){

        service.executeCommand(device1 ,new Order("[en," + space4 + "]"));

        service.executeCommand(device1 ,new Order("[no,3]"));
        assertEquals(service.getCoordinate(device1), new Coordinate(0, 3));
        service.executeCommand(device1 ,new Order("[we,3]"));
        assertEquals(service.getCoordinate(device1), new Coordinate(0, 3));
        service.executeCommand(device1 ,new Order("[no,3]"));
        assertEquals(service.getCoordinate(device1), new Coordinate(0, 4));
        service.executeCommand(device1 ,new Order("[ea,7]"));
        assertEquals(service.getCoordinate(device1), new Coordinate(4, 4));

    }


    @Test
    public void connectionOutOfBoundsTest(){

        assertThrows(RuntimeException.class , () ->  service.addConnection(space1 ,new Coordinate(0,0)  , space2  , new Coordinate(4,4)));
        assertThrows(RuntimeException.class , () ->  service.addConnection(space1 ,new Coordinate(3,12) , space2 , new Coordinate(0,0)));
        assertThrows(RuntimeException.class , () ->  service.addConnection(space1 ,new Coordinate(34,2) , space2 , new Coordinate(4,4)));


    };

    @Test
    public void transporterBlockedTest(){
        service.executeCommand(device1 ,new Order("[en," + space1 + "]"));
        service.executeCommand(device2 ,new Order("[en," + space2 + "]"));

        service.addConnection(space1 ,  new Coordinate( 0,2), space2 , new Coordinate(0,0));
        service.executeCommand(device1 , new Order("[no,2]"));
        assertFalse(service.executeCommand(device1 , new Order("[tr," + space2 + "]")));
        assertEquals(service.getCoordinate(device1), new Coordinate(0, 2));
        assertEquals(service.getCleaningDeviceSpaceId(device1), space1);
    }


    @Test
    public void transporterNotInRangeTest(){
        service.executeCommand(device1 ,new Order("[en," + space1 + "]"));
        service.addConnection(space1 ,  new Coordinate( 0,2), space2 , new Coordinate(0,1));
        assertFalse(service.executeCommand(device1 , new Order("[tr," + space2 + "]")));
        assertEquals(service.getCleaningDeviceSpaceId(device1) , space1);
        assertEquals(service.getCoordinate(device1) , new Coordinate(0,0));
    }


    @Test
    public void placeObstacleOutOfBoundsNotAllowedTest(){
        assertThrows(RuntimeException.class , () ->  service.addObstacle(space1 ,new Obstacle(new Coordinate(34,2) , new Coordinate(34,8))));
        assertThrows(RuntimeException.class , () ->  service.addObstacle(space1 ,new Obstacle(new Coordinate(0,2) , new Coordinate(8,2))));
        assertThrows(RuntimeException.class , () ->  service.addObstacle(space1 ,new Obstacle(new Coordinate(2,2) , new Coordinate(2,7))));
    }












}
