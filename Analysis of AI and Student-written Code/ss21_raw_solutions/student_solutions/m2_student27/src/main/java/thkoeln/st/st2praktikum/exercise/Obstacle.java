package thkoeln.st.st2praktikum.exercise;

public class Obstacle {

    private Point start;
    private Point end;


    public Obstacle(Point pos1, Point pos2) {
        this.start = pos1;
        this.end = pos2;
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public Obstacle(String obstacleString) {
        throw new UnsupportedOperationException();
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }
}
