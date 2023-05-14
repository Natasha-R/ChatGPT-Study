package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

@EnableJpaRepositories
public interface TransportTechnologyRepository extends CrudRepository<TransportTechnology, UUID>
{

}
