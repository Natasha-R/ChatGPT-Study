package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.movement;

import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Orientation;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
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


    public Boolean initializeRobot(TidyUpRobot tidyUpRobot, Task task) {
        Room destinationRoom = fetchRoom(task.getGridId());
        Vector2D destinationPosition = new Vector2D(0, 0);

        RoomMovementService roomMovementService = new RoomMovementService(destinationRoom, tidyUpRobotRepository);
        if (roomMovementService.roomPositionIsEmpty(destinationPosition)) {
            tidyUpRobot.placeRobot(destinationRoom, destinationPosition);
            return true;
        }
        return false;
    }

    public Boolean transportRobot(TidyUpRobot tidyUpRobot, Task task) {
        Optional<Connection> connectionOptional = transportCategoryService.getConnection(tidyUpRobot.getRoom(), tidyUpRobot.getVector2D(), task.getGridId());

        if (connectionOptional.isPresent()) {
            Connection connection = connectionOptional.get();
            Room newRoom = connection.getEndRoom();
            Vector2D newPosition = connection.getEndPosition();
            RoomMovementService roomMovementService = new RoomMovementService(newRoom, tidyUpRobotRepository);
            if (roomMovementService.roomPositionIsEmpty(newPosition)) {
                tidyUpRobot.placeRobot(newRoom, newPosition);
                return true;
            }
        }
        return false;
    }

    public Boolean moveRobot(TidyUpRobot tidyUpRobot, Task task) {
        Moveable movement = createMovementFromTask(tidyUpRobot.getPosition(), task);
        RoomMovementService roomMovementService = new RoomMovementService(tidyUpRobot.getRoom(), tidyUpRobotRepository);
        Moveable movementResult = roomMovementService.movePosition(movement);
        tidyUpRobot.moveRobot(movementResult.getCurrentPosition());
        return true;
    }

    private Moveable createMovementFromTask(Vector2D currentPosition, Task task) {
        return new Movement(currentPosition, parseTaskTypeToMovementOrientation(task.getTaskType()), task.getNumberOfSteps());
    }

    private Orientation parseTaskTypeToMovementOrientation(TaskType task) {
        switch (task) {
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
