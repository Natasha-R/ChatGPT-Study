package thkoeln.st.st2praktikum.exercise;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class Wall {

    @Setter
    @Getter
    Point start,end;

}
