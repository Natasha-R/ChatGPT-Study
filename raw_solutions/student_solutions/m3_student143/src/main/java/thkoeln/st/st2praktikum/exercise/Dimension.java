package thkoeln.st.st2praktikum.exercise;

import lombok.*;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;


@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Access(AccessType.FIELD)
public class Dimension {
    int width;
    int height;
}