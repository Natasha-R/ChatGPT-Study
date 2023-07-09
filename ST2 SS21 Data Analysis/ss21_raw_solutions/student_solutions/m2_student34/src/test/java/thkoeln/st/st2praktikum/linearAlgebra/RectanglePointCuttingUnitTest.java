package thkoeln.st.st2praktikum.linearAlgebra;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RectanglePointCuttingUnitTest {

    private Rectangle rectangle;

    @BeforeEach
    public void setup() {
        this.rectangle = new ConcreteRectangle(
                Vector.of(0.75, 2.25),
                Vector.of(0.75, 0.75),
                Vector.of(3.25, 2.25),
                Vector.of(3.25, 0.75)
        );
    }

    @Test
    public void testCalculateCutShouldReturnAnyCutOfBorder() {
        //given
        var point = new ConcretePoint(Vector.of(0.75, 0.75));
        var testSubject = new RectanglePointCutting(this.rectangle, point);
        //when
        var result = testSubject.calculateCut();
        //then
        Assertions.assertThat(result).hasValue(point.getCoordinates());
    }

    @Test
    public void testCalculateCutShouldReturnEmptyWhenPointIsAbove() {
        //given
        var point = new ConcretePoint(Vector.of(2, 2.5));
        var testSubject = new RectanglePointCutting(this.rectangle, point);
        //when
        var result = testSubject.calculateCut();
        //then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void testCalculateCutShouldReturnEmptyWhenPointIsToTheRight() {
        //given
        var point = new ConcretePoint(Vector.of(3.75, 1));
        var testSubject = new RectanglePointCutting(this.rectangle, point);
        //when
        var result = testSubject.calculateCut();
        //then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void testCalculateCutShouldReturnEmptyWhenPointIsBelow() {
        //given
        var point = new ConcretePoint(Vector.of(2, -1));
        var testSubject = new RectanglePointCutting(this.rectangle, point);
        //when
        var result = testSubject.calculateCut();
        //then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void testCalculateCutShouldReturnEmptyWhenPointIsToTheLeft() {
        //given
        var point = new ConcretePoint(Vector.of(0.5, 1));
        var testSubject = new RectanglePointCutting(this.rectangle, point);
        //when
        var result = testSubject.calculateCut();
        //then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void testCalculateCutShouldReturnPointWhenInsideOfRectangle() {
        //given
        var point = new ConcretePoint(Vector.of(2, 2));
        var testSubject = new RectanglePointCutting(this.rectangle, point);
        //when
        var result = testSubject.calculateCut();
        //then
        Assertions.assertThat(result).hasValue(point.getCoordinates());
    }
}
