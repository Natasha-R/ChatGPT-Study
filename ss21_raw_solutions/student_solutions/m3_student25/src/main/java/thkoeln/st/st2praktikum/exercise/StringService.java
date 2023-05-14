package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface StringService {

    static Direction translateToDirection(String command) throws IllegalArgumentException {
        switch (command) {
            case "no":
                return Direction.NO;
            case "ea":
                return Direction.EA;
            case "so":
                return Direction.SO;
            case "we":
                return Direction.WE;
            case "tr":
                return Direction.TR;
            case "en":
                return Direction.EN;
            default:
                throw new IllegalArgumentException(command);
        }
    }

    static Obstacle translateToObstacle(String obstacleString) {
        return new Obstacle(obstacleString);
    }

    static Coordinate translateStringToCoordinate(String coordinateString) {

        coordinateString = coordinateString.replace("(", "");
        coordinateString = coordinateString.replace(")", "");
        String[] resultString = coordinateString.split(",");
        return new Coordinate(Integer.parseInt(resultString[0]), Integer.parseInt(resultString[1]));
    }

    static Command translateToCommand(String commandString) throws IllegalArgumentException, IllegalStateException {
        commandString = commandString.replace("[", "");
        commandString = commandString.replace("]", "");
        String[] resultCommandString = commandString.split(",");
        Direction direction = StringService.translateToDirection(resultCommandString[0]);
        switch (direction) {
            case NO:
            case EA:
            case SO:
            case WE:
                return new Command(direction, Integer.parseInt(resultCommandString[1]));
            case EN:
            case TR:
                return new Command(direction, UUID.fromString(resultCommandString[1]));
        }
        throw new IllegalStateException("translateToCommandPair failure");
    }

    static Command translateOrderToCommand(Order order) throws IllegalArgumentException {
        switch (order.getOrderType()) {

            case NORTH:
                return new Command(Direction.NO, order.getNumberOfSteps());
            case WEST:
                return new Command(Direction.WE, order.getNumberOfSteps());
            case SOUTH:
                return new Command(Direction.SO, order.getNumberOfSteps());
            case EAST:
                return new Command(Direction.EA, order.getNumberOfSteps());
            case TRANSPORT:
                return new Command(Direction.TR, order.getGridId());
            case ENTER:
                return new Command(Direction.EN, order.getGridId());
        }
        throw new IllegalStateException("translateOrderToCommand failure");
    }
}
