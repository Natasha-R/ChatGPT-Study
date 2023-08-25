package thkoeln.st.st2praktikum.exercise.domainprimitives;


import java.util.Arrays;

public class Validator {

    private Integer vectorX;
    private Integer vectorY;
    private Vector2D wallStart;
    private Vector2D wallEnd;
    private Vector2D wallStartTemp;
    private Vector2D wallEndTemp;
    private String taskString;
    private String taskParameter;

    public Validator(){}


    public boolean isVector(String toBeValidated)
    {
        if(!toBeValidated.contains(",") || toBeValidated.chars().filter(ch -> ch == ',').count() > 1
                || toBeValidated.matches("[a-zA-Z]+")
                || toBeValidated.charAt(0) != '('
                || toBeValidated.charAt(toBeValidated.length()-1) != ')')
        {
            return false;
        }

        String[] coordinates = toBeValidated.replace("(", "")
                .replace(")", "")
                .split(",");

        vectorX = Integer.parseInt(coordinates[0]);
        vectorY = Integer.parseInt(coordinates[1]);

        if(vectorX < 0 || vectorY < 0)
        {
            return false;
        }
        return true;
    }

    public boolean isHorizontalOrVertical(Vector2D wallStart, Vector2D wallEnd)
    {
        return (wallStart.getX().equals(wallEnd.getX()) && !wallStart.getY().equals(wallEnd.getY()))
                || (!wallStart.getX().equals(wallEnd.getX()) && wallStart.getY().equals(wallEnd.getY()));
    }

    public void orderWallVectors(Vector2D vector1, Vector2D vector2)
    {
        if(vector1.getX() + vector1.getY() < vector2.getX() + vector2.getY())
        {
            wallStart = vector1;
            wallEnd = vector2;
        }
        else
        {
            wallStart = vector2;
            wallEnd = vector1;
        }
    }

    public boolean isWallString(String toBeValidated)
    {
        if(!toBeValidated.contains("-") || toBeValidated.chars().filter(ch -> ch == '-').count() > 1
                || toBeValidated.matches("[a-zA-Z]+"))
        {
            return false;
        }

        String[] vectors = toBeValidated.split("-");

        if(isVector(vectors[0]))
        {
            wallStartTemp = new Vector2D(vectorX, vectorY);
        }
        else
        {
            return false;
        }

        if(isVector(vectors[1]))
        {
            wallEndTemp = new Vector2D(vectorX, vectorY);
        }
        else
        {
            return false;
        }

        if(isHorizontalOrVertical(wallStartTemp, wallEndTemp))
        {
            orderWallVectors(wallStartTemp, wallEndTemp);
            return true;
        }
        return false;
    }

    public boolean isCommandString(String toBeValidated)
    {
        if(!toBeValidated.contains(",") || toBeValidated.chars().filter(ch -> ch == ',').count() > 1
                || toBeValidated.charAt(0) != '['
                || toBeValidated.charAt(toBeValidated.length()-1) != ']')
        {
            return false;
        }

        String[] commandAndParameter = toBeValidated.replace("[", "")
                .replace("]", "")
                .toLowerCase()
                .split(",");

        if(commandAndParameter[0].equals("no")
                || commandAndParameter[0].equals("ea")
                || commandAndParameter[0].equals("so")
                || commandAndParameter[0].equals("we")
                || commandAndParameter[0].equals("tr")
                || commandAndParameter[0].equals("en"))
        {
            taskString = commandAndParameter[0];
            taskParameter = commandAndParameter[1];
            return true;
        }
        return false;
    }



    public Integer getVectorX()
    {
        return vectorX;
    }

    public Integer getVectorY()
    {
        return vectorY;
    }

    public Vector2D getWallStart()
    {
        return wallStart;
    }

    public Vector2D getWallEnd()
    {
        return wallEnd;
    }

    public String getTaskString()
    {
        return taskString;
    }

    public String getTaskParameter()
    {
        return taskParameter;
    }
}
