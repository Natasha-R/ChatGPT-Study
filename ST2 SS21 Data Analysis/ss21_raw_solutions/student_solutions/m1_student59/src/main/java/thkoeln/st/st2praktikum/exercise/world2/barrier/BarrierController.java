package thkoeln.st.st2praktikum.exercise.world2.barrier;

import thkoeln.st.st2praktikum.exercise.world2.coordinate.Coordinate;

import java.util.ArrayList;
import java.util.UUID;

public interface BarrierController {
    Barrier getBarrier();
    UUID getFieldId();
    ArrayList<Coordinate> getBarrierCoordinates();
    boolean containsMaybeBarrier(Coordinate maybeBarrierStartCoordinates, Coordinate maybeBarrierEndCoordinates);
}
