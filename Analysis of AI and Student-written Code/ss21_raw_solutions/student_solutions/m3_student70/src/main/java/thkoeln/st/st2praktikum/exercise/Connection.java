package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.Assist.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.Point;

import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
public class Connection extends AbstractEntity {
    private Point src;
    private Point dest;
}
