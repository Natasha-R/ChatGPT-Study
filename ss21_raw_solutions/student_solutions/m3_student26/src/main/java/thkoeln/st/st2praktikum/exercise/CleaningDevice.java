package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;
@NoArgsConstructor
@Entity
public class CleaningDevice extends AbstractEntity {

    @Getter
    private String name;
    @Getter @Setter
    private UUID spaceId;
    @Getter @Setter
    private Coordinate coordinate;

    public CleaningDevice(String name){
        this.name = name;
    }


}
