package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Barrier {
    @Id
    UUID BarrierId;

    int leveX;
    int leveY;
    int barrierstart;
    int barrierend;

}
