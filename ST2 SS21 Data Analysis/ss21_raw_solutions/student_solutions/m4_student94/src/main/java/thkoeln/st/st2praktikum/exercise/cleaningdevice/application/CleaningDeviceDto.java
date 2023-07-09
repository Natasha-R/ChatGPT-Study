package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CleaningDeviceDto {
    UUID cleaningDeviceId;
    String name;
    UUID spaceId;
    List<Order> orders=new ArrayList<>();
}
