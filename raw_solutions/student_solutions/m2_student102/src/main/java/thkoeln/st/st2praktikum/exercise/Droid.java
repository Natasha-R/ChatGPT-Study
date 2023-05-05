package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Droid {
    private UUID id;
    private UUID idSpaceShip;
    private Point point=new Point();

    public Droid() { }

    public Droid(String name) {
        id=UUID.randomUUID();
        point.setX(0);
        point.setY(0);
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

    public int getPositionX() { return point.getX(); }
    public int getPositionY() {
        return point.getY();
    }
    public void setPositionY(int positionY) { point.setY(positionY); }
    public void setPositionX(int positionX) { point.setX(positionX); }
}
