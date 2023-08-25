package thkoeln.st.st2praktikum.exercise;

import java.util.regex.Pattern;

public class StringEncoder {
    String encodeable;
    public StringEncoder(String encodeable)
    {
        this.encodeable=encodeable;
        encoding();
    }


   private int x1,y1,x2,y2;

private void encoding()
{
    String[] parts=encodeable.split("-");
    String[] partsFromParts1=parts[0].split(Pattern.quote(","));
    String[] partsFromParts2=parts[1].split(Pattern.quote(","));
    x1= Integer.parseInt(partsFromParts1[0].replaceAll("\\D", ""));
    y1= Integer.parseInt(partsFromParts1[1].replaceAll("\\D", ""));
    x2= Integer.parseInt(partsFromParts2[0].replaceAll("\\D", ""));
    y2= Integer.parseInt(partsFromParts2[1].replaceAll("\\D", ""));

}
public int getX1(){return x1;}
public int getX2(){return x2;}
public int getY1(){return y1;}
public int getY2(){return y2;}

}
