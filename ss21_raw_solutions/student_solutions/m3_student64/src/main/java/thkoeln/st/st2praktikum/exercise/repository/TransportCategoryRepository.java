package thkoeln.st.st2praktikum.exercise.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.TransportCategory;

import java.util.UUID;

@Service
public interface TransportCategoryRepository extends CrudRepository<TransportCategory, UUID> {
}
