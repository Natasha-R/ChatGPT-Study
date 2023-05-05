package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpaceShipDeck {

    @ElementCollection(targetClass = Obstacle.class, fetch = FetchType.EAGER)
    private List<Obstacle> obstacles = new ArrayList<>();

    @Id
    private UUID spaceShipDeskId;

    private Integer height;
    private Integer width;

    @OneToMany
    private List<Connection> connections = new ArrayList<>();

    public SpaceShipDeck(Integer height, Integer width, UUID spaceShipDeskId) {
        this.spaceShipDeskId = spaceShipDeskId;
        this.height = height;
        this.width = width;
    }

    public void addObstacle(String obstacleString) {
        obstacles.add(new Obstacle(obstacleString));
    }

    public UUID getSpaceShipDeskId() {
        return spaceShipDeskId;
    }

    public MaintenanceDroid moveMaintenanceDroid(MaintenanceDroid maintenanceDroid, TaskType command, String step, List<MaintenanceDroid> maintenanceDroids) {
        for (int i = 0; i < Integer.parseInt(step); i++) {
            switch (command) {
                case NORTH:
                    if (moveNo(maintenanceDroid, maintenanceDroids)) {
                        maintenanceDroid.setVector2D(new Vector2D(maintenanceDroid.getVector2D().getX(),maintenanceDroid.getVector2D().getY() + 1 ));
                        System.out.println(maintenanceDroid.getVector2D());
                    } else return maintenanceDroid;break;
                case SOUTH:
                    if (moveSo(maintenanceDroid, maintenanceDroids)) {
                        maintenanceDroid.setVector2D(new Vector2D(maintenanceDroid.getVector2D().getX(),maintenanceDroid.getVector2D().getY() - 1 ));
                        System.out.println(maintenanceDroid.getVector2D());
                    } else return maintenanceDroid;break;
                case EAST:
                    if (moveEa(maintenanceDroid, maintenanceDroids)) {
                        maintenanceDroid.setVector2D(new Vector2D(maintenanceDroid.getVector2D().getX() + 1 , maintenanceDroid.getVector2D().getY()));
                        System.out.println(maintenanceDroid.getVector2D());
                    } else return maintenanceDroid;break;
                case WEST:
                    if (moveWe(maintenanceDroid, maintenanceDroids)) {
                        maintenanceDroid.setVector2D(new Vector2D(maintenanceDroid.getVector2D().getX() - 1 , maintenanceDroid.getVector2D().getY()));
                        System.out.println(maintenanceDroid.getVector2D());
                    } else return maintenanceDroid;break;
                default:
                    return maintenanceDroid;
            }
        }
        return maintenanceDroid;
    }

    private boolean moveNo(MaintenanceDroid maintenanceDroid, List<MaintenanceDroid> maintenanceDroids) {
        if (height == maintenanceDroid.getVector2D().getY()) {
            return false;
        }
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getDirection() == 'h') {
                System.out.println(maintenanceDroid.getVector2D().getY() + 1);
                System.out.println(obstacle.getEnd().getY());
                if (obstacle.getStart().getY() == maintenanceDroid.getVector2D().getY() + 1) {
                    System.out.println("sdffdsfsd");
                    if (obstacle.getStart().getX() <= maintenanceDroid.getVector2D().getX() && obstacle.getEnd().getX() >= maintenanceDroid.getVector2D().getX() + 1) {
                        System.out.println("aaasdasd");
                        return false;
                    }
                }
            }
        }
        return checkPositionForDroid(maintenanceDroids, maintenanceDroid.getVector2D().getX(), maintenanceDroid.getVector2D().getY() + 1);
    }

    private boolean moveSo(MaintenanceDroid maintenanceDroid, List<MaintenanceDroid> maintenanceDroids) {
        if (maintenanceDroid.getVector2D().getY() == 0) {
            return false;
        }
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getDirection() == 'h') {
                if (obstacle.getStart().getY()== maintenanceDroid.getVector2D().getY()) {
                    if (obstacle.getStart().getX() <= maintenanceDroid.getVector2D().getX() && obstacle.getEnd().getX() >= maintenanceDroid.getVector2D().getX() + 1) {
                        return false;
                    }
                }
            }
        }
        return checkPositionForDroid(maintenanceDroids, maintenanceDroid.getVector2D().getX(), maintenanceDroid.getVector2D().getY() - 1);
    }


    private boolean moveEa(MaintenanceDroid maintenanceDroid, List<MaintenanceDroid> maintenanceDroids) {
        if (width == maintenanceDroid.getVector2D().getX()) {
            return false;
        }
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getDirection() == 'v') {
                if (obstacle.getStart().getX() == maintenanceDroid.getVector2D().getX() + 1) {
                    if (obstacle.getStart().getY() <= maintenanceDroid.getVector2D().getY() && obstacle.getEnd().getY()  >= maintenanceDroid.getVector2D().getY() + 1) {
                        return false;
                    }
                }
            }
        }
        return checkPositionForDroid(maintenanceDroids, maintenanceDroid.getVector2D().getX() + 1, maintenanceDroid.getVector2D().getY());
    }

    private boolean moveWe(MaintenanceDroid maintenanceDroid, List<MaintenanceDroid> maintenanceDroids) {
        if (0 == maintenanceDroid.getVector2D().getX()) {
            return false;
        }
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getDirection() == 'v') {
                if (obstacle.getStart().getX() == maintenanceDroid.getVector2D().getX()) {
                    if (obstacle.getStart().getY()  <= maintenanceDroid.getVector2D().getY() && obstacle.getEnd().getY()  >= maintenanceDroid.getVector2D().getY() + 1) {
                        return false;
                    }
                }
            }
        }
        return checkPositionForDroid(maintenanceDroids, maintenanceDroid.getVector2D().getX() - 1, maintenanceDroid.getVector2D().getY());
    }

    public MaintenanceDroid transport(MaintenanceDroid currentMaintenanceDroid, String step, List<MaintenanceDroid> maintenanceDroids) {
        for (Connection connection : connections) {
            if (UUID.fromString(step).equals(connection.getDestinationSpaceShipDeckId())) {
                if (currentMaintenanceDroid.getVector2D().getX() == connection.getSourceCoordinate().getX() && currentMaintenanceDroid.getVector2D().getY() == connection.getSourceCoordinate().getY()) {
                    if (checkPositionForDroid(maintenanceDroids, connection.getDestinationCoordinate().getX(), connection.getDestinationCoordinate().getY())) {
                        currentMaintenanceDroid.setVector2D(new Vector2D(connection.getDestinationCoordinate().getX(),connection.getDestinationCoordinate().getY()));
                        currentMaintenanceDroid.setSpaceShipDeckId(connection.getDestinationSpaceShipDeckId());
                        return currentMaintenanceDroid;
                    }
                }
            }
        }

        return currentMaintenanceDroid;
    }


    public void addConnection(Connection connection) {
        this.connections.add(connection);
    }

    public boolean checkPositionForDroid(List<MaintenanceDroid> maintenanceDroids, int nextPositionX, int nextPositionY) {
        for (MaintenanceDroid maintenanceDroid : maintenanceDroids) {
            if (spaceShipDeskId.equals(maintenanceDroid.getSpaceShipDeckId())) {
                if (maintenanceDroid.getVector2D().getX() == nextPositionX && maintenanceDroid.getVector2D().getY() == nextPositionY) {
                    return false;
                }
            }
        }
        return true;
    }


    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }

    public void addObstacleToList(Obstacle obstacle){
        obstacles.add(obstacle);
    }
}
