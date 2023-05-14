package thkoeln.st.st2praktikum.exercise.map;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.Barrier;
import thkoeln.st.st2praktikum.exercise.Point;

import java.util.HashMap;
import java.util.UUID;

@Getter
public class Grid {

    private final UUID gridID = UUID.randomUUID();

    private final int height;
    private final int width;

    HashMap<String, Point> points = new HashMap<>();
    HashMap<String, Barrier> barriers = new HashMap<>();


    public Grid(int height, int width) {

        this.height = height;
        this.width = width;

        for ( int x = 0; x < width; x++ ) {
            for (int y = 0; y < height; y++ ) {
                Point point = new Point( x, y );
                addPointToGridWithConnections( point, x, y );
            }
        }
    }

    private void addPointToGridWithConnections( Point point, int x ,int y ){

        point.setGridID( gridID );
        points.put(point.toString(), point);

        if (y > 0){
            Point southernPoint = points.get( "(" + x + "," + ( y - 1 ) + ")" );
            point.makeSouthernConnection(southernPoint);

        }
        if (x > 0){
            Point westernPoint = points.get( "(" + ( x - 1 ) + "," + y + ")" );
            point.makeWesternConnection(westernPoint);
        }
    }

    public void addBarriers ( Barrier barrier) {

        Point start = points.get( barrier.getStart().toString() );
        Point end = points.get( barrier.getEnd().toString() );
        barriers.put( barrier.toString(), barrier );

        removeConnections(start, end, barrier.isHorizontal());
    }

    public void removeConnections( Point current, Point end, Boolean isHorizontal ) {

        if ( current.equals( end ) ) {
            return;
        }
        if ( isHorizontal ) {
            current.removeSouthernConnection();
            //current = current.getEast();
            removeConnections(current.getEast(), end, true);
        } else {
            current.removeWesternConnection();
            removeConnections(current.getNorth(), end, false);
        }
    }


    public  Point getPointFromGrid( String coordinates ) {

        return getPoints().get( coordinates );
    }


}
