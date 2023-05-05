package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransportCategoryDto {
    private List<ConnectionDto> connections;
    private String category;
}
