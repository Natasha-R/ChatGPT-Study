package thkoeln.st.st2praktikum.exercise;

public interface Connectable extends Identifiable
{
    public Roomable showConnectedDestinationRoom();

    public Positionable showStartConnectionPoint();

    public Positionable showDestinationCoordinate();

}
