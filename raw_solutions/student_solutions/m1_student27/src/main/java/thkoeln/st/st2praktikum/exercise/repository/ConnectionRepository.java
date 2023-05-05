package thkoeln.st.st2praktikum.exercise.repository;

import thkoeln.st.st2praktikum.exercise.entyties.Connection;

import java.util.UUID;

public interface ConnectionRepository {
    public Connection addConnection(UUID sourceSpaceId, String sourceCoordinate,
                                    UUID destinationSpaceId, String destinationCoordinate) ;

    }
