package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.internal.Pair;

import java.util.UUID;

@Setter
@Getter
public class Room {
    private UUID id;
    private Square[][] squares;
    private int width, height;

    public Room(int height, int width) {
        id = UUID.randomUUID();
        this.height = height;
        this.width = width;

        initSquares();
        addBorderWalls();
    }

    private void initSquares() {
        if(height <= 0 || width <= 0)
            throw new RuntimeException("The room has invalid height and width.");

        squares = new Square[height][width];
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                squares[y][x] = new Square();
    }

    private void addBorderWalls() {
        addWall(String.format("(%d,%d)-(%d,%d)", 0, 0, 0, height));
        addWall(String.format("(%d,%d)-(%d,%d)", 0, 0, width, 0));
        addWall(String.format("(%d,%d)-(%d,%d)", 0, height, width, height));
        addWall(String.format("(%d,%d)-(%d,%d)", width, 0, width, height));
    }

    public void addWall(String wallString) {
        Pair coordinatePair = Coordinate.getCoordinatePair(wallString);
        addWall(
                (Coordinate) coordinatePair.getLeft(),
                (Coordinate) coordinatePair.getRight()
        );
    }

    private void addWall(Coordinate first, Coordinate second) {
        if (first.x == second.x) {
            for (int y = first.y; y < second.y; y++) {
                if (first.x < width && first.x > 0) {
                    addWall(first.x - 1, y, "ea");
                    addWall(first.x, y, "we");
                }
                else if (first.x == width)
                    addWall(first.x - 1, y, "ea");
                else
                    addWall(first.x, y, "we");
            }
        }
        else {
            for (int x = first.x; x < second.x; x++) {
                if (first.y < height && first.y > 0) {
                    addWall(x, first.y - 1, "no");
                    addWall(x, first.y, "so");
                }
                else if (first.y == height)
                    addWall(x, first.y - 1, "no");
                else
                    addWall(x, first.y, "so");
            }
        }
    }

    private void addWall(int x, int y, String wall) {
        int x_coordinate = x;
        int y_coordinate = Coordinate.flipYCoordinate(y, height);
        squares[y_coordinate][x_coordinate].addWall(wall);
    }

    public void addConnection(Connection connection) {
        UUID sourceId = connection.getSourceId();
        Coordinate sourceCoordinate = connection.getSourceCoordinate();
        Coordinate destinationCoordinate = connection.getDestinationCoordinate();
        int x_coordinate, y_coordinate;

        if(id == sourceId){
            x_coordinate = sourceCoordinate.x;
            y_coordinate = Coordinate.flipYCoordinate(sourceCoordinate.y, height);
        }
        else {
            x_coordinate = destinationCoordinate.x;
            y_coordinate = Coordinate.flipYCoordinate(destinationCoordinate.y, height);
        }

        squares[y_coordinate][x_coordinate].addConnection(connection);
    }

    public boolean hasNoWall(int x, int y, String border) {
        int x_coordinate = x;
        int y_coordinate = Coordinate.flipYCoordinate(y, height);;

        switch (border) {
            case "no":
                return !squares[y_coordinate][x_coordinate].isNorthWall();
            case "ea":
                return !squares[y_coordinate][x_coordinate].isEastWall();
            case "so":
                return !squares[y_coordinate][x_coordinate].isSouthWall();
            default:
                return !squares[y_coordinate][x_coordinate].isWestWall();
        }
    }

    public void addTidyUpRobotId(Coordinate position, UUID tidyUpRobotId) {
        int x_coordinate = position.x;
        int y_coordinate = Coordinate.flipYCoordinate(position.y, height);
        squares[y_coordinate][x_coordinate].addTidyUpRobotId(tidyUpRobotId);
    }

    public void removeTidyUpRobotId(Coordinate position) {
        int x_coordinate = position.x;
        int y_coordinate = Coordinate.flipYCoordinate(position.y, height);
        squares[y_coordinate][x_coordinate].removeTidyUpRobotId();
    }

    public Boolean isSquareOccupied(Coordinate position) {
        int x_coordinate = position.x;
        int y_coordinate = Coordinate.flipYCoordinate(position.y, height);
        return squares[y_coordinate][x_coordinate].isOccupied();
    }

    public Connection getSquareConnection(Coordinate position) {
        int x_coordinate = position.x;
        int y_coordinate = Coordinate.flipYCoordinate(position.y, height);
        return squares[y_coordinate][x_coordinate].getConnection();
    }
}
