package thkoeln.st.st2praktikum.exercise.converter;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringConverterTests {

    StringConverter convert = new StringConverter();
    String move = "[we,2]";
    String spawn = "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]";
    String transport = "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]";
    String coordinates = "(2,4)";
    String barrier = "(2,2)-(2,4)";

    @Test
    public void toIntArray(){
        int[] xy = {-1,-1};
        xy[0] = convert.pointStringToIntArray(coordinates)[0];
        xy[1] = convert.pointStringToIntArray(coordinates)[1];

        assertEquals(2, xy[0]);
        assertEquals(4, xy[1]);
    }

    @Test
    public void toUUID(){
        UUID deckID = convert.toUUID(spawn);
        UUID testID = UUID.fromString("4de4d85c-612c-4fa0-8e0f-7212f0711b40");

        assertEquals(testID, deckID);
    }

    @Test
    public void toUUIDAsString() {
        assertEquals("4de4d85c-612c-4fa0-8e0f-7212f0711b40", convert.toUUIDAsString(spawn));
    }

    @Test
    public void removeBracketsTest () {
        assertEquals("we,2", convert.removeBrackets(move));
        assertEquals("en,4de4d85c-612c-4fa0-8e0f-7212f0711b40", convert.removeBrackets(spawn));
        assertEquals("tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40", convert.removeBrackets(transport));
        assertEquals("2,4", convert.removeBrackets(coordinates));
    }

    @Test
    public void splitBarrierStringTest () {
        assertEquals("(2,2)", convert.splitBarrierString( barrier )[0] );
        assertEquals("(2,4)", convert.splitBarrierString( barrier )[1] );

    }
}
