package thkoeln.archilab.bauzeichner20.solution.canvas.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CanvasRepository extends CrudRepository<Canvas, UUID> {
}
