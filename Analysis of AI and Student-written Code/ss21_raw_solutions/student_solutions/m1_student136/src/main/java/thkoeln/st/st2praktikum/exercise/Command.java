package thkoeln.st.st2praktikum.exercise;

public enum Command
{
    NORTH     ("no"),
    WEST      ("we"),
    SOUTH     ("so"),
    EAST      ("ea"),
    TRANSPORT ("tr"),
    ENTER     ("en");

    private final String string;

    Command(String string)
    {
        this.string = string;
    }

    public String asString()
    {
        return string;
    }

    public static Command fromString(String string)
    {
        for (Command command : Command.values())
        {
            if (command.asString().equals(string))
            {
                return command;
            }
        }
        return null;
    }
}