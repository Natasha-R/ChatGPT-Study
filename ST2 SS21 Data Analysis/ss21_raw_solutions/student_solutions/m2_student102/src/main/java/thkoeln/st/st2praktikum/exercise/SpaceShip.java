package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class SpaceShip {
    private int x;
    private int y;
    private UUID id;

    public int getY() {
        return y;
    }
    public SpaceShip(int x ,int y){
        id=UUID.randomUUID();
        this.x=x;
        this.y=y;
    }
    public int getX() {
        return x;
    }

    public UUID getId(){
        return id;
    }
}
