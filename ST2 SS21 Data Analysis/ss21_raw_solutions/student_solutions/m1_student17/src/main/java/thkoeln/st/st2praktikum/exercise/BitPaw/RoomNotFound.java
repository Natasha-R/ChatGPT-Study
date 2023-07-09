package thkoeln.st.st2praktikum.exercise.BitPaw;

import java.util.UUID;

public class RoomNotFound extends Exception
{
    public RoomNotFound(UUID uuid)
    {
        super("Room not found width UUID:" + uuid);
    }
}
