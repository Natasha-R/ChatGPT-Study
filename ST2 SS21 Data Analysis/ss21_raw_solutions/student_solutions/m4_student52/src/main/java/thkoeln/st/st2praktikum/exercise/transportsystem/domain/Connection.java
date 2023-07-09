package thkoeln.st.st2praktikum.exercise.transportsystem.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.core.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@AllArgsConstructor @NoArgsConstructor @Entity
public class Connection extends AbstractEntity {
    @Getter @Embedded private Point src;
    @Getter @Embedded private Point dest;
}
