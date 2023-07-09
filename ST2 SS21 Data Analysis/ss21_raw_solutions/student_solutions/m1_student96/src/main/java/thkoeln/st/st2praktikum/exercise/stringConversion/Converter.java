package thkoeln.st.st2praktikum.exercise.stringConversion;

import lombok.Data;
import thkoeln.st.st2praktikum.exercise.map.Coordinates;

import java.util.UUID;

@Data
public class Converter implements Convertable {

    @Override
    public String toCommand(String input) {
        String tmp = removeBrackets(input);
        return tmp.split(",")[0];
    }

    public String toUUIDAsString(String input) {
        input = removeBrackets(input);
        return input.split(",")[1];
    }

    public UUID toUUID(String input) {
        return UUID.fromString(toUUIDAsString(input));
    }

    public int toSteps(String input){
        input = removeBrackets(input);
        return Integer.parseInt(input.split(",")[1]);
    }

    public int[] toIntArray(String input){
        input = removeBrackets(input);
        int[] coordinates = new int[2];
        String x = input.replaceAll(",.*", "");
        coordinates[0] = Integer.parseInt(input.replaceAll(",.*", ""));         //https://stackoverflow.com/questions/27095087/coordinates-string-to-int
        coordinates[1] = Integer.parseInt(input.replaceAll(".*,", ""));
        return coordinates;
    }

    private String removeBrackets(String input) {
        input = input.replaceAll("\\[", "").replaceAll("]", "");
        input = input.replaceAll("\\(", "").replaceAll("\\)", "");
        return input;
    }
}

