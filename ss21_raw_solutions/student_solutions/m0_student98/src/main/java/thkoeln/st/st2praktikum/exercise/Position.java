package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class Position {
    @Getter
    @Setter
    private int xPos;

    @Getter
    @Setter
    private int yPos;

    public String toString() {
        return "("+xPos+","+yPos+")";
    }
}
