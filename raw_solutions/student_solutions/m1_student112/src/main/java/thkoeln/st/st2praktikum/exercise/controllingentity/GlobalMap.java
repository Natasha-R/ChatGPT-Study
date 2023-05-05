package thkoeln.st.st2praktikum.exercise.controllingentity;

import thkoeln.st.st2praktikum.exercise.entitys.Field;
import thkoeln.st.st2praktikum.exercise.entitys.MiningMachine;
import thkoeln.st.st2praktikum.exercise.interfaces.Connectionable;
import thkoeln.st.st2praktikum.exercise.interfaces.Fieldable;
import thkoeln.st.st2praktikum.exercise.interfaces.Machineable;
import thkoeln.st.st2praktikum.exercise.interfaces.NoMoveable;
import thkoeln.st.st2praktikum.exercise.selfdefinedsupport.CoordinatePair;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import static java.lang.Math.abs;

public class GlobalMap {
    private HashMap<UUID, Connectionable> collectionOfConnection = new HashMap<UUID, Connectionable>();
    private HashMap<UUID, Fieldable> collectionOfField = new HashMap<UUID, Fieldable>();
    private HashMap<UUID, Machineable> collectionOfMiningMachine = new HashMap<UUID, Machineable>();
    private HashMap<UUID, NoMoveable> collectionOfWall = new HashMap<UUID, NoMoveable>();

    public UUID createField(Integer height, Integer width) {
        Fieldable field = new Field();
        field.setHeight(height);
        field.setWidth(width);
        saveField(field);
        return field.getFieldId();
    }

    public void initiateCreationAndPlacementOfWall(CoordinatePair[] coordinate, UUID fieldId) {
        WallBuilder wallBuilder = new WallBuilder();
        NoMoveable wall = wallBuilder.placeWallAtField(wallBuilder.createNewWall(coordinate), getFieldByID(fieldId));
        saveWall(wall);
    }

    public UUID initiateCreationOfConnection(UUID sourceFieldId, String sourceCoordinate, UUID destinationFieldId, String destinationCoordinate) {
        ConnectionBuilder connectionBuilder = new ConnectionBuilder();
        CoordinatePair[] sourceArray = translateCoordinateFromStringToArrayOfCoordinatePair(sourceCoordinate);
        CoordinatePair[] destinationArray = translateCoordinateFromStringToArrayOfCoordinatePair(destinationCoordinate);

        if (sourceArray.length != 1 && destinationArray.length != 1)
            throw new IllegalArgumentException("Das sind zu viele Coordinaten");
        connectionBuilder.connectTwoFields(getFieldByID(sourceFieldId), sourceArray[0], getFieldByID(destinationFieldId), destinationArray[0]);
        Connectionable connection = connectionBuilder.connectTwoFields(getFieldByID(sourceFieldId), sourceArray[0], getFieldByID(destinationFieldId), destinationArray[0]);
        saveConnection(connection);

        return connection.getConnectionId();
    }

    private boolean initiatePlacingOfMachine(Machineable miningMachine, UUID fieldId) {
        MachineControllerCenter machineControllCenter = new MachineControllerCenter();
        CoordinatePair placingCoordinat = new CoordinatePair();
        placingCoordinat.setCoordinatePair(0, 0);
        if (machineControllCenter.checkMachine(collectionOfMiningMachine, placingCoordinat, fieldId)) {
            miningMachine.setIsAtFieldId(fieldId);
            miningMachine.setCoordinate(placingCoordinat);
            return true;
        }
        return false;
    }

    private boolean initiateTeleport(Machineable miningMachine, UUID fieldId) {
        MachineControllerCenter machineControllCenter = new MachineControllerCenter();
        CoordinatePair evasiveCoordinat = new CoordinatePair();

        HashMap<UUID, Connectionable> connections = new HashMap<UUID, Connectionable>();
        connections = findTeleportRoute(miningMachine.getIsAtFieldId(), fieldId);

        Set<UUID> keys = connections.keySet();
        for (UUID key : keys) {
            if (connections.get(key).getsourceCoordinate().getXCoordinate().equals(miningMachine.getCoordinate().getXCoordinate()))
                if (connections.get(key).getsourceCoordinate().getYCoordinate().equals(miningMachine.getCoordinate().getYCoordinate()))
                    if (machineControllCenter.checkMachine(collectionOfMiningMachine, connections.get(key).getdestinationCoordinate(), fieldId)) {
                        machineControllCenter.moveMachine(connections.get(key).getdestinationCoordinate(), fieldId, miningMachine);
                        return true;
                    }
        }
        return false;
    }

    public UUID creationOfMachine(String name) {
        Machineable machine = new MiningMachine();
        CoordinatePair reserveCoordinate = new CoordinatePair();
        reserveCoordinate.setCoordinatePair(-1, -1);
        machine.setName(name);
        machine.setCoordinate(reserveCoordinate);
        saveMiningMachine(machine);
        return machine.getMiningmachineId();
    }

