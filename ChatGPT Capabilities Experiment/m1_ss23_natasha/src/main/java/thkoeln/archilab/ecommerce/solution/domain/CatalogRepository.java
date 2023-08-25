package thkoeln.archilab.ecommerce.solution.domain;

import thkoeln.archilab.ecommerce.solution.thing.domain.Catalog;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CatalogRepository extends CrudRepository<Catalog, UUID> {}