package thkoeln.st.st2praktikum.exercise.connection;

import thkoeln.st.st2praktikum.exercise.Identifiable;
import thkoeln.st.st2praktikum.exercise.Positionable;
import thkoeln.st.st2praktikum.exercise.room.Roomable;

public interface Connectable extends Identifiable
{
    public Roomable showConnectedDestinationRoom();

    public Positionable showStartConnectionPoint();

    public Positionable showDestinationCoordinate();

}
