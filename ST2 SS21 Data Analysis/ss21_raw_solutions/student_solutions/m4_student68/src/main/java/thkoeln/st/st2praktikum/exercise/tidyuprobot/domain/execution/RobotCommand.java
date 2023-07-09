package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.execution;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.room.application.RoomRepository;
import thkoeln.st.st2praktikum.exercise.room.domain.Walkable;
import thkoeln.st.st2praktikum.exercise.transportsystem.application.TransportSystemRepository;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.Connectable;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.TransportSystem;


import java.util.UUID;

@Getter
public class RobotCommand {
    Command command;
    Direction direction;
    Integer steps;
    Walkable targetRoom;
    Coordinate targetCoordinate;

    public RobotCommand(Task task, RoomRepository roomRepository, TransportSystemRepository transportSystemRepository, Walkable robotRoom, Coordinate robotCoordinate) {

        switch(task.getTaskType()){
            case NORTH:     command = Command.MOVE; direction = Direction.NORTH; break;
            case SOUTH:     command = Command.MOVE; direction = Direction.SOUTH; break;
            case WEST:      command = Command.MOVE; direction = Direction.WEST; break;
            case EAST:      command = Command.MOVE; direction = Direction.EAST; break;
            case TRANSPORT: command = Command.TRANSPORT; direction = Direction.NONE; break;
            case ENTER:     command = Command.SPAWN; direction = Direction.NONE; break;
            default: throw new IllegalArgumentException("Illegal Task: "+ task.getTaskType());
        }

        if (command == Command.SPAWN )
            targetRoom = (Walkable) roomRepository.findById(task.getGridId()).get();
        else if (command == Command.TRANSPORT &&  robotRoom != null) {
                Iterable<TransportSystem> transportsystems = transportSystemRepository.findAll();
                for (TransportSystem transportsystem : transportsystems) {
                    for (Connectable connection : transportsystem.getConnections()) {
                        if (connection.getSourceRoomId().equals(robotRoom.getId()) &&
                                connection.getSourcePosition().equals(robotCoordinate) &&
                                connection.getDestinationRoomId().equals(task.getGridId())) {
                            targetRoom = (Walkable) roomRepository.findById(task.getGridId()).get();
                            targetCoordinate = connection.getDestinationPosition();
                        }
                    }
                }
        }
        else
            steps = task.getNumberOfSteps();
        }

    public RobotCommand(String robotCommand, RoomRepository roomRepository) {
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
            targetRoom = (Walkable)  roomRepository.findById(UUID.fromString(fragmentedRobotCommand[1])).get();
        else
            steps = Integer.parseInt(fragmentedRobotCommand[1]);
        }
    }