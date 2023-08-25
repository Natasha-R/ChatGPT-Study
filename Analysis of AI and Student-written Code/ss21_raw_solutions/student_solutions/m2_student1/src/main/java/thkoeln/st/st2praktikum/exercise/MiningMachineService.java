package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class MiningMachineService {
    MachineWalking machineWalking = new MachineWalking(this);

    ArrayList<Deck> decks = new ArrayList<>();
    ArrayList<BarrierList> barriers = new ArrayList<>();
    ArrayList<MiningMachine> miningMachines = new ArrayList<>();
    ArrayList<Connection> connections = new ArrayList<>();

    /**
     * This method creates a new field.
     *
     * @param height the height of the field
     * @param width  the width of the field
     * @return the UUID of the created field
     */

    public UUID addField(Integer height, Integer width) {
        Deck newField = new Deck(width, height);
        decks.add(newField);
        return newField.getFieldID();
    }

    /**
     * This method adds a barrier to a given field.
     *
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID fieldId, Barrier barrier) {
        barrierOnField(barrier.getStart(), barrier.getEnd(), fieldId);
        BarrierList newBarrier = new BarrierList(fieldId, barrier);
        barriers.add(newBarrier);
    }

    private int getCurrentField(int fieldPosition, UUID fieldID) {
        for (int i = 0; i < decks.size(); i++) {
            if (decks.get(i).getFieldID() == fieldID)
                fieldPosition = i;
        }
        return fieldPosition;
    }//das schöner machen jaja

    private void barrierOnField(Point start, Point end, UUID fieldID) {
        int fieldPosition;
        fieldPosition = getCurrentField(0, fieldID);

        if (start.getX() < 0 || start.getY() < 0 || start.getX() > decks.get(fieldPosition).getFieldWidth() || end.getY() > decks.get(fieldPosition).getFieldHeight())
            throw new RuntimeException("Barriere außerhalb des Felds");
    }//schöner machen

    /**
     * This method adds a traversable connection between two fields. Connections only work in one direction.
     *
     * @param sourceFieldId      the ID of the field where the entry point of the connection is located
     * @param sourcePoint        the points of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationPoint   the points of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceFieldId, Point sourcePoint, UUID destinationFieldId, Point destinationPoint) {
        connectionOnField(sourceFieldId, destinationFieldId, sourcePoint, destinationPoint);
        Connection newConnection = new Connection(sourceFieldId, destinationFieldId, sourcePoint, destinationPoint);
        connections.add(newConnection);
        return newConnection.getConnectionID();
    }

    private int getStartField(int fieldPosition, UUID sourceField) {
        for (int i = 0; i < decks.size(); i++) {
            if (decks.get(i).getFieldID() == sourceField)
                fieldPosition = i;
        }
        return fieldPosition;
    }//wie bei Barrier noch in eigene Klasse

    private int getDestinationField(int fieldPosition, UUID destinationField) {
        for (int i = 0; i < decks.size(); i++) {
            if (decks.get(i).getFieldID() == destinationField)
                fieldPosition = i;
        }
        return fieldPosition;
    }

    private void connectionOnField(UUID sourceField, UUID destinationField, Point startPoint, Point destinationPoint) {
        int startFieldPosition = getStartField(0, sourceField);
        int destinationFieldPosition = getDestinationField(0, destinationField);
        if (startPoint.getX() < 0 || startPoint.getY() < 0 || startPoint.getX() > decks.get(startFieldPosition).getFieldWidth() || startPoint.getY() > decks.get(startFieldPosition).getFieldHeight())
            throw new RuntimeException("Verbindung außerhalb des Felds");
        if (destinationPoint.getX() < 0 || destinationPoint.getY() < 0 || destinationPoint.getX() > decks.get(destinationFieldPosition).getFieldWidth() || destinationPoint.getY() > decks.get(destinationFieldPosition).getFieldHeight())
            throw new RuntimeException("Verbindung außerhalb des Felds");
    }


    /**
     * This method adds a new mining machine
     *
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine newMachine = new MiningMachine(name);
        miningMachines.add(newMachine);
        return newMachine.getMachineID();
    }

    /**
     * This method lets the mining machine execute a command.
     *
     * @param miningMachineId the ID of the mining machine
     * @param command         the given command
     *                        NORTH, WEST, SOUTH, EAST for movement
     *                        TRANSPORT for transport - only works on tiles with a connection to another field
     *                        ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the mining machine hits a barrier or
     * another mining machine, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Command command) {
        machineWalking.setMachineID(miningMachineId);
        machineWalking.walkTo(command);
        return machineWalking.commandSuccesfull;
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId) {
        UUID uuid = UUID.randomUUID();
        for (MiningMachineCurrentLocation m : machineWalking.miningMachineCurrentLocations) {
            if (miningMachineId == m.getMachineID()) {
                uuid = m.getCurrentFieldID();
            }
        }
        return uuid;
    }

    /**
     * This method returns the points a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the points of the mining machine
     */
    public Point getPoint(UUID miningMachineId) {
        for (MiningMachineCurrentLocation m : machineWalking.miningMachineCurrentLocations) {
            if (miningMachineId == m.getMachineID()) {
                return m.getPoint();
            }
        }
        throw new RuntimeException("Keine Machine mit dieser ID gefunden");
    }
}
