package thkoeln.st.st2praktikum.exercise.tidyuprobot;

import thkoeln.st.st2praktikum.exercise.connection.Connection;
import thkoeln.st.st2praktikum.exercise.customexceptions.NegativePointException;
import thkoeln.st.st2praktikum.exercise.customexceptions.NotAWalkCommandException;
import thkoeln.st.st2praktikum.exercise.Point;
import thkoeln.st.st2praktikum.exercise.Task;
import thkoeln.st.st2praktikum.exercise.TaskType;
import thkoeln.st.st2praktikum.exercise.room.Room;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class TidyUpRobot implements Walkable {
    private final UUID id;
    private final String name;
    private Point point;
    private UUID roomId;

    private Boolean isUnoccupied(Point point, List<Walkable> walkablesInTargetRoom) {
        Predicate<Walkable> p = walkable -> point.equals(walkable.getPoint());
        return walkablesInTargetRoom.stream().noneMatch(p);
    }

    public Boolean placeInInitialRoom(UUID targetRoomId, List<Walkable> walkablesInTargetRoom) {
        Point targetPoint = new Point(0, 0);
        if(this.roomId == null && isUnoccupied(targetPoint, walkablesInTargetRoom)) {
            this.roomId = targetRoomId;
            this.point = new Point(0, 0);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Point walk(Task task, Room room, List<Walkable> walkablesInRoom) {

        // Not allowing negative Points breaks my entire logic in TidyUpRobot.getNextPosition() and Barrier.isBlocking() .
        // I could also use some kind of "ExperimentalPoint" which would allow negative Points but this seems more reasonable.
        for(int currentStep = 0; currentStep < task.getNumberOfSteps(); currentStep++) {
            Point nextPoint;
            try {
                nextPoint = getNextPosition(task.getTaskType());
            } catch (NegativePointException e) {    // A negative Point would automatically be out of bounds.
                break;                              // So walk() needs to stop here. No further action required.
            }

            if (room.isReachable(point, nextPoint) && isUnoccupied(nextPoint, walkablesInRoom)) {
                point = nextPoint;
            } else {
                break;
            }
        }

        return point;
    }

    public Point getNextPosition(TaskType direction) {
        switch (direction) {
            case NORTH:
                return new Point(point.getX(), point.getY()+1);
            case EAST:
                return new Point(point.getX()+1, point.getY());
            case SOUTH:
                return new Point(point.getX(), point.getY()-1);
            case WEST:
                return new Point(point.getX()-1, point.getY());
            default:
                throw new NotAWalkCommandException(direction.toString() + " is not a valid direction in walk().");
        }
    }

    public Boolean transport(List<Connection> connectionsInDestinationRoom, List<Walkable> walkablesInDestinationRoom) {
        Connection currentConnection = connectionsInDestinationRoom
                .stream()
                .filter(connection -> point.equals(connection.getSourceCoordinate()))
                .findFirst()
                .orElse(null);

        if(currentConnection != null && isUnoccupied(new Point(0, 0), walkablesInDestinationRoom)) {
            this.roomId = currentConnection.getDestinationRoomId();
            this.point = currentConnection.getDestinationCoordinate();
            return true;
        } else {
            return false;
        }
    }

    public UUID getId() {
        return this.id;
    }
    public UUID getRoomId() { return this.roomId; }
    @Override
    public Point getPoint() {
        return point;
    }

    public TidyUpRobot(String _name) {
        this.id = UUID.randomUUID();
        this.name = _name;
        this.roomId = null;
        this.point = null;
    }
}
