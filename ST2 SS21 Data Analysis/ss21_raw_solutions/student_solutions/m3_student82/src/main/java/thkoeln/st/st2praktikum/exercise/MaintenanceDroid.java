package thkoeln.st.st2praktikum.exercise;

import lombok.*;
import org.dom4j.tree.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceDroid extends AbstractEntity  {

    @Id
    private UUID maintenanceDroidId = UUID.randomUUID();

    private String name;

    private boolean spawned;


    private Coordinate coordinate = new Coordinate();

    private UUID spaceShipDeckId;

    public MaintenanceDroid(String name) {
        this.maintenanceDroidId = UUID.randomUUID();
        this.name = name;
        this.spawned = false;
    }

    public MaintenanceDroid getDroid(UUID droidId) {
        return this;
    }

    public boolean isSpawned() {
        return this.spawned;
    }

    public boolean move(OrderType orderType, Map map) throws NotSpawnedYetException, NoFieldException {


        switch (orderType) {
            case NORTH:
                Coordinate nextNorth = new Coordinate(this.coordinate.getX(), this.coordinate.getY() + 1);
                changeBlockedStatus();
                map.getDecks().findById(this.spaceShipDeckId).get().getCoordinates().forEach(coordinate1 -> {
                    if (coordinate1.getX().equals(nextNorth.getX()) && coordinate1.getY().equals(nextNorth.getY()) )
                       this.coordinate = coordinate1;
                        }

                        );
                //this.coordinate = new Coordinate(this.coordinate.getX(), this.getCoordinate().getY() + 1 );
                System.out.println(this.coordinate);
                changeBlockedStatus();
                return true;
            case EAST:
                Coordinate nextEast = new Coordinate(this.coordinate.getX() + 1, this.coordinate.getY());
                changeBlockedStatus();
                map.getDecks().findById(this.spaceShipDeckId).get().getCoordinates().forEach(coordinate1 -> {
                            if (coordinate1.getX().equals(nextEast.getX()) && coordinate1.getY().equals(nextEast.getY()))
                                this.coordinate = coordinate1;
                        }

                );

                //this.coordinate = new Coordinate(this.coordinate.getX() + 1, this.getCoordinate().getY() );
                System.out.println(this.coordinate);
                changeBlockedStatus();
                return true;
            case SOUTH:
                    Coordinate nextSouth = new Coordinate(this.coordinate.getX(), this.coordinate.getY() - 1);
                    changeBlockedStatus();
                    map.getDecks().findById(this.spaceShipDeckId).get().getCoordinates().forEach(coordinate1 -> {
                                if (coordinate1.getX().equals(nextSouth.getX()) && coordinate1.getY().equals(nextSouth.getY()))
                                    this.coordinate = coordinate1;
                            }


                    );

                    //this.coordinate = new Coordinate(this.coordinate.getX(), this.getCoordinate().getY() - 1);
                    System.out.println(this.coordinate);
                    changeBlockedStatus();
                    return true;


            case WEST:

                    Coordinate nextWest = new Coordinate(this.coordinate.getX() - 1, this.coordinate.getY());
                    changeBlockedStatus();
                    map.getDecks().findById(this.spaceShipDeckId).get().getCoordinates().forEach(coordinate1 -> {
                                if (coordinate1.getX().equals(nextWest.getX()) && coordinate1.getY().equals(nextWest.getY()))
                                    this.coordinate = coordinate1;
                            }

                    );


                    //this.coordinate = new Coordinate(this.coordinate.getX() - 1, this.getCoordinate().getY() );
                    System.out.println(this.coordinate);
                    changeBlockedStatus();
                    return true;

            default:
                return false;

        }
    }

    public Decision canGoForward(OrderType orderType, Map map) throws NotSpawnedYetException, NoFieldException {
        if(Map.wallInDirection(orderType, this, map) || Map.droidInDirection(orderType, this, map) || Map.endInDirection(orderType, this, map)){
            return Decision.CANT_GO_FORWARD;
        }else{
            return  Decision.CAN_GO_FORWARD;
        }

    }

    private void changeBlockedStatus() throws NoFieldException {
        this.coordinate.changeBlocked();
    }

    public boolean travel(Map map) throws NoFieldException, NoConnectionException {


        if(this.coordinate.hasConnection()){
            if(!this.coordinate.getConnection().getDestinationCoordinate().isBlocked()){
                changeBlockedStatus();


                System.out.println("Erste DeckId der Coordinate: " + this.spaceShipDeckId);
                this.spaceShipDeckId = this.coordinate.getConnection().getDestinationDeckId();
                System.out.println("Zweite DeckId der Coordinate: " + this.spaceShipDeckId);

                Coordinate destTemp = this.coordinate.getConnection().getDestinationCoordinate();
                map.getDecks().findById(this.coordinate.getConnection().getDestinationDeckId()).get().getCoordinates().forEach(coordinate1 -> {
                            if (coordinate1.getX().equals(destTemp.getX()) && coordinate1.getY().equals(destTemp.getY()))
                                this.coordinate = coordinate1;
                        }

                );
                changeBlockedStatus();
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public boolean spawn(Map map, UUID spaceShipId) throws NoFieldException {
        if(!map.getDecks().findById(spaceShipId).get().getCoordinates().get(0).isBlocked()){
            this.coordinate = map.getDecks().findById(spaceShipId).get().getCoordinates().get(0);
            changeBlockedStatus();
            this.spaceShipDeckId = spaceShipId;
            this.spawned = true;
            return true;
        }else{
            return false;
        }
    }
}
