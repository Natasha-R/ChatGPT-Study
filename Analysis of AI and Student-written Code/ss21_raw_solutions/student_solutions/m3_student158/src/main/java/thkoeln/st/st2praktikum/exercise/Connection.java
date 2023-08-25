package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.repositories.SpaceRepository;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
public class Connection extends AbstractEntity {

    private UUID transportSystem;

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
