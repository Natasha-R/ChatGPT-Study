package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class SpaceShipDeckRepository {
    private List<SpaceShipDeck> spaceShipDecks = new ArrayList<>();

    public SpaceShipDeck findSpaceShipDeckById(String id) {
        for (int i = 0; i < spaceShipDecks.size(); i++) {
            if (spaceShipDecks.get(i).getId().toString().equals(id))
                return spaceShipDecks.get(i);
        }
        throw new NoSuchElementException("no spaceship deck found!");
    }


    public List<SpaceShipDeck> getSpaceShipDecks() {
        return spaceShipDecks;
    }

    public void setSpaceShipDecks(List<SpaceShipDeck> spaceShipDecks) {
        this.spaceShipDecks = spaceShipDecks;
    }
}
