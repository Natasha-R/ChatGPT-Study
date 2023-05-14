package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.customexceptions.NegativePointException;
import thkoeln.st.st2praktikum.exercise.customexceptions.NotAWalkCommandException;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Entity
@Setter
@Getter
public class TidyUpRobot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    @Embedded
    private Point point;
    private UUID roomId;

    @ElementCollection(targetClass = Task.class, fetch = FetchType.EAGER)
    private List<Task> tasks = new ArrayList<>();

    public TidyUpRobot() { }

    private Boolean isUnoccupied(Point point, List<TidyUpRobot> robotsInTargetRoom) {
        Predicate<TidyUpRobot> p = walkable -> point.equals(walkable.getPoint());
        return robotsInTargetRoom.stream().noneMatch(p);
    }

    public Boolean placeInInitialRoom(Task task, List<TidyUpRobot> robotsInTargetRoom) {
        tasks.add(task);

        Point targetPoint = new Point(0, 0);
        if(this.roomId == null && isUnoccupied(targetPoint, robotsInTargetRoom)) {
            this.roomId = task.getGridId();
            this.point = new Point(0, 0);
            return true;
        } else {
            return false;
        }
    }


    public Point walk(Task task, Room room, List<TidyUpRobot> robotsInRoom) {
        tasks.add(task);

        // Not allowing negative Points breaks my entire logic in TidyUpRobot.getNextPosition() and Barrier.isBlocking() .
        // I could also use some kind of "ExperimentalPoint" which would allow negative Points but this seems more reasonable.
        for(int currentStep = 0; currentStep < task.getNumberOfSteps(); currentStep++) {
            Point nextPoint;
            try {
                nextPoint = getNextPosition(task.getTaskType());
            } catch (NegativePointException e) {    // A negative Point would automatically be out of bounds.
                break;                              // So walk() needs to stop here. No further action required.
            }

            if (room.isReachable(point, nextPoint) && isUnoccupied(nextPoint, robotsInRoom)) {
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

    public Boolean transport(Task task, TransportTechnology transportTechnology, List<TidyUpRobot> robotsInDestinationRoom) {
        tasks.add(task);

        if (transportTechnology != null) {
            return transportTechnology.transport(this, robotsInDestinationRoom);
        } else {
            return false;
        }
    }

    public UUID getId() {
        return this.id;
    }
    public UUID getRoomId() { return this.roomId; }
    public Point getPoint() {
        return point;
    }

    public TidyUpRobot(String _name) {
        this.name = _name;
        this.roomId = null;
        this.point = null;
    }
}
