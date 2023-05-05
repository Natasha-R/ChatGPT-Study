package thkoeln.st.st2praktikum.exercise.services;

import thkoeln.st.st2praktikum.exercise.command.Command;
import thkoeln.st.st2praktikum.exercise.command.CommandInterface;
import thkoeln.st.st2praktikum.exercise.command.Direction;
import thkoeln.st.st2praktikum.exercise.coordinate.Coordinate;
import thkoeln.st.st2praktikum.exercise.coordinate.CoordinateInterface;
import thkoeln.st.st2praktikum.exercise.obstacle.Obstacle;
import thkoeln.st.st2praktikum.exercise.obstacle.ObstacleInterface;

import java.util.UUID;

public interface StringService {

    static Direction translateToDirection(String command) throws IllegalArgumentException{
        switch (command){
            case "no" : return Direction.NO;
            case "ea" : return Direction.EA;
            case "so" : return Direction.SO;
            case "we" : return Direction.WE;
            case "tr" : return Direction.TR;
            case "en" : return Direction.EN;
            default: throw new IllegalArgumentException(command);
        }
    }
    static ObstacleInterface translateToObstacle(String obstacleString){

        obstacleString = obstacleString.replace("(", "");
        obstacleString = obstacleString.replace(")", "");
        String[] parts = obstacleString.split("-");
        String[] first = parts[0].split(",");
        String[] second = parts[1].split(",");
        return  new Obstacle(new Coordinate(Integer.parseInt(first[0]), Integer.parseInt(first[1])), new Coordinate(Integer.parseInt(second[0]), Integer.parseInt(second[1])));
    }
    static CoordinateInterface translateToCoordinate(String obstacleString){

        obstacleString = obstacleString.replace("(", "");
        obstacleString = obstacleString.replace(")", "");
        String[] resultString = obstacleString.split(",");
        return new Coordinate(Integer.parseInt(resultString[0]), Integer.parseInt(resultString[1]));
    }

    static CommandInterface translateToCommandPair (String commandString) throws IllegalArgumentException, IllegalStateException{
        commandString = commandString.replace("[", "");
        commandString = commandString.replace("]", "");
        String[] resultCommandString = commandString.split(",");
        Direction direction = StringService.translateToDirection(resultCommandString[0]);
        switch (direction){
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
}
