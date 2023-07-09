package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MaintenanceDroidService {

    List<SpaceShipDeck> spaceShipDecks = new ArrayList<>();

    public SpaceShipDeck findSpaceShipDeckById(String id) {
        for (int i = 0; i < spaceShipDecks.size(); i++) {
            if (spaceShipDecks.get(i).id.toString().equals(id))
                return spaceShipDecks.get(i);
        }
        throw new UnsupportedOperationException("no spaceship deck found!");
    }


    List<MaintenanceDroid> maintenanceDroids = new ArrayList<>();

    public MaintenanceDroid findMaintenanceDroidById(String id) {
        for (int i = 0; i < maintenanceDroids.size(); i++) {
            if (maintenanceDroids.get(i).id.toString().equals(id))
                return maintenanceDroids.get(i);
        }
        throw new UnsupportedOperationException("no maintenance droid found!");
    }


    public Boolean isFieldFree(Integer x, Integer y, String spaceShipDeckId) {
        //Überprüft Grenzen des Decks
        if (x < 0 || x > findSpaceShipDeckById(spaceShipDeckId).width)
            return false;

        if (y < 0 || y > findSpaceShipDeckById(spaceShipDeckId).height)
            return false;

        //Überprüft das sich kein anderer Droid auf dem Feld befindet
        for (int i = 0; i < maintenanceDroids.size(); i++) {

            if (maintenanceDroids.get(i).xPosition == x && maintenanceDroids.get(i).yPosition == y)
                return false;
        }
        return true;


    }


    /**
     * This method creates a new spaceShipDeck.
     * @param height the height of the spaceShipDeck
     * @param width the width of the spaceShipDeck
     * @return the UUID of the created spaceShipDeck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        SpaceShipDeck spaceShipDeck = new SpaceShipDeck();
        spaceShipDeck.id = UUID.randomUUID();
        spaceShipDeck.height = height;
        spaceShipDeck.width = width;
        spaceShipDecks.add(spaceShipDeck);

        return spaceShipDeck.id;
    }

    /**
     * This method adds a wall to a given spaceShipDeck.
     * @param spaceShipDeckId the ID of the spaceShipDeck the wall shall be placed on
     * @param wallString the end points of the wall, encoded as a String, eg. "(2,3)-(10,3)" for a wall that spans from (2,3) to (10,3)
     */
    public void addWall(UUID spaceShipDeckId, String wallString) {

        SpaceShipDeck spaceShipDeck = findSpaceShipDeckById(spaceShipDeckId.toString());

        if (wallString.substring(1, 2).equals(wallString.substring(7, 8)))
            spaceShipDeck.yWalls.add(wallString);

        if (wallString.substring(3, 4).equals(wallString.substring(9, 10)))
            spaceShipDeck.xWalls.add(wallString);
    }

    /**
     * This method adds a traversable connection between two spaceship decks. Connections only work in one direction.
     * @param sourceSpaceShipDeckId the ID of the spaceShipDeck where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceShipDeck where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceSpaceShipDeckId, String sourceCoordinate, UUID destinationSpaceShipDeckId, String destinationCoordinate) {
        Connection connection = new Connection();
        connection.id = UUID.randomUUID();
        connection.sourceSpaceShipDeckId = sourceSpaceShipDeckId;
        connection.sourceCoordinate = sourceCoordinate;
        connection. destinationSpaceShipDeckId = destinationSpaceShipDeckId;
        connection.destinationCoordinate = destinationCoordinate;
        SpaceShipDeck spaceShipDeck = findSpaceShipDeckById(sourceSpaceShipDeckId.toString());
        spaceShipDeck.connections.add(connection);

        return connection.id;
    }

    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        MaintenanceDroid maintenanceDroid = new MaintenanceDroid();
        maintenanceDroid.id = UUID.randomUUID();
        maintenanceDroid.name = name;
        maintenanceDroid.xPosition = 99;
        maintenanceDroid.yPosition = 99;
        maintenanceDroid.spaceShipDeckId = UUID.randomUUID();

        maintenanceDroids.add(maintenanceDroid);

        return maintenanceDroid.id;
    }

    /**
     * This method lets the maintenance droid execute a command.
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,spaceShipDeck-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on cells with a connection to another spaceShipDeck
     * "[en,<spaceShipDeck-id>]" for setting the initial spaceShipDeck where a maintenance droid is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The maintenance droid will always spawn at (0,0) of the given spaceShipDeck.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the maintenance droid hits a wall or
     *      another maintenance droid, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, String commandString) {

        switch (InteractionType.valueOf(commandString.substring(1,3).toUpperCase())) {
            case EN:
                if (isFieldFree(0, 0, commandString.substring(4,40))) {
                    findMaintenanceDroidById(maintenanceDroidId.toString()).xPosition = 0;
                    findMaintenanceDroidById(maintenanceDroidId.toString()).yPosition = 0;
                    findMaintenanceDroidById(maintenanceDroidId.toString()).spaceShipDeckId = UUID.fromString(commandString.substring(4,40));
                    return true;
                }
                return false;


            case TR:
                for (int i = 0; i < findSpaceShipDeckById(findMaintenanceDroidById(maintenanceDroidId.toString()).spaceShipDeckId.toString()).connections.size(); i++) {

                    int sourceDeckCoordinationX = Integer.parseInt(String.valueOf(findSpaceShipDeckById(findMaintenanceDroidById(maintenanceDroidId.toString()).spaceShipDeckId.toString()).connections.get(i).sourceCoordinate.charAt(1)));
                    int sourceDeckCoordinationY = Integer.parseInt(String.valueOf(findSpaceShipDeckById(findMaintenanceDroidById(maintenanceDroidId.toString()).spaceShipDeckId.toString()).connections.get(i).sourceCoordinate.charAt(3)));
                    int destinationDeckCoordinationX = Integer.parseInt(String.valueOf(findSpaceShipDeckById(findMaintenanceDroidById(maintenanceDroidId.toString()).spaceShipDeckId.toString()).connections.get(i).destinationCoordinate.charAt(1)));
                    int destinationDeckCoordinationY = Integer.parseInt(String.valueOf(findSpaceShipDeckById(findMaintenanceDroidById(maintenanceDroidId.toString()).spaceShipDeckId.toString()).connections.get(i).destinationCoordinate.charAt(3)));

                    if (findMaintenanceDroidById(maintenanceDroidId.toString()).xPosition == sourceDeckCoordinationX  && findMaintenanceDroidById(maintenanceDroidId.toString()).yPosition == sourceDeckCoordinationY) {
                        findMaintenanceDroidById(maintenanceDroidId.toString()).xPosition = destinationDeckCoordinationX;
                        findMaintenanceDroidById(maintenanceDroidId.toString()).yPosition = destinationDeckCoordinationY;
                        findMaintenanceDroidById(maintenanceDroidId.toString()).spaceShipDeckId = findSpaceShipDeckById(findMaintenanceDroidById(maintenanceDroidId.toString()).spaceShipDeckId.toString()).connections.get(i).destinationSpaceShipDeckId;
                        return true;
                    }
                }
                return false;


            case NO:

                for (int i = 0; i < commandString.charAt(4) - '0'; i++) {

                    int xNextFieldNO = findMaintenanceDroidById(maintenanceDroidId.toString()).xPosition;
                    int yNextFieldNO = findMaintenanceDroidById(maintenanceDroidId.toString()).yPosition + 1;
                    String deckNO = findMaintenanceDroidById(maintenanceDroidId.toString()).spaceShipDeckId.toString();

                    if(!isFieldFree(xNextFieldNO, yNextFieldNO, deckNO)) {
                        return true;
                    }

                    for (int j = 0; j < findSpaceShipDeckById(deckNO).xWalls.size(); j++) {

                        char mauerBeginnXNO = findSpaceShipDeckById(deckNO).xWalls.get(j).charAt(1);
                        char mauerEndeXNO = findSpaceShipDeckById(deckNO).xWalls.get(j).charAt(7);
                        char mauerHoeheNO = findSpaceShipDeckById(deckNO).xWalls.get(j).charAt(3);

                        int maintenanceXNO = findMaintenanceDroidById(maintenanceDroidId.toString()).xPosition;
                        int maintenanceYNO = findMaintenanceDroidById(maintenanceDroidId.toString()).yPosition;

                        if (maintenanceYNO + 1 == Integer.parseInt(String.valueOf(mauerHoeheNO)) && Integer.parseInt(String.valueOf(mauerBeginnXNO)) <= maintenanceXNO && Integer.parseInt(String.valueOf(mauerEndeXNO)) > maintenanceXNO) {
                            return true;
                        }
                    }
                    findMaintenanceDroidById(maintenanceDroidId.toString()).yPosition += 1;
                }
                return true;


            case EA:

                for (int i = 0; i < commandString.charAt(4) - '0'; i++) {

                    int xNextFieldEA = findMaintenanceDroidById(maintenanceDroidId.toString()).xPosition + 1;
                    int yNextFieldEA = findMaintenanceDroidById(maintenanceDroidId.toString()).yPosition;
                    String deckEA = findMaintenanceDroidById(maintenanceDroidId.toString()).spaceShipDeckId.toString();

                    if(!isFieldFree(xNextFieldEA, yNextFieldEA, deckEA)) {
                        return true;
                    }

                    for (int j = 0; j < findSpaceShipDeckById(deckEA).yWalls.size(); j++) {

                        char mauerBeginnYEA = findSpaceShipDeckById(deckEA).yWalls.get(j).charAt(3);
                        char mauerEndeYEA = findSpaceShipDeckById(deckEA).yWalls.get(j).charAt(9);
                        char mauerLaengeEA = findSpaceShipDeckById(deckEA).yWalls.get(j).charAt(1);

                        int maintenanceXEA = findMaintenanceDroidById(maintenanceDroidId.toString()).xPosition;
                        int maintenanceYEA = findMaintenanceDroidById(maintenanceDroidId.toString()).yPosition;

                        if (maintenanceXEA == mauerLaengeEA - 1 && mauerBeginnYEA <= maintenanceYEA && mauerEndeYEA > maintenanceYEA)
                            return true;
                    }
                    findMaintenanceDroidById(maintenanceDroidId.toString()).xPosition += 1;
                }
                return true;


            case SO:

                for (int i = 0; i < commandString.charAt(4) - '0'; i++) {

                    int xNextFieldSO = findMaintenanceDroidById(maintenanceDroidId.toString()).xPosition;
                    int yNextFieldSO = findMaintenanceDroidById(maintenanceDroidId.toString()).yPosition - 1;
                    String deckSO = findMaintenanceDroidById(maintenanceDroidId.toString()).spaceShipDeckId.toString();

                    if(!isFieldFree(xNextFieldSO, yNextFieldSO, deckSO)) {
                        return true;
                    }

                    for (int j = 0; j < findSpaceShipDeckById(deckSO).xWalls.size(); j++) {

                        char mauerBeginnXSO = findSpaceShipDeckById(deckSO).xWalls.get(j).charAt(1);
                        char mauerEndeXSO = findSpaceShipDeckById(deckSO).xWalls.get(j).charAt(7);
                        char mauerHoeheSO = findSpaceShipDeckById(deckSO).xWalls.get(j).charAt(3);

                        int maintenanceXSO = findMaintenanceDroidById(maintenanceDroidId.toString()).xPosition;
                        int maintenanceYSO = findMaintenanceDroidById(maintenanceDroidId.toString()).yPosition;

                        if (maintenanceYSO == mauerHoeheSO + 1 && mauerBeginnXSO <= maintenanceXSO && mauerEndeXSO > maintenanceXSO)
                            return true;
                    }
                    findMaintenanceDroidById(maintenanceDroidId.toString()).yPosition -= 1;
                }
                return true;


            case WE:

                for (int i = 0; i < commandString.charAt(4) - '0'; i++) {

                    int xNextFieldWE = findMaintenanceDroidById(maintenanceDroidId.toString()).xPosition - 1;
                    int yNextFieldWE = findMaintenanceDroidById(maintenanceDroidId.toString()).yPosition;
                    String deckWE = findMaintenanceDroidById(maintenanceDroidId.toString()).spaceShipDeckId.toString();

                    if(!isFieldFree(xNextFieldWE, yNextFieldWE, deckWE)) {
                        return true;
                    }

                    for (int j = 0; j < findSpaceShipDeckById(deckWE).yWalls.size(); j++) {

                        char mauerBeginnYWE = findSpaceShipDeckById(deckWE).yWalls.get(j).charAt(3);
                        char mauerEndeYWE = findSpaceShipDeckById(deckWE).yWalls.get(j).charAt(9);
                        char mauerLaengeWE = findSpaceShipDeckById(deckWE).yWalls.get(j).charAt(1);

                        int maintenanceXWE = findMaintenanceDroidById(maintenanceDroidId.toString()).xPosition;
                        int maintenanceYWE = findMaintenanceDroidById(maintenanceDroidId.toString()).yPosition;

                        if (maintenanceXWE == mauerLaengeWE + 1 && mauerBeginnYWE <= maintenanceYWE && mauerEndeYWE > maintenanceYWE)
                            return true;
                    }
                    findMaintenanceDroidById(maintenanceDroidId.toString()).xPosition -= 1;
                }
                return true;

            default:
                throw new UnsupportedOperationException("should not happened!");
        }
    }

    /**
     * This method returns the spaceShipDeck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceShipDeck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        MaintenanceDroid maintenanceDroid = findMaintenanceDroidById(maintenanceDroidId.toString());
        return maintenanceDroid.spaceShipDeckId;
    }

    /**
     * This method returns the coordinates a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the coordiantes of the maintenance droid encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID maintenanceDroidId){
        MaintenanceDroid maintenanceDroid = findMaintenanceDroidById(maintenanceDroidId.toString());
        return "(" + maintenanceDroid.xPosition + "," + maintenanceDroid.yPosition + ")";
    }
}
