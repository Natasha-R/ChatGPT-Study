package thkoeln.st.st2praktikum.exercise.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.exceptions.ParseException;

import javax.persistence.Embeddable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Coordinate {
    public int x;
    public int y;

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public static Coordinate fromString(String coordinateString)  throws ParseException{
        Pattern p = Pattern.compile("\\((\\d+),(\\d+)\\)");
        Matcher matcher = p.matcher(coordinateString);

        if (matcher.matches()) {
            int x1 = Integer.parseInt(matcher.group(1));
            int y1 = Integer.parseInt(matcher.group(2));
            return new Coordinate(x1, y1);
        }
        throw new ParseException("Could not parse Coordinate-String");
    }
}
