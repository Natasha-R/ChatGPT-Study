package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.Getter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.CommandType;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Entity
public class Field {

    @Id
    public final UUID id;
    private final Integer height;
    private final Integer width;
    @ElementCollection(targetClass = Barrier.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Barrier> walls = new ArrayList<>();

    @OneToMany( cascade = {CascadeType.ALL} )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Connection> connections = new ArrayList<>();

    @OneToMany( cascade = {CascadeType.ALL} )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<MiningMachine> miningMachines = new ArrayList<>();

    public Field(Integer height, Integer width) {
        this.height = height;
        this.width = width;
        this.id = UUID.randomUUID();
    }

    public Field() {
        id = UUID.randomUUID();
        height = null;
        width = null;
    }

    public void addbarrier(String wallString) {
        ArrayList<Barrier> borders = stringToBorder(wallString);
        this.walls.addAll(borders);
    }

    public void addBarrier(Barrier wall) {
        this.walls.add(wall);
    }

    private ArrayList<Barrier> stringToBorder(String wallString) {
        ArrayList<Barrier> borders = new ArrayList<>();
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
                borders.add(new Barrier(new Point(startX - 1, i), new Point(startX, i)));
            }
        } else {
            for (int i = startX; i < endX; i++) {
                borders.add(new Barrier(new Point(i, startY - 1), new Point(i, startY)));
            }
        }

        return borders;
    }

    public void addConnection(Connection connection) {
        connections.add(connection);
    }

    public void addMaintenanceDroid(MiningMachine maintenanceDroid) {
        miningMachines.add(maintenanceDroid);
    }

    public boolean canPlaceDroid() {
        for (MiningMachine maintenanceDroid : miningMachines) {
            if (maintenanceDroid.isAtZeroZero()) {
                return false;
            }
        }
        return true;
    }

    public MiningMachine moveDroid(UUID maintenanceDroidUUID, Command command) {
        int moves = command.getNumberOfSteps();
        CommandType direction = command.getCommandType();
        MiningMachine maintenanceDroid = this.getDroidByUUID(maintenanceDroidUUID);

        for (int i = 0; i < moves; i++) {
            assert maintenanceDroid != null;
            if(this.isPossibleMove(maintenanceDroid.getCoordinates(), direction)) {
                maintenanceDroid.moveOne(direction);
            }
        }

        return maintenanceDroid;
    }

    private MiningMachine getDroidByUUID(UUID uuid) {
        for (MiningMachine maintenanceDroid :
                this.miningMachines) {
            if(maintenanceDroid.getUuid().equals(uuid)) {
                return maintenanceDroid;
            }
        }
        return null;
    }

    private boolean isPossibleMove(Point coordinates, CommandType direction) {
        Point end = null;
        switch (direction) {
            case EAST:
                end = new Point(coordinates.getX() + 1, coordinates.getY());
                break;
            case WEST:
                if(coordinates.getX() - 1 < 0) return false;
                end = new Point(coordinates.getX() - 1, coordinates.getY());
                break;
            case NORTH:
                end = new Point(coordinates.getX(), coordinates.getY() + 1);
                break;
            case SOUTH:
                if(coordinates.getY() - 1 < 0) return false;
                end = new Point(coordinates.getX(), coordinates.getY() - 1);
                break;
        }

        Point finalEnd = end;
        boolean borderInTheWay = this.walls.stream()
                .anyMatch(wall -> wall.isBlocking(coordinates, finalEnd));

        return !borderInTheWay && end.getX() < this.height && end.getX() >= 0 && end.getY() < this.width && end.getY() >= 0;
    }

    public Connection transportDroid(MiningMachine maintenanceDroid) throws Exception {
        if(this.droidIsOnTransportField(maintenanceDroid)) {
            this.miningMachines.remove(maintenanceDroid);
            return this.getConnectionByPosition(maintenanceDroid.getCoordinates());
        }

        throw new Exception("Droid is not on a Connection Field");
    }

    private Connection getConnectionByPosition(Point coordinates) throws Exception {
        for (Connection connection: this.connections) {
            if(connection.getSourceCoordinate().equals(coordinates)) {
                return connection;
            }
        }
        throw new Exception("Connection not found");
    }

    private boolean droidIsOnTransportField(MiningMachine maintenanceDroid) {
        for (Connection connection: this.connections) {
            if(connection.getSourceCoordinate().equals(maintenanceDroid.getCoordinates())) {
                return true;
            }
        }

        return false;
    }

    public boolean addDroid(MiningMachine maintenanceDroid, Point coordinates) {
        //TODO: Check placed Droids
        maintenanceDroid.setCoordinates(coordinates);
        miningMachines.add(maintenanceDroid);
        return true;
    }
}
