package thkoeln.st.st2praktikum.exercise;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import thkoeln.st.st2praktikum.exercise.TidyUpRobotService;
import thkoeln.st.st2praktikum.exercise.Wall;
import thkoeln.st.st2praktikum.exercise.Command;
import thkoeln.st.st2praktikum.exercise.Point;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class MyUnitTests {
    @Test
    public void tryToMoveOutOfBounds(){
        TidyUpRobotService robotService = new TidyUpRobotService();
        UUID testingRoom = robotService.addRoom(4,4);
        UUID wallE = robotService.addTidyUpRobot("Wall-E");
        Command spawnCommand = new Command("[en,"+testingRoom+"]");

        Command moveOutOfBoundsCommand =  new Command("[ea,9]");
        robotService.executeCommand(wallE,spawnCommand);
        robotService.executeCommand(wallE, moveOutOfBoundsCommand);
        assertEquals(TidyUpRobotMap.getByUUID(wallE).getCurrentPosition(),"(3,0)");
    }
    @Test
    public void transportNotOnConnecntion(){
        TidyUpRobotService robotService = new TidyUpRobotService();
        UUID testingRoom = robotService.addRoom(4,4);
        UUID destinationTestingRoom = robotService.addRoom(9,9);
        UUID connection = robotService.addConnection(testingRoom,new Point(3,3),destinationTestingRoom,new Point(2,3));
        UUID wallE = robotService.addTidyUpRobot("Wall-E");
        Command spawnCommand = new Command("[en,"+testingRoom+"]");
        Command tryToTransport = new Command("[tr,"+destinationTestingRoom+"]");

        robotService.executeCommand(wallE,spawnCommand);
        robotService.executeCommand(wallE,tryToTransport);
        assertNotEquals(TidyUpRobotMap.getByUUID(wallE).getCurrentRoomId(),destinationTestingRoom);


    }
    @Test
    public void placeWallOutOfBounds(){
        TidyUpRobotService robotService = new TidyUpRobotService();
        UUID testingRoom = robotService.addRoom(4,4);
        //robotService.addWall(testingRoom, new Wall("(5,2)-(7,2)"));
        assertThrows(RuntimeException.class, () -> robotService.addWall(testingRoom, new Wall("(5,2)-(7,2)")));
    }
}
