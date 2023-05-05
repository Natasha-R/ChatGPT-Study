package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

public class Coordinate {
    @Getter
    private int x;
    @Getter
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void changeCoordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void move(int xAmount, int yAmount){
        this.x += xAmount;
        this.y += yAmount;
    }

    public Coordinate(String coordinateString) {
        try {
            coordinateString = coordinateString.replace("(", "");
            coordinateString = coordinateString.replace(")", "");

            String[] explodedPosition = coordinateString.split(",");

            this.x = Integer.parseInt(explodedPosition[0]);
            this.y = Integer.parseInt(explodedPosition[1]);
        } catch (Exception exception) {
            throw exception;
        }
    }

    public String toString(){
        return "(" + this.x + "," + this.y + ")";
    }
}
