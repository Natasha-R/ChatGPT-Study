package thkoeln.st.st2praktikum.exercise.space.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.space.domain.Wall;
import thkoeln.st.st2praktikum.exercise.space.repository.SpaceRepository;


import java.util.UUID;


@Service
public class SpaceService {

    @Autowired
    SpaceRepository spaceRepository;
    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        Space space = Space.fromShit(height, width);
        spaceRepository.save(space);
        return space.getId();
    }

    /**
     * This method adds a wall to a given space.
     * @param spaceId the ID of the space the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID spaceId, Wall wall) {
        if(spaceRepository.findById(spaceId).isPresent()){
            Space space = spaceRepository.findById(spaceId).get();
            space.addWall(wall);
            spaceRepository.save(space);
        }
    }
}
