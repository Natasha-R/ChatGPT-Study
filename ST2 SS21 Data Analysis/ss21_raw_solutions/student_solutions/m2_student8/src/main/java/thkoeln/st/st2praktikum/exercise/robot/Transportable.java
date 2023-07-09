package thkoeln.st.st2praktikum.exercise.robot;

import thkoeln.st.st2praktikum.exercise.room.Roomable;

import java.util.UUID;

public interface Transportable extends Movable
{
    public UUID showTransportableRoomID();

    public Roomable showRoom();

}
