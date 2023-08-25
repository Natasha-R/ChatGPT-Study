package thkoeln.st.st2praktikum.exercise.entities;

import thkoeln.st.st2praktikum.exercise.OrderType;

import java.util.LinkedList;
import java.util.List;

public class Obstacle extends AbstractEntity {
    private final List<Move> illegalMoves = new LinkedList<>();
    private final Space space;

    public Obstacle(String obstacleString, Space space) {
        this.space = space;
        parseObstacleString(obstacleString);
    }

    public Obstacle(thkoeln.st.st2praktikum.exercise.Obstacle obstacle, Space space) {
        this.space = space;
        calculateMoves(new Coordinate(obstacle.getStart()), new Coordinate(obstacle.getEnd()));
    }

    private void parseObstacleString(String obstacleString) {
        String[] parts = obstacleString.split("-");
        Coordinate start = new Coordinate(parts[0]);
        Coordinate end = new Coordinate(parts[1]);

        calculateMoves(start, end);
    }

    private void calculateMoves(Coordinate start, Coordinate end) {
        if (!(start.getX() == end.getX() && start.getY() == end.getY())) {
            for (int i = start.getX(); i < end.getX(); i++) { //Horizontal Obstacle
                Move illegalMove = new Move(new Coordinate(i, start.getY()), OrderType.SOUTH);
                illegalMoves.add(illegalMove);
                illegalMoves.add(illegalMove.reverse());
            }
        } else if (!(start.getY() == end.getY() && start.getX() == end.getX())) {
            for (int i = start.getY(); i < end.getY(); i++) { //Vertical Obstacle
                Move illegalMove = new Move(new Coordinate(start.getX(), i), OrderType.WEST);
                illegalMoves.add(illegalMove);
                illegalMoves.add(illegalMove.reverse());
            }
        }

    }

    public List<Move> getIllegalMoves() {
        return illegalMoves;
    }
}
