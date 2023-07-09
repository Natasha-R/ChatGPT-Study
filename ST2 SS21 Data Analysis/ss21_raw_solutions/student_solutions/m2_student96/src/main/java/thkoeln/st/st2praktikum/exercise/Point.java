package thkoeln.st.st2praktikum.exercise;

import java.util.EnumMap;
import java.util.Objects;
import java.util.UUID;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.converter.*;
import thkoeln.st.st2praktikum.exercise.map.PointException;


//TODO Add factory methods
@Getter
public class Point {

    private UUID gridID;
    private final Integer x;
    private final Integer y;
    private boolean isBlocked;

    private EnumMap<OrderType,Point> connections = new EnumMap<>(OrderType.class);


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
    public Point( String pointString ) {

        StringConverter convert = new StringConverter();
        int[] ints = convert.pointStringToIntArray( pointString );
        int x, y;
        x = ints[0];
        y = ints[1];
        this.x = x;
        this.y = y;
        this.isBlocked = false;
    }

    public void makeSouthernConnection( Point southernNeighbor ) {

        if ( !this.getX().equals( southernNeighbor.getX() ) || (this.getY() != ( southernNeighbor.getY() + 1 ) ) )
            throw new PointException( "Point " + southernNeighbor + " is no southern neighbor of " + this );

        connections.put( OrderType.SOUTH, southernNeighbor );
        southernNeighbor.getConnections().put( OrderType.NORTH, this);
    }

    public void makeWesternConnection( Point westernNeighbor ) {

        if (this.getX() != ( westernNeighbor.getX() + 1 ) || !this.getY().equals( westernNeighbor.getY() ) )
            throw new PointException( "Point " + westernNeighbor + " is no western neighbor of " + this );

        connections.put( OrderType.WEST, westernNeighbor );
        westernNeighbor.getConnections().put( OrderType.EAST, this);
    }

    public void removeSouthernConnection() {

        if ( getY() > 0 ) {
            getSouth().connections.remove( OrderType.NORTH );
            connections.remove( OrderType.SOUTH );
        } else {
            throw new PointException( "There are no Points to the south" );
        }
    }

    public void removeWesternConnection() {

        if (getX() > 0 ) {
            getWest().connections.remove( OrderType.EAST);
            connections.remove(OrderType.WEST );
        } else {
            throw new PointException( "There are no Points to the west" );
        }
    }

    public Point getNorth() {
        return getConnectionByOrderType( OrderType.NORTH );
    }

    public Point getSouth() {
        return getConnectionByOrderType( OrderType.SOUTH );
    }

    public Point getEast() {
        return getConnectionByOrderType( OrderType.EAST );
    }

    public Point getWest() {
        return getConnectionByOrderType( OrderType.WEST );
    }

    public Boolean connectionsContains( OrderType orderType ) {
        try {
            getConnectionByOrderType( orderType );
            return true;
        } catch ( PointException pointException ) {
            return false;
        }
    }

    private Point getConnectionByOrderType( OrderType direction ) {
        if ( connections.containsKey( direction ) ) {
            return  connections.get( direction );
        }
        throw new PointException( "This Point has no " + direction + "." );
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setIsBlocked( boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public void setGridID(UUID gridID) {
        this.gridID = gridID;
    }

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
}
