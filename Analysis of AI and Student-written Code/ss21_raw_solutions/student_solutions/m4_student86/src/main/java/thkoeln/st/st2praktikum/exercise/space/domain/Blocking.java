package thkoeln.st.st2praktikum.exercise.space.domain;

/*
public interface Blocking {
    void buildUp (Space destinationSpace);
    void tearDown (Space sourceSpace);
}*/

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
abstract public class Blocking {
    @Id
    @Getter
    private UUID id = UUID.randomUUID();

    abstract public void buildUp (Space destinationSpace);
    abstract public void tearDown (Space sourceSpace);
}