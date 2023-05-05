package thkoeln.st.st2praktikum.map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.linearAlgebra.ConcreteBoundedStraight;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;

import java.util.Arrays;

public class StringRectangleObstacleFactoryUnitTest {

    private StringRectangleObstacleFactory testSubject;

    @BeforeEach
    public void setup() {
        this.testSubject = new StringRectangleObstacleFactory();
    }

    @Test
    public void testBuildShouldCreateHorizontalObstacle() {
        //given
        var input = "(0,0)-(1,0)";
        //when
        var result = (RectangleObstacle) this.testSubject.createRectangleObstacle(input);
        //then
        var expectedSites = Arrays.asList(
                new ConcreteBoundedStraight(Vector.of(-0.25, -0.25), Vector.of(1.25, -0.25)),
                new ConcreteBoundedStraight(Vector.of(-0.25, 0.25), Vector.of(1.25, 0.25)),
                new ConcreteBoundedStraight(Vector.of(-0.25, -0.25), Vector.of(-0.25, 0.25)),
                new ConcreteBoundedStraight(Vector.of(1.25, -0.25), Vector.of(1.25, 0.25))
        );
        TestUtil.assertSites(Arrays.asList(result.getSites()),
                expectedSites);
    }

    @Test
    public void testBuildShouldCreateVerticalObstacle() {
        //given
        var input = "(0,0)-(0,1)";
        //when
        var result = (RectangleObstacle) this.testSubject.createRectangleObstacle(input);
        //then
        var expectedSites = Arrays.asList(
                new ConcreteBoundedStraight(Vector.of(-0.25, -0.25),
                        Vector.of(0.25, -0.25)),
                new ConcreteBoundedStraight(Vector.of(-0.25, 1.25),
                        Vector.of(0.25, 1.25)),
                new ConcreteBoundedStraight(Vector.of(-0.25, -0.25),
                        Vector.of(-0.25, 1.25)),
                new ConcreteBoundedStraight(Vector.of(0.25, -0.25),
                        Vector.of(0.25, 1.25))
        );
        TestUtil.assertSites(Arrays.asList(result.getSites()),
                expectedSites);
    }

    @Test
    public void testBuildShouldCreateHorizontalObstacleWhenPointsAreSwitched() {
        //given
        var input = "(1,0)-(0,0)";
        //when
        var result = (RectangleObstacle) this.testSubject.createRectangleObstacle(input);
        //then
        var expectedSites = Arrays.asList(
                new ConcreteBoundedStraight(Vector.of(-0.25, -0.25), Vector.of(1.25, -0.25)),
                new ConcreteBoundedStraight(Vector.of(-0.25, 0.25), Vector.of(1.25, 0.25)),
                new ConcreteBoundedStraight(Vector.of(-0.25, -0.25), Vector.of(-0.25, 0.25)),
                new ConcreteBoundedStraight(Vector.of(1.25, -0.25), Vector.of(1.25, 0.25))
        );
        TestUtil.assertSites(Arrays.asList(result.getSites()),
                expectedSites);
    }

    @Test
    public void testBuildShouldCreateVerticalObstacleWhenPointsAreSwitched() {
        //given
        var input = "(0,1)-(0,0)";
        //when
        var result = (RectangleObstacle) this.testSubject.createRectangleObstacle(input);
        //then
        var expectedSites = Arrays.asList(
                new ConcreteBoundedStraight(Vector.of(-0.25, -0.25),
                        Vector.of(0.25, -0.25)),
                new ConcreteBoundedStraight(Vector.of(-0.25, 1.25),
                        Vector.of(0.25, 1.25)),
                new ConcreteBoundedStraight(Vector.of(-0.25, -0.25),
                        Vector.of(-0.25, 1.25)),
                new ConcreteBoundedStraight(Vector.of(0.25, -0.25),
                        Vector.of(0.25, 1.25))
        );
        TestUtil.assertSites(Arrays.asList(result.getSites()),
                expectedSites);
    }

    @Test
    public void testCreateObstacleShouldThrowExceptionWhenFirstCoordinatesMiss() {
        //given
        var input = "-(1,0)";
        //when
        //then
        Assertions.assertThatThrownBy(() -> testSubject.createRectangleObstacle(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testCreateObstacleShouldThrowExceptionWhenSecondCoordinatesMiss() {
        //given
        var input = "(1,0)-";
        //when
        //then
        Assertions.assertThatThrownBy(() -> testSubject.createRectangleObstacle(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testCreateObstacleShouldThrowExceptionWhenMinusIsMissing() {
        //given
        var input = "(1,0)(0,1)";
        //when
        //then
        Assertions.assertThatThrownBy(() -> testSubject.createRectangleObstacle(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
