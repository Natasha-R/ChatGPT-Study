package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Robot {
    public String getName();
    public UUID getRobotId();
    public UUID getRobotRoomId();
    public Pair<Integer,Integer> getCoordinate();

}
