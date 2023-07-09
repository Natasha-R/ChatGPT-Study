package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;

import javax.persistence.Embeddable;

@EqualsAndHashCode
@Embeddable
public class Coordinate {

    @Setter
    private  Integer x;

    @Setter
    private  Integer y;


    public Coordinate(Integer x, Integer y) {
        
        this.x = x;
        this.y = y;
        checkIfNegative();
    }

    public Coordinate() { }


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

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public static Coordinate fromString(String coordinateString) {
        return new Coordinate(coordinateString);
    }

    public Coordinate(String coordinateString) throws RuntimeException {
        String[] dividedCoordinateArray=coordinateString.replace("(","").replace(")","").split(",");
        if (dividedCoordinateArray.length==2){
            this.x=Integer.parseInt(dividedCoordinateArray[0]);
            this.y=Integer.parseInt(dividedCoordinateArray[1]);
        } else throw new RuntimeException();
        checkIfNegative();
    }

    public void checkIfNegative(){
        if (x<0||y<0) throw new RuntimeException();
    }

}
