package thkoeln.st.st2praktikum.exercise.entities;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Obstacle {
    private Coordinates startObstacle;
    private Coordinates endObstacle;

    public Obstacle(int startObstacleX, int startObstacleY, int endObstacleX, int endObstacleY){
        startObstacle = new Coordinates(startObstacleX, startObstacleY);
        endObstacle = new Coordinates(endObstacleX, endObstacleY);
    }

    public Obstacle(String obstacleString){
        String[] input = obstacleString.split("-");
        startObstacle = new Coordinates(input[0]);
        endObstacle = new Coordinates(input[1]);
    }

    public Boolean isObstacle(Coordinates coordinates , Command.Cmd direction){
        if (direction == Command.Cmd.no && startObstacle.getY() == (coordinates.getY()+1) && coordinates.getX() >= startObstacle.getX() && coordinates.getX() < endObstacle.getX())
            return true;
        else if (direction == Command.Cmd.ea && startObstacle.getX() == (coordinates.getX()+1) && coordinates.getY() >= startObstacle.getY() && coordinates.getY() < endObstacle.getY())
            return true;
        else if (direction == Command.Cmd.so && startObstacle.getY() == coordinates.getY() && coordinates.getX() >= startObstacle.getX() && coordinates.getX() < endObstacle.getX())
            return true;
        else if (direction == Command.Cmd.we && startObstacle.getX() == coordinates.getX() && coordinates.getY() >= startObstacle.getY() && coordinates.getY() < endObstacle.getY())
            return true;
        else
            return false;
    }
}

