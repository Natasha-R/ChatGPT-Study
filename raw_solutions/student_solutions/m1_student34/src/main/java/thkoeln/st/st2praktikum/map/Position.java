package thkoeln.st.st2praktikum.map;

import thkoeln.st.st2praktikum.linearAlgebra.Cuttable;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;

public interface Position extends Cuttable {
    double distance(Position otherPosition);

    Map getMap();

    Vector getCoordinates();
}
