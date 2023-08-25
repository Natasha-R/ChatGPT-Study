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
        Space space= new Space(height,width);
        spaceRepository.save(space);
        return space.getSpaceId();
    }

    /**
     * This method adds a obstacle to a given space.
     * @param spaceId the ID of the space the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID spaceId, Obstacle obstacle) {
        if (getSpaceShip(spaceId).getHeight() <= obstacle.getStart().getX() || getSpaceShip(spaceId).getWidth() <=obstacle.getStart().getY())
            throw new RuntimeException();
        else if (getSpaceShip(spaceId).getHeight() <= obstacle.getEnd().getX() || getSpaceShip(spaceId).getWidth() <= obstacle.getEnd().getY())
            throw new RuntimeException();
        else
            getSpaceShip(spaceId).addObstacle(obstacle);
    }
    public Space getSpaceShip(UUID spaceId){
        return spaceRepository.findById(spaceId).get();
    }

}
