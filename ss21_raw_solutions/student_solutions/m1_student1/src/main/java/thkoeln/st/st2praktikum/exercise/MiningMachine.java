package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;


public class MiningMachine {
    private UUID currentFieldID; //hier mit Field arbeiten
    private Point position;
    private final UUID machineUUID;
    private final String machineName;

    public MiningMachine(String name) {
        this.machineName = name;
        this.machineUUID = UUID.randomUUID();
        this.currentFieldID = UUID.randomUUID();
        this.position = new Point(0,0);
    }

    public String getName() {
        return this.machineName;
    }

    public UUID getUUID() {
        return this.machineUUID;
    }

    public UUID getCurrentFieldID() {
        return currentFieldID;
    }

    public Point getPosition() {
        return position;
    }

    public void setCurrentFieldID(UUID uuid) {
        this.currentFieldID = uuid;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

}
