package thkoeln.st.st2praktikum.exercise.map;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import thkoeln.st.st2praktikum.exercise.Barrier;
import thkoeln.st.st2praktikum.exercise.Point;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.UUID;

@Entity
@Getter
public class SpaceShipDeck {

    @Id
    private final UUID gridID = UUID.randomUUID();

    private final Integer height;
    private final Integer width;

    @Autowired
//    private PointRepository points;
    HashMap<String, Locatable> points = new HashMap<>();
    HashMap<String, Barrier> barriers = new HashMap<>();


    public SpaceShipDeck(int height, int width) {

        this.height = height;
        this.width = width;

        for ( int x = 0; x < width; x++ ) {
            for (int y = 0; y < height; y++ ) {
                Locatable point = new Point( x, y );
                addPointToGridWithConnections( point, x, y );
            }
        }
    }

    private void addPointToGridWithConnections( Locatable point, int x ,int y ){

        point.setSpaceShipDeckID( this.gridID );
        points.put(point.toString(), point);

        if (y > 0){
            Locatable south = points.get( "(" + x + "," + ( y - 1 ) + ")" );
            point.makeSouthernConnection(south);
        }
        if (x > 0){
            Locatable westernPoint = points.get( "(" + ( x - 1 ) + "," + y + ")" );
            point.makeWesternConnection(westernPoint);
        }
    }

    public void addBarriers ( Barrier barrier) {

        Locatable start = points.get( barrier.getStart().toString() );
        Locatable end = points.get( barrier.getEnd().toString() );
        barriers.put( barrier.toString(), barrier );

        removeConnections( start, end, barrier.isHorizontal());
    }

    public void removeConnections( Locatable current, Locatable end, Boolean isHorizontal ) {

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


    public  Locatable getPointFromSpaceShipDeck(String coordinates ) {

        return getPoints().get( coordinates );
    }


}
