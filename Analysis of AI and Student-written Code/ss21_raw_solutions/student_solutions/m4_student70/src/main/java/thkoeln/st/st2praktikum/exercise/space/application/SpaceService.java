package thkoeln.st.st2praktikum.exercise.space.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.space.domain.Wall;
import thkoeln.st.st2praktikum.exercise.space.repo.SpaceRepositry;


import java.util.Optional;
import java.util.UUID;


@Service
public class SpaceService {
    @Autowired
    SpaceRepositry spaceRepositry;
    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
Space space=new Space(height,width);
         spaceRepositry.save(space);
         return space.getSpaceId();
    }

    /**
     * This method adds a wall to a given space.
     * @param spaceId the ID of the space the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID spaceId, Wall wall) {
        Optional<Space> space =spaceRepositry.findById(spaceId);
        if (space.isPresent()){
            space.get().getWalls().add(wall);
            spaceRepositry.save(space.get());
        }else throw new UnsupportedOperationException();
    }
}
