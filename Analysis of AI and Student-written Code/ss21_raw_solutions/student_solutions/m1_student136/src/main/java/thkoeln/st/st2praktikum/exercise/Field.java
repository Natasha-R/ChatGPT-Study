package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class Field
{
    private final UUID uuid;

    private Integer height, width;
    private ArrayList<Blockable> blockers;


    public Field(Integer height, Integer width)
    {
        uuid = UUID.randomUUID();

        this.height = height;
        this.width = width;
        blockers = new ArrayList<Blockable>();
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

    public ArrayList<Blockable> getBlockers()
    {
        return blockers;
    }
}