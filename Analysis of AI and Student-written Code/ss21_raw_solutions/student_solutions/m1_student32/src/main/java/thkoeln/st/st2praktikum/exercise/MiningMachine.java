package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class MiningMachine {

    private final UUID miningMachineId;
    private String name;
    private Integer xPos;
    private Integer yPos;
    private UUID fieldId;

    public MiningMachine(UUID miningMachineId, String name) {
        this.miningMachineId = miningMachineId;
        this.name = name;
    }

    public void moveNorth(){
        yPos += 1;
    }

    public void moveEast(){
        xPos += 1;
    }

    public void moveSouth(){
        yPos -= 1;
    }

    public void moveWest(){
        xPos -= 1;
    }

    public void setFieldId(UUID fieldId) {
        this.fieldId = fieldId;
    }

    public void setXPos(Integer xPos) {
        this.xPos = xPos;
    }

    public void setYPos(Integer yPos) {
        this.yPos = yPos;
    }

    public String getCoordinates(){
        return "("+xPos+","+yPos+")";
    }

    public UUID getMiningMachineId() {
        return miningMachineId;
    }

    public UUID getFieldId() {
        return fieldId;
    }

    public Integer getXPos() {
        return xPos;
    }

    public Integer getYPos() {
        return yPos;
    }
}
