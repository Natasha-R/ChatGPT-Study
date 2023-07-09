package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Map;
import java.util.UUID;
//@Entity
@Setter
@Getter
public class Command {

 //   @Id
 //   private UUID id=UUID.randomUUID();
    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;
    private  UUID maintenanceDroidId;

    
    protected Command() { }

    public Command(CommandType commandType, Integer numberOfSteps) {
       if (numberOfSteps < 0)
            throw new RuntimeException();
        else {
           this.commandType = commandType;
           this.numberOfSteps = numberOfSteps;
        }
    }

    public Command(CommandType commandType, UUID gridId) {
        this.commandType = commandType;
        this.gridId = gridId;
    }

    public static Command fromString(String commandString ) {
        Command cmd=new Command();

        String[] paramDecomposition = commandString.split(",");
        Map<String, CommandType> deplacement = Map.of("no", CommandType.NORTH, "so", CommandType.SOUTH, "ea", CommandType.EAST, "we", CommandType.WEST);
        if (paramDecomposition.length == 2) {
            if (deplacement.containsKey(paramDecomposition[0].substring(1))) {
                cmd.setCommandType( deplacement.get(paramDecomposition[0].substring(1)) );
                cmd.setNumberOfSteps( Integer.valueOf(paramDecomposition[1].substring(0, 1)) );

            } else if (paramDecomposition[0].substring(1).equals("tr") || paramDecomposition[0].substring(1).equals("en")) {
                cmd.setGridId( UUID.fromString(paramDecomposition[1].substring(0, paramDecomposition[1].length() - 1)) );
                if (paramDecomposition[0].substring(1).equals("tr"))
                    cmd.setCommandType( CommandType.TRANSPORT );

                if (paramDecomposition[0].substring(1).equals("en"))
                    cmd.setCommandType( CommandType.ENTER );
            } else
                throw new RuntimeException();
        } else
            throw new RuntimeException();
        return cmd;
    }
    
    public CommandType getCommandType() {
        return commandType;
    }

    public Integer getNumberOfSteps() {
        return numberOfSteps;
    }

    public UUID getGridId() {
        return gridId;
    }

}
