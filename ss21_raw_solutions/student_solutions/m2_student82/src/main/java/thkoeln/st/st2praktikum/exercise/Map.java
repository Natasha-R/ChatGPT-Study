package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.exceptions.NoFieldException;
import thkoeln.st.st2praktikum.exercise.exceptions.NotSpawnedYetException;

import java.util.HashMap;
import java.util.UUID;

public class Map implements Cloud {

    private final HashMap<UUID, Deck> decks = new HashMap();
    private final HashMap<UUID, Droid> droids = new HashMap();


    public UUID addSpaceShipDeck(Integer height, Integer width) {
        final Deck deck = new Deck(height, width);
        this.decks.put(deck.getID(), deck);
        return deck.getID();
    }

    public void addWall(UUID spaceShipDeckId, Wall wall) {
        final WallTranslator wallTranslator = new WallTranslator(spaceShipDeckId, wall);
        decks.get(spaceShipDeckId).getWallList().add(wallTranslator);
    }

    public UUID addConnection(UUID sourceSpaceShipDeckId, Coordinate sourceCoordinate, UUID destinationSpaceShipDeckId, Coordinate destinationCoordinate) {
        try {
            final Connection connection = new Connection(sourceSpaceShipDeckId, sourceCoordinate, destinationSpaceShipDeckId, destinationCoordinate);
            this.decks.get(sourceSpaceShipDeckId).getFieldFromCoordinate(sourceCoordinate).setConnection(connection);
            return connection.getID();
        } catch (NoFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    public UUID addMaintenanceDroid(String name) {
        final Droid droid = new Droid(name);
        this.droids.put(droid.getID(), droid);
        return droid.getID();
    }

    public Boolean executeCommand(UUID maintenanceDroidId,Order order) {
        Droid droid = this.droids.get(maintenanceDroidId);
        OrderType orderType = order.getOrderType();
        switch(orderType){
            case WEST:
            case NORTH:
            case SOUTH:
            case EAST:
                return OrderService.orderWithPower(order, droid, this);

            case ENTER:
            case TRANSPORT:
                return OrderService.orderWithUUID(order, droid, this);
            default:
                return false;
        }
    }

    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        try {
            return droids.get(maintenanceDroidId).getPosition().getDeckId();
        } catch (NotSpawnedYetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Coordinate getCoordinates(UUID maintenanceDroidId){
        try {
            return droids.get(maintenanceDroidId).getPosition().getCoordinate();
        } catch (NotSpawnedYetException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public HashMap<UUID,Deck> getDecks() {
        return this.decks;
    }

    @Override
    public HashMap<UUID,Droid> getDroids() {
        return this.droids;
    }
}
