package thkoeln.st.st2praktikum.exercise.miningmachine.domain;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;


/**
 * This DTO class might not be complete yet! Enhance it if necessary.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MiningMachineDto {


    private UUID miningmachineuuid;
    private UUID fieldid;
    private String name;
    private boolean executefailer = false;



    public UUID getFieldid() {
        return fieldid;
    }

    public boolean isExecutefailer() {
        return executefailer;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return miningmachineuuid;
    }
}
