package thkoeln.st.st2praktikum.parser;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

public class SerializerUnitTest {

    private MovementSerializer serializer = new MovementSerializer();

    @Test
    public void testSerializeMoveNorth() {
        //given
        var movement = new double[]{2, 4};
        //when
        var result = serializer.serialize(movement);
        //then
        Assertions.assertThat(result).isEqualTo("(2,4)");
    }
}
