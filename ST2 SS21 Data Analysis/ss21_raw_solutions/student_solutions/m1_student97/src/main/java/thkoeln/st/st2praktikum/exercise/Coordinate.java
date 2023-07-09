package thkoeln.st.st2praktikum.exercise;

public class Coordinate {

    protected Integer x;
    protected Integer y;
    
    public Coordinate (String CoordinateString) {
        String[] processedCordinateString = CoordinateString.replaceAll("\\(", "").replaceAll("\\)", "").split(",");
        this.x = Integer.parseInt(processedCordinateString[0]);
        this.y = Integer.parseInt(processedCordinateString[1]);
    }
    

    public String getCoordinateString() {
        return "(" + this.x + "," + this.y + ")";
    }
}
