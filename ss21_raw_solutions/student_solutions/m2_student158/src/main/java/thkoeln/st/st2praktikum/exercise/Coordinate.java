package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Coordinate {

    private Integer x;
    private Integer y;
    public static String DEFAULT="(0, 0)";


    public Coordinate(Integer x, Integer y) {
        assertValuesNotNegative(x,y);
        this.x = x;
        this.y = y;
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) {
        assertFormat(coordinateString);
        String[] coordinateArray =  coordinateString.replace("(", "").replace(")", "").split(",");
        int x = Integer.parseInt(coordinateArray[0]);
        int y = Integer.parseInt(coordinateArray[1]);
        assertValuesNotNegative(x, y);
        this.x = x;
        this.y = y;
    }

    private void assertValuesNotNegative(Integer x, Integer y){
        if(x<0 || y<0){
            throw new NumberFormatException("Values are not allowed to be negative");
        }
    }

    private void assertFormat(String coordinateString){
        Pattern pattern = Pattern.compile("^\\([0-9]{1,2},[0-9]{1,2}\\)");
        Matcher matcher = pattern.matcher(coordinateString);
        if (!matcher.matches()){
            throw new IllegalArgumentException("Coordinate not given in expected format '(x,y)'");
        }
    }

    public void incrementX(){
        x++;
    }

    public void decrementX(){
        x--;
    }

    public void incrementY(){
        y++;
    }

    public void decrementY(){
        y--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Objects.equals(x, that.x) &&
                Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }
}
