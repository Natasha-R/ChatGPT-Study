package thkoeln.st.st2praktikum.parser;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;

public class SerializerUnitTest {

    private Serializer serializer = new Serializer();

    @Test
    public void testSerializeMoveNorth() {
        //given
        var movement = Vector.of(2, 4);
        //when
        var result = serializer.serialize(movement);
        //then
        Assertions.assertThat(result).isEqualTo("(2,4)");
    }
}
