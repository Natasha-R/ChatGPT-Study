package thkoeln.st.st2praktikum.exercise.droid;

import thkoeln.st.st2praktikum.exercise.UUidable;
import thkoeln.st.st2praktikum.exercise.coordinate.Coordinate;
import thkoeln.st.st2praktikum.exercise.exceptions.NoConnectionException;
import thkoeln.st.st2praktikum.exercise.exceptions.NoFieldException;
import thkoeln.st.st2praktikum.exercise.exceptions.NotSpawnedYetException;
import thkoeln.st.st2praktikum.exercise.field.Field;
import thkoeln.st.st2praktikum.exercise.map.Cloud;

import java.util.UUID;

public class Droid implements Moveable, UUidable, GlobalPositioningSystem, Spawnable {
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
    public Field getPosition() throws NotSpawnedYetException{
       if(this.spawned = true){
           return this.position;
       }else{
           throw new NotSpawnedYetException(this.name);
       }

    }

    @Override
    public Decision canGoForward(Direction dir, Cloud world) throws NotSpawnedYetException, NoFieldException {
        if(GlobalPositioningSystem.wallInDirection(dir, this, world) || GlobalPositioningSystem.droidInDirection(dir, this, world)){
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
    public boolean move(Direction direction, Cloud world) throws NotSpawnedYetException, NoFieldException {


            switch (direction) {
                case NO:
                    changeBlockedStatus(world);
                    this.position = world.getDeckList().get(this.position.getDeckId()).getFieldFromCoordinate(new Coordinate(this.position.getCoordinate().getXAxes(), this.position.getCoordinate().getYAxes() + 1));
                    changeBlockedStatus(world);
                    System.out.println(this.position.getCoordinate().toString());
                    return true;
                case EA:
                    changeBlockedStatus(world);
                    this.position = world.getDeckList().get(this.position.getDeckId()).getFieldFromCoordinate(new Coordinate(this.position.getCoordinate().getXAxes() + 1, this.position.getCoordinate().getYAxes()));
                    changeBlockedStatus(world);
                    System.out.println(this.position.getCoordinate().toString());
                    return true;
                case SO:
                    changeBlockedStatus(world);
                    this.position = world.getDeckList().get(this.position.getDeckId()).getFieldFromCoordinate(new Coordinate(this.position.getCoordinate().getXAxes(), this.position.getCoordinate().getYAxes() - 1));
                    changeBlockedStatus(world);
                    System.out.println(this.position.getCoordinate().toString());
                    return true;
                case WE:
                    changeBlockedStatus(world);
                    this.position = world.getDeckList().get(this.position.getDeckId()).getFieldFromCoordinate(new Coordinate(this.position.getCoordinate().getXAxes() - 1, this.position.getCoordinate().getYAxes()));
                    changeBlockedStatus(world);
                    System.out.println(this.position.getCoordinate().toString());
                    return true;
                default:
                    return false;

            }
    }

    private void changeBlockedStatus(Cloud world) throws NoFieldException {
        world.getDeckList().get(this.position.getDeckId()).getFieldFromCoordinate(this.position.getCoordinate()).changeBlocked();
    }

    @Override
    public boolean travel(Cloud world) throws NoFieldException, NoConnectionException {
        if(this.position.hasConnection()){
            if(!world.getDeckList().get(this.position.getConnection().getDestinationFieldId()).getFieldFromCoordinate(this.position.getConnection().getDestinationCoordinate()).isBlocked()){
                changeBlockedStatus(world);
                this.position = world.getDeckList().get(this.position.getConnection().getDestinationFieldId()).getFieldFromCoordinate(this.position.getConnection().getDestinationCoordinate());
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
        if(!world.getDeckList().get(spaceShipId).getFieldFromCoordinate(new Coordinate(0, 0)).isBlocked()){
            this.position = world.getDeckList().get(spaceShipId).getFieldFromCoordinate(new Coordinate(0, 0));
            changeBlockedStatus(world);
            this.spawned = true;
            return true;
        }else{
            return false;
        }
    }
}
