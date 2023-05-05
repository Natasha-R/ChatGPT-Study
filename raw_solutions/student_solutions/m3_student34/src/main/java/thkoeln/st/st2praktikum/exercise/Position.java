package thkoeln.st.st2praktikum.exercise;

import javax.persistence.*;

@Embeddable
public interface Position extends Cuttable {
    double distance(Position otherPosition);

    @Transient
    Map getMap();

    @Embedded
    Vector getCoordinates();
}
