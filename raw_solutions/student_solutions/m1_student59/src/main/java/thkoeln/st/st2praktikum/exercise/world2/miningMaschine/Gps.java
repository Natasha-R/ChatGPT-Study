package thkoeln.st.st2praktikum.exercise.world2.miningMaschine;

import thkoeln.st.st2praktikum.exercise.world2.Cloud;
import thkoeln.st.st2praktikum.exercise.world2.coordinate.Coordinate;
import thkoeln.st.st2praktikum.exercise.world2.exceptions.IllegalDirectionException;
import thkoeln.st.st2praktikum.exercise.world2.exceptions.MiningmaschineNotSpawnedException;
import thkoeln.st.st2praktikum.exercise.world2.exceptions.NoSquareFoundException;
import thkoeln.st.st2praktikum.exercise.world2.exceptions.OutOfFieldException;
import thkoeln.st.st2praktikum.exercise.world2.services.BarrierService;
import thkoeln.st.st2praktikum.exercise.world2.services.CoordinateService;
import thkoeln.st.st2praktikum.exercise.world2.square.Square;
import thkoeln.st.st2praktikum.exercise.world2.types.Decision;
import thkoeln.st.st2praktikum.exercise.world2.types.Direction;

public interface Gps {
    Square getPosition() throws MiningmaschineNotSpawnedException;
    Decision canGoForward(Direction direction, Cloud world);

    static boolean isBarrierInDirection(Miningmaschine miningmaschine, Direction direction, Cloud world) throws IllegalStateException{
        try{
            Coordinate[] maybeBorder = CoordinateService.createMaybeBorder(miningmaschine.getPosition().getCoordinate(),direction, world.getFieldHashMap().get(miningmaschine.getPosition().getPlacedOnFieldId()));
            return BarrierService.containsMaybeBarrier(maybeBorder[0],maybeBorder[1],world.getFieldHashMap().get(miningmaschine.getPosition().getPlacedOnFieldId()).getBarrierList());
        }catch(IllegalDirectionException | MiningmaschineNotSpawnedException exception){
            System.out.println(exception);
        }catch (OutOfFieldException outOfFieldException){
            return true;
        }
        throw new IllegalStateException("isBarrierInDirection");
    }
    static boolean isMiningMaschineInDirection(Miningmaschine miningmaschine, Direction direction, Cloud world) throws IllegalStateException{
        try{
            Coordinate nextCoordinateInDirection = CoordinateService.nextCoordinateInDirection(miningmaschine.getPosition().getCoordinate(), direction);
            return world.getFieldHashMap().get(miningmaschine.getPosition().getPlacedOnFieldId()).getSquareFromCoordinate(nextCoordinateInDirection).isBlocked();
        }catch(IllegalDirectionException | MiningmaschineNotSpawnedException | NoSquareFoundException exception){
            System.out.println(exception);
        }
        throw new IllegalStateException("isMiningMaschineInDirection");
    }
}
