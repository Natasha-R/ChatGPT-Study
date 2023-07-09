package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class MiningMachine
{
    private UUID uuid;

    private boolean initialised;


    private String name;

    private Field field;
    private Vector2D coordinate;

    public MiningMachine(String name)
    {
        uuid = UUID.randomUUID();

        initialised = false;


        this.name = name;

        field = null;
        coordinate = new Vector2D(0, 0);
    }

    public UUID getUUID()
    {
        return uuid;
    }

    public boolean isInitialised()
    {
        return initialised;
    }
    public void setInitialised(boolean initialised)
    {
        this.initialised = initialised;
    }

    public String getName()
    {
        return name;
    }

    public Field getField()
    {
        return field;
    }
    public void setField(Field field)
    {
        this.field = field;
    }

    public Vector2D getCoordinate()
    {
        return new Vector2D(coordinate.getX(), coordinate.getY());
    }
    public void setCoordinate(Vector2D coordinate)
    {
        this.coordinate = coordinate;
    }
}