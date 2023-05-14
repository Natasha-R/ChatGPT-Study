package thkoeln.st.st2praktikum.exercise.controllingentity;

import thkoeln.st.st2praktikum.exercise.entitys.Connection;
import thkoeln.st.st2praktikum.exercise.interfaces.Connectionable;
import thkoeln.st.st2praktikum.exercise.interfaces.Fieldable;
import thkoeln.st.st2praktikum.exercise.selfdefinedsupport.CoordinatePair;

import java.util.UUID;

public class ConnectionBuilder {
    public Connectionable createNewConnection (UUID sourceFieldId, CoordinatePair sourceCoordinate, UUID destinationFieldId, CoordinatePair destinationCoordinate){
        Connectionable connection =new Connection();
        connection.setDestinationFieldID(destinationFieldId);
        connection.setSourceFieldID(sourceFieldId);
        connection.setsourceCoordinate(sourceCoordinate);
        connection.setDestinationCoordinate(destinationCoordinate);
        return connection;
    }
    public boolean isGateAtField(Fieldable fild, CoordinatePair gate){
        return fild.getWidth() >= gate.getXCoordinate() && fild.getHeight() >= gate.getYCoordinate();
    }

    public Connectionable connectTwoFields (Fieldable sourceField,CoordinatePair sourceCoordinate, Fieldable destinationField,CoordinatePair destinationCoordinate){
        Connectionable connection=new Connection();
        if(isGateAtField(sourceField,sourceCoordinate)&&isGateAtField(destinationField,destinationCoordinate))
            connection=createNewConnection(sourceField.getFieldId(),sourceCoordinate,destinationField.getFieldId(),destinationCoordinate);
        else throw new IndexOutOfBoundsException("Das Feld für den Übergang befindet sich nicht auf dem Feld");

        return connection;
    }

}

