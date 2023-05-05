package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.util.UUID;
import java.util.*;
@EqualsAndHashCode
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TransportTechnology {
    private UUID transportTechnologyId;
    private String technology;

    public TransportTechnology(String willBeTechnology){
        transportTechnologyId = UUID.randomUUID();
        technology = willBeTechnology;
    }


}
