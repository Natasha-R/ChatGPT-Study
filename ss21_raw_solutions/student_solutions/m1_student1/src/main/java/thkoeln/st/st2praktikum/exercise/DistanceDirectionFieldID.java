package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class DistanceDirectionFieldID {
    private CommandType commandType;
    private int distance;
    private UUID fieldID;

    public DistanceDirectionFieldID() {
        this.commandType = null;
        this.distance = 0;
        this.fieldID = UUID.randomUUID();
    }

    public int getDistance() {
        return this.distance;
    }

    public CommandType getCommandType() {
        return this.commandType;
    }

    public UUID getFieldID() {
        return this.fieldID;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setFieldID(UUID fieldID) {
        this.fieldID = fieldID;
    }

}
