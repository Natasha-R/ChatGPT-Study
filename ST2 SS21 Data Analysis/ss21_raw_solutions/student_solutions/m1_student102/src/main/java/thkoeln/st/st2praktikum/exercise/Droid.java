package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Droid {
    private UUID id;
    private UUID idSpaceShip;
    private int positionX;
    private int positionY;

    public Droid() {
    }

    public Droid(String name) {
        id=UUID.randomUUID();
        positionX=0;
        positionY=0;
    }

    public UUID getIdSpaceShip() {
        return idSpaceShip;
    }

    public void setIdSpaceShip(UUID idSpaceShip) {
        this.idSpaceShip = idSpaceShip;
    }

    public UUID getId() {
        return id;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}
