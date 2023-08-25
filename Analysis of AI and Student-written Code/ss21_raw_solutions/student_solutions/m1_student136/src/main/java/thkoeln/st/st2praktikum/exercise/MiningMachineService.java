package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class MiningMachineService
{
    private ArrayList<Field> fields;
    private ArrayList<Connection> connections;
    private ArrayList<MiningMachine> miningMachines;


    public MiningMachineService()
    {
        fields = new ArrayList<Field>();
        connections = new ArrayList<Connection>();
        miningMachines = new ArrayList<MiningMachine>();
    }

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width)
    {
        Field field = new Field(height, width);
        fields.add(field);
        return field.getUUID();
    }

    /**
     * This method adds a barrier to a given field.
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrierString the end points of the barrier, encoded as a String, eg. "(2,3)-(10,3)" for a barrier that spans from (2,3) to (10,3)
     */
    public void addBarrier(UUID fieldId, String barrierString)
    {
        Barrier barrier = new Barrier(fieldId, barrierString);
        getFieldByUUID(fieldId).getBlockers().add(barrier);
    }

    /**
     * This method adds a traversable connection between two fields. Connections only work in one direction.
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceFieldId, String sourceCoordinate, UUID destinationFieldId, String destinationCoordinate)
    {
        Connection connection = new Connection(getFieldByUUID(sourceFieldId), sourceCoordinate, getFieldByUUID(destinationFieldId), destinationCoordinate);
        connections.add(connection);
        return connection.getUUID();
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name)
    {
        MiningMachine miningMachine = new MiningMachine(name);
        miningMachines.add(miningMachine);
        return miningMachine.getUUID();
    }

    /**
     * This method lets the mining machine execute a command.
     * @param miningMachineId the ID of the mining machine
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,field-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on tiles with a connection to another field
     * "[en,<field-id>]" for setting the initial field where a mining machine is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a barrier or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, String commandString)
    {
        MiningMachine miningMachine = getMiningMachineByUUID(miningMachineId);

        String[] currentCommand = decodeCommand(commandString);
        Command command = Command.fromString(currentCommand[0]);


        if(!miningMachine.isInitialised()) // if mining machine isn't initialised
        {
            if(command.equals(Command.ENTER)) // initial mining machine placement
            {
                return executeCommandEnter(miningMachine, getFieldByUUID(currentCommand[1]));
            }
        }
        else // if mining machine is initialised
        {
            if(command.equals(Command.TRANSPORT)) // transport
            {
                return executeCommandTransport(miningMachine, getFieldByUUID(currentCommand[1]));
            }
            else // movement
            {
                return executeCommandMovement(miningMachine, Integer.parseInt(currentCommand[1]), command);
            }
        }

        return false;
    }



    private boolean executeCommandEnter(MiningMachine miningMachine, Field desiredField)
    {
        // check if the spawn-point of the field is occupied by another mining machine
        for(MiningMachine otherMiningMachine : miningMachines)
        {
            if(otherMiningMachine != null && otherMiningMachine.isInitialised() && otherMiningMachine.getField().equals(desiredField) && otherMiningMachine.getCoordinate().equals(MiningMachine.zeroCoordinate))
            {
                return false;
            }
        }

        miningMachine.initialise(desiredField);

        return true;
    }

    private boolean executeCommandTransport(MiningMachine miningMachine, Field desiredField)
    {
        for(Connection connection : connections)
        {
            if ((connection.getEntryField()) .equals (miningMachine.getField()) &&
                (connection.getEntryCoordinate()) .equals (miningMachine.getCoordinate()) &&
                (connection.getDestinationField()) .equals (desiredField))
            {
                // check for if another mining machine occupies the destination coordinates of the connection
                for(MiningMachine otherMiningMachine : miningMachines)
                {
                    if(!otherMiningMachine .equals (miningMachine) &&
                        otherMiningMachine.isInitialised() &&
                        otherMiningMachine.getField() .equals (connection.getDestinationField()) &&
                        otherMiningMachine.getCoordinate() .equals (connection.getDestinationCoordinate()))
                    {
                        // the destination coordinates are occupied
                        return false;
                    }
                }

                // move the mining machine to the other end of the connection
                miningMachine.changeField(connection.getDestinationField());
                miningMachine.setCoordinate(connection.getDestinationCoordinate());

                return true;
            }
        }

        // No connection found - command failed
        return false;
    }

    private boolean executeCommandMovement(MiningMachine miningMachine, int steps, Command direction)
    {
        int[] coordinates = decodeCoordinates(miningMachine.getCoordinate());
        Field fieldInQuestion = miningMachine.getField();

        int[] movement = new int[2];
        int[] barrierOffset = new int[2];
        int axis = 0;
        if(direction.equals(Command.NORTH))
        {
            barrierOffset[1] = 1;
            movement[1] = 1;
            axis = 1;
        }
        else if(direction.equals(Command.SOUTH))
        {
            barrierOffset[1] = 0;
            movement[1] = -1;
            axis = 1;
        }
        else if(direction.equals(Command.EAST))
        {
            barrierOffset[0] = 1;
            movement[0] = 1;
            axis = 0;
        }
        else if(direction.equals(Command.WEST))
        {
            barrierOffset[0] = 0;
            movement[0] = -1;
            axis = 0;
        }


        // for every step...
        while(steps > 0)
        {
            // check for collision with any Blockables
            for(Blockable blocker : fieldInQuestion.getBlockers())
            {
                if(blocker.blocksMovementInDirection(coordinates, direction, fieldInQuestion))
                    return true;
            }

            // check boundaries
            if (coordinates[0] + movement[0] < 0 || coordinates[0] + movement[0] > miningMachine.getField().getWidth()  - 1 ||
                coordinates[1] + movement[1] < 0 || coordinates[1] + movement[1] > miningMachine.getField().getHeight() - 1)
            {
                return true;
            }

            // since collision didn't occur: set the successfully calculated coordinates
            coordinates[0] += movement[0];
            coordinates[1] += movement[1];
            miningMachine.setCoordinate(encodeCoordinates(coordinates));

            steps--;
        }

        return true;
    }



    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId)
    {
        return getMiningMachineByUUID(miningMachineId).getField().getUUID();
    }

    /**
     * This method returns the coordinates a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the coordiantes of the mining machine encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID miningMachineId)
    {
        return getMiningMachineByUUID(miningMachineId).getCoordinate();
    }



    private Field getFieldByUUID(UUID id)
    {
        for(Field field : fields)
        {
            if(field.getUUID().compareTo(id) == 0)
                return field;
        }
        return null;
    }
    private Field getFieldByUUID(String uuid)
    {
        return getFieldByUUID(UUID.fromString(uuid));
    }

    private Connection getConnectionByUUID(UUID id)
    {
        for(Connection connection : connections)
        {
            if(connection.getUUID().compareTo(id) == 0)
                return connection;
        }
        return null;
    }
    private Connection getConnectionByUUID(String uuid)
    {
        return getConnectionByUUID(UUID.fromString(uuid));
    }

    private MiningMachine getMiningMachineByUUID(UUID id)
    {
        for(MiningMachine miningMachine : miningMachines)
        {
            if(miningMachine.getUUID().compareTo(id) == 0)
                return miningMachine;
        }
        return null;
    }
    private MiningMachine getMiningMachineByUUID(String uuid)
    {
        return getMiningMachineByUUID(UUID.fromString(uuid));
    }



    public static String encodeCoordinates(int x, int y)
    {
        return "(" + x + "," + y + ")";
    }

    public static String encodeCoordinates(int[] coordinates)
    {
        return "(" + coordinates[0] + "," + coordinates[1] + ")";
    }

    public static int[] decodeCoordinates(String encoded)
    {
        String[] decodedString = encoded.substring(1, encoded.length()-1).split(",");

        return new int[] {
                Integer.parseInt(decodedString[0]),
                Integer.parseInt(decodedString[1])};
    }

    public static String encodeCommand(String command, String parameter)
    {
        return "[" + command + "," + parameter + "]";
    }

    public static String[] decodeCommand(String encoded)
    {
        String[] decodedString = encoded.substring(1, encoded.length()-1).split(",");

        return new String[] {
                decodedString[0],
                decodedString[1]};
    }
}
