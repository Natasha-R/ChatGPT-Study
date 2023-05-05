package thkoeln.st.st2praktikum.exercise.core;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.map.Queryable;
import thkoeln.st.st2praktikum.exercise.room.Walkable;
import java.util.UUID;

@Getter
public class RobotCommand {
    Command command;
    Direction direction;
    Integer steps;
    Walkable targetRoom;

    public RobotCommand(String robotCommand, Queryable map) {
        String[] fragmentedRobotCommand = robotCommand.replaceAll("[\\[\\]]","").split(",");

        switch(fragmentedRobotCommand[0]){
            case "no": command = Command.MOVE; direction = Direction.NORTH; break;
            case "so": command = Command.MOVE; direction = Direction.SOUTH; break;
            case "we": command = Command.MOVE; direction = Direction.WEST; break;
            case "ea": command = Command.MOVE; direction = Direction.EAST; break;
            case "tr": command = Command.TRANSPORT; direction = Direction.NONE; break;
            case "en": command = Command.SPAWN; direction = Direction.NONE; break;
            default: throw new IllegalArgumentException("Illegal command: "+ robotCommand);
        }
        if (command == Command.TRANSPORT || command == Command.SPAWN)
            targetRoom = (Walkable) map.getRoomById(UUID.fromString(fragmentedRobotCommand[1]));
        else
            steps = Integer.parseInt(fragmentedRobotCommand[1]);
        }
    }