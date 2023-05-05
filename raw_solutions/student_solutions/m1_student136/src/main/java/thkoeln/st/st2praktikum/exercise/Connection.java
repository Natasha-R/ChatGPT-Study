package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection
{
    private final UUID uuid;

    private Field entryField, destinationField;
    private String entryCoordinate, destinationCoordinate;


    public Connection(Field entryField, String entryCoordinate, Field destinationField, String destinationCoordinate)
    {
        uuid = UUID.randomUUID();

        this.entryField = entryField;
        this.entryCoordinate = entryCoordinate;
        this.destinationField = destinationField;
        this.destinationCoordinate = destinationCoordinate;
    }


    public UUID getUUID()
    {
        return uuid;
    }

    public Field getEntryField()
    {
        return entryField;
    }

    public Field getDestinationField()
    {
        return destinationField;
    }

    public String getEntryCoordinate()
    {
        return entryCoordinate;
    }

    public String getDestinationCoordinate()
    {
        return destinationCoordinate;
    }
}