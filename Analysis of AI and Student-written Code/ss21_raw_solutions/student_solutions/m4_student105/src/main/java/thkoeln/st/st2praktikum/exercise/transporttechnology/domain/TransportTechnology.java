package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.interfaces.Transporter;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class TransportTechnology extends AbstractEntity {
    @Transient
    private final List<Transporter> transporters = new ArrayList<>();
    private String technologyType;

    public TransportTechnology(String type) {
        this.technologyType = type;
    }

    public void addTransporter(Transporter transporter) {
        this.transporters.add(transporter);
    }

    public List<Transporter> getTransporters() {
        return this.transporters;
    }
}
