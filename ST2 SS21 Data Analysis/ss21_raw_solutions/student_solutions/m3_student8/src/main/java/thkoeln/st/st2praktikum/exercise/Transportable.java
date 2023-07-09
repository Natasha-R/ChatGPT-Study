package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Transportable extends Movable
{
    public UUID showTransportableRoomID();

    public Roomable showRoom();

}
