package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.movement;

import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.room.repository.RoomRepository;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.repository.TidyUpRobotRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.application.TransportCategoryService;

@Component
public class TidyUpRobotTaskService {

    private final TidyUpRobotRepository tidyUpRobotRepository;
    private final RoomRepository roomRepository;
    private final TransportCategoryService transportCategoryService;


    public TidyUpRobotTaskService(TidyUpRobotRepository tidyUpRobotRepository, RoomRepository roomRepository, TransportCategoryService transportCategoryService) {
        this.tidyUpRobotRepository = tidyUpRobotRepository;
        this.roomRepository = roomRepository;
        this.transportCategoryService = transportCategoryService;
    }


    public Boolean processTask(TidyUpRobot tidyUpRobot, Task task) {
        RobotMover robotMover = new RobotMover(tidyUpRobotRepository, roomRepository, transportCategoryService);
        tidyUpRobot.addTask(task);
        switch (task.getTaskType()) {
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:      return robotMover.moveRobot(tidyUpRobot, task);
            case ENTER:     return robotMover.initializeRobot(tidyUpRobot, task);
            case TRANSPORT: return robotMover.transportRobot(tidyUpRobot, task);
        }
        return false;
    }
}
