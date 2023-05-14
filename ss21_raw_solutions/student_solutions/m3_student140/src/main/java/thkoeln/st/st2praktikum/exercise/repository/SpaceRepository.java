package thkoeln.st.st2praktikum.exercise.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import thkoeln.st.st2praktikum.exercise.Obstacle;
import thkoeln.st.st2praktikum.exercise.entity.ObstacleEntity;
import thkoeln.st.st2praktikum.exercise.entity.Space;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface SpaceRepository extends CrudRepository<Space, UUID> {
    @Query("select s from Space s left join fetch s.obstacles where s.spaceId = :uuid")
    Space initializeObstacles(@Param("uuid") UUID uuid);

    @Query("select s from Space s left join fetch s.cleaningDevices where s.spaceId = :uuid")
    Space initializeCleaningDevices(@Param("uuid") UUID uuid);

    /*
    @Query(value = "select s.obstacles from Space s left join fetch s.obstacles where s.spaceId = :spaceId1", nativeQuery = true)
    Set<ObstacleEntity> findAllObstacles(@Param("spaceId1") UUID spaceId1);
     */




}
