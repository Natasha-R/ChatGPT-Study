package thkoeln.st.st2praktikum.exercise.space.domain;


import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.GeometricPosition;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderType;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;

import javax.persistence.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Entity
public class Space {

    @Id
    private final UUID spaceId = UUID.randomUUID();
    @Transient
    private final List<Obstacle> obstacles = new ArrayList<>();
    @ElementCollection(fetch = FetchType.EAGER)
    private final List<Coordinate> coordinates = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private final Map<UUID, Connection> connections = new HashMap<>();
    private Integer width;
    private Integer height;


    public Space(Integer height, Integer width) {
        this.width = width;
        this.height = height;
        this.fillPoints();
    }

    protected Space() {
    }

    public void addObstacle(Obstacle obstacle) {
        this.obstacles.add(obstacle);
    }

    public void addConnection(Connection connection) throws RuntimeException {
        this.connections.put(connection.getConnectionId(), connection);
    }

    public Boolean checkHorizontalObstacles(Coordinate cleaningMachineCoordinate, OrderType orderType) throws IllegalStateException {
        switch (orderType) {
            case NORTH:
                if ((cleaningMachineCoordinate.getY() + 1) == this.height) {
                    return false;
                }
                break;
            case SOUTH:
                if ((cleaningMachineCoordinate.getY() - 1) < 0) {
                    return false;
                }
                break;
        }
        for (Obstacle obstacle : this.obstacles) {
            if (obstacle.getGeometricPosition().equals(GeometricPosition.HORIZONTAL)) {
                switch (orderType) {
                    case NORTH:
                        if ((cleaningMachineCoordinate.getY() + 1) == obstacle.getStart().getY()) {
                            return cleaningMachineCoordinate.getX() < obstacle.getStart().getX() || cleaningMachineCoordinate.getX() >= obstacle.getEnd().getX();
                        } else {
                            return true;
                        }
                    case SOUTH:
                        if ((cleaningMachineCoordinate.getY() - 1) < 0) {
                            return false;
                        }
                        if (cleaningMachineCoordinate.getY().equals(obstacle.getStart().getY())) {
                            return cleaningMachineCoordinate.getX() < obstacle.getStart().getX() || cleaningMachineCoordinate.getX() >= obstacle.getEnd().getX();
                        } else {
                            return true;
                        }
                }
            }
        }
        return true;
    }

    public Boolean checkVerticalObstacles(Coordinate cleaningMachineCoordinate, OrderType orderType) throws IllegalStateException {
        switch (orderType) {
            case EAST:
                if ((cleaningMachineCoordinate.getX() + 1) == this.width) {
                    return false;
                }
                break;
            case WEST:
                if ((cleaningMachineCoordinate.getX() - 1) < 0) {
                    return false;
                }
                break;
        }
        for (Obstacle obstacle : this.obstacles) {
            if (obstacle.getGeometricPosition().equals(GeometricPosition.VERTICAL)) {
                switch (orderType) {
                    case EAST:
                        if ((cleaningMachineCoordinate.getX() + 1) == obstacle.getStart().getX()) {
                            return cleaningMachineCoordinate.getY() < obstacle.getStart().getY() || cleaningMachineCoordinate.getY() >= obstacle.getEnd().getY();
                        } else {
                            return true;
                        }
                    case WEST:
                        if (cleaningMachineCoordinate.getX().equals(obstacle.getStart().getX())) {
                            return cleaningMachineCoordinate.getY() < obstacle.getStart().getY() || cleaningMachineCoordinate.getY() >= obstacle.getEnd().getY();
                        } else {
                            return true;
                        }
                }
            }
        }
        return true;
    }

    public Boolean checkCleaningMachinesInDirection(Coordinate coordinate, CleaningDeviceRepository cleaningDeviceRepository, OrderType orderType, UUID cleaningmschineId) throws IllegalStateException {

        AtomicReference<Boolean> result = new AtomicReference<>(false);

        cleaningDeviceRepository.findAll().forEach(cleaningMachineEntry -> {
            if (cleaningMachineEntry.getSpawned() && !cleaningMachineEntry.getCleaningMachineId().equals(cleaningmschineId)) {
                switch (orderType) {
                    case NORTH:
                        result.set(cleaningMachineEntry.getSpaceId().equals(this.spaceId) && cleaningMachineEntry.getCoordinate().equals(new Coordinate(coordinate.getX(), (coordinate.getY()) + 1)));
                        break;
                    case EAST:
                        result.set(cleaningMachineEntry.getSpaceId().equals(this.spaceId) && cleaningMachineEntry.getCoordinate().equals(new Coordinate((coordinate.getX() + 1), coordinate.getY())));
                        break;
                    case SOUTH:
                        result.set(cleaningMachineEntry.getSpaceId().equals(this.spaceId) && cleaningMachineEntry.getCoordinate().equals(new Coordinate(coordinate.getX(), (coordinate.getY()) - 1)));
                        break;
                    case WEST:
                        result.set(cleaningMachineEntry.getSpaceId().equals(this.spaceId) && cleaningMachineEntry.getCoordinate().equals(new Coordinate((coordinate.getX() - 1), coordinate.getY())));
                        break;
                }
            }
        });
        return result.get();
    }

    private void fillPoints() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                coordinates.add(new Coordinate(x, y));
            }
        }
    }
}
