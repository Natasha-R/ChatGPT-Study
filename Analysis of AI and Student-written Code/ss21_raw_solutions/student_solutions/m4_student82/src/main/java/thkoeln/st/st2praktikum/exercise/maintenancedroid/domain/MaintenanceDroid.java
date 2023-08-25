package thkoeln.st.st2praktikum.exercise.maintenancedroid.domain;

import lombok.*;
import org.dom4j.tree.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.Exception.NoConnectionException;
import thkoeln.st.st2praktikum.exercise.Exception.NoFieldException;
import thkoeln.st.st2praktikum.exercise.Exception.NotSpawnedYetException;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Decision;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderType;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.DeckRepository;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Wall;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceDroid extends AbstractEntity  {

    @Transient
    List<Order> orders = new ArrayList<>();

    @Id
    private final UUID maintenanceDroidId = UUID.randomUUID();

    @Setter
    private String name;

    private boolean spawned;

    @Embedded
    @Setter
    private Coordinate coordinate;

    @Setter
    private UUID spaceShipDeckId;

    public MaintenanceDroid(String name) {
        this.name = name;
        this.spawned = false;
        this.coordinate = new Coordinate(150, 150);
    }

    public MaintenanceDroid getDroid(UUID droidId) {
        return this;
    }

    public boolean isSpawned() {
        return this.spawned;
    }

    public boolean move(OrderType orderType, DeckRepository decks) throws NotSpawnedYetException, NoFieldException {


        switch (orderType) {
            case NORTH:
                Coordinate nextNorth = new Coordinate(this.coordinate.getX(), this.coordinate.getY() + 1);
                changeBlockedStatus();
                decks.findById(this.spaceShipDeckId).get().getCoordinates().forEach(coordinate1 -> {
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
                decks.findById(this.spaceShipDeckId).get().getCoordinates().forEach(coordinate1 -> {
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
                decks.findById(this.spaceShipDeckId).get().getCoordinates().forEach(coordinate1 -> {
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
                decks.findById(this.spaceShipDeckId).get().getCoordinates().forEach(coordinate1 -> {
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

    public Decision canGoForward(OrderType orderType, DeckRepository decks) throws NotSpawnedYetException, NoFieldException {
        if(wallInDirection(orderType, this, decks) || droidInDirection(orderType, this, decks) || endInDirection(orderType, this, decks)){
            return Decision.CANT_GO_FORWARD;
        }else{
            return  Decision.CAN_GO_FORWARD;
        }

    }

    private void changeBlockedStatus() throws NoFieldException {
        this.coordinate.changeBlocked();
    }

    public boolean travel(DeckRepository decks) throws NoFieldException, NoConnectionException {


        if(this.coordinate.hasConnection()){
            if(!this.coordinate.getConnection().getDestinationCoordinate().isBlocked()){
                changeBlockedStatus();


                System.out.println("Erste DeckId der Coordinate: " + this.spaceShipDeckId);
                this.spaceShipDeckId = this.coordinate.getConnection().getDestinationDeckId();
                System.out.println("Zweite DeckId der Coordinate: " + this.spaceShipDeckId);

                Coordinate destTemp = this.coordinate.getConnection().getDestinationCoordinate();
                decks.findById(this.coordinate.getConnection().getDestinationDeckId()).get().getCoordinates().forEach(coordinate1 -> {
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

    public boolean spawn(DeckRepository decks, UUID spaceShipId) throws NoFieldException {
        if(!decks.findById(spaceShipId).get().getCoordinates().get(0).isBlocked()){
            this.coordinate = decks.findById(spaceShipId).get().getCoordinates().get(0);
            changeBlockedStatus();
            this.spaceShipDeckId = spaceShipId;
            this.spawned = true;
            return true;
        }else{
            return false;
        }
    }

    public static boolean endInDirection(OrderType orderType, MaintenanceDroid maintenanceDroid, DeckRepository decks){
        switch (orderType){

            case NORTH:
                return maintenanceDroid.getCoordinate().getY() >= decks.findById(maintenanceDroid.getSpaceShipDeckId()).get().getHeight() -1;
            case WEST:
                return maintenanceDroid.getCoordinate().getX() <= 0;
            case SOUTH:
                return maintenanceDroid.getCoordinate().getY() <= 0;
            case EAST:
                return maintenanceDroid.getCoordinate().getX() >= decks.findById(maintenanceDroid.getSpaceShipDeckId()).get().getWidth() -1;
            default:
                return false;
        }
    }



    public static boolean wallInDirection(OrderType orderType, MaintenanceDroid maintenanceDroid, DeckRepository decks){
        for (Wall wall: decks.findById(maintenanceDroid.getSpaceShipDeckId()).get().getWalls()) {
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






    public static boolean droidInDirection(OrderType orderType, MaintenanceDroid maintenanceDroid, DeckRepository decks) throws NotSpawnedYetException, NoFieldException {
        switch (orderType) {
            case NORTH:
                //return new Coordinate(maintenanceDroid.getCoordinate().getX(), maintenanceDroid.getCoordinate().getY() +1).isBlocked();
                Integer nTempX = maintenanceDroid.getCoordinate().getX();
                Integer nTempY = maintenanceDroid.getCoordinate().getY() + 1;
                AtomicReference<Boolean> nBlock = new AtomicReference<>(false);
                decks.findById(maintenanceDroid.getSpaceShipDeckId()).get().getCoordinates().forEach(coordinate1 -> {
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
                decks.findById(maintenanceDroid.getSpaceShipDeckId()).get().getCoordinates().forEach(coordinate1 -> {
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
                decks.findById(maintenanceDroid.getSpaceShipDeckId()).get().getCoordinates().forEach(coordinate1 -> {
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
                decks.findById(maintenanceDroid.getSpaceShipDeckId()).get().getCoordinates().forEach(coordinate1 -> {
                            if (coordinate1.getX().equals(wTempX) && coordinate1.getY().equals(wTempY))
                                wBlock.set(coordinate1.isBlocked());
                        }
                );
                return wBlock.get();
            default:
                throw new IllegalArgumentException(orderType.toString());
        }

    }
}
