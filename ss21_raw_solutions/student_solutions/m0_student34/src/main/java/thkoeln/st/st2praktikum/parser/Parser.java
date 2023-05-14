package thkoeln.st.st2praktikum.parser;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

@Component
public class Parser {

    private static Parser parser;

    private Pattern pattern;

    private Parser() {
        this.pattern = Pattern.compile("^\\[((no)|(ea)|(so)|(we)),\\d*\\]\\z$");
    }

    public static Parser getInstance() {
        if(Parser.parser == null) {
            Parser.parser = new Parser();
        }
        return Parser.parser;
    }

    public Pair<Direction, Integer> parse(String input) {
        if(!this.pattern.matcher(input).matches()) {
            throw new IllegalArgumentException();
        }
        var stringTokenizer = new StringTokenizer(input, "[],");
        var direction = Direction.of(stringTokenizer.nextToken());
        var distance = Integer.parseInt(stringTokenizer.nextToken());
        return Pair.of(direction, distance);
    }
}
