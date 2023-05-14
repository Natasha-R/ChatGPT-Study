package thkoeln.st.st2praktikum.exercise.map;

import thkoeln.st.st2praktikum.exercise.CoordinateService;
import thkoeln.st.st2praktikum.exercise.connection.Command;
import thkoeln.st.st2praktikum.exercise.connection.CommandService;
import thkoeln.st.st2praktikum.exercise.connection.Connection;
import thkoeln.st.st2praktikum.exercise.deck.Deck;
import thkoeln.st.st2praktikum.exercise.droid.Direction;
import thkoeln.st.st2praktikum.exercise.droid.Droid;
import thkoeln.st.st2praktikum.exercise.exceptions.NoFieldException;
import thkoeln.st.st2praktikum.exercise.exceptions.NotSpawnedYetException;
import thkoeln.st.st2praktikum.exercise.wall.Wall;

import java.util.HashMap;
import java.util.UUID;

public class Map implements Cloud {

    private final HashMap<UUID, Deck> deckHashMap = new HashMap();
    private final HashMap<UUID, Droid> droidHashMap = new HashMap();


    public UUID addSpaceShipDeck(Integer height, Integer width) {
        final Deck deck = new Deck(height, width);
        this.deckHashMap.put(deck.getID(), deck);
        return deck.getID();
    }

    public void addWall(UUID spaceShipDeckId, String wallString) {
        final Wall wall = new Wall(spaceShipDeckId, wallString);
        deckHashMap.get(spaceShipDeckId).getWallList().add(wall);
    }

    public UUID addConnection(UUID sourceSpaceShipDeckId, String sourceCoordinate, UUID destinationSpaceShipDeckId, String destinationCoordinate) {
        try {
            final Connection connection = new Connection(sourceSpaceShipDeckId, CoordinateService.stringToCoordinate(sourceCoordinate), destinationSpaceShipDeckId, CoordinateService.stringToCoordinate(destinationCoordinate));
            this.deckHashMap.get(sourceSpaceShipDeckId).getFieldFromCoordinate(CoordinateService.stringToCoordinate(sourceCoordinate)).setConnection(connection);
            return connection.getID();
        } catch (NoFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    public UUID addMaintenanceDroid(String name) {
        final Droid droid = new Droid(name);
        this.droidHashMap.put(droid.getID(), droid);
        return droid.getID();
    }

    public Boolean executeCommand(UUID maintenanceDroidId, String commandString) {
        Command command = CommandService.stringToCommand(commandString);
        Droid droid = this.droidHashMap.get(maintenanceDroidId);
        Direction dir = command.getDir();
        switch(dir){
            case WE:
            case NO:
            case SO:
            case EA:
                return CommandService.commandWithPower(command, droid, this);

            case EN:
            case TR:
                return CommandService.commandWithUUID(command, droid, this);
            default:
                return false;
        }
    }

    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        try {
            return droidHashMap.get(maintenanceDroidId).getPosition().getDeckId();
        } catch (NotSpawnedYetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getCoordinates(UUID maintenanceDroidId){
        try {
            return droidHashMap.get(maintenanceDroidId).getPosition().getCoordinate().toString();
        } catch (NotSpawnedYetException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public HashMap<UUID,Deck> getDeckList() {
        return this.deckHashMap;
    }

    @Override
    public HashMap<UUID,Droid> getDroidList() {
        return this.droidHashMap;
    }
}
