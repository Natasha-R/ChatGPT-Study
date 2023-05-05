package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpaceShipDeck {
    private static List<SpaceShipDeck> spaceShipDeckList = new ArrayList<>();
    int height;
    int width;
    UUID spaceShipDeckId;
    static UUID maintenanceDroidID;

    public SpaceShipDeck(int height, int width, UUID spaceShipDeckId) {
        this.height = height;
        this.width = width;
        this.spaceShipDeckId = spaceShipDeckId;
        spaceShipDeckList.add(this);
    }

    public static SpaceShipDeck getSpaceShipDeck(UUID uuid) {
        SpaceShipDeck spaceShipDeck = null;
        for (SpaceShipDeck ssd : spaceShipDeckList) {
            if (ssd.getSpaceShipDeckId().equals(uuid)) {
                spaceShipDeck = ssd;
            }
        }
        return spaceShipDeck;
    }

    public UUID getSpaceShipDeckId() {
        return spaceShipDeckId;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public static UUID getMaintenanceDroidID() {
        return maintenanceDroidID;
    }

    public static void setMaintenanceDroidID(UUID maintenanceDroidIDS) {
        maintenanceDroidID = maintenanceDroidIDS;
    }


}
