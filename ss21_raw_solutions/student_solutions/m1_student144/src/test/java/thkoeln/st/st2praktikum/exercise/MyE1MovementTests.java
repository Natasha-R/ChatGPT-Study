package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MyE1MovementTests extends MovementTests {



    @Test
    public void moveOutOfBoundariesTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        executeCommands(service, tidyUpRobot1, new String[]{
                "[en," + room1 + "]",
                "[no,1]",
               "[ea,1]",
               "[tr," + room2 + "]",
               "[no,2]",
               // "[ea,1]",
               // "[tr," + room3 + "]",
               // "[we,5]",
               // "[ea,2]",
                //"[tr," + room2 + "]",
                //"[no,4]",
                //"[ea,3]"



                //"[en," + room1 + "]",
               // "[we,5]",
                //"[no,5]",
                //"[so,1]",
                //"[tr," + room2 + "]"
        });

        assertPosition(service, tidyUpRobot1, room2, 0, 3);
    }


}
