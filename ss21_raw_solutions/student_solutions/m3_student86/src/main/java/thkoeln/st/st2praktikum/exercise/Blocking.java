package thkoeln.st.st2praktikum.exercise;

/*
public interface Blocking {
    void buildUp (Space destinationSpace);
    void tearDown (Space sourceSpace);
}*/

import lombok.Getter;

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

    abstract void buildUp (Space destinationSpace);
    abstract void tearDown (Space sourceSpace);
}