package thkoeln.st.st2praktikum.exercise.world2.miningMaschine;

import thkoeln.st.st2praktikum.exercise.world2.Cloud;
import thkoeln.st.st2praktikum.exercise.world2.coordinate.Coordinate;
import thkoeln.st.st2praktikum.exercise.world2.exceptions.IllegalDirectionException;
import thkoeln.st.st2praktikum.exercise.world2.exceptions.MiningmaschineNotSpawnedException;
import thkoeln.st.st2praktikum.exercise.world2.exceptions.NoConnectionFoundException;
import thkoeln.st.st2praktikum.exercise.world2.exceptions.NoSquareFoundException;
import thkoeln.st.st2praktikum.exercise.world2.services.UUidable;
import thkoeln.st.st2praktikum.exercise.world2.square.Square;
import thkoeln.st.st2praktikum.exercise.world2.types.Decision;
import thkoeln.st.st2praktikum.exercise.world2.types.Direction;

import java.util.UUID;

public class Miningmaschine implements UUidable,Spawnable, Gps, Driveable{

    private final String name;
    private final UUID miningMaschineId;
    private boolean spawned;
    private Square position;

    public Miningmaschine(String name){
        this.miningMaschineId = UUID.randomUUID();
        this.name = name;
        this.spawned = false;
    }

    @Override
    public boolean drive(Direction direction, Cloud world) {
        try{
            switch(direction){
                case NORTH:
                    this.squareChangeBlocked(world);
                    this.position = world.getFieldHashMap().get(this.position.getPlacedOnFieldId()).getSquareFromCoordinate(new Coordinate(this.position.getCoordinate().getXAxis(), this.position.getCoordinate().getYAxis()+1));
                    this.squareChangeBlocked(world);
                    return true;
                case WEST:
                    this.squareChangeBlocked(world);
                    this.position = world.getFieldHashMap().get(this.position.getPlacedOnFieldId()).getSquareFromCoordinate(new Coordinate(this.position.getCoordinate().getXAxis()-1, this.position.getCoordinate().getYAxis()));
                    this.squareChangeBlocked(world);
                    return true;
                case SOUTH:
                    this.squareChangeBlocked(world);
                    this.position = world.getFieldHashMap().get(this.position.getPlacedOnFieldId()).getSquareFromCoordinate(new Coordinate(this.position.getCoordinate().getXAxis(), this.position.getCoordinate().getYAxis()-1));
                    this.squareChangeBlocked(world);
                    return true;
                case EAST:
                    this.squareChangeBlocked(world);
                    this.position = world.getFieldHashMap().get(this.position.getPlacedOnFieldId()).getSquareFromCoordinate(new Coordinate(this.position.getCoordinate().getXAxis()+1, this.position.getCoordinate().getYAxis()));
                    this.squareChangeBlocked(world);
                    return true;
                default:
                    throw new IllegalDirectionException(direction.toString());
            }
        }catch (IllegalDirectionException | NoSquareFoundException exception){
            System.out.println(exception);
            return false;
        }
    }

    private void squareChangeBlocked(Cloud world) throws NoSquareFoundException {
        world.getFieldHashMap().get(this.position.getPlacedOnFieldId()).getSquareFromCoordinate(this.position.getCoordinate()).changeBlocked();
    };


    @Override
    public Square getPosition() throws MiningmaschineNotSpawnedException {
        if(this.spawned){
            return this.position;
        }else{
            throw new MiningmaschineNotSpawnedException(this.name);
        }
    }
    @Override
    public Decision canGoForward(Direction direction, Cloud world) throws IllegalStateException {
        try{
            if(Gps.isBarrierInDirection(this,direction,world) || Gps.isMiningMaschineInDirection(this,direction,world)){
                return Decision.NOWAY;
            }else{
                return Decision.MAKEABLE;
            }
        }catch (IllegalStateException illegalStateException){
            System.out.println(illegalStateException);
        }
        throw new IllegalStateException("canGoForward");
    }

    @Override
    public Miningmaschine getMiningMaschine(UUID MiningMaschineId) {
        return this;
    }

    @Override
    public boolean isSpawned() {
        return this.spawned;
    }

    @Override
    public boolean spawnMiningMaschine(UUID fieldId, Coordinate coordinate, boolean blocked, Cloud world) {
        try{
            if(!world.getFieldHashMap().get(fieldId).getSquareFromCoordinate(coordinate).isBlocked()){
                this.position = new Square(fieldId, coordinate, true);
                this.spawned = true;
                world.getFieldHashMap().get(fieldId).getSquareFromCoordinate(coordinate).changeBlocked();
                return true;
            }else{
                return false;
            }
        }catch (NoSquareFoundException noSquareFoundException){
            System.out.println(noSquareFoundException);
            return false;
        }
    }

    @Override
    public boolean tunnelMininngMaschine(Cloud world) {
        //überprüfung ob es eine connection auf dem square gibt
        if(this.position.hasConnection()){
            try{
                //überprüfung ob destination-square schon blockiert ist
                if(!world.getFieldHashMap().get(this.position.getPlacedOnFieldId()).getSquareFromCoordinate(this.position.getConnection().getDestinationCoordinate()).isBlocked()){
                    //source square wird geunblocked
                    world.getFieldHashMap().get(this.position.getPlacedOnFieldId()).getSquareFromCoordinate(this.position.getCoordinate()).changeBlocked();
                    //destination square wird geblockt
                    world.getFieldHashMap().get(this.position.getConnection().getDestinationFieldId()).getSquareFromCoordinate(this.position.getConnection().getDestinationCoordinate()).changeBlocked();
                    //move miningmaschine
                    this.position = world.getFieldHashMap().get(this.position.getConnection().getDestinationFieldId()).getSquareFromCoordinate(this.position.getConnection().getDestinationCoordinate());
                    return true;
                }else{
                    return false;
                }
            }catch (NoConnectionFoundException | NoSquareFoundException exception){
                System.out.println(exception);
            }
        }else{
            return false;
        }
        throw new IllegalStateException("tunnelMininngMaschine");
    }

    @Override
    public UUID getId() {
        return this.miningMaschineId;
    }

    public String getName() {
        return this.name;
    }
}
