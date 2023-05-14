package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.exceptions.NoConnectionException;
import thkoeln.st.st2praktikum.exercise.exceptions.NoFieldException;
import thkoeln.st.st2praktikum.exercise.exceptions.NotSpawnedYetException;

import java.util.UUID;

public class Droid implements Moveable, UUidable, GlobalPositioningSystem, Spawnable{
    private final String name;
    public final UUID droidId;
    private boolean spawned;
    private Field position;

    public Droid(String name){
        this.droidId = UUID.randomUUID();
        this.name = name;
        this.spawned = false;
    }



    @Override
    public UUID getID() {
        return this.droidId;
    }

    @Override
    public Field getPosition() throws NotSpawnedYetException {
        if(this.spawned = true){
            return this.position;
        }else{
            throw new NotSpawnedYetException(this.name);
        }

    }

    @Override
    public Decision canGoForward(OrderType orderType, Cloud world) throws NotSpawnedYetException, NoFieldException {
        if(GlobalPositioningSystem.wallInDirection(orderType, this, world) || GlobalPositioningSystem.droidInDirection(orderType, this, world)){
            return Decision.cantGoForward;
        }else{
            return  Decision.canGoForward;
        }

    }

    @Override
    public Droid getDroid(UUID droidId) {
        return this;
    }

    @Override
    public boolean isSpawned() {
        return this.spawned;
    }

    @Override
    public boolean move(OrderType orderType, Cloud world) throws NotSpawnedYetException, NoFieldException {


        switch (orderType) {
            case NORTH:
                changeBlockedStatus(world);
                this.position = world.getDecks().get(this.position.getDeckId()).getFieldFromCoordinate(new Coordinate(this.position.getCoordinate().getX(), this.position.getCoordinate().getY() + 1));
                changeBlockedStatus(world);
                return true;
            case EAST:
                changeBlockedStatus(world);
                this.position = world.getDecks().get(this.position.getDeckId()).getFieldFromCoordinate(new Coordinate(this.position.getCoordinate().getX() + 1, this.position.getCoordinate().getY()));
                changeBlockedStatus(world);
                return true;
            case SOUTH:
                changeBlockedStatus(world);
                this.position = world.getDecks().get(this.position.getDeckId()).getFieldFromCoordinate(new Coordinate(this.position.getCoordinate().getX(), this.position.getCoordinate().getY() - 1));
                changeBlockedStatus(world);
                return true;
            case WEST:
                changeBlockedStatus(world);
                this.position = world.getDecks().get(this.position.getDeckId()).getFieldFromCoordinate(new Coordinate(this.position.getCoordinate().getX() - 1, this.position.getCoordinate().getY()));
                changeBlockedStatus(world);
                return true;
            default:
                return false;

        }
    }

    private void changeBlockedStatus(Cloud world) throws NoFieldException {
        world.getDecks().get(this.position.getDeckId()).getFieldFromCoordinate(this.position.getCoordinate()).changeBlocked();
    }

    @Override
    public boolean travel(Cloud world) throws NoFieldException, NoConnectionException {
        if(this.position.hasConnection()){
            if(!world.getDecks().get(this.position.getConnection().getDestinationFieldId()).getFieldFromCoordinate(this.position.getConnection().getDestinationCoordinate()).isBlocked()){
                changeBlockedStatus(world);
                this.position = world.getDecks().get(this.position.getConnection().getDestinationFieldId()).getFieldFromCoordinate(this.position.getConnection().getDestinationCoordinate());
                changeBlockedStatus(world);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public boolean spawn(Cloud world, UUID spaceShipId) throws NoFieldException {
        if(!world.getDecks().get(spaceShipId).getFieldFromCoordinate(new Coordinate(0, 0)).isBlocked()){
            this.position = world.getDecks().get(spaceShipId).getFieldFromCoordinate(new Coordinate(0, 0));
            changeBlockedStatus(world);
            this.spawned = true;
            return true;
        }else{
            return false;
        }
    }
}
