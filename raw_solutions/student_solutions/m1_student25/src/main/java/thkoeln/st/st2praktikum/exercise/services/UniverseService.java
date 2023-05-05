package thkoeln.st.st2praktikum.exercise.services;


import thkoeln.st.st2praktikum.exercise.command.CommandInterface;
import thkoeln.st.st2praktikum.exercise.coordinate.Coordinate;
import thkoeln.st.st2praktikum.exercise.coordinate.CoordinateInterface;
import thkoeln.st.st2praktikum.exercise.exception.MaintenanceDroidPositionIsNull;
import thkoeln.st.st2praktikum.exercise.exception.SquareNotFoundException;
import thkoeln.st.st2praktikum.exercise.maintenanceDroid.MaintenanceDroidPosition;
import thkoeln.st.st2praktikum.exercise.obstacle.Obstacle;
import thkoeln.st.st2praktikum.exercise.obstacle.ObstacleInterface;
import thkoeln.st.st2praktikum.exercise.spaceShipDeck.Square;
import thkoeln.st.st2praktikum.exercise.maintenanceDroid.MaintenanceDroidInterface;
import thkoeln.st.st2praktikum.exercise.spaceShipDeck.SpaceShipDeck;
import thkoeln.st.st2praktikum.exercise.universe.UniverseInterface;

import java.util.UUID;

