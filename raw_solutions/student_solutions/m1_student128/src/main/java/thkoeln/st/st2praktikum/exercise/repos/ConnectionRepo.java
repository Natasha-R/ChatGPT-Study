package thkoeln.st.st2praktikum.exercise.repos;

import thkoeln.st.st2praktikum.exercise.Entity.Connection;

import java.util.UUID;

public interface ConnectionRepo {
    public Connection addConnection(UUID sourceFieldId, String sourceCoordinate,
                                    UUID destinationFieldId, String destinationCoordinate) ;

}
