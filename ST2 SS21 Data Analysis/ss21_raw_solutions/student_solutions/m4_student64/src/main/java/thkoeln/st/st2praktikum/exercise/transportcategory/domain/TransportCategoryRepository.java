package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface TransportCategoryRepository extends CrudRepository<TransportCategory, UUID> {
}
