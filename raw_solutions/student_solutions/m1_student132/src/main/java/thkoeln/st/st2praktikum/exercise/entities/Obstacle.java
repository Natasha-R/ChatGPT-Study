package thkoeln.st.st2praktikum.exercise.entities;

import java.util.LinkedList;
import java.util.List;

public class Obstacle extends AbstractEntity {
    private final String obstacleString;
    private final List<Move> illegalMoves = new LinkedList<Move>();
    private final Space space;

    public Obstacle(String obstacleString, Space space) {
        this.obstacleString = obstacleString;
        this.space = space;
        parseObstacleString(obstacleString);
    }

    private void parseObstacleString(String obstacleString) {
        String[] parts = obstacleString.split("-");
        Coordinate start = new Coordinate(parts[0]);
        Coordinate end = new Coordinate(parts[1]);

        if (!(start.getX() == end.getX() && start.getY() == end.getY())) {
            for (int i = start.getX(); i < end.getX(); i++) { //Horizontal Obstacle
                Move illegalMove = new Move(new Coordinate(i, start.getY()), "so");
                illegalMoves.add(illegalMove);
                illegalMoves.add(illegalMove.reverse());
            }
        } else if (!(start.getY() == end.getY() && start.getX() == end.getX())) {
            for (int i = start.getY(); i < end.getY(); i++) { //Vertical Obstacle
                Move illegalMove = new Move(new Coordinate(start.getX(), i), "we");
                illegalMoves.add(illegalMove);
                illegalMoves.add(illegalMove.reverse());
            }
        } else {
            throw new IllegalArgumentException("String " + obstacleString + " isn't formatted correctly.");
        }
    }

    public String toString() {
        return obstacleString;
    }

    public List<Move> getIllegalMoves() {
        return illegalMoves;
    }
}
