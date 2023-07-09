package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Tile implements Field{

    @Override
    public boolean isWall() {
        return false;
    }

    @Override
    public boolean isConnection() {
        return false;
    }
}
