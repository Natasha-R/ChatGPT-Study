package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Deck;

public interface Spawnable {

    void spawn(Deck deck, Coordinate coordinate);
}
