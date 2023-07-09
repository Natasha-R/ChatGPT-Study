package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;


public class MachineCommander {

    private final ArrayList<Field> fieldList ;
    private final ArrayList<MiningMachine> machineList ;

    public MachineCommander(ArrayList<Field>fieldList, ArrayList<MiningMachine> machineList){
        this.fieldList = fieldList;
        this.machineList = machineList;
    }

    public Boolean executeCommand(UUID miningMachineId, Command commandString) {
        MiningMachine miningMachine = findMachine(miningMachineId);
        switch (commandString.getCommandType()) {
            case ENTER:
                return insertMachine(miningMachineId,commandString);
            case TRANSPORT:
                return transportMachine(miningMachine);
            case WEST:
                    for (int i = 0; i < commandString.getNumberOfSteps(); i++) {
                        miningMachine.moveWest(getFieldList(),getMachineList());
                    }
                break;
            case SOUTH:
                    for (int i = 0; i < commandString.getNumberOfSteps(); i++) {
                        miningMachine.moveSouth(getFieldList(),getMachineList());
                    }
                break;
            case EAST:
                    for (int i = 0; i < commandString.getNumberOfSteps(); i++) {
                        miningMachine.moveEast(getFieldList(),getMachineList());
                    }
                break;
            case NORTH:
                    for (int i = 0; i < commandString.getNumberOfSteps(); i++) {
                        miningMachine.moveNorth(getFieldList(),getMachineList());
                    }
                break;
        }
        return false;
    }

    public Boolean insertMachine(UUID miningMachineId, Command commandString) {
        for (Field value : getFieldList()) {
            if (value.getFieldID().toString().equals(commandString.getGridId().toString())) {
                for (MiningMachine miningMachine : getMachineList()) {
                    if (miningMachine.getFieldID() == value.getFieldID()
                            && miningMachine.getPoint().getX() == 0
                            && miningMachine.getPoint().getY() == 0) {  //entrance ist belegt
                        return false;
                    }
                    if (miningMachine.getMachineID() == miningMachineId) {  //enter field
                        miningMachine.setFieldID(value.getFieldID());
                        miningMachine.setPoint(new Point(0,0));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Boolean transportMachine(MiningMachine miningMachine){
        for (Field value : getFieldList()) {
            for (int x = 0; x < value.getConnections().size(); x++) {
                if(miningMachine.getFieldID()==null) break;
                if (transportConditions(miningMachine,value.getConnections().get(x))) {
                    //transport
                    miningMachine.setPoint(value.getConnections().get(x).getDestinationCoordinates());
                    miningMachine.setFieldID(value.getConnections().get(x).getDestinationField());
                    return true;
                }
            }
        }
        return false;
    }

    public boolean transportConditions(MiningMachine miningMachine, Connection connection){
        if(!miningMachine.getFieldID().toString().equals(connection.getSourceField().toString())) return false;
        if(!miningMachine.getPoint().toString().equals(connection.getSourceCoordinates().toString())) return false;
        return miningMachine.getPoint().toString().equals(connection.getSourceCoordinates().toString());
    }

    public MiningMachine findMachine(UUID miningMachineId){
        for (MiningMachine miningMachine : getMachineList()) {
            if (miningMachine.getMachineID() == miningMachineId) return miningMachine;
        }
        throw new RuntimeException ("machine not found");
    }

    public ArrayList<Field> getFieldList() {
        return fieldList;
    }

    public ArrayList<MiningMachine> getMachineList() {
        return machineList;
    }
}

