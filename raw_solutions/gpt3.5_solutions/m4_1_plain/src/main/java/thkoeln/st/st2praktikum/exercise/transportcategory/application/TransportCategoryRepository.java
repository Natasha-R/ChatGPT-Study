package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportCategoryRepository extends JpaRepository<TransportCategory, Long> {
    TransportCategory findByName(String name);
}

