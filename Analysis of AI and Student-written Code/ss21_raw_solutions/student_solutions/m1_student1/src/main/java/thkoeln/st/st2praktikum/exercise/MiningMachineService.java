package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;


public class MiningMachineService {

    protected Deck deck = new Deck();
    private final MiningMachineManager miningMachineManager = new MiningMachineManager(this);

    /*
      Strings verwerfen -> gemacht
      Referenzen bei UUID -> zu Objekten, zu Deck
    */

    /**
     * This method creates a new field.
     *
     * @param height the height of the field
     * @param width  the width of the field
     * @return the UUID of the created field
     */

    public UUID addField(Integer height, Integer width) {
        Deck newField = new Deck(width, height);
        deck.addDeck(newField);
        return newField.getFieldID();
    }

    /**
     * This method adds a barrier to a given field.
     *
     * @param fieldId       the ID of the field the barrier shall be placed on
     * @param barrierString the end points of the barrier, encoded as a String, eg. "(2,3)-(10,3)" for a barrier that spans from (2,3) to (10,3)
     */
    public void addBarrier(UUID fieldId, String barrierString) {
        getDeckByFieldID(fieldId);
        Barrier newBarrier = new Barrier(fieldId, stringToPoint(barrierString.substring(0,barrierString.indexOf("-"))), stringToPoint(barrierString.substring(barrierString.indexOf("-")+1)));
        Deck deck = this.getDeckByFieldID(fieldId);
        deck.addBarrier(newBarrier);
    }

    private Point stringToPoint(String string){
        Point point = new Point(0,0);
        point.setX(Integer.parseInt(string.substring(1, string.indexOf(","))));
        point.setY(Integer.parseInt(string.substring(string.indexOf(",") + 1, string.length() -1)));
        return point;
    }

    private Deck getDeckByFieldID(UUID fieldID) {
        for (Deck deck : deck.getDecks()) {
            if (deck.getFieldID().equals(fieldID))
                return deck;
        }
        throw new RuntimeException("Feld nicht vorhanden");
    }

    /**
     * This method adds a traversable connection between two fields. Connections only work in one direction.
     *
     * @param sourceFieldId         the ID of the field where the entry point of the connection is located
     * @param sourceCoordinate      the coordinates of the entry point
     * @param destinationFieldId    the ID of the field where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceFieldId, String sourceCoordinate, UUID destinationFieldId, String destinationCoordinate) {
        getDeckByFieldID(sourceFieldId);
        getDeckByFieldID(destinationFieldId);
        Connection newConnection = new Connection(sourceFieldId, stringToPoint(sourceCoordinate), destinationFieldId, stringToPoint(destinationCoordinate));
        Deck deck = this.getDeckByFieldID(sourceFieldId);
        deck.addConnection(newConnection);
        return newConnection.getConnectionID();
    }

    /**
     * This method adds a new mining machine
     *
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine newMachine = new MiningMachine(name);
        miningMachineManager.setMiningMachines(newMachine);
        return newMachine.getUUID();
    }

    /**
     * This method lets the mining machine execute a command.
     *
     * @param miningMachineId the ID of the mining machine
     * @param commandString   the given command, encoded as a String:
     *                        "[direction, steps]" for movement, e.g. "[we,2]"
     *                        "[tr,field-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on tiles with a connection to another field
     *                        "[en,<field-id>]" for setting the initial field where a mining machine is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The mining machine will always spawn at (0,0) of the given field.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the mining machine hits a barrier or
     * another mining machine, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, String commandString) {
        miningMachineManager.setMachineID(miningMachineId);
        miningMachineManager.walkTo(commandString);
        return miningMachineManager.machinePositionCalculation.getCommandSuccesfull();
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId) {

        return miningMachineManager.getMachineByID(miningMachineId).getCurrentFieldID();
    }

    /**
     * This method returns the coordinates a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the coordiantes of the mining machine encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID miningMachineId) {
        int x = miningMachineManager.getMachineByID(miningMachineId).getPosition().getX();
        int y = miningMachineManager.getMachineByID(miningMachineId).getPosition().getY();
        return "(" + x + "," + y + ")";
    }
}