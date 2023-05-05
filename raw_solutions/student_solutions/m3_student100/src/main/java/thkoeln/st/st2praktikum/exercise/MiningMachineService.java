package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class MiningMachineService {

    Fieldllist createdFieldllist = new Fieldllist();
    Walllist createdWalllist = new Walllist();
    ConnectionList createdConnectionList = new ConnectionList();
    MiningMachinelist createdMiningMachineList = new MiningMachinelist();
    Fieldminingmachinehashmap fieldminingmachinehashmap = new Fieldminingmachinehashmap();
    Machinecoordinateshashmap machinecoordinateshashmap = new Machinecoordinateshashmap();
    TransportCategoryList createdTransportcategorylist = new TransportCategoryList();

    @Autowired
    FieldRepository fieldRepository;
    @Autowired
    MiningMachineRepository miningMachineRepository;
    @Autowired
    TransportCategoryRepository transportCategoryRepository;


    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {

        UUID fielduuid = UUID.randomUUID();
        Field field = new Field(height-1, width-1, fielduuid);
        createdFieldllist.add(field);
        fieldRepository.save(field);

        //Boundaries considered as walls. That's why we add them as walls.
        Wall  fieldwallleftside =   new  Wall ("("+0+","+0+")-("+0+","+(height)+ ")");
        Wall  fieldwallbottom =     new  Wall ("("+0+","+0+")-("+(width)+","+0 + ")");
        Wall  fieldwalltop =        new  Wall ("("+0+","+(height+1)+")-("+(height+1)+","+(width) + ")");
        Wall  fieldwallrightside=   new  Wall ("("+(width+1)+","+0+")-("+(width+1)+","+(height) + ")");
        addWall(fielduuid, fieldwallleftside);
        addWall(fielduuid, fieldwallbottom);
        addWall(fielduuid, fieldwalltop);
        addWall(fielduuid, fieldwallrightside);

        return fielduuid;
    }

    /**
     * This method adds a wall to a given field.
     * @param fieldId the ID of the field the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID fieldId, Wall wall) {

        createdWalllist.add(wall);
        for (Field field : createdFieldllist.getFieldList()) {
            if (fieldId.equals(field.getFielduuid())) {
                field.getWalllist().add(wall);
            }
        }
    }

    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        UUID transportcategory = UUID.randomUUID();
        TransportCategory newTransportcategory = new TransportCategory(transportcategory, category);
         createdTransportcategorylist.add(newTransportcategory);
        return transportcategory;

    }

    /**
     * This method adds a traversable connection between two fields based on a transport category. Connections only work in one direction.
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourceCoordinate the coordinate of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId, UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId, Coordinate destinationCoordinate) {
        UUID connectionuuid = UUID.randomUUID();
        Connection newConnection = new Connection(sourceFieldId,sourceCoordinate,destinationFieldId, destinationCoordinate, connectionuuid,  transportCategoryId);
        createdConnectionList.add(newConnection);
        return connectionuuid;
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        UUID miningmachineuuid = UUID.randomUUID();
        MiningMachine newMiningMachine = new MiningMachine(name, miningmachineuuid);
        miningMachineRepository.save(newMiningMachine);
        createdMiningMachineList.add(newMiningMachine);

        return miningmachineuuid;
    }

    /**
     * This method lets the mining machine execute a order.
     * @param miningMachineId the ID of the mining machine
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on cells with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a wall or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Order order) {
        for(MiningMachine machine : createdMiningMachineList.getMiningmachinelist()) {
            if(machine.getMiningmachineuuid() == miningMachineId) {
                UUID fieldwheremachineis = machine.getFieldId();
                OrderType moveCommand = order.getOrderType();
                Integer steps = order.getNumberOfSteps();
                UUID UUID = order.getGridId();
                machine.setCoordinate(machine.executeCommand(moveCommand,steps, UUID, createdMiningMachineList, createdFieldllist, createdConnectionList,
                        miningMachineRepository, fieldwheremachineis, machine.getMiningmachineuuid(), machinecoordinateshashmap));
                miningMachineRepository.save(machine);

                if(!machine.isExecutefailer()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        return fieldminingmachinehashmap.getFieldminingmachinehashmap().get(miningMachineId);
    }

    /**
     * This method returns the coordinate a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the coordinate of the mining machine
     */
    public Coordinate getCoordinate(UUID miningMachineId){
        Coordinate coordinates = null;
        for(MiningMachine miningMachine : createdMiningMachineList.getMiningmachinelist()) {
            if(miningMachine.getMiningmachineuuid() == miningMachineId) {
                coordinates = miningMachine.getCoordinate();
            }
        }
        return coordinates;
    }
}
