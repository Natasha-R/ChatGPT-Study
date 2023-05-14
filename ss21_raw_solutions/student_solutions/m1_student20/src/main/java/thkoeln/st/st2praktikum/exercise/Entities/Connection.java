package thkoeln.st.st2praktikum.exercise.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Connection  {



    @Id
    protected UUID connection_Id;
    public Connection(){
        connection_Id = UUID.randomUUID();
    }

    public UUID getConnection_Id() {
        return connection_Id;
    }

    private SpaceShipDeck sourceSpaceShipDeck;

    public void setSourceSpaceShipDeck(SpaceShipDeck sourceSpaceShipDeck){
        this.sourceSpaceShipDeck = sourceSpaceShipDeck;
    }

    public SpaceShipDeck getSourceSpaceShipDeck(){
        return sourceSpaceShipDeck;
    }

    private SpaceShipDeck destinationSpaceShipDeck;

    public void setDestinationSpaceShipDeck(SpaceShipDeck destinationSpaceShipDeck){
        this.destinationSpaceShipDeck = destinationSpaceShipDeck;
    }

    public SpaceShipDeck getDestinationSpaceShipDeck(){
        return destinationSpaceShipDeck;
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
