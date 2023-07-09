package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Barrier {
    private static List<Barrier> barrierList = new ArrayList<>();
    UUID spaceShipDeckId;
    String barrierString;

    public Barrier(UUID spaceShipDeckId, String barrierString) {
        this.spaceShipDeckId = spaceShipDeckId;
        this.barrierString = barrierString;
        barrierList.add(this);
    }

    public static Barrier getBarrier(UUID spaceShipDeckId) {
        Barrier barrier = null;
        for (Barrier brr : barrierList) {
            if (brr.getSpaceShipDeckId().equals(spaceShipDeckId)) {
                barrier = brr;
            }
        }
        return barrier;
    }

    public UUID getSpaceShipDeckId() {
        return spaceShipDeckId;
    }

    public String getBarrierString() {
        return barrierString;
    }
}
