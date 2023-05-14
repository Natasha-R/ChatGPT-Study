package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.internal.Pair;

import java.util.UUID;

@Setter
@Getter
public class Space {
    private UUID id;
    private Square[][] squares;
    private int width, height;

    public Space(int height, int width) {
        id = UUID.randomUUID();
        this.height = height;
        this.width = width;

        initSquares();
        addBorderObstacles();
    }

    private void initSquares() {

        squares = new Square[height][width];
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                squares[y][x] = new Square();
    }

    private void addBorderObstacles() {
        addObstacle(String.format("(%d,%d)-(%d,%d)", 0, 0, 0, height));
        addObstacle(String.format("(%d,%d)-(%d,%d)", 0, 0, width, 0));
        addObstacle(String.format("(%d,%d)-(%d,%d)", 0, height, width, height));
        addObstacle(String.format("(%d,%d)-(%d,%d)", width, 0, width, height));
    }

    public void addObstacle(String obstacleString) {
        Pair coordinatePair = Coordinate.getCoordinatePair(obstacleString);
        addObstacle(
                (Coordinate) coordinatePair.getLeft(),
                (Coordinate) coordinatePair.getRight()
        );
    }

    private void addObstacle(Coordinate first, Coordinate second) {
        if (first.getX() == second.getX()) {
            for (int y = first.getY(); y < second.getY(); y++) {
                if (first.getX() < width && first.getX() > 0) {
                    addObstacle(first.getX() - 1, y, "ea");
                    addObstacle(first.getX(), y, "we");
                }
                else if (first.getX() == width)
                    addObstacle(first.getX() - 1, y, "ea");
                else
                    addObstacle(first.getX(), y, "we");
            }
        }
        else {
            for (int x = first.getX(); x < second.getX(); x++) {
                if (first.getY() < height && first.getY() > 0) {
                    addObstacle(x, first.getY() - 1, "no");
                    addObstacle(x, first.getY(), "so");
                }
                else if (first.getY() == height)
                    addObstacle(x, first.getY() - 1, "no");
                else
                    addObstacle(x, first.getY(), "so");
            }
        }
    }

    private void addObstacle(int x, int y, String obstacle) {
        int x_coordinate = x;
        int y_coordinate = Coordinate.flipYCoordinate(y, height);
        squares[y_coordinate][x_coordinate].addObstacle(obstacle);
    }

    public void addConnection(Connection connection) {
        UUID sourceId = connection.getSourceId();
        Coordinate sourceCoordinate = connection.getSourceCoordinate();
        Coordinate destinationCoordinate = connection.getDestinationCoordinate();
        int x_coordinate, y_coordinate;

        if(id == sourceId){
            x_coordinate = sourceCoordinate.getX();
            y_coordinate = Coordinate.flipYCoordinate(sourceCoordinate.getY(), height);
        }
        else {
            x_coordinate = destinationCoordinate.getX();
            y_coordinate = Coordinate.flipYCoordinate(destinationCoordinate.getY(), height);
        }

        squares[y_coordinate][x_coordinate].addConnection(connection);
    }

    public boolean hasNoObstacle(int x, int y, String border) {

        y = Coordinate.flipYCoordinate(y, height);;

        switch (border) {
            case "no":
                return !squares[y][x].isNorthObstacle();
            case "ea":
                return !squares[y][x].isEastObstacle();
            case "so":
                return !squares[y][x].isSouthObstacle();
            default:
                return !squares[y][x].isWestObstacle();
        }
    }

    public void addCleaningDeviceId(Coordinate position, UUID cleaningDeviceId) {
        int x_coordinate = position.getX();
        int y_coordinate = Coordinate.flipYCoordinate(position.getY(), height);
        squares[y_coordinate][x_coordinate].addCleaningDeviceId(cleaningDeviceId);
    }

    public void removeCleaningDeviceId(Coordinate position) {
        int x_coordinate = position.getX();
        int y_coordinate = Coordinate.flipYCoordinate(position.getY(), height);
        squares[y_coordinate][x_coordinate].removeCleaningDeviceId();
    }

    public Boolean isSquareOccupied(Coordinate position) {
        int x_coordinate = position.getX();
        int y_coordinate = Coordinate.flipYCoordinate(position.getY(), height);
        return squares[y_coordinate][x_coordinate].isOccupied();
    }

    public Connection getSquareConnection(Coordinate position) {
        int x_coordinate = position.getX();
        int y_coordinate = Coordinate.flipYCoordinate(position.getY(), height);
        return squares[y_coordinate][x_coordinate].getConnection();
    }
}
