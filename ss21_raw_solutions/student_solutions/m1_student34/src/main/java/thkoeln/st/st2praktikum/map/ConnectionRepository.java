package thkoeln.st.st2praktikum.map;

import org.springframework.stereotype.Repository;
import thkoeln.st.st2praktikum.lib.ConcreteCrudRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Repository
public class ConnectionRepository extends ConcreteCrudRepository<Connection> {
    public Iterable<Connection> findAllBySource(Position source) {
        return StreamSupport.stream(this.findAll().spliterator(), false)
                .filter(it -> it.getSource().equals(source))
                .collect(Collectors.toList());
    }
}
