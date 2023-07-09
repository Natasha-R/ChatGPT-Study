package thkoeln.st.st2praktikum.exercise.entities;

import thkoeln.st.st2praktikum.exercise.interfaces.Executable;

import java.util.List;
import java.util.Optional;

public class InitCommand implements Executable {
    private TidyUpRobot robot;
    private Room room;
    private List<TidyUpRobot> allRobots;

    public InitCommand(TidyUpRobot robot, Room room, List<TidyUpRobot> allRobots) {
        this.robot = robot;
        this.room = room;
        this.allRobots = allRobots;
    }

    @Override
    public void execute() {
        Optional<TidyUpRobot> robotOnDestinationCoordinates = allRobots.stream().filter(r ->
                ((r.roomId != null && r.roomId.equals(this.room.getId())) && r.currentPosition.x == 0 && r.currentPosition.y == 0))
                .findFirst();

        if (robot.roomId != null || !robotOnDestinationCoordinates.isEmpty()) {
            System.out.println("Destination coordinates already blocked");
            throw new IllegalArgumentException();
        } else {
            robot.roomId = room.id;
            robot.currentPosition = new Coordinate(0, 0);
        }
    }
}
