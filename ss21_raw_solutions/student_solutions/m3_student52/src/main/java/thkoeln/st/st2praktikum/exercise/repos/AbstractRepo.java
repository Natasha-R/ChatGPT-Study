package thkoeln.st.st2praktikum.exercise.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import thkoeln.st.st2praktikum.exercise.InvalidCommandException;

import java.util.UUID;

@NoRepositoryBean
public interface AbstractRepo<T> extends CrudRepository<T, UUID> {
    default T get(UUID uuid){
        var f = findById(uuid);
        if (f.isEmpty()) throw new InvalidCommandException("Could not find Field of id" + uuid);
        return f.get();
    }

    default Iterable<T> values(){ return this.findAll(); }

    default void put(T element){ save(element); }
}
