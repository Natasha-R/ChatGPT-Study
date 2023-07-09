package thkoeln.archilab.ecommerce.solution.domain;

import thkoeln.archilab.ecommerce.solution.thing.domain.Thing;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ThingRepository extends CrudRepository<Thing, UUID> {}
