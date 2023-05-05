package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.*;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.services.TransportService;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;


import java.util.Optional;
import java.util.UUID;


@Service
public class TransportTechnologyService {
    private final TransportTechnologyRepository transportTechnologyRepository;
    private final SpaceRepository spaceRepository;

    @Autowired
    public TransportTechnologyService(TransportTechnologyRepository transportTechnologyRepository,
                                      SpaceRepository spaceRepository) {
        this.transportTechnologyRepository = transportTechnologyRepository;
        this.spaceRepository = spaceRepository;
    }

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        final Optional<TransportTechnology> technologyEntity = transportTechnologyRepository.findByTechnology(technology);

        if (technologyEntity.isPresent())
            return technologyEntity.get().getId();

        final TransportTechnology t = transportTechnologyRepository.save(new TransportTechnology(UUID.randomUUID(), technology));
        return t.getId();
    }

    /**
     * This method adds a traversable connection between two spaces based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceSpaceId the ID of the space where the entry point of the connection is located
     * @param sourcePoint the point of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationPoint the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId,
                              UUID sourceSpaceId,
                              Point sourcePoint,
                              UUID destinationSpaceId,
                              Point destinationPoint) {
        final Optional<TransportTechnology> technologyEntityOptional = transportTechnologyRepository.findById(transportTechnologyId);

        final Space source = getSpace(sourceSpaceId);
        final Space destination = getSpace(destinationSpaceId);

        if (sourcePoint.getX() > source.getWidth() || sourcePoint.getY() > source.getHeight())
            throw new IllegalArgumentException("sourcePoint is out of bounds!");

        if (destinationPoint.getX() > destination.getWidth() || destinationPoint.getY() > destination.getHeight())
            throw new IllegalArgumentException("destinationPoint is out of bounds!");

        // final SpaceConnection connection = new SpaceConnection(UUID.randomUUID(), source, sourcePoint, destination, destinationPoint);
        final SpaceConnection spaceConnectionEntity = new SpaceConnection(
                UUID.randomUUID(),
                technologyEntityOptional.get(),
                source,
                destination,
                sourcePoint,
                destinationPoint
        );

        //spaceConnectionRepository.save(spaceConnectionEntity);

        source.getSpaceConnections().add(spaceConnectionEntity);
        destination.getSpaceConnections().add(spaceConnectionEntity);

        spaceRepository.save(source);
        spaceRepository.save(destination);

        return spaceConnectionEntity.getId();
    }

    private Space getSpace(UUID spaceId) {
        final Optional<Space> spaceEntity = spaceRepository.getById(spaceId);
        return spaceEntity.orElseThrow(null);
    }
}
