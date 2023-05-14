package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Linkable {
    public boolean checkSourcePosition(int xPosition, int yPosition);
    public int getTargetX_Position();
    public int getTargetY_Position();
    public boolean checkTargetMap(Map targetMap);
}
