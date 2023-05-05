package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.converter.StringConverter;
import thkoeln.st.st2praktikum.exercise.map.Locatable;
import thkoeln.st.st2praktikum.exercise.map.PointException;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Id;
import java.util.*;

@Embeddable
@Getter
@NoArgsConstructor
public class Point implements Locatable {

    @Id
    private UUID spaceShipDeckID;
    private Integer x;
    private Integer y;
    private boolean isBlocked;

    //private List<Locatable> connections = new ArrayList<>();
    private EnumMap<OrderType, Locatable> connections = new EnumMap<>(OrderType.class);

    public Point( Integer x, Integer y ) {

        if( x < 0 ) throw new PointException( "x must be >= 0");
        if( y < 0 ) throw new PointException( "y must be >= 0");

        this.x = x;
        this.y = y;
        this.isBlocked = false;
    }

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public Point( String pointString) {

        StringConverter convert = new StringConverter();
        int[] ints = convert.pointStringToIntArray( pointString );
        int x, y;
        x = ints[0];
        y = ints[1];
        this.x = x;
        this.y = y;
        this.isBlocked = false;
    }

    public void makeSouthernConnection( Locatable southernNeighbor ) {

        if ( this.getX() != southernNeighbor.getX()  || this.getY() != southernNeighbor.getY() + 1  )
            throw new PointException( "Point " + southernNeighbor + " is no southern neighbor of " + this );

        connections.put( OrderType.SOUTH, southernNeighbor );
        southernNeighbor.getConnections().put( OrderType.NORTH, this);
    }

    public void makeWesternConnection( Locatable westernNeighbor ) {

        if (this.getX() != ( westernNeighbor.getX() + 1 ) || this.getY() != westernNeighbor.getY() )
            throw new PointException( "Point " + westernNeighbor + " is no western neighbor of " + this );

        connections.put( OrderType.WEST, westernNeighbor );
        westernNeighbor.getConnections().put( OrderType.EAST, this);
    }

    public void removeSouthernConnection() {

        if ( getY() > 0 ) {
            getSouth().getConnections().remove( OrderType.NORTH );
            connections.remove( OrderType.SOUTH );
        } else {
            throw new PointException( "There are no Points to the south" );
        }
    }

    public void removeWesternConnection() {

        if (getX() > 0 ) {
            getWest().getConnections().remove( OrderType.EAST);
            connections.remove(OrderType.WEST );
        } else {
            throw new PointException( "There are no Points to the west" );
        }
    }

    public Locatable getNorth() {
        return getConnectionByOrderType( OrderType.NORTH );
    }

    public Locatable getSouth() {
        return getConnectionByOrderType( OrderType.SOUTH );
    }

    public Locatable getEast() {
        return getConnectionByOrderType( OrderType.EAST );
    }

    public Locatable getWest() {
        return getConnectionByOrderType( OrderType.WEST );
    }

    public boolean connectionsContains( OrderType orderType ) {
        try {
            getConnectionByOrderType( orderType );
            return true;
        } catch ( PointException pointException ) {
            return false;
        }
    }

    public Locatable getConnectionByOrderType( OrderType direction ) {
        if ( connections.containsKey( direction ) ) {
            return  connections.get( direction );
        }
        throw new PointException( "This Point has no " + direction + "." );
    }

    @Override
    public boolean isBlocked() {
        return isBlocked;
    }

    @Override
    public void block() { this.isBlocked = true; }

    @Override
    public void unblock() { this.isBlocked = false; }

    @Override
    public boolean equals( Object o ) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point that = (Point) o;
        return Objects.equals(x, that.x) &&
                Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public void setSpaceShipDeckID(UUID gridID ) {
        if ( this.spaceShipDeckID == null ) {
            this.spaceShipDeckID = gridID;
        } else {
            throw new PointException( "This Point already has a SpaceShipDeck!");
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
