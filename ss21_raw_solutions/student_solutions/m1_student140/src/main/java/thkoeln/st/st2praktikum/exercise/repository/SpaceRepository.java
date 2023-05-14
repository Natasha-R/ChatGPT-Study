package thkoeln.st.st2praktikum.exercise.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import thkoeln.st.st2praktikum.exercise.entity.Space;

import java.util.UUID;

public interface SpaceRepository extends CrudRepository<Space, UUID> {
    @Query("select s from Space s left join fetch s.obstacles where s.spaceId = :uuid")
    Space initializeObstacles(@Param("uuid") UUID uuid);

    @Query("select s from Space s left join fetch s.cleaningDevices where s.spaceId = :uuid")
    Space initializeCleaningDevices(@Param("uuid") UUID uuid);
}
