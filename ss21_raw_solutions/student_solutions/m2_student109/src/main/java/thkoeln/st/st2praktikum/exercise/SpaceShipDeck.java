package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpaceShipDeck {

    private final ArrayList<Obstacle> obstacles = new ArrayList<>();
    private final UUID spaceShipDeskId;
    private final Integer height;
    private final Integer width;
    private final List<Connection> connections = new ArrayList<>();

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
                        maintenanceDroid.setPosition(new Vector2D(maintenanceDroid.getPosition().getX(),maintenanceDroid.getPosition().getY() + 1 ));
                        System.out.println(maintenanceDroid.getPosition());
                    } else return maintenanceDroid;break;
                case SOUTH:
                    if (moveSo(maintenanceDroid, maintenanceDroids)) {
                        maintenanceDroid.setPosition(new Vector2D(maintenanceDroid.getPosition().getX(),maintenanceDroid.getPosition().getY() - 1 ));
                        System.out.println(maintenanceDroid.getPosition());
                    } else return maintenanceDroid;break;
                case EAST:
                    if (moveEa(maintenanceDroid, maintenanceDroids)) {
                        maintenanceDroid.setPosition(new Vector2D(maintenanceDroid.getPosition().getX() + 1 , maintenanceDroid.getPosition().getY()));
                        System.out.println(maintenanceDroid.getPosition());
                    } else return maintenanceDroid;break;
                case WEST:
                    if (moveWe(maintenanceDroid, maintenanceDroids)) {
                        maintenanceDroid.setPosition(new Vector2D(maintenanceDroid.getPosition().getX() - 1 , maintenanceDroid.getPosition().getY()));
                        System.out.println(maintenanceDroid.getPosition());
                    } else return maintenanceDroid;break;
                default:
                    return maintenanceDroid;
            }
        }
        return maintenanceDroid;
    }

    private boolean moveNo(MaintenanceDroid maintenanceDroid, List<MaintenanceDroid> maintenanceDroids) {
        if (height == maintenanceDroid.getPosition().getY()) {
            return false;
        }
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getDirection() == 'h') {
                System.out.println(maintenanceDroid.getPosition().getY() + 1);
                System.out.println(obstacle.getEnd().getY());
                if (obstacle.getStart().getY() == maintenanceDroid.getPosition().getY() + 1) {
                    System.out.println("sdffdsfsd");
                    if (obstacle.getStart().getX() <= maintenanceDroid.getPosition().getX() && obstacle.getEnd().getX() >= maintenanceDroid.getPosition().getX() + 1) {
                        System.out.println("aaasdasd");
                        return false;
                    }
                }
            }
        }
        return checkPositionForDroid(maintenanceDroids, maintenanceDroid.getPosition().getX(), maintenanceDroid.getPosition().getY() + 1);
    }

    private boolean moveSo(MaintenanceDroid maintenanceDroid, List<MaintenanceDroid> maintenanceDroids) {
        if (maintenanceDroid.getPosition().getY() == 0) {
            return false;
        }
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getDirection() == 'h') {
                if (obstacle.getStart().getY()== maintenanceDroid.getPosition().getY()) {
                    if (obstacle.getStart().getX() <= maintenanceDroid.getPosition().getX() && obstacle.getEnd().getX() >= maintenanceDroid.getPosition().getX() + 1) {
                        return false;
                    }
                }
            }
        }
        return checkPositionForDroid(maintenanceDroids, maintenanceDroid.getPosition().getX(), maintenanceDroid.getPosition().getY() - 1);
    }


    private boolean moveEa(MaintenanceDroid maintenanceDroid, List<MaintenanceDroid> maintenanceDroids) {
        if (width == maintenanceDroid.getPosition().getX()) {
            return false;
        }
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getDirection() == 'v') {
                if (obstacle.getStart().getX() == maintenanceDroid.getPosition().getX() + 1) {
                    if (obstacle.getStart().getY() <= maintenanceDroid.getPosition().getY() && obstacle.getEnd().getY()  >= maintenanceDroid.getPosition().getY() + 1) {
                        return false;
                    }
                }
            }
        }
        return checkPositionForDroid(maintenanceDroids, maintenanceDroid.getPosition().getX() + 1, maintenanceDroid.getPosition().getY());
    }

    private boolean moveWe(MaintenanceDroid maintenanceDroid, List<MaintenanceDroid> maintenanceDroids) {
        if (0 == maintenanceDroid.getPosition().getX()) {
            return false;
        }
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getDirection() == 'v') {
                if (obstacle.getStart().getX() == maintenanceDroid.getPosition().getX()) {
                    if (obstacle.getStart().getY()  <= maintenanceDroid.getPosition().getY() && obstacle.getEnd().getY()  >= maintenanceDroid.getPosition().getY() + 1) {
                        return false;
                    }
                }
            }
        }
        return checkPositionForDroid(maintenanceDroids, maintenanceDroid.getPosition().getX() - 1, maintenanceDroid.getPosition().getY());
    }

    public MaintenanceDroid transport(MaintenanceDroid currentMaintenanceDroid, String step, List<MaintenanceDroid> maintenanceDroids) {
        for (Connection connection : connections) {
            if (UUID.fromString(step).equals(connection.getDestinationSpaceShipDeckId())) {
                if (currentMaintenanceDroid.getPosition().getX() == connection.getSourceCoordinate().getX() && currentMaintenanceDroid.getPosition().getY() == connection.getSourceCoordinate().getY()) {
                    if (checkPositionForDroid(maintenanceDroids, connection.getDestinationCoordinate().getX(), connection.getDestinationCoordinate().getY())) {
                        currentMaintenanceDroid.setPosition(new Vector2D(connection.getDestinationCoordinate().getX(),connection.getDestinationCoordinate().getY()));
                        currentMaintenanceDroid.setSpaceShipDeskId(connection.getDestinationSpaceShipDeckId());
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
            if (spaceShipDeskId.equals(maintenanceDroid.getSpaceShipDeskId())) {
                if (maintenanceDroid.getPosition().getX() == nextPositionX && maintenanceDroid.getPosition().getY() == nextPositionY) {
                    return false;
                }
            }
        }
        return true;
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
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
