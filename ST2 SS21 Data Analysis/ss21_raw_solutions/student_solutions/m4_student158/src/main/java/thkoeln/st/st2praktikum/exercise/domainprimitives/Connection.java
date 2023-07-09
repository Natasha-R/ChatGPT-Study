package thkoeln.st.st2praktikum.exercise.domainprimitives;


import net.minidev.json.annotate.JsonIgnore;
import thkoeln.st.st2praktikum.exercise.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.space.repository.SpaceRepository;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
public class Connection extends AbstractEntity {

    private UUID transportSystem;

//    @ManyToOne
//    @JsonIgnore
//    private Space space;

    private UUID sourceSpaceId;

    private UUID destinationSpaceId;

    private Coordinate sourceSpaceCoordinate;

    private Coordinate destinationSpaceCoordinate;

    public Connection() {
    }

    public static Connection fromShit(UUID transportSystemId, UUID sourceSpace, UUID destinationSpace, Coordinate sourceCoordinate, Coordinate destinationCoordinate, SpaceRepository spaceRepository){
        return new Connection(transportSystemId, sourceSpace, destinationSpace, sourceCoordinate, destinationCoordinate, spaceRepository);
    }

    private Connection(UUID transportSystemId, UUID sourceSpace, UUID destinationSpace, Coordinate sourceSpaceCoordinate, Coordinate destinationSpaceCoordinate, SpaceRepository spaceRepository) {
        this.transportSystem = transportSystemId;
        this.sourceSpaceId = sourceSpace;
        this.destinationSpaceId = destinationSpace;
        this.sourceSpaceCoordinate = sourceSpaceCoordinate;
        this.destinationSpaceCoordinate = destinationSpaceCoordinate;
        checkIfConnectionsAreVaild(spaceRepository);
    }

    private void checkIfConnectionsAreVaild(SpaceRepository spaceRepository){
        Space sourceSpace = spaceRepository.findById(sourceSpaceId).isPresent() ? spaceRepository.findById(sourceSpaceId).get() : null;
        Space destinationSpace = spaceRepository.findById(destinationSpaceId).isPresent() ? spaceRepository.findById(destinationSpaceId).get() : null;
        if((sourceSpace != null && destinationSpace != null) && (sourceSpace.getWidth()<sourceSpaceCoordinate.getX() || sourceSpace.getHeight()<sourceSpaceCoordinate.getY() ||
                destinationSpace.getWidth()<destinationSpaceCoordinate.getX() || destinationSpace.getHeight()<destinationSpaceCoordinate.getY())){
            throw new IllegalArgumentException("Connection coordinates are out bounds");
        }
    }

    public UUID getDestinationSpaceId() {
        return destinationSpaceId;
    }

    public Coordinate getSourceSpaceCoordinate() {
        return sourceSpaceCoordinate;
    }

    public Coordinate getDestinationSpaceCoordinate() {
        return destinationSpaceCoordinate;
    }

}
