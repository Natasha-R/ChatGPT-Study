package thkoeln.st.st2praktikum.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface RoomRepository extends CrudRepository<Room, UUID> {
    Room findByUuid(UUID uuid);
}
