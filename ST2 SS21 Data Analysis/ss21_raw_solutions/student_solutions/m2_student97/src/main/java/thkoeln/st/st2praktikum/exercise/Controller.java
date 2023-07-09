package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Controller {
    Environment environment;


    public Controller (Environment environment) {
        this.environment = environment;
    }


    public Boolean processCommand (UUID robotId, Command command) {
        Robot robot = environment.getRobot(robotId);

        switch (command.getCommandType()) {
            case ENTER: 
                return robot.spawn(this.environment.getRoom(command.getGridId()), this.environment.getRobots());
            case TRANSPORT:
                return robot.transport(this.environment.getRoom(command.getGridId()));
            case NORTH:
            case WEST:
            case SOUTH:
            case EAST:
                return robot.move(command.getCommandType(), command.getNumberOfSteps());
            default: 
                throw new UnsupportedOperationException();
        }
    }
}
