package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.CustomExceptions.NegativePointException;
import thkoeln.st.st2praktikum.exercise.CustomExceptions.NotAWalkCommandException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Entity
@Setter
@Getter
public class MiningMachine {
    @Id
    private UUID id = UUID.randomUUID();
    private String name;
    @Embedded
    private Point point;

    private UUID fieldId;

    @ElementCollection(targetClass = Command.class, fetch = FetchType.EAGER)
    private List<Command> commands = new ArrayList<>();

    public MiningMachine() { }

    private Boolean isUnoccupied(Point point, List<MiningMachine> robotsInTargetRoom) {
        Predicate<MiningMachine> p = walkable -> point.equals(walkable.getPoint());
        return robotsInTargetRoom.stream().noneMatch(p);
    }

    public Boolean placeInInitialRoom(Command task, List<MiningMachine> robotsInTargetRoom) {
        commands.add(task);

        Point targetPoint = new Point(0, 0);
        if(this.fieldId == null && isUnoccupied(targetPoint, robotsInTargetRoom)) {
            this.fieldId = task.getGridId();
            this.point = new Point(0, 0);
            return true;
        } else {
            return false;
        }
    }


    public Point walk(Command task, Field room, List<MiningMachine> robotsInRoom) {
        commands.add(task);

        // Not allowing negative Points breaks my entire logic in TidyUpRobot.getNextPosition() and Barrier.isBlocking() .
        // I could also use some kind of "ExperimentalPoint" which would allow negative Points but this seems more reasonable.
        for(int currentStep = 0; currentStep < task.getNumberOfSteps(); currentStep++) {
            Point nextPoint;
            try {
                nextPoint = getNextPosition(task.getCommandType());
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

    public Point getNextPosition(CommandType direction) {
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

    public Boolean transport(Command task, TransportTechnology transportTechnology, List<MiningMachine> robotsInDestinationRoom) {
        commands.add(task);

        if (transportTechnology != null) {
            return transportTechnology.transport(this, robotsInDestinationRoom);
        } else {
            return false;
        }
    }

    public MiningMachine(String _name) {
        this.name = _name;
        this.fieldId = null;
        this.point = null;
    }
}
