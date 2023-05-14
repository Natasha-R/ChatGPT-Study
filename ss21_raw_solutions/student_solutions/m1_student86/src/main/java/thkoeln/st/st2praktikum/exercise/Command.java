package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import thkoeln.st.st2praktikum.exercise.Obstacle.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.Space.CleaningDeviceTransporter;
import thkoeln.st.st2praktikum.exercise.Space.Space;
import thkoeln.st.st2praktikum.exercise.Space.Tile;

import java.util.Map;
import java.util.UUID;


@AllArgsConstructor
public class Command {

    private Map<UUID, Space> spaceRepo;
    private Map<UUID, CleaningDevice> cleaningDeviceRepo;
    private CleaningDeviceTransporter transporter;

    public Boolean execute (UUID cleaningDeviceId, String commandString) {
        CleaningDevice cleaningDevice = cleaningDeviceRepo.get(cleaningDeviceId);
        String[] commandArguments = commandString
                .replaceAll("[\\[\\]()]", "")
                .split(",", 2);

        if (commandArguments.length != 2)
            throw new IllegalArgumentException("Command-Formatfehler: " + commandString);

        switch (commandArguments[0]) {
            case "en": {
                UUID spaceId = UUID.fromString(commandArguments[1]);
                Space space = spaceRepo.get(spaceId);
                return cleaningDevice.enterSpace(space);
            }
            case "tr": {
                UUID spaceId = UUID.fromString(commandArguments[1]);
                Space space = spaceRepo.get(spaceId);
                return transporter.transportCleaningDevice(cleaningDevice, space);
            }
            default: {
                String directionString = commandArguments[0];
                Tile.Direction direction;

                switch (directionString) {
                    case "we":
                        direction = Tile.Direction.WEST;
                        break;
                    case "ea":
                        direction = Tile.Direction.EAST;
                        break;
                    case "so":
                        direction = Tile.Direction.SOUTH;
                        break;
                    case "no":
                        direction = Tile.Direction.NORTH;
                        break;
                    default:
                        throw new IllegalArgumentException("Unbekannter Befehl: " + commandString);
                }
                int steps = Integer.parseInt(commandArguments[1]);
                cleaningDevice.move(direction, steps);
                return true;
            }
        }
    }

    static public Coordinate parseCoordinate(String coordinateString) {
        try {
            coordinateString = coordinateString.replaceAll("[()]", "");
            String[] axes = coordinateString.split(",");
            int x = Integer.parseInt(axes[0]);
            int y = Integer.parseInt(axes[1]);
            return new Coordinate(x, y);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Coordinate-Formatfehler: " + coordinateString);
        }
    }

    static public Coordinate[] parsePath (String pathString) {
        Coordinate[] path = new Coordinate[2];
        String[] coordinates;
        try {
            coordinates = pathString.split("-");
            path[0] = Command.parseCoordinate(coordinates[0]);
            path[1] = Command.parseCoordinate(coordinates[1]);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Path-Formatfehler: " + pathString);
        }
        return path;
    }
}
