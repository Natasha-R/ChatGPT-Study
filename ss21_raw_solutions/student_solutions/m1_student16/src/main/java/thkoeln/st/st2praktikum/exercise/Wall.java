package thkoeln.st.st2praktikum.exercise;

public class Wall {
    private final Integer startX;
    private final Integer startY;
    private final Integer endX;
    private final Integer endY;
    public Wall(String wallString){
        this.startX = Integer.parseInt(wallString.substring(1,2));
        this.startY = Integer.parseInt(wallString.substring(3,4));
        this.endX = Integer.parseInt(wallString.substring(7,8));
        this.endY = Integer.parseInt(wallString.substring(9,10));
    }

    public Integer getStartX() {
        return startX;
    }

    public Integer getStartY() {
        return startY;
    }

    public Integer getEndX() {
        return endX;
    }

    public Integer getEndY() {
        return endY;
    }
}
