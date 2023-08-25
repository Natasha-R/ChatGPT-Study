package thkoeln.st.st2praktikum.exercise.space.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.space.domain.Obstacle;
import thkoeln.st.st2praktikum.exercise.space.domain.RectangularSpace;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.space.domain.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
public class SpaceService {

    private SpaceRepository spaceRepo;

    public Space findById(UUID id ) {
        Space space = spaceRepo.findById( id ).orElseThrow( () ->
                new RuntimeException( "Space " + id + " existiert nicht") );
        return space;
    }

    @Autowired
    public SpaceService (SpaceRepository spaceRepo) {
        this.spaceRepo = spaceRepo;
    }

    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        Space space = new RectangularSpace(width, height);
        UUID spaceId = space.getId();
        spaceRepo.save(space);
        return spaceId;
    }


    /**
     * This method adds a obstacle to a given space.
     * @param spaceId the ID of the space the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID spaceId, Obstacle obstacle) {
        findById(spaceId).addBlocker(obstacle);
    }
}
