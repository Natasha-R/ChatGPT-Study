package thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Direction;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroidRepository;

import javax.persistence.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Entity
public class SpaceShipDeck {
    private Integer height;
    private Integer width;
    @Id
    private final UUID spaceShipDeckID = UUID.randomUUID();
    @Transient
    private final List<Obstacle> allObstacles = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private final Map<UUID, Square> squares = new HashMap<>();

    protected SpaceShipDeck() {
    }

    public SpaceShipDeck(Integer height, Integer width) {
        this.height = height;
        this.width = width;
        fillSquareHashMap();
    }

    private void fillSquareHashMap() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Square tmpSquare = new Square(new Coordinate(x, y));
                squares.put(tmpSquare.getSquareID(), tmpSquare);
            }
        }
    }

    public Square findSquareAt(Coordinate at) throws SquareNotFoundException {
        AtomicReference<Square> resultSquare = new AtomicReference<>();
        squares.forEach((uuid, square) -> {
            if (square.getCoordinates().getX().equals(at.getX()) && square.getCoordinates().getY().equals(at.getY())) {
                resultSquare.set(square);
            }
        });
        if (resultSquare.get() != null) {
            return resultSquare.get();
        } else throw new SquareNotFoundException("Koordinaten Fehlerhaft(" + at.getX() + "," + at.getY() + ")");
    }

    public Square getNextSquareinDirection(Direction direction, UUID squareID) throws IllegalArgumentException, SquareNotFoundException {
        Coordinate nextSquareCoordinate;
        Coordinate tmpCoordinate = squares.get(squareID).getCoordinates();
        switch (direction) {
            case NO:
                nextSquareCoordinate = new Coordinate(tmpCoordinate.getX(), tmpCoordinate.getY() + 1);
                return findSquareAt(nextSquareCoordinate);
            case EA:
                nextSquareCoordinate = new Coordinate(tmpCoordinate.getX() + 1, tmpCoordinate.getY());
                return findSquareAt(nextSquareCoordinate);
            case SO:
                nextSquareCoordinate = new Coordinate(tmpCoordinate.getX(), tmpCoordinate.getY() - 1);
                return findSquareAt(nextSquareCoordinate);
            case WE:
                nextSquareCoordinate = new Coordinate(tmpCoordinate.getX() - 1, tmpCoordinate.getY());
                return findSquareAt(nextSquareCoordinate);
            default:
                throw new IllegalArgumentException(direction.toString());
        }
    }

    public Boolean contains(Obstacle searchObstacle) {
        AtomicReference<Boolean> result = new AtomicReference<>(false);
        this.allObstacles.forEach(obstacle -> {
            if (obstacle.equals(searchObstacle)) {
                result.set(true);
            }
        });
        return result.get();
    }

    public Boolean isTransportOccupied(UUID squareID, UUID maintenancesDroidID, MaintenanceDroidRepository maintenanceDroids) {
        AtomicReference<Boolean> result = new AtomicReference<>();
        result.set(false);
        maintenanceDroids.findAll().forEach((entry) -> {
            if (entry.getMaintenanceDroidID() != maintenancesDroidID && entry.getSpaceShipDeckId() != null) {
                if (entry.getSquare().getSquareID() == squareID) {
                    result.set(true);
                }
            }
        });
        return result.get();
    }

    public Boolean isSquareOccupied(Direction direction, UUID squareID, UUID maintenancesDroidID, MaintenanceDroidRepository maintenanceDroids) throws SquareNotFoundException {
        AtomicReference<Boolean> result = new AtomicReference<>();
        result.set(false);
        UUID nextSquareID = this.getNextSquareinDirection(direction, squareID).getSquareID();
        maintenanceDroids.findAll().forEach(maintenanceDroid -> {
            if (maintenanceDroid.getMaintenanceDroidID() != maintenancesDroidID) {
                if (maintenanceDroid.getSquare() != null) {
                    if (maintenanceDroid.getSquare().getSquareID().equals(nextSquareID)) result.set(true);
                }
            }
        });
        return result.get();
    }
}
