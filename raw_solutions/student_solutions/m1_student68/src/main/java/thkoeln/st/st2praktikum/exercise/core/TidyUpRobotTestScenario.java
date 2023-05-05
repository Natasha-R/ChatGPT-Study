package thkoeln.st.st2praktikum.exercise.core;

import thkoeln.st.st2praktikum.exercise.TidyUpRobotService;

import java.util.UUID;

public class TidyUpRobotTestScenario {

    public static void main(String[] args) {
        TidyUpRobotService service = new TidyUpRobotService();

        UUID room1=service.addRoom(6,6);
        UUID room2=service.addRoom(5,5);
        UUID room3=service.addRoom(3,3);

        UUID tidyUpRobot1=service.addTidyUpRobot("marvin");
        UUID tidyUpRobot2=service.addTidyUpRobot("darwin");

        service.addBarrier(room1,"(0,3)-(2,3)");
        service.addBarrier(room1,"(3,0)-(3,3)");
        service.addBarrier(room2,"(0,2)-(4,2)");

        UUID connection1=service.addConnection(room1,"(1,1)", room2,"(0,1)");
        UUID connection2=service.addConnection(room2,"(1,0)", room3,"(2,2)");
        UUID connection3=service.addConnection(room3,"(2,2)", room2,"(1,0)");

        //Robot Blocks Robot Test
        service.executeCommand(tidyUpRobot1,"[en," + room1 + "]");
        service.executeCommand(tidyUpRobot1,"[ea,3]");
        service.executeCommand(tidyUpRobot1,"[no,2]");

        service.executeCommand(tidyUpRobot2,"[en," + room1 + "]");
        service.executeCommand(tidyUpRobot2,"[no,5]");
        service.executeCommand(tidyUpRobot2,"[ea,5]");

        System.out.println("Position marvin" + service.map.getRobotById(tidyUpRobot1).getPosition());
        System.out.println("Position darwin" + service.map.getRobotById(tidyUpRobot2).getPosition());
    }
}