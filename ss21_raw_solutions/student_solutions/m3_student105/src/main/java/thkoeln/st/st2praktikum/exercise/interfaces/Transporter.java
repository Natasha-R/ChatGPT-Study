package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Deck;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.UUID;

public interface Transporter {

    @Id
    UUID getId();

    void setId(UUID id);

    Coordinate getSourceCoordinate();
    Coordinate getDestinationCoordinate();

    Deck getSource();
    Deck getDestination();

    void setSourceCoordinate(Coordinate src);
    void setDestinationCoordinate(Coordinate dest);
    void setSource(Deck src);
    void setDestination(Deck src);
}
