package thkoeln.st.st2praktikum.linearAlgebra;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class RectangleBoundedStraightCuttingUnitTest {

    @Test
    public void testCutShouldCutAnClosestPointFromTop() {
        //given
        var straight = new ConcreteBoundedStraight(Vector.of(2, 3),
                Vector.of(2, 0));
        var rectangle = new ConcreteRectangle(
                Vector.of(0.75, 2.25),
                Vector.of(0.75, 0.75),
                Vector.of(3.25, 2.25),
                Vector.of(3.25, 0.75)
        );
        var testSubject = new RectangleBoundedStraightCutting(rectangle, straight);
        //when
        var result = testSubject.calculateCut();
        //then
        Assertions.assertThat(result).hasValue(Vector.of(2, 2.25));
    }
}
