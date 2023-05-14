package thkoeln.st.st2praktikum.exercise.world2.services;

import thkoeln.st.st2praktikum.exercise.world2.barrier.Barrier;
import thkoeln.st.st2praktikum.exercise.world2.coordinate.Coordinate;

import java.util.ArrayList;

public interface BarrierService {
    static boolean containsMaybeBarrier(Coordinate maybeBarrierStartCoordinates, Coordinate maybeBarrierEndCoordinates, ArrayList<Barrier> barrierList){
        boolean result = false;
        for (Barrier barrier: barrierList){
            if(barrier.containsMaybeBarrier(maybeBarrierStartCoordinates,maybeBarrierEndCoordinates)){
                result = true;
            }
        }
        return result;
    }
}
