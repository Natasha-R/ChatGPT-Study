package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Barrier implements Blockable
{
    private final UUID uuid;
    private final UUID fieldId;

    private String[] endpoints;


    public Barrier(UUID fieldId, String barrierString)
    {
        uuid = UUID.randomUUID();
        this.fieldId = fieldId;

        String[] points = barrierString.split("-");
        endpoints = points;
    }

    public Barrier(UUID fieldId, String endpoint1, String endpoint2)
    {
        uuid = UUID.randomUUID();
        this.fieldId = fieldId;

        endpoints = new String[2];
        endpoints[0] = endpoint1;
        endpoints[1] = endpoint2;
    }


    @Override
    public boolean blocksMovementInDirection(int[] coordinates, Command direction, Field fieldInQuestion)
    {
        int[] barrierOffset = new int[2];
        int axis = 0;
        if(direction.equals(Command.NORTH))
        {
            barrierOffset[1] = 1;
            axis = 1;
        }
        else if(direction.equals(Command.SOUTH))
        {
            barrierOffset[1] = 0;
            axis = 1;
        }
        else if(direction.equals(Command.EAST))
        {
            barrierOffset[0] = 1;
            axis = 0;
        }
        else if(direction.equals(Command.WEST))
        {
            barrierOffset[0] = 0;
            axis = 0;
        }

        int[][] endpoints = new int[2][];
        endpoints[0] = MiningMachineService.decodeCoordinates(this.getEndpoints()[0]);
        endpoints[1] = MiningMachineService.decodeCoordinates(this.getEndpoints()[1]);

        // establish whether the barrier is where we want to move
        if (endpoints[0][axis] == endpoints[1][axis] &&
            endpoints[0][axis] == coordinates[axis] + barrierOffset[axis] &&
          ((endpoints[0][(axis+1)%2] < coordinates[0] && endpoints[1][(axis+1)%2] > coordinates[0]) ||
           (endpoints[0][(axis+1)%2] > coordinates[0] && endpoints[1][(axis+1)%2] < coordinates[0])) )
        {
            return true;
        }

        return false;
    }


    public UUID getUuid()
    {
        return uuid;
    }

    public String[] getEndpoints()
    {
        return endpoints;
    }
}