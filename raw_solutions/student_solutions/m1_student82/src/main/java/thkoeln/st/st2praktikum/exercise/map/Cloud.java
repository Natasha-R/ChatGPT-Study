package thkoeln.st.st2praktikum.exercise.map;

import thkoeln.st.st2praktikum.exercise.deck.Deck;
import thkoeln.st.st2praktikum.exercise.droid.Droid;

import java.util.HashMap;
import java.util.UUID;

public interface Cloud {
    HashMap<UUID,Deck> getDeckList();
    HashMap<UUID,Droid> getDroidList();
}
