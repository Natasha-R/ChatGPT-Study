package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.space.domain.Connection;

import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.transaction.TransactionScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Entity
public class CleaningDevice extends AbstractEntity {

    @Getter @Setter
    private String name;
    @Getter @Setter
    private UUID spaceId;
    @Getter @Setter
    private Coordinate coordinate;
    @Getter @Transient
    private List<Command> commands = new ArrayList<Command>();

    public CleaningDevice(String name){
        this.name = name;

    }

    public void addCommand(Command command){
        commands.add(command);
    }


}
