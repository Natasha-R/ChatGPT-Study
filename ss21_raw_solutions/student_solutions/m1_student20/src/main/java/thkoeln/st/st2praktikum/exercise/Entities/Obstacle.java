package thkoeln.st.st2praktikum.exercise.Entities;

import javax.persistence.Id;
import java.util.UUID;


public class Obstacle {


    @Id
    protected UUID obstacle_Id;
    public Obstacle(){
        obstacle_Id = UUID.randomUUID();
    }

    public UUID getObstacle_Id() {
        return obstacle_Id;
    }
    private SpaceShipDeck spaceShipDeck;

    public void setSpaceShipDeck(SpaceShipDeck spaceShipDeck) {
        this.spaceShipDeck = spaceShipDeck;
    }

    public SpaceShipDeck getSpaceShipDeck() {
        return spaceShipDeck;
    }
    private Coordinates sourceCoordinates;

    public void setSourceCoordinates(Coordinates coordinates){
        this.sourceCoordinates = coordinates;
    }

    public Coordinates getSourceCoordinates(){return this.sourceCoordinates;}

    private Coordinates destinationCoordinates;

    public void setDestinationCoordinates(Coordinates coordinates){
        this.destinationCoordinates = coordinates;
    }

    public Coordinates getDestinationCoordinates(){return this.destinationCoordinates;}






}
