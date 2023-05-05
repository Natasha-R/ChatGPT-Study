package thkoeln.st.st2praktikum.entities;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TransportCategoryRepository extends CrudRepository<TransportCategory, UUID> {
    TransportCategory findByUuid(UUID uuid);
}
