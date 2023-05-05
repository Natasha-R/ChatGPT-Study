package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.HE_DOES_SPEAK_ENGLISH.World;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connector;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnologyRepository;


import javax.swing.text.TabExpander;
import java.security.InvalidParameterException;
import java.util.UUID;


@Service
public class TransportTechnologyService {

    @Autowired
    private TransportTechnologyRepository transportTechnologyRepository;

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        World.getInstance().addTransportTechnology(transportTechnology);
        transportTechnologyRepository.save(transportTechnology);
        return transportTechnology.getTechnologyId();
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
        Connector connector = new Connector(sourcePoint,destinationSpaceId,destinationPoint);
        TransportTechnology transportTechnology = transportTechnologyRepository.findById(transportTechnologyId).orElseThrow(InvalidParameterException::new);
        transportTechnology.addConnector(connector);
        World.getInstance().addConnectors(transportTechnology,sourceSpaceId,connector);
        transportTechnologyRepository.save(transportTechnology);
        return connector.getId();
    }
}
