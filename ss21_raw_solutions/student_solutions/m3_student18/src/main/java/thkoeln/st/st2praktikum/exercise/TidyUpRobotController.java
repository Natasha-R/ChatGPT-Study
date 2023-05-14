package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.exceptions.InvalidConnectionException;
import thkoeln.st.st2praktikum.exercise.exceptions.InvalidRoomException;
import thkoeln.st.st2praktikum.exercise.exceptions.RobotException;
import thkoeln.st.st2praktikum.exercise.interfaces.OperatorInterface;
import thkoeln.st.st2praktikum.exercise.interfaces.RobotInterface;
import thkoeln.st.st2praktikum.exercise.repositories.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.repositories.RoomRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TidyUpRobotRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TransportSystemRepository;

import java.util.*;

@Service
@NoArgsConstructor
public class TidyUpRobotController implements RobotInterface, OperatorInterface {

    @Getter
    private TidyUpRobotRepository tidyUpRobotRepository;
    @Getter
    private RoomRepository roomRepository;
    @Getter
    private ConnectionRepository connectionRepository;
    @Getter
    private TransportSystemRepository transportSystemRepository;

    @Autowired
    public TidyUpRobotController(TidyUpRobotRepository tidyUpRobotRepository, RoomRepository roomRepository, ConnectionRepository connectionRepository, TransportSystemRepository transportSystemRepository) {
        this.tidyUpRobotRepository = tidyUpRobotRepository;
        this.roomRepository = roomRepository;
        this.connectionRepository = connectionRepository;
        this.transportSystemRepository = transportSystemRepository;
    }

    @Getter
    private TidyUpRobot tidyUpRobot;

    public void execute(UUID tidyUpRobotId, Command command){
        tidyUpRobot = tidyUpRobotRepository.findById(tidyUpRobotId).get();
        if (command.getCommandType() == CommandType.ENTER)
            initialRobot(command.getGridId());
        else if (command.getCommandType() == CommandType.TRANSPORT)
            changeRoom(command.getGridId());
        else
            move(command);
    }

    public void move(Command command){
        int steps = command.getNumberOfSteps();
        Room room = roomRepository.findById(tidyUpRobot.getRoomId()).get();

        for (int i = 0; i < steps; i++) {
            moveOneStep(command.getCommandType(), room.getRoomObstacles());
        }
    }

    private void moveOneStep(CommandType direction, List<Obstacle> obstacles){
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

        if (roomIsBlockedByRobot(tidyUpRobot.getRoomId(), newRobotPosition, tidyUpRobotRepository.findAll()))
            throw new InvalidRoomException("Room is blocked by another Robot");

        tidyUpRobot.setRobotPosition(newRobotPosition);
        tidyUpRobotRepository.save(tidyUpRobot);
    }


    public void initialRobot(UUID destinationRoomId) {
        if (tidyUpRobot.getRoomId() != null)
            throw new RobotException("Error: Robot is already in a room\n");

        if (roomIsBlockedByRobot(destinationRoomId, tidyUpRobot.getRobotPosition(), tidyUpRobotRepository.findAll()))
            throw new InvalidRoomException("Initial Room is blocked by another Robot");

        tidyUpRobot.setRoomId(destinationRoomId);
        tidyUpRobotRepository.save(tidyUpRobot);
    }

    public void changeRoom(UUID destinationRoomId){
        Room room = roomRepository.findById(tidyUpRobot.getRoomId()).get();
        Connection roomConnection = room.getConnections().get(destinationRoomId);

        if (roomConnection == null)
            throw new InvalidConnectionException("Connection does not exist");

        if (!roomConnection.getSourceRoomCoordinates().equals(tidyUpRobot.getRobotPosition()))
            throw new InvalidConnectionException("Robot is not on a valid Connection!");

        if (roomIsBlockedByRobot(destinationRoomId, roomConnection.getDestinationRoomCoordinates(), tidyUpRobotRepository.findAll()))
            throw new InvalidRoomException("Room is blocked by another Robot");

        tidyUpRobot.setRoomId(destinationRoomId);
        tidyUpRobot.setRobotPosition(roomConnection.getDestinationRoomCoordinates());
        tidyUpRobotRepository.save(tidyUpRobot);
    }

    @Override
    public boolean roomIsBlockedByRobot(UUID roomID, Vector2D vector2D, List<TidyUpRobot> tidyUpRobots){
        for (TidyUpRobot tidyUpRobot : tidyUpRobots) {
            if (vector2D.equals(tidyUpRobot.getRobotPosition()) && roomID.equals(tidyUpRobot.getRoomId()))
                return true;
        }
        return false;
    }


}
