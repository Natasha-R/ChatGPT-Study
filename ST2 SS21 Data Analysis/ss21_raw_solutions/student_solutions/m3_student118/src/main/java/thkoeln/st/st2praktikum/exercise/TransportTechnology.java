package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class TransportTechnology extends AbstractEntity {

    private String technology;

    @OneToMany
    private List<Connection> connections = new ArrayList<>();

    public TransportTechnology(String technology){
        this.technology = technology;
    }

}
