package thkoeln.st.st2praktikum.exercise.space.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleanerRepository;
import thkoeln.st.st2praktikum.exercise.space.domain.Obstacle;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.space.domain.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;


import java.util.HashMap;
import java.util.UUID;


@Service
public class SpaceService {

    @Autowired
    CleanerRepository cleanerRepository;

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    TransportCategoryRepository transportCategoryRepository;

    //HashMap<UUID, Space> spaceHashMap = new HashMap<UUID, Space>();

    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        Space space = new Space(height, width);
        //spaceHashMap.put(space.getUuid(), space);
        spaceRepository.save(space);
        return space.getUuid();
    }

    /**
     * This method adds a obstacle to a given space.
     * @param spaceId the ID of the space the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID spaceId, Obstacle obstacle) {
        Space space = spaceRepository.findById(spaceId).orElseThrow(() -> new NullPointerException("There is no object with this id"));
        space.addObstacle(obstacle);

        //spaceRepository.deleteById(space.getUuid());
        spaceRepository.save(space);
    }
}