    private boolean initiateMovement(CoordinatePair steps, Machineable miningMachine) {
        MachineControllerCenter machineControllCenter = new MachineControllerCenter();
        if ((steps.getYCoordinate().equals(0)) && (steps.getXCoordinate().equals(0))) return false;
        else {
            CoordinatePair step = machineControllCenter.generateOneStep(steps);
            for (int i = 0; i < (abs(steps.getXCoordinate()) + abs(steps.getYCoordinate())); i++) {
                if (obstracleCheck(step, miningMachine.getCoordinate(), miningMachine))
                    machineControllCenter.moveMachine(machineControllCenter.generateNewPosition(step, miningMachine.getCoordinate()), miningMachine.getIsAtFieldId(), miningMachine);
            }
            return true;
        }
    }


    public CoordinatePair[] translateCoordinateFromStringToArrayOfCoordinatePair(String coordinateString) {
        coordinateString = coordinateString.replace("(", "");
        coordinateString = coordinateString.replace(")", "");
        coordinateString = coordinateString.replace("-", ",");
        String[] coordinatesAsString = coordinateString.split(",");
        CoordinatePair[] coordinates = new CoordinatePair[(coordinatesAsString.length) / 2];
        int b = 0;
        for (int a = 0; a < (coordinatesAsString.length); a += 2) {
            CoordinatePair coordinatePair = new CoordinatePair();
            coordinatePair.setCoordinatePair(Integer.valueOf(coordinatesAsString[a]), Integer.valueOf(coordinatesAsString[a + 1]));
            coordinates[b] = coordinatePair;
            b++;
        }
        return coordinates;
    }

    private HashMap<UUID, Connectionable> findTeleportRoute(UUID sourceFildID, UUID destinationFieldID) {
        HashMap<UUID, Connectionable> connections = new HashMap<UUID, Connectionable>();
        Set<UUID> keys = collectionOfConnection.keySet();
        for (UUID key : keys) {
            if (getConnectionByID(key).getDestinationFieldID().equals(destinationFieldID))
                if (getConnectionByID(key).getDestinationFieldID().equals(destinationFieldID))
                    connections.put(key, collectionOfConnection.get(key));
        }
        return connections;
    }

    public String[] translateCommandstring(String commandString) {
        commandString = commandString.replace("[", "");
        commandString = commandString.replace("]", "");
        String[] command = commandString.split(",");
        return command;
    }

    public boolean identifyComand(String[] command, UUID miningMachineId) {
        MachineControllerCenter machineControllCenter = new MachineControllerCenter();
        Machineable miningMachine = getMiningMachineByID(miningMachineId);
        switch (command[0]) {
            case "tr": {
                return initiateTeleport(getMiningMachineByID(miningMachineId), UUID.fromString(command[1]));
            }
            case "en": {
                return initiatePlacingOfMachine(getMiningMachineByID(miningMachineId), UUID.fromString(command[1]));
            }
            default:
                return initiateMovement(machineControllCenter.readCommand(command), getMiningMachineByID(miningMachineId));
        }
    }

    private boolean obstracleCheck(CoordinatePair machinestep, CoordinatePair machineCoordinate, Machineable miningMachine) {
        MachineControllerCenter machineControllCenter = new MachineControllerCenter();
        if (machineControllCenter.checkMachine(collectionOfMiningMachine, machineControllCenter.generateNewPosition(machinestep, machineCoordinate), miningMachine.getIsAtFieldId())) {
            if (machineControllCenter.checkField(getFieldByID(miningMachine.getIsAtFieldId()), machineControllCenter.generateNewPosition(machinestep, machineCoordinate)))
                return machineControllCenter.checkWall(getWallHashMapByFieldId(miningMachine.getIsAtFieldId(), machinestep), machinestep, machineCoordinate);
        }
        return false;

    }
    public HashMap<UUID, NoMoveable> getWallHashMapByFieldId(UUID fieldID, CoordinatePair step) {
        HashMap<UUID, NoMoveable> wallsAtField = new HashMap<UUID, NoMoveable>();
        Set<UUID> keys = collectionOfWall.keySet();
        for (UUID key : keys) {
            if (getWallByID(key).getField().equals(fieldID))
                if (step.getXCoordinate().equals(0)) {
                    if (getWallByID(key).getSourceY().equals(getWallByID(key).getDestinationY()))
                        wallsAtField.put(key, getWallByID(key));
                } else if (getWallByID(key).getSourceX().equals(getWallByID(key).getDestinationX()))
                    wallsAtField.put(key, getWallByID(key));
        }
        return wallsAtField;
    }
    public void saveField(Fieldable field) {
        this.collectionOfField.put(field.getFieldId(), field);
    }

    public void saveMiningMachine(Machineable machine) {
        this.collectionOfMiningMachine.put(machine.getMiningmachineId(), machine);
    }

    public void saveConnection(Connectionable connection) {
        this.collectionOfConnection.put(connection.getConnectionId(), connection);
    }

    public void saveWall(NoMoveable wall) {
        this.collectionOfWall.put(wall.getWallId(), wall);
    }

    public Fieldable getFieldByID(UUID fieldID) {
        return this.collectionOfField.get(fieldID);
    }

    public Machineable getMiningMachineByID(UUID miningMachineID) {
        return this.collectionOfMiningMachine.get(miningMachineID);
    }

    public Connectionable getConnectionByID(UUID connectionID) {
        return this.collectionOfConnection.get(connectionID);
    }

    public NoMoveable getWallByID(UUID wallID) {
        return this.collectionOfWall.get(wallID);
    }


}