package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class Field
{
    private UUID uuid;

    private Integer height, width;
    private ArrayList<Barrier> barriers;

    public Field(Integer height, Integer width)
    {
        uuid = UUID.randomUUID();

        this.height = height;
        this.width = width;
        barriers = new ArrayList<Barrier>();
    }

    public UUID getUUID()
    {
        return uuid;
    }

    public Integer getHeight()
    {
        return height;
    }

    public Integer getWidth()
    {
        return width;
    }

    public ArrayList<Barrier> getBarriers()
    {
        return barriers;
    }
}