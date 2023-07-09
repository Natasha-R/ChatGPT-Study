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
        obstacles.add(filterString(obstacleString));
    }

    private Obstacle filterString(String obstacleString) {
        Pattern searchForInt = Pattern.compile("[+-]?[0-9]+");
        Matcher intExtracted = searchForInt.matcher(obstacleString);
        int[] positions = new int[4];
        int i = 0;
        while (intExtracted.find()) {
            positions[i] = Integer.parseInt(obstacleString.substring(intExtracted.start(), intExtracted.end()));
            i++;
        }
        if (positions[0] == positions[2]) {
            return new Obstacle('v', positions[1], positions[3], positions[2]);
        } else {
            return new Obstacle('h', positions[0], positions[2], positions[1]);
        }
    }

    public UUID getSpaceShipDeskId() {
        return spaceShipDeskId;
    }

    public MaintenanceDroid moveMaintenanceDroid(MaintenanceDroid maintenanceDroid, String command, String step, List<MaintenanceDroid> maintenanceDroids) {
        for (int i = 0; i < Integer.parseInt(step); i++) {
            switch (command) {
                case "no":
                    if (moveNo(maintenanceDroid, maintenanceDroids)) {
                        maintenanceDroid.setMaintenceDroidsYPosition(maintenanceDroid.getMaintenceDroidsYPosition() + 1);
                    } else return maintenanceDroid;break;
                case "so":
                    if (moveSo(maintenanceDroid, maintenanceDroids)) {
                        maintenanceDroid.setMaintenceDroidsYPosition(maintenanceDroid.getMaintenceDroidsYPosition() - 1);
                    } else return maintenanceDroid;break;
                case "ea":
                    if (moveEa(maintenanceDroid, maintenanceDroids)) {
                        maintenanceDroid.setMaintenceDroidsXPosition(maintenanceDroid.getMaintenceDroidsXPosition() + 1);
                    } else return maintenanceDroid;break;
                case "we":
                    if (moveWe(maintenanceDroid, maintenanceDroids)) {
                        maintenanceDroid.setMaintenceDroidsXPosition(maintenanceDroid.getMaintenceDroidsXPosition() - 1);
                    } else return maintenanceDroid;break;
                default:
                    return maintenanceDroid;
            }
        }
        return maintenanceDroid;
    }

    private boolean moveNo(MaintenanceDroid maintenanceDroid, List<MaintenanceDroid> maintenanceDroids) {
        if (height == maintenanceDroid.getMaintenceDroidsYPosition()) {
            return false;
        }
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getDirection() == 'h') {
                if (obstacle.getPosition() == maintenanceDroid.getMaintenceDroidsYPosition() + 1) {
                    if (obstacle.getStartPos() <= maintenanceDroid.getMaintenceDroidsXPosition() && obstacle.getEndPos() >= maintenanceDroid.getMaintenceDroidsXPosition() + 1) {
                        return false;
                    }
                }
            }
        }
        return checkPositionForDroid(maintenanceDroids, maintenanceDroid.getMaintenceDroidsXPosition(), maintenanceDroid.getMaintenceDroidsYPosition() + 1);
    }

    private boolean moveSo(MaintenanceDroid maintenanceDroid, List<MaintenanceDroid> maintenanceDroids) {
        if (maintenanceDroid.getMaintenceDroidsYPosition() == 0) {
            return false;
        }
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getDirection() == 'h') {
                if (obstacle.getPosition() == maintenanceDroid.getMaintenceDroidsYPosition()) {
                    if (obstacle.getStartPos() <= maintenanceDroid.getMaintenceDroidsXPosition() && obstacle.getEndPos() >= maintenanceDroid.getMaintenceDroidsXPosition() + 1) {
                        return false;
                    }
                }
            }
        }
        return checkPositionForDroid(maintenanceDroids, maintenanceDroid.getMaintenceDroidsXPosition(), maintenanceDroid.getMaintenceDroidsYPosition() - 1);
    }


    private boolean moveEa(MaintenanceDroid maintenanceDroid, List<MaintenanceDroid> maintenanceDroids) {
        if (width == maintenanceDroid.getMaintenceDroidsXPosition()) {
            return false;
        }
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getDirection() == 'v') {
                if (obstacle.getPosition() == maintenanceDroid.getMaintenceDroidsXPosition() + 1) {
                    if (obstacle.getStartPos() <= maintenanceDroid.getMaintenceDroidsYPosition() && obstacle.getEndPos() >= maintenanceDroid.getMaintenceDroidsYPosition() + 1) {
                        return false;
                    }
                }
            }
        }
        return checkPositionForDroid(maintenanceDroids, maintenanceDroid.getMaintenceDroidsXPosition() + 1, maintenanceDroid.getMaintenceDroidsYPosition());
    }

    private boolean moveWe(MaintenanceDroid maintenanceDroid, List<MaintenanceDroid> maintenanceDroids) {
        if (0 == maintenanceDroid.getMaintenceDroidsXPosition()) {
            return false;
        }
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getDirection() == 'v') {
                if (obstacle.getPosition() == maintenanceDroid.getMaintenceDroidsXPosition()) {
                    if (obstacle.getStartPos() <= maintenanceDroid.getMaintenceDroidsYPosition() && obstacle.getEndPos() >= maintenanceDroid.getMaintenceDroidsYPosition() + 1) {
                        return false;
                    }
                }
            }
        }
        return checkPositionForDroid(maintenanceDroids, maintenanceDroid.getMaintenceDroidsXPosition() - 1, maintenanceDroid.getMaintenceDroidsYPosition());
    }

    public MaintenanceDroid transport(MaintenanceDroid currentMaintenanceDroid, String step, List<MaintenanceDroid> maintenanceDroids) {
        for (Connection connection : connections) {
            if (UUID.fromString(step).equals(connection.getDestinationSpaceShipDeckId())) {
                if (currentMaintenanceDroid.getMaintenceDroidsXPosition() == connection.getxSourceCoordinate() && currentMaintenanceDroid.getMaintenceDroidsYPosition() == connection.getySourceCoordinate()) {
                    if (checkPositionForDroid(maintenanceDroids, connection.getxDestinationCoordinate(), connection.getyDestinationCoordinate())) {
                        currentMaintenanceDroid.setMaintenceDroidsXPosition(connection.getxDestinationCoordinate());
                        currentMaintenanceDroid.setMaintenceDroidsYPosition(connection.getyDestinationCoordinate());
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
                if (maintenanceDroid.getMaintenceDroidsXPosition() == nextPositionX && maintenanceDroid.getMaintenceDroidsYPosition() == nextPositionY) {
                    return false;
                }
            }
        }
        return true;
    }
}
