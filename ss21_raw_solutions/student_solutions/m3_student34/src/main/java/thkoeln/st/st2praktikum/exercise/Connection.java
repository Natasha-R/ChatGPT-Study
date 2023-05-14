package thkoeln.st.st2praktikum.exercise;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Connection extends AbstractEntity {
    @Embedded
    private MapPosition source;
    @Embedded
    private MapPosition target;
}
