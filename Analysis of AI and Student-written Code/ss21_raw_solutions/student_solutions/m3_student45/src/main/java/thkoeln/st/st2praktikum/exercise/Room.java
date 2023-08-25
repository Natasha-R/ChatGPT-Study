package thkoeln.st.st2praktikum.exercise;

import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Exception.InvalidCoordinateException;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Room {
    @Id
    @Generated
    private UUID id;
    private int width, height;

    @Transient
    private Square[][] squares;

    public Room(int height, int width) {
        id = UUID.randomUUID();
        this.height = height;
        this.width = width;

        initSquares();
        addBorderWalls();
    }

    private void initSquares() {
        if(height <= 0 || width <= 0)
            throw new IndexOutOfBoundsException("The room has invalid height and width.");

        squares = new Square[height][width];
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                squares[y][x] = new Square();
    }

    private void addBorderWalls() {
        List<Wall> borderWalls = Arrays.asList(
                new Wall(String.format("(%d,%d)-(%d,%d)", 0, 0, 0, height)),            // leftBorder
                new Wall(String.format("(%d,%d)-(%d,%d)", width, 0, width, height)),    // rightBorder
                new Wall(String.format("(%d,%d)-(%d,%d)", 0, 0, width, 0)),             // bottomBorder
                new Wall(String.format("(%d,%d)-(%d,%d)", 0, height, width, height))    // topBorder
        );

        for (Wall borderWall : borderWalls)
            addWall(borderWall);
    }

    public void addWall(Wall wall) {
        if(wall.isOutOfBound(width, height)) {
            throw new InvalidCoordinateException("The wall has one or two coordinates that are out of bound.");
        }

        Coordinate start = wall.getStart();
        Coordinate end = wall.getEnd();

        addWall(start, end);
    }

    private void addWall(Coordinate first, Coordinate second) {
        if (first.getX().equals(second.getX())) {
            for (int y = first.getY(); y < second.getY(); y++) {
                if (first.getX() < width && first.getX() > 0) {
                    addWall(first.getX() - 1, y, "ea");
                    addWall(first.getX(), y, "we");
                }
                else if (first.getX() == width)
                    addWall(first.getX() - 1, y, "ea");
                else
                    addWall(first.getX(), y, "we");
            }
        }
        else {
            for (int x = first.getX(); x < second.getX(); x++) {
                if (first.getY() < height && first.getY() > 0) {
                    addWall(x, first.getY() - 1, "no");
                    addWall(x, first.getY(), "so");
                }
                else if (first.getY() == height)
                    addWall(x, first.getY() - 1, "no");
                else
                    addWall(x, first.getY(), "so");
            }
        }
    }

    private void addWall(int x, int y, String wall) {
        y = Coordinate.getFlippedYCoordinate(y, height);
        squares[y][x].addWall(wall);
    }

    public void addConnection(Connection connection) {
        UUID sourceId = connection.getSourceId();
        Coordinate firstCoordinate = connection.getSourceCoordinate();
        Coordinate secondCoordinate = connection.getDestinationCoordinate();
        Coordinate destinationCoordinate;
        int x, y;

        if(id == sourceId){
            x = firstCoordinate.getX();
            y = Coordinate.getFlippedYCoordinate(firstCoordinate.getY(), height);
        }
        else {
            x = secondCoordinate.getX();
            y = Coordinate.getFlippedYCoordinate(secondCoordinate.getY(), height);
        }

        destinationCoordinate = new Coordinate(x, y);
        if(destinationCoordinate.isOutOfBound(width, height))
            throw new InvalidCoordinateException("The connection has one or two coordinates that are out of bound.");

        squares[y][x].addConnection(connection);
    }

    public boolean hasNoWall(int x, int y, String border) {
        y = Coordinate.getFlippedYCoordinate(y, height);

        switch (border) {
            case "no":
                return !squares[y][x].isNorthWall();
            case "ea":
                return !squares[y][x].isEastWall();
            case "so":
                return !squares[y][x].isSouthWall();
            default:
                return !squares[y][x].isWestWall();
        }
    }

    public void addTidyUpRobotId(Coordinate position, UUID tidyUpRobotId) {
        int x_coordinate = position.getX();
        int y_coordinate = Coordinate.getFlippedYCoordinate(position.getY(), height);
        squares[y_coordinate][x_coordinate].addTidyUpRobotId(tidyUpRobotId);
    }

    public void removeTidyUpRobotId(Coordinate position) {
        int x_coordinate = position.getX();
        int y_coordinate = Coordinate.getFlippedYCoordinate(position.getY(), height);
        squares[y_coordinate][x_coordinate].removeTidyUpRobotId();
    }

    public Boolean isSquareOccupied(Coordinate position) {
        int x_coordinate = position.getX();
        int y_coordinate = Coordinate.getFlippedYCoordinate(position.getY(), height);
        return squares[y_coordinate][x_coordinate].isOccupied();
    }

    public Connection getSquareConnection(Coordinate position) {
        int x_coordinate = position.getX();
        int y_coordinate = Coordinate.getFlippedYCoordinate(position.getY(), height);
        return squares[y_coordinate][x_coordinate].getConnection();
    }

    public void addTidyUpRobotId(UUID tidyUpRobotId, Coordinate destinationCoordinate) {
        int x = destinationCoordinate.getX();
        int y = Coordinate.getFlippedYCoordinate(destinationCoordinate.getY(), height);
        squares[y][x].addTidyUpRobotId(tidyUpRobotId);
    }
}
