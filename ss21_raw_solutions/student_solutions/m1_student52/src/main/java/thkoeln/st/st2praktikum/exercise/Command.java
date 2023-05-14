package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Command {
    @Getter private final Name name;
    @Getter private final String parameter;

    public Command(String cmd){
        var s = cmd.replace("[","").replace("]","").split(",");
        name = Name.valueOf(s[0]);
        parameter = s[1];
    }

    enum Name { no, ea, so, we, en, tr }
}
