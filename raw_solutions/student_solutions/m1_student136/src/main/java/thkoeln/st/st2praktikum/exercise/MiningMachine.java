package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class MiningMachine implements Blockable
{
    public static final String zeroCoordinate = "(0,0)";


    private final UUID uuid;

    private boolean initialised;


    private String name;

    private Field field;
    private String coordinate;


    public MiningMachine(String name)
    {
        uuid = UUID.randomUUID();

        initialised = false;


        this.name = name;

        field = null;
        coordinate = zeroCoordinate;
    }


    @Override
    public boolean blocksMovementInDirection(int[] coordinates, Command direction, Field fieldInQuestion)
    {

        int[] movement = new int[2];
        if(direction.equals(Command.NORTH))
        {
            movement[1] = 1;
        }
        else if(direction.equals(Command.SOUTH))
        {
            movement[1] = -1;
        }
        else if(direction.equals(Command.EAST))
        {
            movement[0] = 1;
        }
        else if(direction.equals(Command.WEST))
        {
            movement[0] = -1;
        }

        // establish whether the Mining Machine is where we want to move
        if(this.isInitialised() && this.getField() .equals (fieldInQuestion))
        {
            int[] otherMiningMachine_coordinates = MiningMachineService.decodeCoordinates(this.getCoordinate());
            if (coordinates[0] + movement[0] == otherMiningMachine_coordinates[0] &&
                coordinates[1] + movement[1] == otherMiningMachine_coordinates[1])
            {
                return true;
            }
        }

        return false;
    }


    public UUID getUUID()
    {
        return uuid;
    }

    public boolean isInitialised()
    {
        return initialised;
    }
    public void initialise(Field field)
    {
        initialised = true;

        field.getBlockers().add(this);
        this.field = field;

        this.coordinate = zeroCoordinate;
    }

    public String getName()
    {
        return name;
    }

    public Field getField()
    {
        return field;
    }
    public void changeField(Field field)
    {
        this.field.getBlockers().remove(this);

        this.field = field;

        field.getBlockers().add(this);
    }

    public String getCoordinate()
    {
        return coordinate;
    }
    public void setCoordinate(String coordinate)
    {
        this.coordinate = coordinate;
    }
}