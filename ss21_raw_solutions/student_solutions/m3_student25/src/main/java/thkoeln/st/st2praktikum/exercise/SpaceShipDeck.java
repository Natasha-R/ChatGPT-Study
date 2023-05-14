package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

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
    @OneToMany(cascade = {CascadeType.ALL})
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
}
