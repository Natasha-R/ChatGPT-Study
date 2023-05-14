package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class TransportSystem  extends AbstractEntity{
    private String system;

    @OneToMany
    private List<Connection> connections = new ArrayList<>();

    public TransportSystem(String system){
        this.system = system;
    }


}
