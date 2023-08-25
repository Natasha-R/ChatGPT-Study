package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;


@Service
public class MiningMachineService
{

    private ArrayList<Field> fields;
    private ArrayList<Connection> connections;
    private ArrayList<MiningMachine> miningMachines;
    private ArrayList<TransportCategory> transportCategories;

    private FieldRepository fieldRepository;
    private ConnectionRepository connectionRepository;
    private MiningMachineRepository miningMachineRepository;
    private TransportCategoryRepository transportCategoryRepository;

    @Autowired
    public MiningMachineService(FieldRepository fieldRepository, ConnectionRepository connectionRepository, MiningMachineRepository miningMachineRepository, TransportCategoryRepository transportCategoryRepository)
    {
        fields = new ArrayList<Field>();
        connections = new ArrayList<Connection>();
        miningMachines = new ArrayList<MiningMachine>();
        transportCategories = new ArrayList<TransportCategory>();

        this.fieldRepository = fieldRepository;
        this.connectionRepository = connectionRepository;
        this.miningMachineRepository = miningMachineRepository;
        this.transportCategoryRepository = transportCategoryRepository;
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
        return field.getUuid();
    }

    /**
     * This method adds a barrier to a given field.
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID fieldId, Barrier barrier)
    {
        getFieldByUUID(fieldId).getBarriers().add(barrier);
    }

    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category)
    {
        TransportCategory transportCategory = new TransportCategory(category);
        transportCategories.add(transportCategory);
        return transportCategory.getUuid();
    }

    /**
     * This method adds a traversable connection between two fields based on a transport category. Connections only work in one direction.
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourceVector2D the vector-2D of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationVector2D the vector-2D of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId, UUID sourceFieldId, Vector2D sourceVector2D, UUID destinationFieldId, Vector2D destinationVector2D)
    {
        Connection connection = new Connection(getTransportCategoryByUUID(transportCategoryId), getFieldByUUID(sourceFieldId), sourceVector2D, getFieldByUUID(destinationFieldId), destinationVector2D);
        connections.add(connection);
        return connection.getUuid();
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
        return miningMachine.getUuid();
    }

    /**
     * This method lets the mining machine execute a order.
     * @param miningMachineId the ID of the mining machine
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on tiles with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a barrier or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Order order)
    {
        MiningMachine miningMachine = getMiningMachineByUUID(miningMachineId);

        if(!miningMachine.isInitialised())
        {
            // initial mining machine placement
            if(OrderType.ENTER == order.getOrderType())
            {
                Field desiredField = getFieldByUUID(order.getFieldId());

                // check if the spawn-point of the field is occupied by another mining machine
                for(MiningMachine thisMiningMachine : miningMachines)
                {
                    if(thisMiningMachine.getField() != null && thisMiningMachine.getField().equals(desiredField) && thisMiningMachine.getCoordinate().equals(MiningMachine.zeroCoordinate))
                    {
                        manageDatabase();
                        return false;
                    }
                }

                miningMachine.setInitialised(true);

                miningMachine.setField(desiredField);
                miningMachine.setCoordinate(MiningMachine.zeroCoordinate);

                manageDatabase();
                return true;
            }
        }
        else // if mining machine is initialised
        {
            if(OrderType.TRANSPORT == order.getOrderType())
            {
                for(Connection connection : connections)
                {
                    // if the mining machine is on the correct field AND the coordinates match with the entry point of the connection
                    if (connection.getSource().equals(miningMachine.getField()) &&
                            connection.getSourceCoordinate().equals(miningMachine.getCoordinate()))
                    {
                        // check for if another mining machine occupies the destination coordinates of the connection
                        for(MiningMachine thisMiningMachine : miningMachines)
                        {
                            // if it's a different mining machine AND it's initialised AND it's on the destination field AND it's at the destination coordinates of the connection
                            if(!thisMiningMachine.equals(miningMachine) && thisMiningMachine.isInitialised() && thisMiningMachine.getField().equals(connection.getDestination()) && thisMiningMachine.getCoordinate().equals(connection.getDestinationCoordinate()))
                            {
                                // the destination coordinates are occupied
                                manageDatabase();
                                return false;
                            }
                        }

                        // move the mining machine to the other end of the connection
                        miningMachine.setField(connection.getDestination());
                        miningMachine.setCoordinate(connection.getDestinationCoordinate());

                        manageDatabase();
                        return true;
                    }
                }

                // No connection found - command failed
                manageDatabase();
                return false;
            }
            else // movement
            {
                int steps = order.getNumberOfSteps();
                int x = miningMachine.getCoordinate().getX();
                int y = miningMachine.getCoordinate().getY();

                ArrayList<Barrier> barriers = (ArrayList<Barrier>) miningMachine.getField().getBarriers();

                boolean colliding = false;

                // for every step...
                while(steps > 0)
                {
                    // check for collision with any barriers
                    for(Barrier barrier : barriers)
                    {
                        Vector2D end1 = barrier.getStart();
                        Vector2D end2 = barrier.getEnd();

                        // establish whether the barrier is where the mining machine wants to move
                        if(OrderType.NORTH == order.getOrderType())
                        {
                            // check if the barrier is horizontal AND right above the mining machine, vertically AND horizontally
                            if (end1.getY() == end2.getY() &&
                                    end1.getY() == y + 1 &&
                                    end1.getX() <= x && x < end2.getX())
                            {
                                colliding = true;
                            }
                        }
                        else if(OrderType.SOUTH == order.getOrderType())
                        {
                            // if the barrier is NOT in the way:
                            // if the barrier is horizontal AND right below the mining machine, vertically AND horizontally
                            if (end1.getY() == end2.getY() &&
                                    end1.getY() == y &&
                                    end1.getX() <= x && x < end2.getX())
                            {
                                colliding = true;
                            }
                        }
                        else if(OrderType.EAST == order.getOrderType())
                        {
                            // if the barrier is NOT in the way:
                            // if the barrier is vertical AND directly to the right of the mining machine, horizontally AND vertically
                            if (end1.getX() == end2.getX() &&
                                    end1.getX() == x + 1 &&
                                    end1.getY() <= y && y < end2.getY())
                            {
                                colliding = true;
                            }
                        }
                        else if(OrderType.WEST == order.getOrderType())
                        {
                            // if the barrier is NOT in the way:
                            // if the barrier is vertical AND directly to the left of the mining machine, horizontally AND vertically
                            if (end1.getX() == end2.getX() &&
                                    end1.getX() == x &&
                                    end1.getY() <= y && y < end2.getY())
                            {
                                colliding = true;
                            }
                        }
                    }

                    // check for collision with any other mining machines
                    for(MiningMachine thisMiningMachine : miningMachines)
                    {
                        // if it's a different mining machine AND it's initialised AND it's in the same field
                        if(!thisMiningMachine.equals(miningMachine) && thisMiningMachine.isInitialised() && thisMiningMachine.getField().equals(miningMachine.getField()))
                        {
                            if(OrderType.NORTH == order.getOrderType())
                            {
                                if (miningMachine.getCoordinate().getX()     == thisMiningMachine.getCoordinate().getX() &&
                                        miningMachine.getCoordinate().getY() + 1 == thisMiningMachine.getCoordinate().getY())
                                {
                                    colliding = true;
                                }
                            }
                            else if(OrderType.SOUTH == order.getOrderType())
                            {
                                if (miningMachine.getCoordinate().getX()     == thisMiningMachine.getCoordinate().getX() &&
                                        miningMachine.getCoordinate().getY() - 1 == thisMiningMachine.getCoordinate().getY())
                                {
                                    colliding = true;
                                }
                            }
                            else if(OrderType.EAST == order.getOrderType())
                            {
                                if (miningMachine.getCoordinate().getX() + 1 == thisMiningMachine.getCoordinate().getX() &&
                                        miningMachine.getCoordinate().getY()     == thisMiningMachine.getCoordinate().getY())
                                {
                                    colliding = true;
                                }
                            }
                            else if(OrderType.WEST == order.getOrderType())
                            {
                                if (miningMachine.getCoordinate().getX() - 1 == thisMiningMachine.getCoordinate().getX() &&
                                        miningMachine.getCoordinate().getY()     == thisMiningMachine.getCoordinate().getY())
                                {
                                    colliding = true;
                                }
                            }
                        }
                    }

                    // check for collision with any boundaries
                    if ((OrderType.NORTH == order.getOrderType() && y == miningMachine.getField().getHeight() - 1) ||
                            (OrderType.SOUTH == order.getOrderType() && y == 0) ||
                            (OrderType.EAST == order.getOrderType()  && x == miningMachine.getField().getWidth() - 1) ||
                            (OrderType.WEST == order.getOrderType()  && x == 0))
                    {
                        colliding = true;
                    }

                    // execute collision check
                    if(colliding)
                    {
                        manageDatabase();
                        return true;
                    }

                    // since collision didn't occur: set the successfully calculated coordinates
                    if(OrderType.NORTH == order.getOrderType())
                        y++;
                    else if(OrderType.SOUTH == order.getOrderType())
                        y--;
                    else if(OrderType.EAST == order.getOrderType())
                        x++;
                    else if(OrderType.WEST == order.getOrderType())
                        x--;

                    miningMachine.setCoordinate(new Vector2D(x, y));


                    steps--;
                }

                manageDatabase();
                return true;
            }
        }

        manageDatabase();
        return false;
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId)
    {
        return getMiningMachineByUUID(miningMachineId).getField().getUuid();
    }

    /**
     * This method returns the vector-2D a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the vector-2D of the mining machine
     */
    public Vector2D getMiningMachineVector2D(UUID miningMachineId)
    {
        return getMiningMachineByUUID(miningMachineId).getCoordinate();
    }



