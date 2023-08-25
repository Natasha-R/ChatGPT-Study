package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransportCategoryRepo extends JpaRepository<TransportCategory, UUID> {
    TransportCategory findTransportCategoriesById(UUID id);
    TransportCategory findTransportCategoriesByCategory(String category);
}
