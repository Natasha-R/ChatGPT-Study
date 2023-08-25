package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class RobotCommander {

    private Command command;
    private String commandInformation;
    private ArrayList<Roomable> rooms;
    private final int defaultXPos;
    private final int defaultYPos;

    public RobotCommander(ArrayList<Roomable> rooms, int defaultXPos, int defaultYPos) {
        this.rooms = rooms;
        this.defaultXPos = defaultXPos;
        this.defaultYPos = defaultYPos;
    }

    public Boolean controlRobot(AbstractRobot robot, String command) {
        commandToData(command);
        return moveRobot(robot);
    }

    private Boolean moveRobot(AbstractRobot robot) {
        switch (this.command) {
            case NO: return processMovement(robot, Orientation.NO, Integer.parseInt(commandInformation));
            case EA: return processMovement(robot, Orientation.EA, Integer.parseInt(commandInformation));
            case SO: return processMovement(robot, Orientation.SO, Integer.parseInt(commandInformation));
            case WE: return processMovement(robot, Orientation.WE, Integer.parseInt(commandInformation));
            case EN: return processInitialization(robot, commandInformation);
            case TR: return processTransport(robot, commandInformation);
        }
        return false;
    }

    private Boolean processMovement(AbstractRobot robot, Orientation orientation, Integer stepLength) {
        XYMovable movement = createMovement(robot, orientation, stepLength);
        robot.simpleMovement(movement);
        return true;
    }

    private Boolean processTransport(AbstractRobot robot, String roomUUID) {
        try {
            robot.transportRobot(findRoom(UUID.fromString(roomUUID)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Boolean processInitialization(AbstractRobot robot, String roomUUID) {
        try {
            robot.initializeIntoRoom(createPosition(findRoom(UUID.fromString(roomUUID))));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Movement createMovement(AbstractRobot robot, Orientation orientation, Integer stepLength) {
        return new Movement(robot.position, orientation, stepLength);
    }

    private XYPositionable createPosition(Roomable room) {
        return new Position(defaultXPos, defaultYPos, room);
    }

    private void commandToData(String command) {
        String[] commandSplit = removeBracesFromString(command).split(",");
        this.command = stringToCommand(commandSplit[0]);
        this.commandInformation = commandSplit[1];
    }

    private String removeBracesFromString(String input) {
        return input.replaceAll("\\[", "").replaceAll("]", "");
    }

    private Command stringToCommand(String command) {
        switch (command) {
            case "no": return Command.NO;
            case "ea": return Command.EA;
            case "so": return Command.SO;
            case "we": return Command.WE;
            case "en": return Command.EN;
            case "tr": return Command.TR;
            default: return null;
        }
    }

    private Roomable findRoom(UUID roomID) throws NoDataFoundException{
        for (Roomable room : rooms) {
            if (room.getId().equals(roomID))
                return room;
        }
        throw new NoDataFoundException("No room found for room id");
    }
}
