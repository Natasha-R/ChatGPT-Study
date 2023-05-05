package thkoeln.st.st2praktikum.exercise.space.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.HE_DOES_SPEAK_ENGLISH.SpaceNotFoundExeption;
import thkoeln.st.st2praktikum.exercise.HE_DOES_SPEAK_ENGLISH.World;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.space.domain.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.space.domain.Wall;



import java.util.UUID;


@Service
public class SpaceService {

    @Autowired
    private SpaceRepository spaceRepository;
    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        Space space = new Space(height,width);
        World.getInstance().addSpace(space);
        spaceRepository.save(space);
        return space.getId();
    }

    /**
     * This method adds a wall to a given space.
     * @param spaceId the ID of the space the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID spaceId, Wall wall) {
        Space space = spaceRepository.findById(spaceId).orElseThrow(()->new SpaceNotFoundExeption(spaceId));
        World.getInstance().addWalltoSpace(space,wall);
        spaceRepository.save(space);
    }
}
