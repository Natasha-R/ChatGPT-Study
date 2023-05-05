package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.field.domain.Obstacle;
import thkoeln.st.st2praktikum.exercise.miningmachine.application.MiningMachineService;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
import java.util.HashMap;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class TransportTechnology {
    @Id
    private UUID id;
    private String technology;

    //@Autowired
    //private ConnectionRepository connectionRepository;

    @ElementCollection(targetClass = Connection.class, fetch = FetchType.EAGER)
    private List<Connection> connections = new ArrayList<>();

    //@OneToMany(mappedBy = "mCustomer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //private HashMap<UUID, Connection> connections = new HashMap<>();
    //List<Connection> connections = new ArrayList<>();

    public TransportTechnology(String technology, UUID id) {
        this.technology = technology;
        this.id = id;
    }
    public void setTechnology(String technology) {
        this.technology = technology;
    }
    public UUID addConnection(List<Field> fields,UUID sourceFieldId,
                              Point sourcePoint,
                              UUID destinationFieldId,
                              Point destinationPoint)
    {
        UUID uuid = UUID.randomUUID();
        CheckIfConnectionOutOfBounds(fields, sourceFieldId, sourcePoint, destinationFieldId, destinationPoint);

        connections.add(new Connection(sourceFieldId, sourcePoint, destinationFieldId, destinationPoint, uuid));
/*
        for(int i=0;i<connections.size();i++)
        {
            connectionRepository.save(connections.get(i));
        }
        */
        return uuid;
    }
    public void CheckIfConnectionOutOfBounds(List<Field> fields,UUID sourceFieldId, Point sourcePoint, UUID destinationFieldId,Point desetinationPoint)
    {
        if(sourcePoint.getX() >= fields.get(MiningMachineService.GetIndexWhereUUID(fields,sourceFieldId)).getSize().getX() || sourcePoint.getY() >= fields.get(MiningMachineService.GetIndexWhereUUID(fields,sourceFieldId)).getSize().getY())
        {
            throw new IllegalActionException("Connection has to be placed inside fieldbounds!");
        }
        if(desetinationPoint.getX() >= fields.get(MiningMachineService.GetIndexWhereUUID(fields,destinationFieldId)).getSize().getX() || desetinationPoint.getY() >= fields.get(MiningMachineService.GetIndexWhereUUID(fields,destinationFieldId)).getSize().getY())
        {
            throw new IllegalActionException("Connection has to be placed inside fieldbounds!");
        }
    }
}