public interface UniverseService {
    static Boolean isStepCommand(CommandInterface command, MaintenanceDroidInterface maintenanceDroid, UniverseInterface universe) throws MaintenanceDroidPositionIsNull, SquareNotFoundException, IllegalArgumentException {
        for (int i = 0; i < command.getSteps(); i++) {
            SpaceShipDeck tmpSpaceShipDeck = universe.getSpaceShipDeckHashMap().get(universe.getMaintenanceDroidSpaceShipDeckId(maintenanceDroid.getMaintenanceDroidID()));
            CoordinateInterface coordinateMaintenanceDroid = StringService.translateToCoordinate(universe.getCoordinates(maintenanceDroid.getMaintenanceDroidID()));
            ObstacleInterface tmpObstacle;
            switch (command.getDirection()) {
                case NO:
                    if (coordinateMaintenanceDroid.getY() + 1 == tmpSpaceShipDeck.getHeight()) break;
                    tmpObstacle = new Obstacle(new Coordinate(coordinateMaintenanceDroid.getX(), coordinateMaintenanceDroid.getY() + 1), new Coordinate(coordinateMaintenanceDroid.getX() + 1, coordinateMaintenanceDroid.getY() + 1));
                    if (!ObstacleService.contains(tmpSpaceShipDeck.getAllObstacles(), tmpObstacle) || universe.isSquareOccupied(command.getDirection(),maintenanceDroid.getMaintenanceDroidPosition().getSquareID(),maintenanceDroid.getMaintenanceDroidID(),tmpSpaceShipDeck)) {
                        universe.getMaintenanceDroidHashMap().get(maintenanceDroid.getMaintenanceDroidID()).getMaintenanceDroidPosition().setSquareID(tmpSpaceShipDeck.getNextSquareUUIDinDirection(command.getDirection(),maintenanceDroid.getMaintenanceDroidPosition().getSquareID()));
                    }
                    break;
                case EA:
                    if (coordinateMaintenanceDroid.getX() + 1 == tmpSpaceShipDeck.getWidth()) break;
                    tmpObstacle = new Obstacle(new Coordinate(coordinateMaintenanceDroid.getX() + 1, coordinateMaintenanceDroid.getY()), new Coordinate(coordinateMaintenanceDroid.getX() + 1, coordinateMaintenanceDroid.getY() + 1));
                    if (!ObstacleService.contains(tmpSpaceShipDeck.getAllObstacles(), tmpObstacle) || universe.isSquareOccupied(command.getDirection(),maintenanceDroid.getMaintenanceDroidPosition().getSquareID(),maintenanceDroid.getMaintenanceDroidID(),tmpSpaceShipDeck)) {
                        universe.getMaintenanceDroidHashMap().get(maintenanceDroid.getMaintenanceDroidID()).getMaintenanceDroidPosition().setSquareID(tmpSpaceShipDeck.getNextSquareUUIDinDirection(command.getDirection(),maintenanceDroid.getMaintenanceDroidPosition().getSquareID()));
                    }
                    break;
                case SO:
                    if (coordinateMaintenanceDroid.getY() - 1 == -1) break;
                    tmpObstacle = new Obstacle(new Coordinate(coordinateMaintenanceDroid.getX(), coordinateMaintenanceDroid.getY()), new Coordinate(coordinateMaintenanceDroid.getX() + 1, coordinateMaintenanceDroid.getY()));
                    if (!ObstacleService.contains(tmpSpaceShipDeck.getAllObstacles(), tmpObstacle) || universe.isSquareOccupied(command.getDirection(),maintenanceDroid.getMaintenanceDroidPosition().getSquareID(),maintenanceDroid.getMaintenanceDroidID(),tmpSpaceShipDeck)) {
                        universe.getMaintenanceDroidHashMap().get(maintenanceDroid.getMaintenanceDroidID()).getMaintenanceDroidPosition().setSquareID(tmpSpaceShipDeck.getNextSquareUUIDinDirection(command.getDirection(),maintenanceDroid.getMaintenanceDroidPosition().getSquareID()));
                    }
                    break;
                case WE:
                    if (coordinateMaintenanceDroid.getX() - 1 == -1) break;
                    tmpObstacle = new Obstacle(new Coordinate(coordinateMaintenanceDroid.getX(), coordinateMaintenanceDroid.getY()), new Coordinate(coordinateMaintenanceDroid.getX(), coordinateMaintenanceDroid.getY() + 1));
                    if (!ObstacleService.contains(tmpSpaceShipDeck.getAllObstacles(), tmpObstacle) || universe.isSquareOccupied(command.getDirection(),maintenanceDroid.getMaintenanceDroidPosition().getSquareID(),maintenanceDroid.getMaintenanceDroidID(),tmpSpaceShipDeck)) {
                        universe.getMaintenanceDroidHashMap().get(maintenanceDroid.getMaintenanceDroidID()).getMaintenanceDroidPosition().setSquareID(tmpSpaceShipDeck.getNextSquareUUIDinDirection(command.getDirection(),maintenanceDroid.getMaintenanceDroidPosition().getSquareID()));
                    }
                    break;
                default: throw new IllegalArgumentException(command.getDirection().toString());
            }
        }
        return true;
    }
    static Boolean isUUIDCommand(CommandInterface command, MaintenanceDroidInterface maintenanceDroid, UniverseInterface universe) throws MaintenanceDroidPositionIsNull, SquareNotFoundException, IllegalArgumentException, IllegalStateException {
        switch (command.getDirection()){
            case TR:
                Square tmpSquare = universe.getSpaceShipDeckHashMap().get(maintenanceDroid.getMaintenanceDroidPosition().getSpaceShipDeckID()).getSquareHashMap().get(maintenanceDroid.getMaintenanceDroidPosition().getSquareID());
                if(tmpSquare.hasConnection()){
                    if (!universe.isTransportOccupied(universe.getSpaceShipDeckHashMap().get(command.getSpaceShipDeckUUUID()).findSquareAt(tmpSquare.getConnection().getDestinationCoordinate()).getSquareID(), maintenanceDroid.getMaintenanceDroidID())){
                        universe.getMaintenanceDroidHashMap().get(maintenanceDroid.getMaintenanceDroidID()).setMaintenanceDroidPosition(new MaintenanceDroidPosition(command.getSpaceShipDeckUUUID(),universe.getSpaceShipDeckHashMap().get(command.getSpaceShipDeckUUUID()).findSquareAt(tmpSquare.getConnection().getDestinationCoordinate()).getSquareID()));
                        return true;
                    }
                }
                break;
            case EN:
                UUID tmpSquareID = universe.getSpaceShipDeckHashMap().get(command.getSpaceShipDeckUUUID()).findSquareAt(new Coordinate(0,0)).getSquareID();
                if(!universe.isTransportOccupied(tmpSquareID, maintenanceDroid.getMaintenanceDroidID())){
                    universe.getMaintenanceDroidHashMap().get(maintenanceDroid.getMaintenanceDroidID()).setMaintenanceDroidPosition(new MaintenanceDroidPosition(command.getSpaceShipDeckUUUID(), tmpSquareID));
                    return true;
                }
                break;
            default: throw new IllegalArgumentException(command.getDirection().toString());

        }
        throw new IllegalStateException("isUUIDCommand Failure");
    }




}
