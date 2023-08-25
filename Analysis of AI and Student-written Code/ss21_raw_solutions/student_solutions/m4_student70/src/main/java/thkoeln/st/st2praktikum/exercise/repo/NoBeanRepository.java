package thkoeln.st.st2praktikum.exercise.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface NoBeanRepository<T> extends CrudRepository<T, UUID> {

    default T get(UUID uuid){
        var f = findById(uuid);
        if (f.isEmpty()) throw new UnsupportedOperationException();
        return f.get();
    }

    default Iterable<T> values(){ return this.findAll(); }

    default void put(T element){ save(element); }



}
