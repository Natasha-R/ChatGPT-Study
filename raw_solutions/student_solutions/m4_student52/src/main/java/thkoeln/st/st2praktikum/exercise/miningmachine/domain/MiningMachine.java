package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.core.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.*;

@Entity
public class MiningMachine extends AbstractEntity {
    @Getter @Setter private String name;
    @Getter @Setter private Point point;
    @Getter @Setter private UUID fieldId;
    public MiningMachine(){}
    public MiningMachine(String name, Point point, UUID fieldId) {
        this.name = name;
        this.point = point;
        this.fieldId = fieldId;
    }

    @Getter @ElementCollection private List<Order> orders = new ArrayList<>();
}
