package thkoeln.st.st2praktikum.exercise;

import javax.persistence.Entity;
import java.util.List;
import java.util.UUID;

public interface SpaceInterface {

    public Boolean horizontalTileFree(Coordinate coordinate, Integer step);

    public Boolean verticalTileFree(Coordinate coordinate, Integer step);

    public void addConnection(Connection connection);

    public void addWall(Wall wall);

    public UUID getId();

    public Integer getWidth();

    public Integer getHeight();

    public List<Connection> getConnections();

    public Boolean isDefaultPositionFree();

    public void setDefaultPositionFree(Boolean isFree);

}
