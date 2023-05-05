package thkoeln.st.st2praktikum.exercise.exception;

import java.util.UUID;

public class TransportSystemNotFoundException extends RuntimeException
{
    public TransportSystemNotFoundException(UUID uuid)
    {
        super(uuid.toString());
    }
}