package thkoeln.st.st2praktikum.exercise.world2.field;

import thkoeln.st.st2praktikum.exercise.world2.barrier.Barrier;
import thkoeln.st.st2praktikum.exercise.world2.coordinate.Coordinate;
import thkoeln.st.st2praktikum.exercise.world2.exceptions.NoSquareFoundException;
import thkoeln.st.st2praktikum.exercise.world2.square.Square;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public interface FieldController {
    Field getField();
    HashMap<UUID, Square> getSquareHashmap();
    ArrayList<Barrier> getBarrierList();
    Square getSquareFromCoordinate(Coordinate coordinate) throws NoSquareFoundException;
}
