package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.movement;

import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.room.repository.RoomRepository;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.repository.TidyUpRobotRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.application.TransportCategoryService;

@Component
public class TidyUpRobotCommandService {

    private final TidyUpRobotRepository tidyUpRobotRepository;
    private final RoomRepository roomRepository;
    private final TransportCategoryService transportCategoryService;


    public TidyUpRobotCommandService(TidyUpRobotRepository tidyUpRobotRepository, RoomRepository roomRepository, TransportCategoryService transportCategoryService) {
        this.tidyUpRobotRepository = tidyUpRobotRepository;
        this.roomRepository = roomRepository;
        this.transportCategoryService = transportCategoryService;
    }


    public Boolean processCommand(TidyUpRobot tidyUpRobot, Command command) {
        RobotMover robotMover = new RobotMover(tidyUpRobotRepository, roomRepository, transportCategoryService);
        tidyUpRobot.addCommand(command);
        switch (command.getCommandType()) {
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:      return robotMover.moveRobot(tidyUpRobot, command);
            case ENTER:     return robotMover.initializeRobot(tidyUpRobot, command);
            case TRANSPORT: return robotMover.transportRobot(tidyUpRobot, command);
        }
        return false;
    }
}
