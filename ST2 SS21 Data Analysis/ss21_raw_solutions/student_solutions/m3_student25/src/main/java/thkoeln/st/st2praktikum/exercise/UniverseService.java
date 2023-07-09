package thkoeln.st.st2praktikum.exercise;


public interface UniverseService {
    static Boolean isStepCommand(Command command, MaintenanceDroid maintenanceDroid, UniverseInterface universe) throws MaintenanceDroidPositionIsNull, SquareNotFoundException, IllegalArgumentException {
        for (int i = 0; i < command.getSteps(); i++) {
            SpaceShipDeck tmpSpaceShipDeck = universe.getSpaceShipDecks().findById(universe.getMaintenanceDroidSpaceShipDeckId(maintenanceDroid.getMaintenanceDroidID())).get();
            Coordinate coordinateMaintenanceDroid = universe.getCoordinates(maintenanceDroid.getMaintenanceDroidID());
            Obstacle tmpMyObstacle;
            switch (command.getDirection()) {
                case NO:
                    if (coordinateMaintenanceDroid.getY() + 1 == tmpSpaceShipDeck.getHeight()) break;
                    tmpMyObstacle = new Obstacle(new Coordinate(coordinateMaintenanceDroid.getX(), coordinateMaintenanceDroid.getY() + 1), new Coordinate(coordinateMaintenanceDroid.getX() + 1, coordinateMaintenanceDroid.getY() + 1));
                    if (!ObstacleService.contains(tmpSpaceShipDeck.getAllObstacles(), tmpMyObstacle) || universe.isSquareOccupied(command.getDirection(), maintenanceDroid.getSquare().getSquareID(), maintenanceDroid.getMaintenanceDroidID(), tmpSpaceShipDeck)) {
                        universe.getMaintenanceDroids().findById(maintenanceDroid.getMaintenanceDroidID()).get().setSquare(tmpSpaceShipDeck.getNextSquareinDirection(command.getDirection(), maintenanceDroid.getSquare().getSquareID()));
                    }
                    break;
                case EA:
                    if (coordinateMaintenanceDroid.getX() + 1 == tmpSpaceShipDeck.getWidth()) break;
                    tmpMyObstacle = new Obstacle(new Coordinate(coordinateMaintenanceDroid.getX() + 1, coordinateMaintenanceDroid.getY()), new Coordinate(coordinateMaintenanceDroid.getX() + 1, coordinateMaintenanceDroid.getY() + 1));
                    if (!ObstacleService.contains(tmpSpaceShipDeck.getAllObstacles(), tmpMyObstacle) || universe.isSquareOccupied(command.getDirection(), maintenanceDroid.getSquare().getSquareID(), maintenanceDroid.getMaintenanceDroidID(), tmpSpaceShipDeck)) {
                        universe.getMaintenanceDroids().findById(maintenanceDroid.getMaintenanceDroidID()).get().setSquare(tmpSpaceShipDeck.getNextSquareinDirection(command.getDirection(), maintenanceDroid.getSquare().getSquareID()));
                    }
                    break;
                case SO:
                    if (coordinateMaintenanceDroid.getY() - 1 == -1) break;
                    tmpMyObstacle = new Obstacle(new Coordinate(coordinateMaintenanceDroid.getX(), coordinateMaintenanceDroid.getY()), new Coordinate(coordinateMaintenanceDroid.getX() + 1, coordinateMaintenanceDroid.getY()));
                    if (!ObstacleService.contains(tmpSpaceShipDeck.getAllObstacles(), tmpMyObstacle) || universe.isSquareOccupied(command.getDirection(), maintenanceDroid.getSquare().getSquareID(), maintenanceDroid.getMaintenanceDroidID(), tmpSpaceShipDeck)) {
                        universe.getMaintenanceDroids().findById(maintenanceDroid.getMaintenanceDroidID()).get().setSquare(tmpSpaceShipDeck.getNextSquareinDirection(command.getDirection(), maintenanceDroid.getSquare().getSquareID()));
                    }
                    break;
                case WE:
                    if (coordinateMaintenanceDroid.getX() - 1 == -1) break;
                    tmpMyObstacle = new Obstacle(new Coordinate(coordinateMaintenanceDroid.getX(), coordinateMaintenanceDroid.getY()), new Coordinate(coordinateMaintenanceDroid.getX(), coordinateMaintenanceDroid.getY() + 1));
                    if (!ObstacleService.contains(tmpSpaceShipDeck.getAllObstacles(), tmpMyObstacle) || universe.isSquareOccupied(command.getDirection(), maintenanceDroid.getSquare().getSquareID(), maintenanceDroid.getMaintenanceDroidID(), tmpSpaceShipDeck)) {
                        universe.getMaintenanceDroids().findById(maintenanceDroid.getMaintenanceDroidID()).get().setSquare(tmpSpaceShipDeck.getNextSquareinDirection(command.getDirection(), maintenanceDroid.getSquare().getSquareID()));
                    }
                    break;
                default:
                    throw new IllegalArgumentException(command.getDirection().toString());
            }
        }
        return true;
    }

    static Boolean isUUIDCommand(Command command, MaintenanceDroid maintenanceDroid, UniverseInterface universe) throws MaintenanceDroidPositionIsNull, SquareNotFoundException, IllegalArgumentException, IllegalStateException {
        switch (command.getDirection()) {
            case TR:
                Square tmpSquare = universe.getSpaceShipDecks().findById(maintenanceDroid.getSpaceShipDeckId()).get().getSquares().get(maintenanceDroid.getSquare().getSquareID());
                if (tmpSquare.hasConnection()) {
                    if (!universe.isTransportOccupied(universe.getSpaceShipDecks().findById(command.getSpaceShipDeckUUID()).get().findSquareAt(tmpSquare.getConnection().getDestinationCoordinate()).getSquareID(), maintenanceDroid.getMaintenanceDroidID())) {
                        universe.getMaintenanceDroids().findById(maintenanceDroid.getMaintenanceDroidID()).get().setSpaceShipDeckID(command.getSpaceShipDeckUUID());
                        universe.getMaintenanceDroids().findById(maintenanceDroid.getMaintenanceDroidID()).get().setSquare(universe.getSpaceShipDecks().findById(command.getSpaceShipDeckUUID()).get().findSquareAt(universe.getMaintenanceDroids().findById(maintenanceDroid.getMaintenanceDroidID()).get().getSquare().getConnection().getDestinationCoordinate()));
                        return true;
                    }
                }
                break;
            case EN:
                Square squareTmp = universe.getSpaceShipDecks().findById(command.getSpaceShipDeckUUID()).get().findSquareAt(new Coordinate(0, 0));
                if (!universe.isTransportOccupied(squareTmp.getSquareID(), maintenanceDroid.getMaintenanceDroidID())) {
                    universe.getMaintenanceDroids().findById(maintenanceDroid.getMaintenanceDroidID()).get().setSpaceShipDeckID(command.getSpaceShipDeckUUID());
                    universe.getMaintenanceDroids().findById(maintenanceDroid.getMaintenanceDroidID()).get().setSquare(squareTmp);
                    return true;
                }
                break;
            default:
                throw new IllegalArgumentException(command.getDirection().toString());

        }
        throw new IllegalStateException("isUUIDCommand Failure");
    }


}
