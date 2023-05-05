package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public interface Cloud {
    HashMap<UUID,Deck> getDecks();
    HashMap<UUID,Droid> getDroids();
}
