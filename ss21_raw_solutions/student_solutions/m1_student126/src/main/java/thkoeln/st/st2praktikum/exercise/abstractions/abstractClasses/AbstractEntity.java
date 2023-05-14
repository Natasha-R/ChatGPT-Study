package thkoeln.st.st2praktikum.exercise.abstractions.abstractClasses;

import lombok.Getter;

import java.util.UUID;

@Getter
public class AbstractEntity {

    protected final UUID id = UUID.randomUUID();

}
