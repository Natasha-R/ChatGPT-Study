package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Controller {
    Environment environment;

    public Controller (Environment environment) {
        this.environment = environment;
    }


    public Boolean processCommand (UUID robotId, String commandString) {
        String[] command = commandString.replaceAll("\\[", "").replaceAll("\\]", "").split(",");
        String commandType = command[0];
        String commandValue = command[1];
        Robot robot = environment.getRobot(robotId);

        switch (commandType) {
            case "en": 
                return robot.spawn(this.environment.getRoom(UUID.fromString(commandValue)), this.environment.robots);
            case "tr":
                return robot.transport(this.environment.getRoom(UUID.fromString(commandValue)));
            case "no":
            case "we":
            case "so":
            case "ea":
                return robot.move(commandType, Integer.parseInt(commandValue));
            default: 
                throw new UnsupportedOperationException();
        }
    }
}
