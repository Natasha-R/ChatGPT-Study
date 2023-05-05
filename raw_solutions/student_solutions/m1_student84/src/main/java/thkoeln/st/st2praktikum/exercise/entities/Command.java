package thkoeln.st.st2praktikum.exercise.entities;

import javassist.NotFoundException;
import thkoeln.st.st2praktikum.exercise.interfaces.Executable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



public class Command {
    public static Executable fromString(String commandString, UUID robotId, List<TidyUpRobot> robots, List<Room> rooms, List<Connection> connections) {
        String[] command = commandString.replace("[", "").replace("]", "").split(",");

        Optional<TidyUpRobot> robot = robots.stream().filter(r -> r.getId().equals(robotId)).findFirst();
        if(robot.isEmpty()) {
            System.out.println("No robot found");
            throw new IllegalArgumentException();
        }
        UUID roomId = (command[0].equals("tr") || command[0].equals("en")) ? UUID.fromString(command[1]) : robot.get().roomId;
        Optional<Room> room = rooms.stream().filter(r -> r.getId().equals(roomId)).findFirst();
        if(room.isEmpty()) {
            System.out.println("No room found for id " + roomId.toString());
            throw new IllegalArgumentException();
        }

        switch (command[0]) {
            case "tr":
                System.out.println("Transport");
                return new TransportCommand(robot.get(), room.get(), connections);
            case "en":
                System.out.println("Initialize");
                return new InitCommand(robot.get(), room.get(), robots);
            default:
                System.out.println("Move");
                return new MoveCommand(robot.get(), room.get(), Direction.valueOf(command[0]), Integer.parseInt(command[1]));
        }
    }
}
