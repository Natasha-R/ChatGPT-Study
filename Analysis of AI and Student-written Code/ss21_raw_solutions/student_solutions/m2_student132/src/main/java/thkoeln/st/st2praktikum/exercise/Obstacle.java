package thkoeln.st.st2praktikum.exercise;

public class Obstacle {

    private Coordinate start;
    private Coordinate end;


    public Obstacle(Coordinate pos1, Coordinate pos2) {
        if (pos1.getX().equals(pos2.getX())) {
            if (pos1.getY() < pos2.getY()) {
                this.start = pos1;
                this.end = pos2;
            } else {
                this.start = pos2;
                this.end = pos1;
            }
        } else if (pos1.getY().equals(pos2.getY())) {
            if (pos1.getX() < pos2.getX()) {
                this.start = pos1;
                this.end = pos2;
            } else {
                this.start = pos2;
                this.end = pos1;
            }
        } else {
            throw new IllegalArgumentException("Argument not formatted correctly");
        }
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public Obstacle(String obstacleString) {
        String[] parts = obstacleString.split("-");
        this.start = new Coordinate(parts[0]);
        this.end = new Coordinate(parts[1]);
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }
}
