package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.room.application.RoomDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionDto {
    private RoomDto sourceRoom;
    private RoomDto destinationRoom;
    private Point sourcePoint;
    private Point destinationPoint;

}
