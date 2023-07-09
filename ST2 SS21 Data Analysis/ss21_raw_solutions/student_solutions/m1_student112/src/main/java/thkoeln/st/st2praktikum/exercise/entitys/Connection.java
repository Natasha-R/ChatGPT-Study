package thkoeln.st.st2praktikum.exercise.entitys;

import thkoeln.st.st2praktikum.exercise.interfaces.Connectionable;
import thkoeln.st.st2praktikum.exercise.selfdefinedsupport.CoordinatePair;

import javax.persistence.Id;
import java.util.UUID;


public class Connection implements Connectionable {

    @Id
    protected UUID connectionId;
    private UUID sourceFieldID;
    private CoordinatePair sourceCoordinate;
    private UUID destinationFieldID;
    private CoordinatePair destinationCoordinate;

    public Connection(){ connectionId = UUID.randomUUID();}
    //Getter-----------------------------------------------------------------------------------------
    @Override
    public UUID getConnectionId() {
        return connectionId;
    }
    @Override
    public UUID getSourceFieldID(){return sourceFieldID;}
    @Override
    public CoordinatePair getsourceCoordinate(){return sourceCoordinate;}
    @Override
    public UUID getDestinationFieldID(){return destinationFieldID;}
    @Override
    public CoordinatePair getdestinationCoordinate(){return destinationCoordinate;}

    //Setter-----------------------------------------------------------------------------------------
    @Override
    public void setSourceFieldID(UUID sourceFieldID){this.sourceFieldID =sourceFieldID;}
    @Override
    public void setsourceCoordinate(CoordinatePair sourceCoordinate){ this.sourceCoordinate=sourceCoordinate;}
    @Override
    public void setDestinationFieldID(UUID destinationFieldID){this.destinationFieldID = destinationFieldID;}
    @Override
    public void setDestinationCoordinate(CoordinatePair destinationCoordinate){this.destinationCoordinate=destinationCoordinate;}

}
