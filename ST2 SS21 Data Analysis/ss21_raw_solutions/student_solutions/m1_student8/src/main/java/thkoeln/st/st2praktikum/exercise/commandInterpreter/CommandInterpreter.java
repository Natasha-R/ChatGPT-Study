package thkoeln.st.st2praktikum.exercise.commandInterpreter;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CommandInterpreter
{

    private UUID roomID;

    private String steps;

    public CommandInterpreter()
    {

    }



    public ExecutionKey interpretCommand(String command)
    {
        String[] splitCommand = transformCommand(command);
        if (splitCommand[0].equals("en"))
        {
            this.roomID = UUID.fromString(splitCommand[1]);
            return ExecutionKey.EN;

        }else if (splitCommand[0].equals("tr"))
        {
            this.roomID = UUID.fromString(splitCommand[1]);
            return ExecutionKey.TR;

        }else if (splitCommand[0].equals("no"))
        {
            this.steps = splitCommand[1];
            return ExecutionKey.NO;
        }else if (splitCommand[0].equals("ea"))
        {
            this.steps = splitCommand[1];
            return ExecutionKey.EA;
        }else if (splitCommand[0].equals("so"))
        {

            this.steps = splitCommand[1];
            return ExecutionKey.SO;

        }else if (splitCommand[0].equals("we"))
        {
            this.steps = splitCommand[1];
            return ExecutionKey.WE;
        }

        return ExecutionKey.ERROR;
    }


    private String[] transformCommand(String command)
    {
        command = command.replace("[","").replace("]","");
        String[] splitCommand = command.split(",");
        return splitCommand;
    }



}
