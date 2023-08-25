package thkoeln.st.st2praktikum.exercise.map;

import thkoeln.st.st2praktikum.exercise.OrderType;

import javax.persistence.Embeddable;
import java.util.EnumMap;
import java.util.UUID;

@Embeddable
public interface Locatable extends Blockable {

    String toString();

    Locatable getNorth();

    Locatable getEast();

    Locatable getSouth();

    Locatable getWest();

    Locatable getConnectionByOrderType( OrderType orderType );

    int getX();

    int getY();

    EnumMap<OrderType, Locatable> getConnections();

    void makeSouthernConnection( Locatable locatable );

    void makeWesternConnection( Locatable locatable );

    void removeSouthernConnection();

    void removeWesternConnection();

    boolean connectionsContains( OrderType orderType);

    UUID getSpaceShipDeckID();

    void setSpaceShipDeckID( UUID spaceShipDeckID );

}
