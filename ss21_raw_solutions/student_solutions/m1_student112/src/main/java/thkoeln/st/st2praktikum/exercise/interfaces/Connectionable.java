package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.selfdefinedsupport.CoordinatePair;

import java.util.UUID;

public interface Connectionable {

    //Getter-----------------------------------------------------------------------------------------
    UUID getConnectionId();

    UUID getSourceFieldID();

    CoordinatePair getsourceCoordinate();

    UUID getDestinationFieldID();

    CoordinatePair getdestinationCoordinate();

    void setSourceFieldID(UUID sourceFieldID);

    void setsourceCoordinate(CoordinatePair sourceCoordinate);

    void setDestinationFieldID(UUID destinationFieldID);

    void setDestinationCoordinate(CoordinatePair destinationCoordinate);
}
