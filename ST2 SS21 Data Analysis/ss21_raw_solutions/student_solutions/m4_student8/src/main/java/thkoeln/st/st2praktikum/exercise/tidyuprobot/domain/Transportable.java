package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import thkoeln.st.st2praktikum.exercise.room.domain.Roomable;

import java.util.UUID;

public interface Transportable extends Movable
{
    public UUID showTransportableRoomID();

    public Roomable showRoom();

}
