package thkoeln.st.st2praktikum.exercise;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestExecutionListeners;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class E2CustomTests extends MovementTests {

     private TidyUpRobotService robotService;

    @BeforeEach
     public void createWorld(){
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);
        robotService=service;
    }

    //Using the TR-command must not be possible if there is no outgoing connection on the current position
    @Test
    public void TrCommandOnlyWorksOnTrFieldsTest() {
        executeOrders(robotService, tidyUpRobot1, new Order[]{
                new Order("[en," + room2 + "]"),
                new Order("[ea,2]"),
                new Order("[tr," + room3 + "]"),
                new Order("[no,2]"),
        });
        assertPosition(robotService, tidyUpRobot1, room2, 2,1 );
    }

    //Traversing a connection must not be possible in case the destination is occupied
    @Test
    public void CantTransferRobotWhileDestinationIsBlocked(){

        executeOrders(robotService, tidyUpRobot1, new Order[]{
                new Order("[en," + room2 + "]"),
                new Order("[ea,1]"),
                new Order("[tr," + room3 + "]"),
        });

        executeOrders(robotService, tidyUpRobot2, new Order[]{
                new Order("[en," + room2 + "]"),
                new Order("[ea,1]"),
        });
        assertThrows(RuntimeException.class, () -> robotService.executeCommand(tidyUpRobot2, new Order("[tr," + room3 + "]")));
    }

    //Hitting another tidyUpRobot during a Move-Command has to interrupt the current movement
    @Test
    public void cancelMovementIfDestinationIsBlocked(){


        executeOrders(robotService, tidyUpRobot1, new Order[]{
                new Order("[en," + room2 + "]"),
                new Order("[ea,1]"),
                new Order("[no,1]"),
        });
        assertPosition(robotService, tidyUpRobot1, room2, 1,1 );

        executeOrders(robotService, tidyUpRobot2, new Order[]{
                new Order("[en," + room2 + "]"),
                new Order("[ea,1]"),
                new Order("[no,1]"),
        });
        assertPosition(robotService, tidyUpRobot2, room2, 1,0 );
    }


    
}
