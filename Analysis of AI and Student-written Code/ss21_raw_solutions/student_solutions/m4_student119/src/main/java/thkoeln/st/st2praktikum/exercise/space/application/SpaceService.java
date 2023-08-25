package thkoeln.st.st2praktikum.exercise.space.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.Space;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.TransportTechnologyRepository;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.services.TransportService;
import thkoeln.st.st2praktikum.exercise.space.domain.Barrier;


import java.util.Optional;
import java.util.UUID;


@Service
public class SpaceService {
    private final SpaceRepository spaceRepository;

    @Autowired
    public SpaceService(SpaceRepository spaceRepository) {
        this.spaceRepository = spaceRepository;
    }

    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        final Space spaceEntity = new Space();
        spaceEntity.setWidth(width);
        spaceEntity.setHeight(height);
        spaceEntity.setId(UUID.randomUUID());

        spaceRepository.save(spaceEntity);
        return spaceEntity.getId();
    }

    /**
     * This method adds a barrier to a given space.
     * @param spaceId the ID of the space the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID spaceId, Barrier barrier) {
        final Space space = getSpace(spaceId);
        if (barrier.getStart().getX() > space.getWidth() ||
                barrier.getEnd().getX() > space.getWidth() ||
                barrier.getStart().getY() > space.getHeight() ||
                barrier.getEnd().getY() > space.getHeight()
        ) {
            throw new IllegalArgumentException("barrier is out of bound!");
        }

        /*final Barrier barrierEntity = barrierRepository.save(new Barrier(
                barrier.getStart(),
                barrier.getEnd()
        ));*/

        space.getBarriers().add(new Barrier(
                barrier.getStart(),
                barrier.getEnd()
        ));
        spaceRepository.save(space);
    }

    private Space getSpace(UUID spaceId) {
        final Optional<Space> spaceEntity = spaceRepository.getById(spaceId);
        return spaceEntity.orElseThrow(null);
    }
}
