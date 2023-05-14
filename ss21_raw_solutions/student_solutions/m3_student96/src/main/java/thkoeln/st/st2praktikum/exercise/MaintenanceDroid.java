package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.map.SpaceShipDeck;
import thkoeln.st.st2praktikum.exercise.map.Locatable;
import thkoeln.st.st2praktikum.exercise.map.PointException;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
public class MaintenanceDroid {

    @Id
    private UUID droidID;

    @Embedded
    private Locatable position;
    private String name;

    public MaintenanceDroid(String name) {
        this.name = name;
        this.droidID = UUID.randomUUID();
        this.position = null;
    }

    public boolean executeOrder(Order order, MaintenanceDroidService service) {
        OrderType orderType = order.getOrderType();
        SpaceShipDeck destination = service.getSpaceShipDeckByID( order.getGridId() );
        switch ( orderType ) {
            case ENTER:
                return spawnDroid( destination );
            case TRANSPORT:
                return transportDroid( destination, orderType );
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                return step( orderType, order.getNumberOfSteps() );
            default:
                return false;
        }
    }

    private boolean spawnDroid(SpaceShipDeck destinationSpaceShipDeck) {

        Locatable destination = destinationSpaceShipDeck.getPointFromSpaceShipDeck("(0,0)");
        if (destination.isBlocked()) {
            return false;
        }
        destination.block();
        this.position = destination;
        return true;
    }


    private boolean transportDroid(SpaceShipDeck destinationDeck, OrderType transport) {
        try {
            Locatable destination = this.position.getConnectionByOrderType( transport );
            //TODO simplify ifStatement
            if (destinationDeck.getPoints().containsKey(destination.toString()) && !destination.isBlocked()) {
                return step( transport, 1 );
            }
        } catch (PointException e) {
            return false;
        }
        return false;
    }

    private boolean step( OrderType direction, int steps ) {
        Locatable target, oldPosition = position;
        try {
            target = position.getConnectionByOrderType( direction );
            target.block();
            position = target;
            oldPosition.unblock();
        } catch ( PointException e ){
            return true;
        }
        if (steps == 0  || target.isBlocked() ) return true;
        return step( direction, steps - 1 );
    }

    public String toString() {
        return name;
    }
}