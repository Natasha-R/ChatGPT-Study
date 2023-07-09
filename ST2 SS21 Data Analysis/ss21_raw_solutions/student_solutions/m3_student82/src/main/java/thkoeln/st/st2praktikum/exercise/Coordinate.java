package thkoeln.st.st2praktikum.exercise;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import java.util.Objects;

@Embeddable
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class Coordinate {

    @Setter
    private Integer x;

    @Setter
    private Integer y;

    @Setter
    private boolean blocked;

    @OneToOne
    @Setter
    private Connection connection;

    public Coordinate(Integer x, Integer y, boolean blocked, Connection connection) {
        this.x = x;
        this.y = y;
        this.blocked = blocked;
        this.connection = connection;
    }

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

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) {
        String newCoordinateRaw = coordinateString.substring(1, coordinateString.length()-1);
        String[] newCoordinate = newCoordinateRaw.split(",");

        if (newCoordinate.length == 2 && Integer.parseInt(newCoordinate[0]) >= 0 && Integer.parseInt(newCoordinate[1]) >= 0) {
            this.x = Integer.parseInt(newCoordinate[0]);
            this.y = Integer.parseInt(newCoordinate[1]);
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

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public Coordinate getCoordinate(){return this;}

}
