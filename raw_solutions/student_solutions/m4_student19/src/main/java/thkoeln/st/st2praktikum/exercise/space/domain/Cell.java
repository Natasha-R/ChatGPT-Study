package thkoeln.st.st2praktikum.exercise.space.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class Cell {
    private Boolean borderNorth;
    private Boolean borderEast;
    private Boolean borderSouth;
    private Boolean borderWest;

    public Cell() {
        this.borderNorth = false;
        this.borderEast = false;
        this.borderSouth = false;
        this.borderWest = false;
    }
}
