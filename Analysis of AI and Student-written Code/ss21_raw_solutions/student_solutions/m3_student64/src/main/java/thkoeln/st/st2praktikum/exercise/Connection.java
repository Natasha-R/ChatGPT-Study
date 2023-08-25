package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.Core.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Connection extends AbstractEntity {
    @ManyToOne
    private TransportCategory transportCategory;

    @ManyToOne
    private Space startSpace;
    private Point startPoint;

    @ManyToOne
    private Space endSpace;
    private Point endPoint;

    public Connection(TransportCategory transportCategory, Space startSpace, Point startPoint, Space endSpace, Point endPoint) {
        this.transportCategory = transportCategory;
        this.startSpace = startSpace;
        this.startPoint = startPoint;
        this.endSpace = endSpace;
        this.endPoint = endPoint;
    }
}
