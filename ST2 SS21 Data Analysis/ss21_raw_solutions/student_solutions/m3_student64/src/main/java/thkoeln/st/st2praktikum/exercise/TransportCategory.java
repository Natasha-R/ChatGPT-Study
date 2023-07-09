package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.Core.AbstractEntity;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TransportCategory extends AbstractEntity {

    private String name;

    public TransportCategory(String name) {
        this.name = name;
    }
}
