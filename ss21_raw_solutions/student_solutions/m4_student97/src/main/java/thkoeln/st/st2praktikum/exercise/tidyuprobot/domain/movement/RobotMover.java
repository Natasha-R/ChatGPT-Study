package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.movement;

import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Orientation;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.CommandType;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.room.domain.movement.RoomMovementService;
import thkoeln.st.st2praktikum.exercise.room.repository.RoomRepository;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.repository.TidyUpRobotRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.application.TransportCategoryService;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;

import java.util.Optional;
import java.util.UUID;

@Component
public class RobotMover {

    private final TidyUpRobotRepository tidyUpRobotRepository;
    private final RoomRepository roomRepository;
    private final TransportCategoryService transportCategoryService;


    public RobotMover(TidyUpRobotRepository tidyUpRobotRepository, RoomRepository roomRepository, TransportCategoryService transportCategoryService) {
        this.tidyUpRobotRepository = tidyUpRobotRepository;
        this.roomRepository = roomRepository;
        this.transportCategoryService = transportCategoryService;
    }


    public Boolean initializeRobot(TidyUpRobot tidyUpRobot, Command command) {
        Room destinationRoom = fetchRoom(command.getGridId());
        Point destinationPosition = new Point(0, 0);

        RoomMovementService roomMovementService = new RoomMovementService(destinationRoom, tidyUpRobotRepository);
        if (roomMovementService.roomPositionIsEmpty(destinationPosition)) {
            tidyUpRobot.placeRobot(destinationRoom, destinationPosition);
            return true;
        }
        return false;
    }

    public Boolean transportRobot(TidyUpRobot tidyUpRobot, Command command) {
        Optional<Connection> connectionOptional = transportCategoryService.getConnection(tidyUpRobot.getRoom(), tidyUpRobot.getPoint(), command.getGridId());

        if (connectionOptional.isPresent()) {
            Connection connection = connectionOptional.get();
            Room newRoom = connection.getEndRoom();
            Point newPosition = connection.getEndPosition();
            RoomMovementService roomMovementService = new RoomMovementService(newRoom, tidyUpRobotRepository);
            if (roomMovementService.roomPositionIsEmpty(newPosition)) {
                tidyUpRobot.placeRobot(newRoom, newPosition);
                return true;
            }
        }
        return false;
    }

    public Boolean moveRobot(TidyUpRobot tidyUpRobot, Command command) {
        Moveable movement = createMovementFromCommand(tidyUpRobot.getPosition(), command);
        RoomMovementService roomMovementService = new RoomMovementService(tidyUpRobot.getRoom(), tidyUpRobotRepository);
        Moveable movementResult = roomMovementService.movePosition(movement);
        tidyUpRobot.moveRobot(movementResult.getCurrentPosition());
        return true;
    }

    private Moveable createMovementFromCommand(Point currentPosition, Command command) {
        return new Movement(currentPosition, parseCommandTypeToMovementOrientation(command.getCommandType()), command.getNumberOfSteps());
    }

    private Orientation parseCommandTypeToMovementOrientation(CommandType command) {
        switch (command) {
            case NORTH: return Orientation.NO;
            case EAST:  return Orientation.EA;
            case SOUTH: return Orientation.SO;
            case WEST:  return Orientation.WE;
            default: throw new RuntimeException();
        }
    }

    private Room fetchRoom(UUID roomId) {
        return roomRepository.findById(roomId).get();
    }
}
