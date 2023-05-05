package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.stringConversion.Converter;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConverterTest {
    Converter convert = new Converter();
    String move = "[we,2]";
    String spawn = "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]";
    String traverse = "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]";
    String coordinates = "(2,4)";

    @Test
    public void toUUID(){
        UUID deckID = convert.toUUID(spawn);
        UUID testID = UUID.fromString("4de4d85c-612c-4fa0-8e0f-7212f0711b40");

        assertEquals(testID, deckID);
    }

    @Test
    public void toIntArray(){
        int[] xy = {-1,-1};
        xy[0] = convert.toIntArray(coordinates)[0];
        xy[1] = convert.toIntArray(coordinates)[1];

        assertEquals(2, xy[0]);
        assertEquals(4, xy[1]);
    }
}
