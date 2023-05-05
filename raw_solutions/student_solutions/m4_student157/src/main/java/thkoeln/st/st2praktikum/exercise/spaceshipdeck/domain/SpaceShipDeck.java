package thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain;

import lombok.Getter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderType;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Entity
public class SpaceShipDeck {

    @Id
    public final UUID id;
    private final Integer height;
    private final Integer width;
    @ElementCollection(targetClass = Wall.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Wall> walls = new ArrayList<>();

    @OneToMany( cascade = {CascadeType.ALL} )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Connection> connections = new ArrayList<>();

    @OneToMany( cascade = {CascadeType.ALL} )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<MaintenanceDroid> maintenanceDroids = new ArrayList<>();

    public SpaceShipDeck(Integer height, Integer width) {
        this.height = height;
        this.width = width;
        this.id = UUID.randomUUID();
    }

    public SpaceShipDeck() {
        id = UUID.randomUUID();
        height = null;
        width = null;
    }

    public void addWall(String wallString) {
        ArrayList<Wall> borders = stringToBorder(wallString);
        this.walls.addAll(borders);
    }

    public void addWall(Wall wall) {
        this.walls.add(wall);
    }

    private ArrayList<Wall> stringToBorder(String wallString) {
        ArrayList<Wall> borders = new ArrayList<>();
        final String regex = "\\(([0-9]*),([0-9]*)\\)-\\(([0-9]*),([0-9]*)\\)";

        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(wallString);

        int startX = 0;
        int startY = 0;
        int endX = 0;
        int endY = 0;

        while (matcher.find()) {
            startX = Integer.parseInt(matcher.group(1));
            startY = Integer.parseInt(matcher.group(2));
            endX = Integer.parseInt(matcher.group(3));
            endY = Integer.parseInt(matcher.group(4));
        }

        if(startX == endX) {
            for (int i = startY; i < endY; i++) {
                borders.add(new Wall(new Vector2D(startX - 1, i), new Vector2D(startX, i)));
            }
        } else {
            for (int i = startX; i < endX; i++) {
                borders.add(new Wall(new Vector2D(i, startY - 1), new Vector2D(i, startY)));
            }
        }

        return borders;
    }

    public void addConnection(Connection connection) {
        connections.add(connection);
    }

    public void addMaintenanceDroid(MaintenanceDroid maintenanceDroid) {
        maintenanceDroids.add(maintenanceDroid);
    }

    public boolean canPlaceDroid() {
        for (MaintenanceDroid maintenanceDroid : maintenanceDroids) {
            if (maintenanceDroid.isAtZeroZero()) {
                return false;
            }
        }
        return true;
    }

    public MaintenanceDroid moveDroid(UUID maintenanceDroidUUID, Order command) {
        int moves = command.getNumberOfSteps();
        OrderType direction = command.getOrderType();
        MaintenanceDroid maintenanceDroid = this.getDroidByUUID(maintenanceDroidUUID);

        for (int i = 0; i < moves; i++) {
            assert maintenanceDroid != null;
            if(this.isPossibleMove(maintenanceDroid.getCoordinates(), direction)) {
                maintenanceDroid.moveOne(direction);
            }
        }

        return maintenanceDroid;
    }

    private MaintenanceDroid getDroidByUUID(UUID uuid) {
        for (MaintenanceDroid maintenanceDroid :
                this.maintenanceDroids) {
            if(maintenanceDroid.getUuid().equals(uuid)) {
                return maintenanceDroid;
            }
        }
        return null;
    }

    private boolean isPossibleMove(Vector2D coordinates, OrderType direction) {
        Vector2D end = null;
        switch (direction) {
            case EAST:
                end = new Vector2D(coordinates.getX() + 1, coordinates.getY());
                break;
            case WEST:
                if(coordinates.getX() - 1 < 0) return false;
                end = new Vector2D(coordinates.getX() - 1, coordinates.getY());
                break;
            case NORTH:
                end = new Vector2D(coordinates.getX(), coordinates.getY() + 1);
                break;
            case SOUTH:
                if(coordinates.getY() - 1 < 0) return false;
                end = new Vector2D(coordinates.getX(), coordinates.getY() - 1);
                break;
        }

        Vector2D finalEnd = end;
        boolean borderInTheWay = this.walls.stream()
                .anyMatch(wall -> wall.isBlocking(coordinates, finalEnd));

        return !borderInTheWay && end.getX() < this.height && end.getX() >= 0 && end.getY() < this.width && end.getY() >= 0;
    }

    public Connection transportDroid(MaintenanceDroid maintenanceDroid) throws Exception {
        if(this.droidIsOnTransportField(maintenanceDroid)) {
            this.maintenanceDroids.remove(maintenanceDroid);
            return this.getConnectionByPosition(maintenanceDroid.getCoordinates());
        }

        throw new Exception("Droid is not on a Connection Field");
    }

    private Connection getConnectionByPosition(Vector2D coordinates) throws Exception {
        for (Connection connection: this.connections) {
            if(connection.getSourceCoordinate().equals(coordinates)) {
                return connection;
            }
        }
        throw new Exception("Connection not found");
    }

    private boolean droidIsOnTransportField(MaintenanceDroid maintenanceDroid) {
        for (Connection connection: this.connections) {
            if(connection.getSourceCoordinate().equals(maintenanceDroid.getCoordinates())) {
                return true;
            }
        }

        return false;
    }

    public boolean addDroid(MaintenanceDroid maintenanceDroid, Vector2D coordinates) {
        //TODO: Check placed Droids
        maintenanceDroid.setCoordinates(coordinates);
        maintenanceDroids.add(maintenanceDroid);
        return true;
    }
}
