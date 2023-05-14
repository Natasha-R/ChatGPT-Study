package thkoeln.st.st2praktikum.linearAlgebra;

import java.util.Optional;

public interface Cuttable {
    Optional<Vector> cut(Cuttable cuttable);
}
