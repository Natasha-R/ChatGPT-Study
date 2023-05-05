package thkoeln.st.st2praktikum.exercise;

import org.modelmapper.internal.Pair;

import java.security.InvalidParameterException;
import java.util.UUID;


public class Room {
    private UUID id = UUID.randomUUID();
    private int height;
    private int width;
    private Square[][] square;

    public Room(int height, int width){
    this.height = height;
    this.width = width;
    this.initiateSquares();
    this.addBorders();
    }
    private void initiateSquares(){
        if (this.height > 0 && this.width > 0) {
            this.square = new Square[this.height][this.width];

            for(int y = 0; y < this.height; ++y) {
                for(int x = 0; x < this.width; ++x) {
                    this.square[y][x] = new Square();
                }
            }

        } else {
            throw new IndexOutOfBoundsException("Room height and width need to be > 0");
        }
    }

    private void addBorders() {
        this.addBarrier(String.format("(%d,%d)-(%d,%d)", this.width, 0, this.width, this.height));
        this.addBarrier(String.format("(%d,%d)-(%d,%d)", 0, this.height, this.width, this.height));
        this.addBarrier(String.format("(%d,%d)-(%d,%d)", 0, 0, this.width, 0));
        this.addBarrier(String.format("(%d,%d)-(%d,%d)", 0, 0, 0, this.height));
    }

    public void addBarrier(String barrierString){
        Pair coordinatePair = Coordinate.getCoordinatePair(barrierString);
        this.addBarrier((Coordinate)coordinatePair.getLeft(), (Coordinate)coordinatePair.getRight());
    }

    private void addBarrier(Coordinate first, Coordinate second){
        int yCoordinate;
            if (first.getX() == second.getX()) {
                for(yCoordinate = first.getY(); yCoordinate < second.getY(); ++yCoordinate) {
                    if (first.getX() < this.width && first.getX() > 0) {
                        this.addBarrier(first.getX() - 1, yCoordinate, "ea");
                        this.addBarrier(first.getX(), yCoordinate, "we");
                    } else if (first.getX() == this.width) {
                        this.addBarrier(first.getX() - 1, yCoordinate, "ea");
                    } else {
                        this.addBarrier(first.getX(), yCoordinate, "we");
                    }
                }
            } else {
                for(yCoordinate = first.getX(); yCoordinate < second.getX(); ++yCoordinate) {
                    if (first.getY() < this.height && first.getY() > 0) {
                        this.addBarrier(yCoordinate, first.getY() - 1, "no");
                        this.addBarrier(yCoordinate, first.getY(), "so");
                    } else if (first.getY() == this.height) {
                        this.addBarrier(yCoordinate, first.getY() - 1, "no");
                    } else {
                        this.addBarrier(yCoordinate, first.getY(), "so");
                    }
                }
            }


    }
    private void addBarrier(int x, int y, String barrier) {
        int y_coordinate = Coordinate.flipYCoordinate(y, this.height);
        this.square[y_coordinate][x].addBarrier(barrier);
    }

    public void addConnection(Connection connection) {
        UUID sourceId = connection.getSourceId();
        Coordinate sourceCoordinate = connection.getSourceCoordinate();
        Coordinate destinationCoordinate = connection.getDestinationCoordinate();
        int x_coordinate;
        int y_coordinate;
        if (this.id == sourceId) {
            x_coordinate = sourceCoordinate.getX();
            y_coordinate = Coordinate.flipYCoordinate(sourceCoordinate.getY(), this.height);
        } else {
            x_coordinate = destinationCoordinate.getX();
            y_coordinate = Coordinate.flipYCoordinate(destinationCoordinate.getY(), this.height);
        }

        this.square[y_coordinate][x_coordinate].addConnection(connection);
    }

    public boolean barrierCheck(int x, int y, String barrierString) {
        int y_coordinate = Coordinate.flipYCoordinate(y, this.height);
        switch (barrierString) {
            case "no":
                return !this.square[y_coordinate][x].isNorthBarrier();
            case "ea":
                return !this.square[y_coordinate][x].isEastBarrier();
            case "so":
                return !this.square[y_coordinate][x].isSouthBarrier();
            case "we":
                return !this.square[y_coordinate][x].isWestBarrier();
            default:
                throw new InvalidParameterException("the barrierString is an invalid parameter");
        }
    }
        public void addTidyUpRobotId(Coordinate position, UUID tidyUpRobotId) {
            int x_coordinate = position.getX();
            int y_coordinate = Coordinate.flipYCoordinate(position.getY(), this.height);
            this.square[y_coordinate][x_coordinate].addTidyUpRobotId(tidyUpRobotId);
        }

        public void removeTidyUpRobotId(Coordinate position) {
            int x_coordinate = position.getX();
            int y_coordinate = Coordinate.flipYCoordinate(position.getY(), this.height);
            this.square[y_coordinate][x_coordinate].removeTidyUpRobotId();
        }

        public Boolean isSquareOccupied(Coordinate position) {
            int x_coordinate = position.getX();
            int y_coordinate = Coordinate.flipYCoordinate(position.getY(), this.height);
            return this.square[y_coordinate][x_coordinate].isOccupied();
        }

        public Connection getSquareConnection(Coordinate position) {
            int x_coordinate = position.getX();
            int y_coordinate = Coordinate.flipYCoordinate(position.getY(), this.height);
            return this.square[y_coordinate][x_coordinate].getConnection();
        }


    public UUID getId() {return this.id;}
}
