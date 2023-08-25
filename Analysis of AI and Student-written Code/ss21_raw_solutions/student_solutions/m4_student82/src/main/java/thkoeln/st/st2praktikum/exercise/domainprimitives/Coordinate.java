package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;
import thkoeln.st.st2praktikum.exercise.Exception.NoConnectionException;
import thkoeln.st.st2praktikum.exercise.Exception.WrongCoordinateException;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import java.util.Objects;

@Embeddable
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class Coordinate {

    private Integer x;
    private Integer y;

    @Setter
    private boolean blocked;

    @OneToOne
    @Setter
    private Connection connection;


    public Coordinate(Integer x, Integer y) {
        if (x >= 0 && y >= 0) {
            this.x = x;
            this.y = y;
            this.blocked = false;
            this.connection = null;
        } else {
            throw new WrongCoordinateException("Joa, dumm und so " + " " + x + " " + y);
        }
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

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public static Coordinate fromString(String coordinateString) {

        String newCoordinateRaw = coordinateString.substring(1, coordinateString.length()-1);
        String[] newCoordinate = newCoordinateRaw.split(",");

        if (newCoordinate.length == 2 && Integer.parseInt(newCoordinate[0]) >= 0 && Integer.parseInt(newCoordinate[1]) >= 0) {

            return new Coordinate(Integer.parseInt(newCoordinate[0]), Integer.parseInt(newCoordinate[1]));
        }else {
            throw new WrongCoordinateException(coordinateString);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Objects.equals(x, that.x) &&
                Objects.equals(y, that.y);
    }

    public Connection getConnection() throws NoConnectionException {
        if(hasConnection()){
            return this.connection;
        }else{
            throw new NoConnectionException("No Connection" + this.toString());

        }
    }

    public void changeBlocked() {
        this.blocked = ! this.blocked;
    }

    public boolean isBlocked() {
        return this.blocked;
    }



    public boolean hasConnection() {
        return this.connection != null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public Coordinate getCoordinate(){return this;}
}
