package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import java.util.UUID;

import static org.junit.Assert.assertEquals;


public class E2WritingUnitTests extends MovementTests {


    private TidyUpRobotService service;

    @BeforeEach
    public void setUp(){

        service = new TidyUpRobotService();
        createWorld(service);

    }


    @Test
    public void moveOutOfBounds(){


        executeCommands(service, tidyUpRobot1, new Command[]{

                new Command("[en," + room3 + "]"),
                new Command("[so,2]"),
                new Command("[we,2]"),
                new Command("[ea,7]"),
                new Command("[no,7]")

        });

        assertPosition(service , tidyUpRobot1 , room3 , 3 , 3);

    }

    @Test
    public void hitTidyUpRobot(){

        executeCommands(service, tidyUpRobot1, new Command[]{

                new Command("[en," + room1 + "]"),
                new Command("[no,2]"),

        });

        executeCommands(service, tidyUpRobot2, new Command[]{

                new Command("[en," + room1 + "]"),
                new Command("[no,2]"),

        });
        assertPosition(service , tidyUpRobot1,  room1 , 0 , 2);
        assertPosition(service , tidyUpRobot2,  room1 , 0 , 1);
    }


    @Test
    public void transportRobotToOccupiedField (){

        executeCommands(service, tidyUpRobot1, new Command[]{
                new Command("[en," + room1 + "]"),
                new Command("[ea,1]"),
                new Command("[no,1]"),
                new Command("[tr," + room2 + "]")


    });

        executeCommands(service, tidyUpRobot2, new Command[]{
                new Command("[en," + room1 + "]"),
                new Command("[ea,1]"),
                new Command("[no,1]"),
                new Command("[tr," + room2 + "]")

        });


        assertPosition(service , tidyUpRobot1 ,room2 , 0 , 1);
        assertPosition(service , tidyUpRobot2 , room1 , 1 , 1);

    }
}
