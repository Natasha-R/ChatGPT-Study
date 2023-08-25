package thkoeln.st.st2praktikum.exercise.space.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.space.domain.Barrier;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.space.domain.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.space.domain.transition.BlockedTransition;
import thkoeln.st.st2praktikum.exercise.space.domain.transition.BlockedTransitionRepository;
import thkoeln.st.st2praktikum.exercise.space.domain.transition.ConnectedTransition;
import thkoeln.st.st2praktikum.exercise.space.domain.transition.ConnectedTransitionRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;


@Service("spaceService")
public class SpaceService {

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private BlockedTransitionRepository blockedTransitionRepository;

    @Autowired
    private ConnectedTransitionRepository connectedTransitionRepository;

    private static Supplier<SpaceService> instance = () -> null;

    public static Supplier<SpaceService> getServiceSupplier() {
        return instance;
    }

    @PostConstruct
    public void init() {
        instance = () -> this;
    }

    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        Space space = new Space(width, height);
        return spaceRepository.save(space).getId();
    }

    /**
     * This method adds a barrier to a given space.
     * @param spaceId the ID of the space the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID spaceId, Barrier barrier) {
        Space space = getSpace(spaceId);
        List<BlockedTransition> blockedTransitions = barrier.getMyBarrier().getBlockedTransitions();
        Iterable<BlockedTransition> saved = blockedTransitionRepository.saveAll(blockedTransitions);

        blockedTransitions.clear();
        saved.forEach(blockedTransitions::add);
        space.addBlockedTransitions(blockedTransitions);

        spaceRepository.save(space);
    }

    public Space getSpace(UUID spaceId) {
        return spaceRepository.findById(spaceId).get();
    }

    public List<ConnectedTransition> getConnectedTransitions(Space space) {
        ArrayList<ConnectedTransition> connectedTransitions = new ArrayList<>();
        connectedTransitionRepository.findAll().forEach(connectedTransition -> connectedTransitions.add(connectedTransition));

        return connectedTransitions.stream()
                .filter(connectedTransition -> Objects.equals(space, connectedTransition.getFromSpace()) ||
                        Objects.equals(space, connectedTransition.getToSpace()))
                .collect(Collectors.toList());
    }
}
