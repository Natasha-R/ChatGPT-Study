package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;


public class Controller {

    @Autowired
    TidyUpRobotRepository tidyUpRobotRepository;
    @Autowired
    RoomRepository roomRepository;

    public Controller (TidyUpRobotRepository tidyUpRobotRepository, RoomRepository roomRepository) {
        this.tidyUpRobotRepository = tidyUpRobotRepository;
        this.roomRepository = roomRepository;
    }


    public Boolean processCommand (UUID robotId, Command command) {

        TidyUpRobot tidyUpRobot = tidyUpRobotRepository.findById(robotId).get();

        switch (command.getCommandType()) {
            case ENTER: 
                return tidyUpRobot.spawn(this.roomRepository.findById(command.getGridId()).get(), this.tidyUpRobotRepository.findAll());
            case TRANSPORT:
                return tidyUpRobot.transport(this.roomRepository.findById(command.getGridId()).get());
            case NORTH:
            case WEST:
            case SOUTH:
            case EAST:
                return tidyUpRobot.move(command.getCommandType(), command.getNumberOfSteps());
            default: 
                throw new UnsupportedOperationException();
        }
    }
}
