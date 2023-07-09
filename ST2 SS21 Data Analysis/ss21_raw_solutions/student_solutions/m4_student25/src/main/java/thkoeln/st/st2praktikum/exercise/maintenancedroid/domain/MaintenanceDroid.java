package thkoeln.st.st2praktikum.exercise.maintenancedroid.domain;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
public class MaintenanceDroid {
    @Id
    private final UUID maintenanceDroidID = UUID.randomUUID();
    @Setter
    private String name;
    @Setter
    private UUID spaceShipDeckID;
    @Setter
    @OneToOne
    private Square square;
    @Setter
    @Transient
    private List<Order> orders = new ArrayList<>();

    public UUID getSpaceShipDeckId(){
        return this.spaceShipDeckID;
    }

    public Coordinate getCoordinate(){
        return this.square.getCoordinates();
    }

    protected MaintenanceDroid() {
    }

    public MaintenanceDroid(String name) {
        this.name = name;
    }

    public Boolean isStepCommand(Command command, MaintenanceDroidRepository maintenanceDroidRepository, SpaceShipDeckRepository spaceShipDeckRepository) throws MaintenanceDroidPositionIsNull, SquareNotFoundException, IllegalArgumentException {
        for (int i = 0; i < command.getSteps(); i++) {
            SpaceShipDeck tmpSpaceShipDeck = spaceShipDeckRepository.findById(spaceShipDeckID).get();
            Coordinate coordinateMaintenanceDroid = this.getCoordinate();
            Obstacle tmpMyObstacle;
            switch (command.getDirection()) {
                case NO:
                    if (coordinateMaintenanceDroid.getY() + 1 == tmpSpaceShipDeck.getHeight()) break;
                    tmpMyObstacle = new Obstacle(new Coordinate(coordinateMaintenanceDroid.getX(), coordinateMaintenanceDroid.getY() + 1), new Coordinate(coordinateMaintenanceDroid.getX() + 1, coordinateMaintenanceDroid.getY() + 1));
                    if (!tmpSpaceShipDeck.contains(tmpMyObstacle) || tmpSpaceShipDeck.isSquareOccupied(command.getDirection(), this.getSquare().getSquareID(), this.getMaintenanceDroidID(), maintenanceDroidRepository)) {
                        this.setSquare(tmpSpaceShipDeck.getNextSquareinDirection(command.getDirection(), this.getSquare().getSquareID()));
                    }
                    break;
                case EA:
                    if (coordinateMaintenanceDroid.getX() + 1 == tmpSpaceShipDeck.getWidth()) break;
                    tmpMyObstacle = new Obstacle(new Coordinate(coordinateMaintenanceDroid.getX() + 1, coordinateMaintenanceDroid.getY()), new Coordinate(coordinateMaintenanceDroid.getX() + 1, coordinateMaintenanceDroid.getY() + 1));
                    if (!tmpSpaceShipDeck.contains(tmpMyObstacle) || tmpSpaceShipDeck.isSquareOccupied(command.getDirection(), this.getSquare().getSquareID(), this.getMaintenanceDroidID(), maintenanceDroidRepository)) {
                        this.setSquare(tmpSpaceShipDeck.getNextSquareinDirection(command.getDirection(), this.getSquare().getSquareID()));
                    }
                    break;
                case SO:
                    if (coordinateMaintenanceDroid.getY() - 1 == -1) break;
                    tmpMyObstacle = new Obstacle(new Coordinate(coordinateMaintenanceDroid.getX(), coordinateMaintenanceDroid.getY()), new Coordinate(coordinateMaintenanceDroid.getX() + 1, coordinateMaintenanceDroid.getY()));
                    if (!tmpSpaceShipDeck.contains(tmpMyObstacle) || tmpSpaceShipDeck.isSquareOccupied(command.getDirection(), this.getSquare().getSquareID(), this.getMaintenanceDroidID(), maintenanceDroidRepository)) {
                        this.setSquare(tmpSpaceShipDeck.getNextSquareinDirection(command.getDirection(), this.getSquare().getSquareID()));
                    }
                    break;
                case WE:
                    if (coordinateMaintenanceDroid.getX() - 1 == -1) break;
                    tmpMyObstacle = new Obstacle(new Coordinate(coordinateMaintenanceDroid.getX(), coordinateMaintenanceDroid.getY()), new Coordinate(coordinateMaintenanceDroid.getX(), coordinateMaintenanceDroid.getY() + 1));
                    if (!tmpSpaceShipDeck.contains(tmpMyObstacle) || tmpSpaceShipDeck.isSquareOccupied(command.getDirection(), this.getSquare().getSquareID(), this.getMaintenanceDroidID(), maintenanceDroidRepository)) {
                        this.setSquare(tmpSpaceShipDeck.getNextSquareinDirection(command.getDirection(), this.getSquare().getSquareID()));
                    }
                    break;
                default:
                    throw new IllegalArgumentException(command.getDirection().toString());
            }
        }
        return true;
    }

    public Boolean isUUIDCommand(Command command, SpaceShipDeckRepository spaceShipDeckRepository, MaintenanceDroidRepository maintenanceDroidRepository) throws MaintenanceDroidPositionIsNull, SquareNotFoundException, IllegalArgumentException, IllegalStateException {
        switch (command.getDirection()) {
            case TR:
                Square tmpSquare = this.getSquare();
                if (tmpSquare.hasConnection()) {
                    if (!spaceShipDeckRepository.findById(command.getSpaceShipDeckUUID()).get().isTransportOccupied(spaceShipDeckRepository.findById(command.getSpaceShipDeckUUID()).get().findSquareAt(tmpSquare.getConnection().getDestinationCoordinate()).getSquareID(), this.getMaintenanceDroidID(), maintenanceDroidRepository)) {
                        this.setSpaceShipDeckID(command.getSpaceShipDeckUUID());
                        this.setSquare(spaceShipDeckRepository.findById(command.getSpaceShipDeckUUID()).get().findSquareAt(this.getSquare().getConnection().getDestinationCoordinate()));
                        return true;
                    }
                }
                break;
            case EN:
                Square squareTmp = spaceShipDeckRepository.findById(command.getSpaceShipDeckUUID()).get().findSquareAt(new Coordinate(0, 0));
                if (!spaceShipDeckRepository.findById(command.getSpaceShipDeckUUID()).get().isTransportOccupied(squareTmp.getSquareID(), this.getMaintenanceDroidID(), maintenanceDroidRepository)) {
                    this.setSpaceShipDeckID(command.getSpaceShipDeckUUID());
                    this.setSquare(squareTmp);
                    return true;
                }
                break;
            default:
                throw new IllegalArgumentException(command.getDirection().toString());

        }
        throw new IllegalStateException("isUUIDCommand Failure");
    }

}
