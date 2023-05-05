package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Connection{

    @Id
    private UUID id =UUID.randomUUID();
    @ManyToOne
    private Field sourceField;
    @Embedded
    private Point sourcePoint;
    @ManyToOne
    private Field destinationField;
    @Embedded
    private Point destinationPoint;
    @ManyToOne
    private TransportCategory transportCategory;

    public Connection(){}

    public Connection(Field sourceField, Point sourcePoint, Field destinationField, Point destinationPoint){
        this.sourceField = sourceField;
        this.sourcePoint = sourcePoint;
        this.destinationField = destinationField;
        this.destinationPoint = destinationPoint;
    }

    public Connection getConnection(){
        return this;
    }

}

