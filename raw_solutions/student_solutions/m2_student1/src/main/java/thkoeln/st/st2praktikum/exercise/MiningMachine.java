package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class MiningMachine {

    private final UUID uuid;
    private final String name;

    public MiningMachine(String machineName) {
        this.name = machineName;
        this.uuid = UUID.randomUUID();

    }

    public UUID getMachineID() {
        return uuid;
    }

    public String getMachineName() {
        return name;
    }

}
