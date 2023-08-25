package thkoeln.st.st2praktikum.exercise;

import java.util.regex.Pattern;

public class CommandEncoder {
    private String encodeable;
    public CommandEncoder(String encodeable)
    {
        this.encodeable=encodeable;

    }
    public String[] encoding(){
        StringBuilder stringBuilder=new StringBuilder(encodeable);
        stringBuilder.deleteCharAt(0);
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        String resultstring =stringBuilder.toString();
      return resultstring.split(Pattern.quote(","));

    }
}
