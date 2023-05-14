package thkoeln.st.st2praktikum.StringParser;

import thkoeln.st.st2praktikum.StringParser.Exception.InvalidParsingString;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskParser implements IParser {
    private final String stringRegex = "\\[(no|ea|so|we|tr|en),(.*)\\]";
    private final Pattern stringPattern = Pattern.compile(this.stringRegex);

    @Override
    public MatchResult parse(String obstacleString) {
        Matcher patternMatcher = this.stringPattern.matcher(obstacleString);

        if(!patternMatcher.matches())
        {
            System.out.println(obstacleString);
            throw new InvalidParsingString();
        }

        return patternMatcher.toMatchResult();
    }
}
