package thkoeln.st.st2praktikum.exercise.entities;

import thkoeln.st.st2praktikum.exercise.interfaces.Executable;

import java.util.List;
import java.util.Optional;

public class TransportCommand implements Executable {
    private TidyUpRobot robot;
    private Room room;
    private List<Connection> connections;

    public TransportCommand(TidyUpRobot robot, Room room, List<Connection> connections) {
        this.robot = robot;
        this.room = room;
        this.connections = connections;
    }

    @Override
    public void execute() {
        Optional<Connection> usableConnection = connections.stream().filter(c ->
                (c.sourceRoomId.equals(robot.roomId) && c.sourceCoordinate.x == robot.currentPosition.x && c.sourceCoordinate.y == robot.currentPosition.y))
                .findFirst();

        if(usableConnection.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            robot.roomId = usableConnection.get().destinationRoomId;
            robot.currentPosition = usableConnection.get().destinationCoordinate;
        }
    }
}
