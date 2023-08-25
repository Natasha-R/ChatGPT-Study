package thkoeln.st.st2praktikum.exercise.entities;
import lombok.Getter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Getter
public class Command {
    public enum Cmd { no, ea, so, we, tr, en }

    @Getter
    private final Cmd cmd;
    private String uuid;
    private String steps;

    public Command(String commandString){
        String[] input = commandString.replace("[","").replace("]","").split(",");
        cmd  = Cmd.valueOf(input[0].toLowerCase());

        if (input[1].matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}"))
            uuid = input[1];
        else
            steps = input[1];

    }
}



