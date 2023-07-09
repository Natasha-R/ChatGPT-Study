package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Deck;

import javax.persistence.Id;
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
