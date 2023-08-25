package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

@Getter
public class SpaceShip {

    private HashMap<UUID,MaintanceDroid> droidHashMap = new HashMap<>();
    private HashMap<UUID, ShipDeck> shipDeckHashMap = new HashMap<>();
}

