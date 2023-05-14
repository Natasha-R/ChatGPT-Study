package thkoeln.st.st2praktikum.exercise;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
public class Coordinate {

    @Setter
    private  Integer x;

    @Setter
    private  Integer y;

    public Coordinate(Integer x, Integer y) throws RuntimeException {
        this.x = x;
        this.y = y;
        checkIfNegative();
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) throws RuntimeException {
        String[] dividedCoordinateArray=coordinateString.replace("(","").replace(")","").split(",");
        if (dividedCoordinateArray.length==2){
            this.x=Integer.parseInt(dividedCoordinateArray[0]);
            this.y=Integer.parseInt(dividedCoordinateArray[1]);
        } else throw new RuntimeException();
        checkIfNegative();
    }

    public Coordinate() { }


    public Coordinate max(Coordinate position) {
        if ( this.getX()>=position.getX() && this.getY()>=position.getY() )
            return this;
        else if ( this.getX()<=position.getX() && this.getY()<=position.getY()  )
            return position;
        throw new RuntimeException("Too big Coordinates. \n " );
    }

    public Coordinate min(Coordinate position) {
        if (this.getX() >= position.getX() && this.getY() >= position.getY() )
            return position;
        else if (this.getX() <= position.getX() && this.getY() <= position.getY())
            return this;
        throw new RuntimeException("Too minimal Coordinates. \n ");

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
        return this.x;
    }

    public Integer getY() {
        return this.y;
    }



    public void checkIfNegative(){
        if (x<0||y<0) throw new RuntimeException();
    }

}
