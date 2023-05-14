package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.Area;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;



import java.util.UUID;


@Service
public class TransportTechnologyService {
    @Autowired
    private Area area;
    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        return area.createTransportTechnology(technology);
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
    public UUID addConnection(UUID transportTechnologyId,
                              UUID sourceSpaceId,
                              Coordinate sourceCoordinate,
                              UUID destinationSpaceId,
                              Coordinate destinationCoordinate) {
        return area.createConnection(sourceSpaceId,
                                     sourceCoordinate,
                                     destinationSpaceId,
                                     destinationCoordinate);
    }
}
