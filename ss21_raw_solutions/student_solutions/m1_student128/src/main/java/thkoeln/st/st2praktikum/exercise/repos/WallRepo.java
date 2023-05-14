package thkoeln.st.st2praktikum.exercise.repos;

import thkoeln.st.st2praktikum.exercise.Entity.Wall;

import java.util.UUID;

public interface WallRepo {

    public Wall addWall(UUID spaceId, String wallString);
}
