package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.core.AbstractEntity;

import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor @Entity
public class Connection extends AbstractEntity {
    @Getter @Embedded private Point src;
    @Getter @Embedded private Point dest;
}
