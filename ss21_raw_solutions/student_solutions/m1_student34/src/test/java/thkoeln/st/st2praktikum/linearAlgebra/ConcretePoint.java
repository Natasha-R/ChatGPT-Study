package thkoeln.st.st2praktikum.linearAlgebra;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ConcretePoint extends Point{
    private final Vector coordinates;
}
