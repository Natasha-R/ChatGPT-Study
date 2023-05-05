package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class MiningMachineService implements Searchable, Addable, Executable{

    private MiningMachineCommands commands = new MiningMachineCommands();
    @Autowired
    private MiningMachineRepository miningMachineRepository;
    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private ConnectionRepository connectionRepository;
    @Autowired
    private TransportCategoryRepository transportCategoryRepository;

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field temp = new Field();
        temp.setHeight(height);
        temp.setWidth(width);
        commands.getFields().add(temp);
        fieldRepository.save(temp);
        return temp.getId();

    }

    /**
     * This method adds a wall to a given field.
     * @param fieldId the ID of the field the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID fieldId, Wall wall) {
        Field temp = commands.findField(fieldId);
        if(wall.getStart().getX() >= 0 && wall.getStart().getY() >= 0 && wall.getEnd().getX() <= temp.getWidth() && wall.getEnd().getY() <= temp.getHeight()){
            temp.addWall(wall);
            fieldRepository.save(temp);
        }else{
            throw new RuntimeException("wall out of bounds");
        }


    }

    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        TransportCategory temp = new TransportCategory(category);
        commands.getTransportCategories().add(temp);
        transportCategoryRepository.save(temp);
        return temp.getId();
    }

    /**
     * This method adds a traversable connection between two fields based on a transport category. Connections only work in one direction.
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourcePoint the point of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationPoint the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId, UUID sourceFieldId, Point sourcePoint, UUID destinationFieldId, Point destinationPoint) {
        Connection temp = new Connection();
        temp.setSourceField(commands.findField(sourceFieldId));
        temp.setSourcePoint(sourcePoint);
        temp.setDestinationField(commands.findField(destinationFieldId));
        temp.setDestinationPoint(destinationPoint);
        temp.setTransportCategory(commands.findTransportCategory(transportCategoryId));
        commands.getConnections().add(temp);
        connectionRepository.save(temp);
        return temp.getId();
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine temp = new MiningMachine();
        temp.setName(name);
        commands.getMiningMachines().add(temp);
        miningMachineRepository.save(temp);
        return temp.getId();
    }

    /**
     * This method lets the mining machine execute a task.
     * @param miningMachineId the ID of the mining machine
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on tiles with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a wall or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Task task) {
        switch(task.getTaskType()){
            case ENTER:
                return commands.spawnMiningMachine(miningMachineId, task.getGridId(), miningMachineRepository);
            case NORTH:
                commands.walk(miningMachineId, task.getNumberOfSteps(),0,1, miningMachineRepository);
                return true;
            case EAST:
                commands.walk(miningMachineId, task.getNumberOfSteps(),1,0, miningMachineRepository);
                return true;
            case SOUTH:
                commands.walk(miningMachineId, task.getNumberOfSteps(),0,-1, miningMachineRepository);
                return true;
            case WEST:
                commands.walk(miningMachineId, task.getNumberOfSteps(),-1,0, miningMachineRepository);
                return true;
            case TRANSPORT:
                return commands.transportMiningMachine(miningMachineId, task.getGridId(), miningMachineRepository);
            default:
                return false;


        }

    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        for(int i = 0; i<commands.getFields().size(); i++){
            if(commands.findMiningMachine(miningMachineId).getFieldId().equals(commands.getFields().get(i).getId())){
                return commands.getFields().get(i).getId();
            }
        }
        throw new NullPointerException();


    }

    /**
     * This method returns the point a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the point of the mining machine
     */
    public Point getMiningMachinePoint(UUID miningMachineId){
        return commands.findMiningMachine(miningMachineId).getPoint();

    }
}
