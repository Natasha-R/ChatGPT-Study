package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.core.AbstractEntity;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TransportTechnology extends AbstractEntity {

    String name;
}
