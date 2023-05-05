package thkoeln.st.st2praktikum.exercise;

import org.jboss.jandex.Main;

import java.util.HashMap;
import java.util.UUID;

public class Field implements Creatable,Commandable{

    protected HashMap<UUID, MaintenanceDroid> maintenanceDroids = new HashMap<>();
    protected HashMap<UUID, SpaceDeck> spaceDecks = new HashMap<>();

    public MaintenanceDroid retrieveMaintenanceDroid(UUID maintenanceDroid){
        return  maintenanceDroids.get(maintenanceDroid);
    }

    public SpaceDeck retrieveSpaceDeck(UUID spaceDeck){
        return spaceDecks.get(spaceDeck);
    }

    public  UUID retrieveOccupiedSpaceDeckId(UUID maintenanceDroid){
        return retrieveMaintenanceDroid(maintenanceDroid).getCurrentSpaceDeckUUID();
    }

    public Point retrievePoint(UUID maintenanceDroid){
        return retrieveMaintenanceDroid(maintenanceDroid).getPoint();
    }

    @Override
    public boolean movement(UUID maintenanceDroid, Command command) {
        int steps= command.getNumberOfSteps();
        if(command.getCommandType().equals(CommandType.NORTH)){
            maintenanceDroids.get(maintenanceDroid).moveNorth(steps);
        }else if(command.getCommandType().equals(CommandType.SOUTH)){
            maintenanceDroids.get(maintenanceDroid).moveSouth(steps);
        }else if(command.getCommandType().equals(CommandType.EAST)){
            maintenanceDroids.get(maintenanceDroid).moveEast(steps);
        }else if(command.getCommandType().equals(CommandType.WEST)){
            maintenanceDroids.get(maintenanceDroid).moveWest(steps);
        }
        return false;
    }

    @Override
    public boolean transport(UUID maintenanceDroid, Command command) {
        UUID destinationSpaceDeckId = command.getGridId();
        MaintenanceDroid droid= retrieveMaintenanceDroid(maintenanceDroid);
        Connection connexion = retrieveSpaceDeck(droid.getCurrentSpaceDeckUUID()).retrieveConnection(destinationSpaceDeckId);
        SpaceDeck destinationSpace= retrieveSpaceDeck(destinationSpaceDeckId);
        // droids' coordinate =src coordinate of connection
        if(connexion.getSourcePoint().equals(droid.getPoint())) {
            if (!destinationSpace.maintenanceDroids.isEmpty()) {
                for (int i = 0; i < destinationSpace.maintenanceDroids.size(); i++) {
                    //check if the destination point is free
                    if (destinationSpace.maintenanceDroids.get(i).getPoint().equals(connexion.getDestinationPoint())) {
                        throw new RuntimeException();
                    }
                }
            } else {
                //transfer droid from the source deck to the destination deck
                retrieveSpaceDeck(droid.getCurrentSpaceDeckUUID()).maintenanceDroids.remove(maintenanceDroids.get(maintenanceDroid));
                droid.setXY(connexion.getDestinationPoint().getX(), connexion.getDestinationPoint().getY());
                droid.setCurrentSpaceDeckUUID(destinationSpaceDeckId);
                droid.setCurrentSpaceDeck(retrieveSpaceDeck(destinationSpaceDeckId));
                return true;
            }
        }
        return false;
    }

    public void setMaintenanceDroid(UUID maintenanceDroid, UUID spaceDeck){
        retrieveMaintenanceDroid(maintenanceDroid).setCurrentSpaceDeckUUID(spaceDeck);
        retrieveMaintenanceDroid(maintenanceDroid).setXY(0,0);
        retrieveMaintenanceDroid(maintenanceDroid).setCurrentSpaceDeck(retrieveSpaceDeck(spaceDeck));
        retrieveSpaceDeck(spaceDeck).maintenanceDroids.add(retrieveMaintenanceDroid(maintenanceDroid));
    }

    @Override
    public boolean initialize(UUID maintenanceDroid, Command command) {
        UUID spaceDeck= command.getGridId();

        if(retrieveSpaceDeck(spaceDeck).maintenanceDroids.isEmpty()){
            setMaintenanceDroid(maintenanceDroid, spaceDeck);
            return  true;
        }else{
            for(int i = 0; i<retrieveSpaceDeck(spaceDeck).maintenanceDroids.size(); i++){
                if(retrieveSpaceDeck(spaceDeck).maintenanceDroids.get(i).getPoint().equals(new Point(0,0))){return false;}
                else {setMaintenanceDroid(maintenanceDroid,spaceDeck);
                return true;}
            }
        }
        return false;
    }

    @Override
    public UUID createConnection(UUID sourceSpaceDeckId, Point sourcePoint,
                                 UUID destinationSpaceDeckId, Point destinationPoint) {

        Connection createdConnection = new Connection(sourceSpaceDeckId,destinationSpaceDeckId,sourcePoint,destinationPoint);
        SpaceDeck sourceSpaceDek= retrieveSpaceDeck(sourceSpaceDeckId);
        SpaceDeck destinationSpaceDeck = retrieveSpaceDeck(destinationSpaceDeckId);
        //make sure connection isn't out of bounds
        if(sourcePoint.getX() <= sourceSpaceDek.getWidth() && sourcePoint.getY()<= sourceSpaceDek.getHeight()){
            if(destinationPoint.getX()<= destinationSpaceDeck.getWidth() && destinationPoint.getY() <= destinationSpaceDeck.getHeight()){
                retrieveSpaceDeck(sourceSpaceDeckId).addConnection(createdConnection);
                return createdConnection.getId();
            }else throw new RuntimeException();
        }else throw new RuntimeException();
    }

    @Override
    public UUID createSpaceDeck(Integer height, Integer width) {
        SpaceDeck spaceDeck = new SpaceDeck(height-1,width-1);
        spaceDecks.put(spaceDeck.getId(),spaceDeck);
        return spaceDeck.getId();
    }

    @Override
    public void createBarrier(UUID spaceDeck, Barrier barrier) {
        retrieveSpaceDeck(spaceDeck).addBarrier(barrier);
    }

    @Override
    public UUID createMaintenanceDroid(String name) {
        MaintenanceDroid maintenanceDroid = new MaintenanceDroid();
        maintenanceDroid.setName(name);
        maintenanceDroids.put(maintenanceDroid.getId(),maintenanceDroid);
        return maintenanceDroid.getId();
    }

    public boolean canCommandBeExecuted(UUID maintenanceDroid, Command command){
        if(command.getCommandType().equals(CommandType.ENTER)){
            return initialize(maintenanceDroid,command);
        }else if(command.getCommandType().equals(CommandType.TRANSPORT)){
            return transport(maintenanceDroid,command);
        } else return movement(maintenanceDroid,command);
    }
}
