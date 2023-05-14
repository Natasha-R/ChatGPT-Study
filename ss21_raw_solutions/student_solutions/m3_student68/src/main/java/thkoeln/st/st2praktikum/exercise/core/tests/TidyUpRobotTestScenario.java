package thkoeln.st.st2praktikum.exercise.core.tests;

import java.util.Scanner;

public class TidyUpRobotTestScenario {

    public static void main(String[] args) {

    stringTest();

    }

    private static void stringTest () {
        Scanner in = new Scanner(System.in);
        String string = null;


        while (string != "exit") {
            System.out.print("Geben Sie eine String ein: ");
            string = in.next();
            System.out.println("String: " + string + " ist " + string.matches("\\[[a-z]{2},([1-9][0-9]*|\\w{8}\\-\\w{4}\\-\\w{4}\\-\\w{4}\\-\\w{12})\\]"));
        }
    }


//    private static void roboWalk () {
//        TidyUpRobotService service = new TidyUpRobotService();
//
//        UUID room1=service.addRoom(6,6);
//        UUID room2=service.addRoom(5,5);
//        UUID room3=service.addRoom(3,3);
//
//        UUID tidyUpRobot1=service.addTidyUpRobot("marvin");
//        UUID tidyUpRobot2=service.addTidyUpRobot("darwin");
//
//        service.addBarrier(room1,new Barrier("(0,3)-(2,3)"));
//        service.addBarrier(room1,new Barrier("(3,0)-(3,3)"));
//        service.addBarrier(room2,new Barrier("(0,2)-(4,2)"));
//
//        UUID connection1=service.addConnection(room1,new Coordinate("(1,1)"), room2,new Coordinate("(0,1)"));
//        UUID connection2=service.addConnection(room2,new Coordinate("(1,0)"), room3,new Coordinate("(2,2)"));
//        UUID connection3=service.addConnection(room3,new Coordinate("(2,2)"), room2,new Coordinate("(1,0)"));
//
//        //Robot Blocks Robot Test
//        service.executeCommand(tidyUpRobot1,new Task("[en," + room1 + "]"));
//        service.executeCommand(tidyUpRobot1,new Task("[ea,3]"));
//        service.executeCommand(tidyUpRobot1,new Task("[no,2]"));
//
//        service.executeCommand(tidyUpRobot2,new Task("[en," + room1 + "]"));
//        service.executeCommand(tidyUpRobot2,new Task("[no,5]"));
//        service.executeCommand(tidyUpRobot2,new Task("[ea,5]"));
//
//        System.out.println("Position marvin" + service.map.getRobotById(tidyUpRobot1).getCoordinate());
//        System.out.println("Position darwin" + service.map.getRobotById(tidyUpRobot2).getCoordinate());
//
//    }
}