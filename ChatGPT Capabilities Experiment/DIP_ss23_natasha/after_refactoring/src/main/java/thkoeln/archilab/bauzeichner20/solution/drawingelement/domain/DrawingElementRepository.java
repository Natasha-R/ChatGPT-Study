package thkoeln.archilab.bauzeichner20.solution.drawingelement.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DrawingElementRepository extends CrudRepository<DrawingElement, UUID> {
}
