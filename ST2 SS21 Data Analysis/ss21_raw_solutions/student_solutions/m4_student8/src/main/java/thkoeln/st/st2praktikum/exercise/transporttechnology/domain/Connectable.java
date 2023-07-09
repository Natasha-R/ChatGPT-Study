package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import thkoeln.st.st2praktikum.exercise.Identifiable;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Positionable;

import java.util.UUID;

public interface Connectable extends Identifiable
{
    public UUID showConnectedDestinationRoom();

    public Positionable showStartConnectionPoint();

    public Positionable showDestinationCoordinate();

    public UUID getDestationRoomID();
}