    public void manageDatabase()
    {
        // Clear repositories
        fieldRepository.deleteAll();
        connectionRepository.deleteAll();
        miningMachineRepository.deleteAll();
        transportCategoryRepository.deleteAll();

        // Fill repositories
        for(TransportCategory transportCategory : transportCategories)
        {
            transportCategoryRepository.save(transportCategory);
        }
        for(Field field : fields)
        {
            fieldRepository.save(field);
        }
        for(MiningMachine miningMachine : miningMachines)
        {
            miningMachineRepository.save(miningMachine);
        }
        for(Connection connection : connections)
        {
            connectionRepository.save(connection);
        }
    }



    private Field getFieldByUUID(UUID id)
    {
        for(Field field : fields)
        {
            if(field.getUuid().compareTo(id) == 0)
                return field;
        }
        return null;
    }

    private Connection getConnectionByUUID(UUID id)
    {
        for(Connection connection : connections)
        {
            if(connection.getUuid().compareTo(id) == 0)
                return connection;
        }
        return null;
    }

    private MiningMachine getMiningMachineByUUID(UUID id)
    {
        for(MiningMachine miningMachine : miningMachines)
        {
            if(miningMachine.getUuid().compareTo(id) == 0)
                return miningMachine;
        }
        return null;
    }

    private TransportCategory getTransportCategoryByUUID(UUID id)
    {
        for(TransportCategory transportCategory : transportCategories)
        {
            if(transportCategory.getUuid().compareTo(id) == 0)
                return transportCategory;
        }
        return null;
    }

    private String[] decodeCommand(String encoded)
    {
        String[] decodedString = encoded.substring(1, encoded.length()-1).split(",");

        return new String[] {
                decodedString[0],
                decodedString[1]};
    }
}
