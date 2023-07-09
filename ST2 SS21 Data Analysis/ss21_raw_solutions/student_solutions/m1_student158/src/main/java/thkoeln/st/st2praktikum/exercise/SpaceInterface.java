package thkoeln.st.st2praktikum.exercise;

import java.util.List;
import java.util.UUID;

public interface SpaceInterface {

    public Boolean horizontalTileFree(Position position, Integer step);

    public Boolean verticalTileFree(Position position, Integer step);

    public void addConnection(Connection connection);

    public void addWall(Wall wall);

    public UUID getId();

    public Integer getWidth();

    public Integer getHeight();

    public List<Connection> getConnections();

    public Boolean isDefaultPositionFree();

    public void setDefaultPositionFree(Boolean isFree);

}
