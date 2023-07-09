package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection
{
    private UUID uuid;

    private Field source, destination;
    private Vector2D sourceCoordinate, destinationCoordinate;

    public Connection(Field source, Vector2D sourceCoordinate, Field destination, Vector2D destinationCoordinate)
    {
        uuid = UUID.randomUUID();

        this.source = source;
        this.sourceCoordinate = sourceCoordinate;
        this.destination = destination;
        this.destinationCoordinate = destinationCoordinate;
    }

    public UUID getUUID()
    {
        return uuid;
    }

    public Field getSource()
    {
        return source;
    }

    public Field getDestination()
    {
        return destination;
    }

    public Vector2D getSourceCoordinate()
    {
        return sourceCoordinate;
    }

    public Vector2D getDestinationCoordinate()
    {
        return destinationCoordinate;
    }
}