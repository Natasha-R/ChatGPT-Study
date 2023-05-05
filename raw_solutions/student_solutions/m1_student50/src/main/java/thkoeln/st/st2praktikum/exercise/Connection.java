package thkoeln.st.st2praktikum.exercise;
import java.util.UUID;


public class Connection extends AbstractEntity{

    private UUID sourceRoom;
    private UUID destinationRoom;

    private Pair sourceCoordinate;
    private Pair  destinationCoordinate;

    public UUID getDestinationRoom(){ return destinationRoom; }
    public void setDestinationRoom(UUID destinationRoom){ this.destinationRoom = destinationRoom; }

    public UUID getSourceRoom(){ return sourceRoom; }
    public void setSourceRoom(UUID sourceRoom){ this.sourceRoom = sourceRoom; }

    public Pair getSourceCoordinate(){ return sourceCoordinate; }
    public void setSourceCoordinate(Pair  sourceCoordinate){ this.sourceCoordinate = sourceCoordinate; }

    public Pair  getDestinationCoordinate(){ return destinationCoordinate; }
    public void setDestinationCoordinate(Pair  destinationCoordinate){ this.destinationCoordinate = destinationCoordinate; }

}

