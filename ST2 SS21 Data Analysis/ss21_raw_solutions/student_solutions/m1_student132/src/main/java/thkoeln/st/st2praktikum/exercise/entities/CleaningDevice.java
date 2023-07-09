package thkoeln.st.st2praktikum.exercise.entities;

import thkoeln.st.st2praktikum.exercise.interfaces.Walkable;
import thkoeln.st.st2praktikum.exercise.interfaces.WorldStorage;

public class CleaningDevice extends AbstractEntity implements Walkable {
    private Space space;
    private String name;
    private final WorldStorage world;

    private Coordinate position = new Coordinate(0, 0);

    public CleaningDevice(Coordinate startingPosition, Space startingSpace, WorldStorage associatedWorld) {
        this.position = startingPosition;
        this.space = startingSpace;
        this.world = associatedWorld;
    }

    public CleaningDevice(String name, WorldStorage associatedWorld) {
        this.name = name;
        this.world = associatedWorld;
    }

    public CleaningDevice(String name, Space startingSpace, WorldStorage associatedWorld) {
        this.name = name;
        this.space = startingSpace;
        this.world = associatedWorld;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate newPosition) {
        this.position = newPosition;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space newSpace) {
        this.space = newSpace;
    }

    public String walkTo(String walkCommandString) {
        //Parse Command String
        String cleanCommandString = walkCommandString.replaceFirst("\\[", "");
        cleanCommandString = cleanCommandString.replaceFirst("]", "");
        String[] parts = cleanCommandString.split(",");

        String direction = parts[0];
        int distance = Integer.parseInt(parts[1]);

        return move(distance, direction).toString();
    }

    private Coordinate move(int distance, String direction) {
        if (distance > 0) {

            if (canMove(new Move(position, direction))) {
                switch (direction) {
                    case "no":
                        this.position = new Coordinate(position.getX(), position.getY() + 1);
                        break;
                    case "so":
                        this.position = new Coordinate(position.getX(), position.getY() - 1);

                        break;
                    case "ea":
                        this.position = new Coordinate(position.getX() + 1, position.getY());

                        break;
                    case "we":
                        this.position = new Coordinate(position.getX() - 1, position.getY());

                        break;
                }
            }
            return move(distance - 1, direction);
        } else {
            return this.position;
        }

    }

    private Boolean canMove(Move move) {
        Coordinate destination = move.destination();

        //Is the destination not out of bounds?
        if (destination.getX() < 0) return false;
        if (destination.getY() < 0) return false;
        if (destination.getX() >= space.getWidth()) return false;
        if (destination.getY() >= space.getHeight()) return false;

        //is the destination not blocked by another CleaningDevice?
        for (CleaningDevice cleaningDevice : world.getCleaningDeviceMap().values()) {
            if (this.space.equals(cleaningDevice.getSpace())) {
                if (move.destination().equals(cleaningDevice.getPosition())) return false;
            }
        }

        return space.notBlocked(move);
    }
}

