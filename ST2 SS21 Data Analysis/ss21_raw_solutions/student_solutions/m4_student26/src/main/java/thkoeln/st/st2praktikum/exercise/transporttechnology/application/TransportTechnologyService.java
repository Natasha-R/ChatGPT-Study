package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.WrongFormatException;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.space.application.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.space.domain.Connection;


import java.util.UUID;


@Service
public class TransportTechnologyService {

    private final SpaceRepository spaceRepository;

    public TransportTechnologyService(SpaceRepository spaceRepository) {
        this.spaceRepository = spaceRepository;
    }

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        return UUID.randomUUID();
    }

    /**
     * This method adds a traversable connection between two spaces based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceSpaceId the ID of the space where the entry point of the connection is located
     * @param sourceCoordinate the coordinate of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId, UUID sourceSpaceId, Coordinate sourceCoordinate, UUID destinationSpaceId, Coordinate destinationCoordinate) {

        Space sourceSpace = spaceRepository.findById(sourceSpaceId).get();
        Space destinationSpace = spaceRepository.findById(destinationSpaceId).get();

        if(!(sourceSpace.checkCoordinateWithinSpace(sourceCoordinate)) || !(destinationSpace.checkCoordinateWithinSpace(destinationCoordinate))){
            throw new WrongFormatException("Coordinates must be within the Space");
        }

        Connection temp = new Connection(sourceSpaceId,sourceCoordinate,destinationSpaceId,destinationCoordinate);
        sourceSpace.getConnections().add(temp);
        spaceRepository.save(sourceSpace);
        return temp.getUuid();


    }
}
