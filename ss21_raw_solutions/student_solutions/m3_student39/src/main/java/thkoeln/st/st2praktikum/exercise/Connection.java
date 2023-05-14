package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Connection {


    private UUID sourceMap;
    private UUID destinationMap;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "sourceX")),
            @AttributeOverride(name = "y", column = @Column(name = "sourceY"))
    })
    protected Vector2D sourcePosition;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "destinationX")),
            @AttributeOverride(name = "y", column = @Column(name = "destinationY"))
    })
    protected Vector2D destinationPosition;
    @Id
    private UUID identifier;


    public Connection(UUID connectionId,UUID sourceMap, UUID destinationMap, Vector2D sourcePosition, Vector2D destinationPosition){
        identifier = connectionId;
        this.sourceMap = sourceMap;
        this.destinationMap = destinationMap;
        this.sourcePosition = sourcePosition;
        this.destinationPosition = destinationPosition;
    }

    public boolean checkSourcePosition(Vector2D vector2D) {
        return sourcePosition.equals(vector2D);
    }

    public boolean checkTargetMap(UUID destinationMap) {
        return destinationMap.equals(destinationMap);
    }
}
