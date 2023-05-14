package thkoeln.st.st2praktikum.exercise;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.UUID;


public class MiningMachineManager implements Walkable {

    MachinePositionCalculation machinePositionCalculation = new MachinePositionCalculation(this);

    public MiningMachineManager(MiningMachineService machineService) {
        this.miningMachineService = machineService;
    }

    protected void setMachineID(UUID machine) {
        this.machineID = machine;
    }

    protected final MiningMachineService miningMachineService;
    private final ArrayList<MiningMachine> miningMachines = new ArrayList<>();
    private UUID machineID;
    private final DistanceDirectionFieldID distanceDirectionFieldID = new DistanceDirectionFieldID();
    private CommandType commandType;


    @Override
    public void walkTo(String walkCommandString) {

        String commandString = walkCommandString.substring(1, 3);
        String secondPartOfWalkCommandString = walkCommandString.substring(walkCommandString.indexOf(",") + 1, walkCommandString.length() - 1);

        MiningMachine currentMachine = getMachineByID(machineID);

        setCommandType(commandString);
        setFieldIDDistanceAndDirection(commandType, secondPartOfWalkCommandString);

        executeCommand(commandType, currentMachine, distanceDirectionFieldID);
    }

    public ArrayList<MiningMachine> getMiningMachines() {
        return this.miningMachines;
    }

    public void setMiningMachines(MiningMachine miningMachine) {
        this.miningMachines.add(miningMachine);
    }

    protected MiningMachine getMachineByID(UUID machineUUID) {
        for (MiningMachine miningMachine : miningMachines) {
            if (miningMachine.getUUID().equals(machineUUID))
                return miningMachine;
        }
        throw new RuntimeException("Keine MiningMachine mit dieser ID");
    }

    private void executeCommand(CommandType commandType, MiningMachine currentMachine, DistanceDirectionFieldID distanceDirectionFieldID) {
        switch (commandType) {

            case ENTER: {
                machinePositionCalculation.spawn(distanceDirectionFieldID.getFieldID(), currentMachine);
                break;
            }

            case TRANSPORT: {
                machinePositionCalculation.transport(currentMachine);
                break;
            }

            case EAST: {
                machinePositionCalculation.east(distanceDirectionFieldID, currentMachine);
                break;
            }

            case WEST: {
                machinePositionCalculation.west(distanceDirectionFieldID, currentMachine);
                break;
            }

            case NORTH: {
                machinePositionCalculation.north(distanceDirectionFieldID, currentMachine);
                break;
            }

            case SOUTH: {
                machinePositionCalculation.south(distanceDirectionFieldID, currentMachine);

                break;
            }

            default:
                throw new InvalidParameterException("Keine gültige Eingabe");
        }

    }

    private void setFieldIDDistanceAndDirection(CommandType commandType, String substring) {
        if (commandType.equals(CommandType.ENTER) || commandType.equals(CommandType.TRANSPORT)) {
            distanceDirectionFieldID.setFieldID(UUID.fromString(substring));
        } else
            distanceDirectionFieldID.setDistance(Integer.parseInt(substring));
        distanceDirectionFieldID.setCommandType(commandType);
    }

    private void setCommandType(String commandString) { //schöner machen. Danke
        switch (commandString) {
            case "en":
                commandType = CommandType.ENTER;
                break;
            case "tr":
                commandType = CommandType.TRANSPORT;
                break;
            case "ea":
                commandType = CommandType.EAST;
                break;
            case "we":
                commandType = CommandType.WEST;
                break;
            case "no":
                commandType = CommandType.NORTH;
                break;
            case "so":
                commandType = CommandType.SOUTH;
                break;
        }
    }

}