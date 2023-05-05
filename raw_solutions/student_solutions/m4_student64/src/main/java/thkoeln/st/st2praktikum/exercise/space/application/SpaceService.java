package thkoeln.st.st2praktikum.exercise.space.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.space.domain.Obstacle;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.space.domain.SpaceRepository;


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
        Space newSpace = new Space(height, width);
        this.spaceRepository.save(newSpace);

        return newSpace.getId();
    }

    /**
     * This method adds a obstacle to a given space.
     * @param spaceId the ID of the space the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID spaceId, Obstacle obstacle) {
        Space someSpace = this.spaceRepository.findById(spaceId).orElseThrow();
        someSpace.addObstacle(obstacle);
        this.spaceRepository.save(someSpace);
    }

    public Space getSpaceById(UUID spaceId)
    {
        return this.spaceRepository.findById(spaceId).orElse(null);
    }
}
