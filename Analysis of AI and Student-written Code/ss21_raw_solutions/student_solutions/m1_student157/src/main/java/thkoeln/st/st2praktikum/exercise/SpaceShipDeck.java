package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class SpaceShipDeck {

    private final UUID uuid;
    private final Integer height;
    private final Integer width;
    private ArrayList<Border> borders = new ArrayList<>();
    private ArrayList<Connection> connections = new ArrayList<>();
    private ArrayList<Droid> droids = new ArrayList<>();

    public SpaceShipDeck(Integer height, Integer width) {
        this.height = height;
        this.width = width;
        this.uuid = UUID.randomUUID();
    }

    public void addWall(String wallString) {
        ArrayList<Border> borders = stringToBorder(wallString);
        this.borders.addAll(borders);
    }

    private ArrayList<Border> stringToBorder(String wallString) {
        ArrayList<Border> borders = new ArrayList<>();
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
                borders.add(new Border(new Coordinates(startX - 1, i), new Coordinates(startX, i)));
            }
        } else {
            for (int i = startX; i < endX; i++) {
                borders.add(new Border(new Coordinates(i, startY - 1), new Coordinates(i, startY)));
            }
        }

        return borders;
    }

    public void addConnection(Connection connection) {
        this.connections.add(connection);
    }

    public boolean canPlaceDroid() {
        for (Droid droid : droids) {
            if (droid.isAtZeroZero()) {
                return false;
            }
        }
        return true;
    }

    public boolean addDroid(Droid droid) {
        return this.addDroid(droid, new Coordinates(0, 0));
    }

    public boolean addDroid(Droid droid, Coordinates coordinates) {
        //TODO: Check placed Droids
        droid.setCoordinates(coordinates);
        droids.add(droid);
        return true;
    }

    public boolean hasDroidWithId(UUID maintenanceDroidId) {
        for (Droid droid: this.droids) {
            if(droid.getUuid().equals(maintenanceDroidId)) {
                return true;
            }
        }
        return false;
    }

    public boolean moveDroid(UUID maintenanceDroidUUID, Command command) {
        int moves = Integer.parseInt(command.getOption());
        String direction = command.getCommand();
        Droid droid = this.getDroidByUUID(maintenanceDroidUUID);

        for (int i = 0; i < moves; i++) {
            if(this.isPossibleMove(droid.getCoordinates(), direction)) {
                droid.moveOne(direction);
            }
        }

        return true;
    }

    private Droid getDroidByUUID(UUID uuid) {
        for (Droid droid:
             this.droids) {
            if(droid.getUuid().equals(uuid)) {
                return droid;
            }
        }
        return null;
    }

    private boolean isPossibleMove(Coordinates coordinates, String direction) {
        Coordinates end = null;
        switch (direction) {
            case "ea":
                end = new Coordinates(coordinates.getX() + 1, coordinates.getY());
                break;
            case "we":
                end = new Coordinates(coordinates.getX() - 1, coordinates.getY());
                break;
            case "no":
                end = new Coordinates(coordinates.getX(), coordinates.getY() + 1);
                break;
            case "so":
                end = new Coordinates(coordinates.getX(), coordinates.getY() - 1);
                break;
        }

        Border possibleBorder = new Border(coordinates, end);

        return !this.borders.contains(possibleBorder) && end.getX() < this.height && end.getX() >= 0 && end.getY() < this.width && end.getY() >= 0;
    }

    public Connection transportDroid(Droid droid) throws Exception {
        if(this.droidIsOnTransportField(droid)) {
            this.droids.remove(droid);
            return this.getConnectionByPosition(droid.getCoordinates());
        }

        throw new Exception("Droid is not on a Connection Field");
    }

    private Connection getConnectionByPosition(Coordinates coordinates) throws Exception {
        for (Connection connection: this.connections) {
            if(connection.getSourceCoordinate().equals(coordinates)) {
                return connection;
            }
        }
        throw new Exception("Connection not found");
    }

    private boolean droidIsOnTransportField(Droid droid) {
        for (Connection connection: this.connections) {
            if(connection.getSourceCoordinate().equals(droid.getCoordinates())) {
                return true;
            }
        }

        return false;
    }
}
