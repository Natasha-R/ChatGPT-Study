package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Assist.AbstractEntity;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;


@Entity @AllArgsConstructor @NoArgsConstructor
public class CleaningDevice extends AbstractEntity {
    @Getter private String name;
    @Getter @Setter private Point point;
    @Getter @Setter private UUID spaceId;
}
