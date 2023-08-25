package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class MiningMachineService {

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */

    Fieldllist createdFieldllist = new Fieldllist();
    Walllist createdWalllist = new Walllist();
    Connectionlist createdConnectionList = new Connectionlist();
    Miningmachinelist createdMiningMachineList = new Miningmachinelist();
    Fieldminingmachinehashmap fieldminingmachinehashmap = new Fieldminingmachinehashmap();
    Machinecoordinateshashmap machinecoordinateshashmap = new Machinecoordinateshashmap();


    public UUID addField(Integer height, Integer width) {
        UUID fielduuid = UUID.randomUUID();
        Field field = new Field(height-1, width-1, fielduuid);
        createdFieldllist.fieldList.add(field);

        //Boundaries considered as walls. That's why we add them as walls.
        String fieldwallleftside = new String("("+0+","+0+")-("+0+","+(height));
        String fieldwallbottom = new String("("+0+","+0+")-("+(width)+","+0);
        String fieldwalltop = new String("("+0+","+(height+1)+")-("+(height+1)+","+(width));
        String fieldwallrightside= new String("("+(width+1)+","+0+")-("+(width+1)+","+(height));
        addWall(fielduuid, fieldwallleftside);
        addWall(fielduuid, fieldwallbottom);
        addWall(fielduuid, fieldwalltop);
        addWall(fielduuid, fieldwallrightside);

       return fielduuid;

    }

    /**
     * This method adds a wall to a given field.
     * @param fieldId the ID of the field the wall shall be placed on
     * @param wallString the end points of the wall, encoded as a String, eg. "(2,3)-(10,3)" for a wall that spans from (2,3) to (10,3)
     */
    public void addWall(UUID fieldId, String wallString) {
        Wall newWall = new Wall(wallString);
        createdWalllist.add(newWall);
        for (Field field : createdFieldllist.fieldList)
            if (fieldId.equals(field.fielduuid)) {
                field.walllist.add(newWall);
            }
    }


    /**
     * This method adds a traversable connection between two fields. Connections only work in one direction.
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceFieldId, String sourceCoordinate, UUID destinationFieldId, String destinationCoordinate) {
        UUID connectionuuid = UUID.randomUUID();
        Connection newConnection = new Connection(sourceFieldId,sourceCoordinate,destinationFieldId, destinationCoordinate);
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
        Miningmachine newMiningMachine = new Miningmachine(name, miningmachineuuid);
        createdMiningMachineList.add(newMiningMachine);
        return miningmachineuuid;
    }

    /**
     * This method lets the mining machine execute a command.
     * @param miningMachineId the ID of the mining machine
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,field-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on cells with a connection to another field
     * "[en,<field-id>]" for setting the initial field where a mining machine is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a wall or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, String commandString) {
        for(Miningmachine machine : createdMiningMachineList.miningmachinelist) {
            if(machine.miningmachineuuid == miningMachineId) {
                UUID fieldwheremachineis = getMiningMachineFieldId(machine.miningmachineuuid);
                String moveCommand = commandString.substring(1,3);
                String steps = commandString.substring(4, commandString.length()-1);
                String UUID = commandString.substring(4, commandString.length()-1);
             fieldminingmachinehashmap = machine.executecommand(moveCommand,steps, UUID, createdMiningMachineList, createdFieldllist, createdConnectionList,
                     fieldminingmachinehashmap, fieldwheremachineis, machine.miningmachineuuid, machinecoordinateshashmap);
                if(!machine.executefailer) {
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
        return fieldminingmachinehashmap.fieldminingmachinehashmap.get(miningMachineId);
    }

    /**
     * This method returns the coordinates a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the coordiantes of the mining machine encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID miningMachineId){
        String coordinates = null;
        for(Miningmachine miningMachine : createdMiningMachineList.miningmachinelist) {
            if(miningMachine.miningmachineuuid == miningMachineId) {
                coordinates = "("+miningMachine.coordinates[0]+","+miningMachine.coordinates[1]+")";
            }
        }
        return coordinates;
    }
}
