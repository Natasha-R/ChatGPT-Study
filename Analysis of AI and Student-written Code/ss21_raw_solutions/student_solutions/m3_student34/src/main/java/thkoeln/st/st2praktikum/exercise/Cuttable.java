package thkoeln.st.st2praktikum.exercise;

import java.util.Optional;

public interface Cuttable {
    Optional<Vector> cut(Cuttable cuttable);
}
