package thkoeln.st.st2praktikum.exercise;

public class Obstacle {

    private char direction;
    private Integer startPos;
    private Integer endPos;
    private Integer position;

    public Obstacle(char direction, Integer startPos, Integer endPos, Integer position) {
        this.direction = direction;
        this.startPos = startPos;
        this.endPos = endPos;
        this.position = position;
    }

    public char getDirection() {
        return direction;
    }

    public Integer getStartPos() {
        return startPos;
    }

    public Integer getEndPos() {
        return endPos;
    }

    public Integer getPosition() {
        return position;
    }
}
