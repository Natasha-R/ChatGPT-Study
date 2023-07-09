package thkoeln.st.st2praktikum.exercise;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import thkoeln.st.st2praktikum.exercise.connection.Connection;
import thkoeln.st.st2praktikum.exercise.room.Room;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExerciseTests
{
    protected UUID room1;
    protected UUID room2;

    Room room3;
    Room room4;

    protected UUID tidyUpRobot;
    TidyUpRobotService service;



    @Before
    public void initializeService()
    {
        service = new TidyUpRobotService();
        tidyUpRobot = service.addTidyUpRobot("Kevin");
        room1 = service.addRoom(4,4);
        room2 = service.addRoom(2,4);
    }


    @Test
    public void moveOutOfBoundaryTest()
    {
        service.executeCommand(tidyUpRobot,new Task("[en,"+room1+"]"));
        service.executeCommand(tidyUpRobot,new Task("[no,7]"));
        service.executeCommand(tidyUpRobot,new Task("[so,2]"));
        service.executeCommand(tidyUpRobot,new Task("[ea,2]"));
        assertPosition(service,tidyUpRobot,room1,2, 2);
    }


    @Test
    public void transportNotPossibleTest()
    {
        service.addConnection(room1,new Coordinate("(2,2)"),room2,new Coordinate("(4,0)"));
        service.executeCommand(tidyUpRobot,new Task("[en,"+room1+"]"));
        service.executeCommand(tidyUpRobot,new Task("[tr,"+room2+"]"));
        assertPosition(service,tidyUpRobot,room1,0,0);
    }


    @Test
    public void connectionInBoundaryTest()
    {
        room3 = new Room(4,4);
        room4 = new Room(5,2);
        assertThrows(RuntimeException.class,() -> new Connection(room4,new Coordinate("(0,1)"),room3,new Coordinate("(5,3)")));
    }


    protected void assertPosition(TidyUpRobotService service, UUID tidyUpRobotID, UUID roomID, int posX, int posY)
    {
        assertEquals(roomID,service.getTidyUpRobotRoomId(tidyUpRobotID));
        assertEquals(new Coordinate(posX,posY),service.getCoordinate(tidyUpRobotID));
    }




}
