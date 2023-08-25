package thkoeln.st.st2praktikum.exercise;

import java.util.Optional;

public interface Cutting {
    Optional<Vector> calculateCut();
    Cuttable getLeftCuttable();
    Cuttable getRightCuttable();
}
