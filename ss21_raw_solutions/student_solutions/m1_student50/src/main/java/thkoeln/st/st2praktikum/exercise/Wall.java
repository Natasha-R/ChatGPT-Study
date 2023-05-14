package thkoeln.st.st2praktikum.exercise;

public class Wall {

    private Integer startValueOfX;
    private Integer endValueOfX;
    private Integer startValueOfY;
    private Integer endValueOfY;

    public Wall(String wallString) {

        int startValueOfX = Integer.parseInt(String.valueOf(wallString.charAt(1)));
        int endValueOfX = Integer.parseInt(String.valueOf(wallString.charAt(7)));
        int startValueOfY = Integer.parseInt(String.valueOf(wallString.charAt(3)));
        int endValueOfY = Integer.parseInt(String.valueOf(wallString.charAt(9)));

        this.startValueOfX = startValueOfX;
        this.startValueOfY = startValueOfY;
        this.endValueOfX = endValueOfX;
        this.endValueOfY = endValueOfY;

    }

    public Integer getStartValueOfX() {
        return startValueOfX;
    }
    public Integer getEndValueOfX() {
        return endValueOfX;
    }
    public Integer getStartValueOfY() {
        return startValueOfY;
    }
    public Integer getEndValueOfY() {
        return endValueOfY;
    }

}
