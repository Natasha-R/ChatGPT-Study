package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.exceptions.InvalidConnectionException;
import thkoeln.st.st2praktikum.exercise.exceptions.InvalidRoomException;
import thkoeln.st.st2praktikum.exercise.exceptions.RobotException;
import thkoeln.st.st2praktikum.exercise.interfaces.OperatorInterface;
import thkoeln.st.st2praktikum.exercise.interfaces.RobotInterface;
import thkoeln.st.st2praktikum.exercise.repositories.RoomRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TidyUpRobotRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@NoArgsConstructor
public class TidyUpRobotController implements RobotInterface, OperatorInterface {

    @Getter
    private TidyUpRobot tidyUpRobot;
    @Getter
    private final TidyUpRobotRepository tidyUpRobotRepository = new TidyUpRobotRepository();
    @Getter
    private final RoomRepository roomRepository = new RoomRepository();


    public void execute(UUID tidyUpRobotId, Command command){
        tidyUpRobot = tidyUpRobotRepository.getTidyUpRobots().get(tidyUpRobotId);
        if (command.getCommandType() == CommandType.ENTER)
            initialRobot(command.getGridId());
        else if (command.getCommandType() == CommandType.TRANSPORT)
            changeRoom(command.getGridId());
        else
            move(command);
    }


    public void move(Command command){
        int steps = command.getNumberOfSteps();
        Room room = roomRepository.getRooms().get(tidyUpRobot.getRoomID());

        for (int i = 0; i < steps; i++) {
            moveOneStep(command.getCommandType(), room.getRoomObstacles());
        }
    }

    private void moveOneStep(CommandType direction, ArrayList<Obstacle> obstacles){
        for (Obstacle obstacle : obstacles){
            if (obstacle.isObstacle(tidyUpRobot.getRobotPosition(), direction))
                return;
        }

        Vector2D newRobotPosition = new Vector2D(tidyUpRobot.getRobotPosition().getX(), tidyUpRobot.getRobotPosition().getY());

        if (direction == CommandType.NORTH)
            newRobotPosition.setY(newRobotPosition.getY()+1);
        if (direction == CommandType.EAST)
            newRobotPosition.setX(newRobotPosition.getX()+1);
        if (direction == CommandType.SOUTH)
            newRobotPosition.setY(newRobotPosition.getY()-1);
        if (direction == CommandType.WEST)
            newRobotPosition.setX(newRobotPosition.getX()-1);

        if (roomIsBlockedByRobot(tidyUpRobot.getRoomID(), newRobotPosition, tidyUpRobotRepository.getTidyUpRobots()))
            throw new InvalidRoomException("Room is blocked by another Robot");

        tidyUpRobot.setRobotPosition(newRobotPosition);
    }


    public void initialRobot(UUID destinationRoomId) {
        if (tidyUpRobot.getRoomID() != null)
            throw new RobotException("Error: Robot is already in a room\n");

        if (roomIsBlockedByRobot(destinationRoomId, tidyUpRobot.getRobotPosition(), tidyUpRobotRepository.getTidyUpRobots()))
            throw new InvalidRoomException("Initial Room is blocked by another Robot");

        tidyUpRobot.setRoomID(destinationRoomId);
    }

    public void changeRoom(UUID destinationRoomId){
        Room room = roomRepository.getRooms().get(tidyUpRobot.getRoomID());
        Connection roomConnection = room.getConnections().get(destinationRoomId);

        if (roomConnection == null)
            throw new InvalidConnectionException("Connection does not exist");

        if (!roomConnection.getSourceRoomCoordinates().equals(tidyUpRobot.getRobotPosition()))
            throw new InvalidConnectionException("Robot is not on a valid Connection!");

        if (roomIsBlockedByRobot(destinationRoomId, roomConnection.getDestinationRoomCoordinates(), tidyUpRobotRepository.getTidyUpRobots()))
            throw new InvalidRoomException("Room is blocked by another Robot");

        tidyUpRobot.setRoomID(destinationRoomId);
        tidyUpRobot.setRobotPosition(roomConnection.getDestinationRoomCoordinates());
    }

    @Override
    public boolean roomIsBlockedByRobot(UUID roomID, Vector2D vector2D, HashMap<UUID, TidyUpRobot> tidyUpRobots){
        for (TidyUpRobot tidyUpRobot : tidyUpRobots.values()) {
            if (vector2D.equals(tidyUpRobot.getRobotPosition()) && roomID.equals(tidyUpRobot.getRoomID()))
                return true;
        }
        return false;
    }

}
