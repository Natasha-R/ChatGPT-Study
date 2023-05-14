package thkoeln.st.st2praktikum.exercise.space.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.space.domain.ObstacleEntity;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.repository.ObstacleRepository;
import thkoeln.st.st2praktikum.exercise.repository.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.space.domain.Obstacle;


import javax.persistence.EntityNotFoundException;
import java.util.UUID;


@Service
public class SpaceService {

    @Autowired
    protected SpaceRepository spaceRepo;

    @Autowired
    protected ObstacleRepository obstacleRepo;
    
    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        UUID spaceId = UUID.randomUUID();

        Space space = new Space( spaceId , height , width );

        spaceRepo.save( space );

        return spaceId;
    }

    /**
     * This method adds a obstacle to a given space.
     * @param spaceId the ID of the space the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID spaceId, Obstacle obstacle) {
        try {
            UUID obstacleId = UUID.randomUUID();

            Space space = spaceRepo.findById(spaceId)
                    .orElseThrow(() -> new EntityNotFoundException());

            ObstacleEntity obstacleEntity = new ObstacleEntity( obstacleId , obstacle , space );

            space.addObstacle( obstacleEntity );

            obstacleRepo.save( obstacleEntity );
            spaceRepo.save( space );

        }
        catch (Error e) {
            System.out.println(e);
        }

        catch (NumberFormatException e) {
            System.out.println("Points must be a number");
        }
    }
}
