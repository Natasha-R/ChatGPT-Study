package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class BarrierList {
    private final UUID fieldID;
    private final Barrier barrier;
    private final String setXorY;

    MiningMachineService mms = new MiningMachineService();

    public BarrierList(UUID uuid, Barrier barrier) {
        this.fieldID = uuid;
        this.barrier = barrier;
        if (!barrier.getStart().getX().equals(barrier.getEnd().getX())) //wenn irgendwann felder > 9 existieren, mit lastIndexOf arbeiten
            this.setXorY = "x"; //Bilden Barriere auf x-Achse
        else
            this.setXorY = "y"; //Bilden Barriere auf y-Achse
    }

    public UUID getFieldID() {
        return this.fieldID;
    }

    public Barrier getBarrier() {
        return this.barrier;
    }

    public String getSetXorY() {
        return setXorY;
    }


}