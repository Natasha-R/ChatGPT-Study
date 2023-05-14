package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Barrier {
    private UUID idSpaceShip;
    private char barrierType;
    private int firstBarrierPointX;
    private int firstBarrierPointY;
    private int secondBarrierPointX;
    private int secondBarrierPointY;

    public UUID getIdSpaceShip() {
        return idSpaceShip;
    }

    public void setIdSpaceShip(UUID idSpaceShip) {
        this.idSpaceShip = idSpaceShip;
    }

    public char getBarrierType() {
        return barrierType;
    }

    public void setBarrierType(char barrierType) {
        this.barrierType = barrierType;
    }
    public Barrier(){}

    public Barrier(UUID id, char barrierType, int firstBarrierPointX, int firstBarrierPointY, int secondBarrierPointX, int secondBarrierPointY) {
        this.idSpaceShip = id;
        this.barrierType = barrierType;
        this.firstBarrierPointX = firstBarrierPointX;
        this.firstBarrierPointY = firstBarrierPointY;
        this.secondBarrierPointX = secondBarrierPointX;
        this.secondBarrierPointY = secondBarrierPointY;
    }
    public int getFirstBarrierPointX() {
        return firstBarrierPointX;
    }

    public int getFirstBarrierPointY() {
        return firstBarrierPointY;
    }

    public int getSecondBarrierPointX() {
        return secondBarrierPointX;
    }

    public int getSecondBarrierPointY() {
        return secondBarrierPointY;
    }
}
