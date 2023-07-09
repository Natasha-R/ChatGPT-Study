package thkoeln.st.st2praktikum.exercise.space.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.WrongFormatException;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
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
        Space tempspace = new Space(height, width);
        spaceRepository.save( tempspace );
        return tempspace.getUuid();
    }
    /**
     * This method adds a wall to a given space.
     * @param spaceId the ID of the space the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID spaceId, Wall wall) {

        Space space = spaceRepository.findById(spaceId).get();

        if(!(space.checkCoordinateWithinSpace(wall.getStart())) || !(space.checkCoordinateWithinSpace(wall.getEnd()))){
            throw new WrongFormatException("Coordinates must be within the Space");
        }

        space.getWalls().add(wall);
        spaceRepository.save(space);
    }

}
