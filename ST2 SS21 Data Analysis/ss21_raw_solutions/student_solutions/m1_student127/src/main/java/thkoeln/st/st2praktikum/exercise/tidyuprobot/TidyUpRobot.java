package thkoeln.st.st2praktikum.exercise.tidyuprobot;

import org.springframework.data.util.Pair;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.room.Room;
import thkoeln.st.st2praktikum.exercise.Walkable;
import thkoeln.st.st2praktikum.exercise.connection.Connection;

import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.function.Predicate;

public class TidyUpRobot implements Walkable {
    private final UUID id;
    private final String name;
    private Coordinate coordinate;
    private UUID roomId;

    private Boolean isUnoccupied(Coordinate coordinate, List<Walkable> walkablesInTargetRoom) {
        Predicate<Walkable> p = walkable -> coordinate.equals(walkable.getCoordinate());
        return walkablesInTargetRoom.stream().noneMatch(p);
    }
    public Boolean placeInInitialRoom(UUID targetRoomId, List<Walkable> walkablesInTargetRoom) {
        Coordinate targetCoordinate = new Coordinate(0, 0);
        if(this.roomId == null && isUnoccupied(targetCoordinate, walkablesInTargetRoom)) {
            this.roomId = targetRoomId;
            this.coordinate = new Coordinate(0, 0);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String walk(String walkCommandString, Room room, List<Walkable> otherWalkablesInRoom) {
        Pair<String, Integer> walkCommand = parseWalkCommandStringToPair(walkCommandString);

        for(int currentStep = 0; currentStep < walkCommand.getSecond(); currentStep++) {
            Coordinate nextCoordinate = getNextPosition(walkCommand.getFirst());

            if(room.isReachable(coordinate, nextCoordinate) && isUnoccupied(nextCoordinate, otherWalkablesInRoom)) {
                coordinate = nextCoordinate;
            } else {
                break;
            }
        }

        return coordinate.toString();
    }

    public Pair<String, Integer> parseWalkCommandStringToPair(String walkCommandString) {
        final StringTokenizer st = new StringTokenizer(walkCommandString, "[],");

        String direction = st.nextToken();
        Integer steps = Integer.parseInt(st.nextToken());

        return Pair.of(direction,steps);
    }

    public Coordinate getNextPosition(String direction) {
        switch (direction) {
            case "no":
                return new Coordinate(coordinate.getX(), coordinate.getY()+1);
            case "ea":
                return new Coordinate(coordinate.getX()+1, coordinate.getY());
            case "so":
                return new Coordinate(coordinate.getX(), coordinate.getY()-1);
            case "we":
                return new Coordinate(coordinate.getX()-1, coordinate.getY());
            default:
                throw new IllegalArgumentException();
        }
    }

    public Boolean transport(List<Connection> connectionsInDestinationRoom, List<Walkable> walkablesInDestinationRoom) {
        Connection currentConnection = connectionsInDestinationRoom
                .stream()
                .filter(connection -> coordinate.equals(connection.getSourceCoordinate()))
                .findFirst()
                .orElse(null);

        if(currentConnection != null && isUnoccupied(new Coordinate(0, 0), walkablesInDestinationRoom)) {
            this.roomId = currentConnection.getDestinationRoomId();
            this.coordinate = currentConnection.getDestinationCoordinate();
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
    public Coordinate getCoordinate() {
        return coordinate;
    }

    public TidyUpRobot(String _name) {
        this.id = UUID.randomUUID();
        this.name = _name;
        this.roomId = null;
        this.coordinate = null;
    }
}
