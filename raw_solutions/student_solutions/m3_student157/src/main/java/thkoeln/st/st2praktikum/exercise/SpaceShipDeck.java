package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
@Getter
@Setter
public class SpaceShipDeck {

    @Id
    private final UUID uuid;
    private final Integer height;
    private final Integer width;
    @ElementCollection(targetClass = Wall.class, fetch = FetchType.EAGER)
    private List<Wall> borders = new ArrayList<>();
    @ElementCollection(targetClass = Connection.class, fetch = FetchType.EAGER)
    private List<Connection> connections = new ArrayList<>();
    @OneToMany
    private List<MaintenanceDroid> maintenanceDroids = new ArrayList<>();

    public SpaceShipDeck(Integer height, Integer width) {
        this.height = height;
        this.width = width;
        this.uuid = UUID.randomUUID();
    }

    public SpaceShipDeck() {
        uuid = UUID.randomUUID();
        height = null;
        width = null;
    }

    public void addWall(String wallString) {
        ArrayList<Wall> borders = stringToBorder(wallString);
        this.borders.addAll(borders);
    }

    public void addWall(Wall wall) {
        if(wall.getEnd().getX() > height || wall.getEnd().getY() > width || wall.getStart().getX() > height || wall.getStart().getY() > width) {
            throw new RuntimeException();
        }
        this.borders.add(wall);
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
        if(connection.getDestinationCoordinate().getX() > height || connection.getDestinationCoordinate().getY() > width || connection.getSourceCoordinate().getX() > height || connection.getSourceCoordinate().getY() > width) {
            throw new RuntimeException();
        }
        this.connections.add(connection);
    }

    public boolean canPlaceDroid() {
        for (MaintenanceDroid maintenanceDroid : maintenanceDroids) {
            if (maintenanceDroid.isAtZeroZero()) {
                return false;
            }
        }
        return true;
    }

    public boolean addDroid(MaintenanceDroid maintenanceDroid) {
        return this.addDroid(maintenanceDroid, new Vector2D(0, 0));
    }

    public boolean addDroid(MaintenanceDroid maintenanceDroid, Vector2D coordinates) {
        //TODO: Check placed Droids
        maintenanceDroid.setCoordinates(coordinates);
        maintenanceDroids.add(maintenanceDroid);
        return true;
    }

    public boolean hasDroidWithId(UUID maintenanceDroidId) {
        for (MaintenanceDroid maintenanceDroid : this.maintenanceDroids) {
            if(maintenanceDroid.getUuid().equals(maintenanceDroidId)) {
                return true;
            }
        }
        return false;
    }

    public boolean moveDroid(UUID maintenanceDroidUUID, Order command) {
        int moves = command.getNumberOfSteps();
        OrderType direction = command.getOrderType();
        MaintenanceDroid maintenanceDroid = this.getDroidByUUID(maintenanceDroidUUID);

        for (int i = 0; i < moves; i++) {
            assert maintenanceDroid != null;
            if(this.isPossibleMove(maintenanceDroid.getCoordinates(), direction)) {
                maintenanceDroid.moveOne(direction);
            }
        }

        return true;
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
        boolean borderInTheWay = this.borders.stream()
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
}
