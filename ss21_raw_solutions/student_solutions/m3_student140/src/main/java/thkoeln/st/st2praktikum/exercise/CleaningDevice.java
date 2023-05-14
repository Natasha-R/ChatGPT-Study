package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.entity.Space;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CleaningDevice {
    @Id
    private UUID cleaningDeviceId;
    private String name;
    private Integer locationX;
    private Integer locationY;

    @ManyToOne
    @JoinColumn(name = "Space_ID")
    private Space space;

    public CleaningDevice(UUID cleaningDeviceId, String name) {
        this.cleaningDeviceId = cleaningDeviceId;
        this.name = name;
    }

    public Integer getLocationX() {
        return this.locationX;
    }

    public Integer getLocationY() {
        return this.locationY;
    }

    public UUID getCleaningDeviceId() {
        return this.cleaningDeviceId;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public void setLocationX(Integer locationX) {
        this.locationX = locationX;
    }

    public void setLocationY(Integer locationY) {
        this.locationY = locationY;
    }

    public Coordinate getCoordinate() { return new Coordinate(this.locationX,this.locationY); }

    public Space getSpace(){
        return this.space;
    }
    public UUID getSpaceId(){
        return this.space.getSpaceId();
    }

}
