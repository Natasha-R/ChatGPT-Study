package thkoeln.archilab.bauzeichner20.solution;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CanvasRepo extends CrudRepository<Canvas, UUID> {
}
