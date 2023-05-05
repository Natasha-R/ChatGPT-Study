package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DroidRepository extends CrudRepository<MaintenanceDroid, UUID> {
}
