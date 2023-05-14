package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;

import java.util.UUID;

public interface RobotInterface {
    void execute(UUID tidyUpRobotId, Command commandString);
    void move(Command commandString);
    void initialRobot(UUID destinationRoomId);
    void changeRoom(UUID destinationRoomId);
}
