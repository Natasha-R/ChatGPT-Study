package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Component
@Getter
public class Map {


    private final DeckRepository decks;

    private final TransportCategoryRepositroy categories;

    private final DroidRepository droids;

    @Autowired
    public Map(DeckRepository decks, TransportCategoryRepositroy categories, DroidRepository droids) {
        this.decks = decks;
        this.categories = categories;
        this.droids = droids;
    }

    public UUID addSpaceShipDeck(Integer height, Integer width) {
        final SpaceShipDeck spaceShipDeck = new SpaceShipDeck(height, width);
        this.decks.save(spaceShipDeck);
        return spaceShipDeck.getSpaceShipDeckId();
    }

    public void addWall(UUID spaceShipDeckId, Wall wall) {
        decks.findById(spaceShipDeckId).get().getWalls().add(wall);
    }


    public UUID addTransportCategory(String category) {
        final TransportCategory transportCategory = new TransportCategory(category);
        this.categories.save(transportCategory);
        return transportCategory.getTransportCategorieId();
    }

    public UUID addConnection(UUID transportId, UUID sourceSpaceShipDeckId, Coordinate sourceCoordinate, UUID destinationSpaceShipDeckId, Coordinate destinationCoordinate) {
        try {
             Connection connection = new Connection(transportId, sourceSpaceShipDeckId, destinationSpaceShipDeckId, sourceCoordinate, destinationCoordinate);

                this.decks.findById(sourceSpaceShipDeckId).get().getCoordinates().forEach(coordinate -> {
                    if (coordinate.equals(sourceCoordinate)){
                        coordinate.setConnection(connection);
                        System.out.println("Gesetzte connection: " + coordinate.getConnection());
                    }

            });
            return connection.getConnectionId();
        } catch (NoFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    public UUID addMaintenanceDroid(String name) {
        final MaintenanceDroid maintenanceDroid = new MaintenanceDroid(name);
        this.droids.save(maintenanceDroid);
        return maintenanceDroid.getMaintenanceDroidId();
    }

    static boolean endInDirection(OrderType orderType, MaintenanceDroid maintenanceDroid, Map map){
        switch (orderType){

            case NORTH:
                return maintenanceDroid.getCoordinate().getY() >= map.getDecks().findById(maintenanceDroid.getSpaceShipDeckId()).get().getHeight() -1;
            case WEST:
                return maintenanceDroid.getCoordinate().getX() <= 0;
            case SOUTH:
                return maintenanceDroid.getCoordinate().getY() <= 0;
            case EAST:
                return maintenanceDroid.getCoordinate().getX() >= map.getDecks().findById(maintenanceDroid.getSpaceShipDeckId()).get().getWidth() -1;
            default:
                return false;
        }
    }



    static boolean wallInDirection(OrderType orderType, MaintenanceDroid maintenanceDroid, Map map){
        for (Wall wall: map.getDecks().findById(maintenanceDroid.getSpaceShipDeckId()).get().getWalls()) {
            switch (wall.getPlumbLine()) {
                case VERTICAL:
                    switch (orderType) {
                        case EAST:
                            return wall.getStart().getX().equals(maintenanceDroid.getCoordinate().getX() + 1) && wall.getStart().getY() <= maintenanceDroid.getCoordinate().getY() && wall.getEnd().getY() >= maintenanceDroid.getCoordinate().getY() + 1;
                        case WEST:
                            return wall.getStart().getX().equals(maintenanceDroid.getCoordinate().getX()) && wall.getStart().getY() <= maintenanceDroid.getCoordinate().getY() && wall.getEnd().getY() >= maintenanceDroid.getCoordinate().getY() + 1;
                        default: return false;
                    }
                case HORIZONTAL:
                    switch (orderType) {
                        case NORTH:
                            return wall.getStart().getY().equals(maintenanceDroid.getCoordinate().getY() + 1) && wall.getStart().getX() <= maintenanceDroid.getCoordinate().getX() && wall.getEnd().getX() >= maintenanceDroid.getCoordinate().getX() + 1;
                        case SOUTH:
                            return wall.getStart().getY().equals(maintenanceDroid.getCoordinate().getX()) && wall.getStart().getX() <= maintenanceDroid.getCoordinate().getY() && wall.getEnd().getX() >= maintenanceDroid.getCoordinate().getX() + 1;
                        default:
                            return false;
                    }
                default:
                    return false;


            }

        }

        return false;

    }

    public Boolean executeCommand(UUID maintenanceDroidId,Order order) {
        MaintenanceDroid maintenanceDroid = this.droids.findById(maintenanceDroidId).get();
        OrderType orderType = order.getOrderType();
        switch(orderType){
            case WEST:
            case NORTH:
            case SOUTH:
            case EAST:
                return OrderService.orderWithPower(order, maintenanceDroid, this);

            case ENTER:
            case TRANSPORT:
                return OrderService.orderWithUUID(order, maintenanceDroid, this);
            default:
                return false;
        }
    }

    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        try {
            return droids.findById(maintenanceDroidId).get().getSpaceShipDeckId();
        } catch (NotSpawnedYetException e) {
            e.printStackTrace();
            return null;
        }
    }


    static boolean droidInDirection(OrderType orderType, MaintenanceDroid maintenanceDroid, Map map) throws NotSpawnedYetException, NoFieldException {
        switch (orderType) {
            case NORTH:
                //return new Coordinate(maintenanceDroid.getCoordinate().getX(), maintenanceDroid.getCoordinate().getY() +1).isBlocked();
                Integer nTempX = maintenanceDroid.getCoordinate().getX();
                Integer nTempY = maintenanceDroid.getCoordinate().getY() + 1;
                AtomicReference<Boolean> nBlock = new AtomicReference<>(false);
                map.getDecks().findById(maintenanceDroid.getSpaceShipDeckId()).get().getCoordinates().forEach(coordinate1 -> {
                        if (coordinate1.getX().equals(nTempX) && coordinate1.getY().equals(nTempY))
                            nBlock.set(coordinate1.isBlocked());
                    }
            );
            return nBlock.get();
            case SOUTH:
                //return new Coordinate(maintenanceDroid.getCoordinate().getX(), maintenanceDroid.getCoordinate().getY() - 1).isBlocked();
                Integer sTempX = maintenanceDroid.getCoordinate().getX();
                Integer sTempY = maintenanceDroid.getCoordinate().getY() - 1;
                AtomicReference<Boolean> sBlock = new AtomicReference<>(false);
                map.getDecks().findById(maintenanceDroid.getSpaceShipDeckId()).get().getCoordinates().forEach(coordinate1 -> {
                            if (coordinate1.getX().equals(sTempX) && coordinate1.getY().equals(sTempY))
                                sBlock.set(coordinate1.isBlocked());
                        }
                );
                return sBlock.get();
            case EAST:
                //return new Coordinate(maintenanceDroid.getCoordinate().getX() + 1, maintenanceDroid.getCoordinate().getY()).isBlocked();
                Integer eTempX = maintenanceDroid.getCoordinate().getX() + 1;
                Integer eTempY = maintenanceDroid.getCoordinate().getY();
                AtomicReference<Boolean> eBlock = new AtomicReference<>(false);
                map.getDecks().findById(maintenanceDroid.getSpaceShipDeckId()).get().getCoordinates().forEach(coordinate1 -> {
                            if (coordinate1.getX().equals(eTempX) && coordinate1.getY().equals(eTempY))
                                eBlock.set(coordinate1.isBlocked());
                        }
                );
                return eBlock.get();
            case WEST:
                //return new Coordinate(maintenanceDroid.getCoordinate().getX() - 1, maintenanceDroid.getCoordinate().getY()).isBlocked();
                Integer wTempX = maintenanceDroid.getCoordinate().getX() - 1;
                Integer wTempY = maintenanceDroid.getCoordinate().getY();
                AtomicReference<Boolean> wBlock = new AtomicReference<>(false);
                map.getDecks().findById(maintenanceDroid.getSpaceShipDeckId()).get().getCoordinates().forEach(coordinate1 -> {
                            if (coordinate1.getX().equals(wTempX) && coordinate1.getY().equals(wTempY))
                                wBlock.set(coordinate1.isBlocked());
                        }
                );
                return wBlock.get();
            default:
                throw new IllegalArgumentException(orderType.toString());
        }

    }

    public Coordinate getCoordinates(UUID maintenanceDroidId){
        try {
            return droids.findById(maintenanceDroidId).get().getCoordinate().getCoordinate();
        } catch (NotSpawnedYetException e) {
            e.printStackTrace();
            return null;
        }
    }



    public Coordinate getMaintenanceDroidCoordinate(UUID maintenanceDroidId) {
        return this.droids.findById(maintenanceDroidId).get().getCoordinate();
    }

}
