package thkoeln.st.st2praktikum.exercise.space.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface SpaceRepository extends CrudRepository<Space, UUID> {
}
