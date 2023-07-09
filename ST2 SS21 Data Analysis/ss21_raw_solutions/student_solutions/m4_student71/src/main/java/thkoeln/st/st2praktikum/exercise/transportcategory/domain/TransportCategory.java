package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Connection;
import thkoeln.st.st2praktikum.exercise.domainprimitives.UUIDBase;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
public class TransportCategory extends UUIDBase {

    @Getter
    @Setter
    private String category;

    @Getter @Setter
    @OneToMany
    private List<Connection> connections = new ArrayList<>();

    public TransportCategory(String category){
        super();
        this.category = category;
    }


}
