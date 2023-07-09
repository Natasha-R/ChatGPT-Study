package thkoeln.st.st2praktikum.parser;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

public class ParserUnitTest {
    private Parser testSubject;

    @BeforeEach
    public void setup() {
        this.testSubject = Parser.getInstance();
    }

    @Test
    @DisplayName("[no,3] should be Pair<NORTH, 3>")
    public void testNo3ShouldBeNo3() {
        //given
        String input = "[no,3]";
        //when
        var result = testSubject.parse(input);
        //then
        Assertions.assertThat(result).isEqualTo(Pair.of(Direction.NORTH, 3));
    }

    @Test
    @DisplayName("[ea,4] should be Pair<EAST, 4>")
    public void testEa4ShouldBeEA4() {
        //given
        String input = "[ea,4]";
        //when
        var result = testSubject.parse(input);
        //then
        Assertions.assertThat(result).isEqualTo(Pair.of(Direction.EAST, 4));
    }

    @Test
    @DisplayName("[so,14] should be Pair<SOUTH, 14>")
    public void testSo14ShouldBeSo14() {
        //given
        String input = "[so,14]";
        //when
        var result = testSubject.parse(input);
        //then
        Assertions.assertThat(result).isEqualTo(Pair.of(Direction.SOUTH, 14));
    }

    @Test
    @DisplayName("[we,2] should be Pair<WEST, 2>")
    public void testWe2ShouldBeWe2() {
        //given
        String input = "[we,2]";
        //when
        var result = testSubject.parse(input);
        //then
        Assertions.assertThat(result).isEqualTo(Pair.of(Direction.WEST, 2));
    }

    @Test
    public void testShouldThrowInvalidArgumentExceptionForMissingEnd() {
        //given
        String input = "[no,4";
        //when
        //then
        Assertions.assertThatThrownBy(() -> testSubject.parse(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testShouldThrowInvalidArgumentExceptionForMissingComma() {
        //given
        String input = "[no4]";
        //when
        //then
        Assertions.assertThatThrownBy(() -> testSubject.parse(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testShouldThrowInvalidArgumentExceptionForMissingDirection() {
        //given
        String input = "[,4]";
        //when
        //then
        Assertions.assertThatThrownBy(() -> testSubject.parse(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testShouldThrowInvalidArgumentExceptionForNegativeDistance() {
        //given
        String input = "[no,-4]";
        //when
        //then
        Assertions.assertThatThrownBy(() -> testSubject.parse(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
